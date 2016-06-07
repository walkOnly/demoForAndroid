package com.hhxc.demo.pay;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hhxc.demo.app.Config;
import com.hhxc.demo.app.Constant;
import com.hhxc.demo.http.Api;
import com.hhxc.demo.pay.alipay.AlipayTask;
import com.hhxc.demo.pay.wxpay.Wxpay;

import java.text.DecimalFormat;

import me.walkonly.lib.util.DialogUtils;
import me.walkonly.lib.util.Tip;
import me.walkonly.lib.util.Utils;

public class PayUtils {

    private PayUtils() {
    }

    /**
     * 调用微信支付
     *
     * @param  cent  金额，单位：分
     * @param  orderName  订单名称
     * @param  orderNumber  订单号
     * @param  extras  附加数据
     */
    public static void payWithWeChat(Activity activity, int cent, int buyType, String tempOrderId, String orderName, String orderNumber, String extras) {
        // extras 传 "" 会报告 "签名错误"
        if (TextUtils.isEmpty(extras))
            extras = "_extra";
        String notifyUrl = getNotifyUrl(buyType, Constant.PAY_WAY_WX);
        if (Utils.weChatInstalled(activity)) {
            Wxpay wxpay = new Wxpay(activity);
            wxpay.pay(orderName, cent + "", genOutTradeNo(buyType, tempOrderId, orderNumber), extras, notifyUrl);
        } else {
            Tip.show("你还没有安装微信");
        }
    }

    /**
     * 调用支付宝支付
     *
     * @param  cent  金额，单位：分
     * @param  orderName  订单名称
     * @param  orderNumber  订单号
     * @param  extras  附加数据
     */
    public static void payWithAlipay(Activity activity, int cent, int buyType, String tempOrderId, String orderName, String orderNumber, String extras) {
        // 支付宝不支持传附加数据
        if (TextUtils.isEmpty(extras))
            extras = "_extra";
        String notifyUrl = getNotifyUrl(buyType, Constant.PAY_WAY_ALI);
        String amount = PayUtils.fenToYuan(cent) + "";
        new AlipayTask(activity, orderName, amount, genOutTradeNo(buyType, tempOrderId, orderNumber), extras, notifyUrl).execute();
    }

    /**
     * 根据订单号生成提交给微信/支付宝支付后台的 out_trade_no(商户订单号)
     *
     * 生成的字符串-格式：临时订单号_正式订单号_随机数
     * （微信支付限制：商户订单号最多32个字符）
     */
    private static String genOutTradeNo(int buyType, String tempOrderId, String orderNumber) {
        // 末尾的 A 代表 Android客户端，测试用
        String str = tempOrderId + "_" + orderNumber + "_" + (Utils.randomNumber() + "").substring(0, 3) + "A"; // 附加了3位随机数字
        Log.e("AAA", "生成的订单号：" + str);
        return str;
    }

    // 获取后台回调地址
    private static String getNotifyUrl(int buyType, int payWay) {
        String notifyUrl;

        if (payWay == Constant.PAY_WAY_WX)
            notifyUrl = Api.PAY_NOTIFY_URL_WX;
        else
            notifyUrl = Api.PAY_NOTIFY_URL_ALI;

        return notifyUrl;
    }

    /**
     * @param yuan 金额 元
     * @return 金额 分
     */
    public static int yuanToFen(double yuan) {
        if (Config.DEBUG)
            return 1;
        if (yuan < 1.1)
            return 3; // test
        else
            return (int) (yuan * 100);
    }

    /**
     * @param fen 金额 分
     * @return 金额 元
     */
    public static double fenToYuan(int fen) {
        if (fen < 1) return 0;
        double amount = fen / 100.0;
        DecimalFormat df = new DecimalFormat("#.##");
        String formatAmount = df.format(amount);
        return Double.valueOf(formatAmount);
    }

    public static void onWxpayFailed(Context context, int errorCode) {
        switch (errorCode) {
            case -1:
                DialogUtils.showDialog(context, "测试版本", "当前版本为测试版本，无法发起微信支付");
                break;
            case -2:
                DialogUtils.showDialog(context, "支付取消", "您取消了支付");
                break;
            default:
                break;
        }
    }

}
