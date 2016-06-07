package com.hhxc.demo.http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import me.walkonly.lib.http.HttpClient;

public class Api {

    public static final String HTTP_URL_BASE = "http://open.hohoxc.com/";

    public static final String HTTP_URL_FAQ = "http://open.hohoxc.com/doc/common_problem.html"; // 常见问题

    /************************************************************************************
     * 支付相关
     ***********************************************************************************/

    // 微信支付 回调后台接口
    public static final String PAY_NOTIFY_URL_WX = "http://open.hohoxc.com/reserve/wxpay";

    // 支付宝支付 回调后台接口
    public static final String PAY_NOTIFY_URL_ALI = "http://open.hohoxc.com/reserve/zfbaopay";

    /*********************************** 接口开始 ****************************************/

    /**
     * 更新sid
     */
    public static String HTTP_URL_POST_UPDATE_SID = HTTP_URL_BASE
            + "flushsid";

    /**
     * 登陆
     */
    public static String HTTP_URL_POST_LOGIN = HTTP_URL_BASE
            + "login";
    /**
     * 发送短信验证
     */
    public static String HTTP_URL_POST_SENDSMSVERIFY = HTTP_URL_BASE
            + "sendverifycode";

    /**
     * 首页Banner
     */
    public static String HTTP_URL_POST_BANNER = HTTP_URL_BASE
            + "banners";

    /**
     * 城市列表
     */
    public static String HTTP_URL_POST_CITYLIST = HTTP_URL_BASE
            + "citys";

    /*********************************** http请求Method ****************************************/

    /**
     * 更新sid
     *
     * @param handler bean:LoginBean
     */
    public static void updateSid(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        appendSID(params);
        HttpClient.post(HTTP_URL_POST_UPDATE_SID, params, handler);
    }

    /**
     * 登录
     *
     * @param handler bean:LoginBean
     */
    public static void doLogin(String phone, String verifyCode, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", Long.parseLong(phone));
        params.put("verifyCode", verifyCode);
        HttpClient.post(HTTP_URL_POST_LOGIN, params, handler);
    }

    /**
     * 发送短信验证
     * bean:BaseResponse
     */
    public static void getVerifyCode(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.add("phone", phone);
        HttpClient.post(HTTP_URL_POST_SENDSMSVERIFY, params, handler);
    }

    /**
     * 城市列表
     *
     * @param handler
     */
    public static void getCityList(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        appendSID(params);
        HttpClient.post(HTTP_URL_POST_CITYLIST, params, handler);
    }

    /**
     * 首页Banner
     *
     * @param handler
     */
    public static void getBanner(String cityId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("cityId", cityId);
        HttpClient.post(HTTP_URL_POST_BANNER, params, handler);
    }

    private static void appendSID(RequestParams params) {
//        String sid = LocalStorage.getSID();
//        if (TextUtils.isEmpty(sid)) sid = "_SID_";
//        params.put("sid", sid);
    }

    private static void checkParams(boolean exp) {
        if (!exp)
            throw new IllegalArgumentException("checkParams failed.");
    }

}
