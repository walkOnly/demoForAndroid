package com.hhxc.demo.pay.alipay;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.hhxc.demo.pay.Config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 支付宝支付工具类
 */
public class AlipayUtils {

    /**
     * 支付成功
     */
    public static final int PAY_RESULT_STATUS_SUC = 0;
    /**
     * 支付失败
     */
    public static final int PAY_RESULT_STATUS_FAIL = -1;
    /**
     * 支付结果确认中
     */
    public static final int PAY_RESULT_STATUS_WAITING = -2;
    /**
     * 用户中途取消
     */
    public static final int PAY_RESULT_STATUS_CANCEL = -3;
    /**
     * 网络连接出错
     */
    public static final int PAY_RESULT_STATUS_HTTP_ERROR = -4;

    /**
     * 订单支付成功
     */
    private static final String PAY_STATUS_SUC = "9000";

    /**
     * 正在处理中
     */
    private static final String PAY_STATUS_PROCESSING = "8000";

    /**
     * 订单支付失败
     */
    private static final String PAY_STATUS_FAIL = "4000";

    /**
     * 用户中途取消
     */
    private static final String PAY_STATUS_CANCEL = "6001";

    /**
     * 网络连接出错
     */
    private static final String PAY_STATUS_HTTP_ERROR = "6002";

    /**
     * 支付宝支付，未付款交易的超时时间为10分钟
     *
     * @param subject 商品名称
     * @param body    商品详情
     * @param price   商品金额
     * @return
     */
    public static int pay(Activity activity, String subject, String body, String price, String orderNo, String extras, String notifyUrl) {
        return doPay(activity, genOrderInfo(subject, body, price, notifyUrl, Config.PAY_TIMEOUT_ALI, null, orderNo, extras));
    }

    /**
     * 支付宝支付
     *
     * @param activity
     * @param orderInfo 订单信息，考虑安全因素，可能订单生成由用户服务器生成
     * @return
     */
    private static int doPay(Activity activity, String orderInfo) {
        Log.e("doPay", orderInfo);

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        // 构造 PayTask 对象
        PayTask alipay = new PayTask(activity);

        // 调用支付接口，获取支付结果
        PayResult payResult = new PayResult(alipay.pay(payInfo, true));

        if (TextUtils.equals(payResult.getResultStatus(), PAY_STATUS_SUC))
            return PAY_RESULT_STATUS_SUC;
        else if (TextUtils.equals(payResult.getResultStatus(), PAY_STATUS_PROCESSING))
            return PAY_RESULT_STATUS_WAITING;
        else if (TextUtils.equals(payResult.getResultStatus(), PAY_STATUS_FAIL))
            return PAY_RESULT_STATUS_FAIL;
        else if (TextUtils.equals(payResult.getResultStatus(), PAY_STATUS_CANCEL))
            return PAY_RESULT_STATUS_CANCEL;
        else if (TextUtils.equals(payResult.getResultStatus(), PAY_STATUS_HTTP_ERROR))
            return PAY_RESULT_STATUS_HTTP_ERROR;
        return PAY_RESULT_STATUS_HTTP_ERROR;
    }

    /**
     * @param subject   商品名称
     * @param body      商品详情
     * @param price     商品金额
     * @param notifyUrl 服务器异步通知页面路径
     * @param timeout   未付款交易的超时时间,默认30分钟，一旦超时，该笔交易就会自动被关闭。 取值范围：1m～15d。
     *                  m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
     *                  该参数数值不接受小数点，如1.5h，可转换为90m。
     * @param returnUrl 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
     * @return
     */
    private static String genOrderInfo(String subject, String body, String price, String notifyUrl, String timeout, String returnUrl, String orderNo, String extras) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Config.ZFB_PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Config.ZFB_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        if (!TextUtils.isEmpty(notifyUrl))
            // 服务器异步通知页面路径
            orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.doPay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。

        if (!TextUtils.isEmpty(timeout))
            orderInfo += "&it_b_pay=\"" + timeout + "\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        if (TextUtils.isEmpty(returnUrl))
            returnUrl = "http://m.alipay.com";

        if (!TextUtils.isEmpty(returnUrl))
            // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
            orderInfo += "&return_url=\"" + returnUrl + "\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private static String sign(String content) {
        return SignUtils.sign(content, Config.ZFB_RSA_PRIVATE);
    }

    /**
     * 获取签名方式
     */
    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public static String getErrorMsg(int result) {
        String ret = "";
        switch (result) {
            case PAY_RESULT_STATUS_SUC:
                ret = "支付成功";
                break;
            case PAY_RESULT_STATUS_FAIL:
                ret = "支付宝支付失败";
                break;
            case PAY_RESULT_STATUS_WAITING:
                ret = "正在确认支付结果 ...";
                break;
            case PAY_RESULT_STATUS_CANCEL:
                ret = "支付被取消";
                break;
            case PAY_RESULT_STATUS_HTTP_ERROR:
                ret = "联网失败，请检查网络是否可用";
                break;
            default:
                ret = "未知错误";
                break;
        }
        return ret;
    }

}
