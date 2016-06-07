package com.hhxc.demo.pay.wxpay;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.hhxc.demo.pay.Config;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import me.walkonly.lib.http.HttpClient;
import me.walkonly.lib.util.DialogUtils;

public class Wxpay {

    private static final String TAG = "wxpay";

    // 微信支付统一下单接口地址
    public static final String GET_PREPARE_ID = "https://api.mch.weixin.qq.com/doPay/unifiedorder";

    private static final DateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private Context context;
    private IWXAPI wxapi;
    private long timestamp; // 发起交易的时间戳，单位：秒

    public Wxpay(Context context) {
        wxapi = WXAPIFactory.createWXAPI(context, null);
        wxapi.registerApp(Config.WX_APP_ID);
        this.context = context;
    }

    public void pay(String text, String price, String order, String extras, String notifyUrl) {
        timestamp = genTimestamp();
        String entity = genProductArgs(text, price, order, extras, notifyUrl);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (stringEntity == null) {
            Logger.e("Wxpay API String entity is null");
            return;
        }

        DialogUtils.showProgressDialog(context, "正在发起微信支付...");
        HttpClient.getInstance().post(context, GET_PREPARE_ID, stringEntity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Logger.d("Response is " + response);
                Map<String, String> xmlMap = decodeXml(response);
                String prepayId = xmlMap.get("prepay_id");
                doPay(prepayId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                DialogUtils.dismissProgressDialog();
                String response = responseBody != null ? new String(responseBody) : "";
                Logger.e(response + error.getMessage());
            }
        });
    }

    private void doPay(String prepayId) {
        PayReq req = new PayReq();
        req.appId = Config.WX_APP_ID;
        req.partnerId = Config.WX_MCH_ID;
        req.prepayId = prepayId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = timestamp + "";//String.valueOf(genTimestamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();

        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        wxapi.sendReq(req);

        DialogUtils.dismissProgressDialog();
    }

    private String genProductArgs(String text, String price, String order, String extras, String notifyUrl) {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();

            // 签名算法: https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
            // 1. 参数名ASCII码从小到大排序（字典序）
            // 2. 如果参数的值为空不参与签名
            // 3. 参数名区分大小写
            // ...
            packageParams.add(new BasicNameValuePair("appid", Config.WX_APP_ID));
            packageParams.add(new BasicNameValuePair("attach", extras));
            packageParams.add(new BasicNameValuePair("body", text));
            packageParams.add(new BasicNameValuePair("mch_id", Config.WX_MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));
            packageParams.add(new BasicNameValuePair("out_trade_no", order));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("time_expire", genTimeExpire()));
            //packageParams.add(new BasicNameValuePair("time_start", genTimeStart()));
            packageParams.add(new BasicNameValuePair("total_fee", price));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);

            return new String(xmlstring.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            return null;
        }

    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genTimeStart() {
        Date date = new Date(timestamp * 1000);
        String str = sDateFormat.format(date);
        Log.e("AAA", "genTimeStart(): " + str);
        return str;
    }

    private String genTimeExpire() {
        Date date = new Date((timestamp + Config.PAY_TIMEOUT_WX) * 1000);
        String str = sDateFormat.format(date);
        Log.e("AAA", "genTimeExpire(): " + str);
        return str;
    }

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.WX_API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();

        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.WX_API_KEY);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e(TAG, sb.toString());
        return sb.toString();
    }

}
