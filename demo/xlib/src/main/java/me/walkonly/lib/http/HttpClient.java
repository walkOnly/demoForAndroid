package me.walkonly.lib.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import me.walkonly.lib.Config;

public class HttpClient {
    private static final AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getInstance() {
        return client;
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        // 测试代码
        if (Config.DEBUG) {
            if (handler instanceof GsonResponseHandler) {
                ((GsonResponseHandler) handler).setUrl(url);
            }
        }

        Log.e("HttpClient", "url: " + url + " " + params.toString());
        addCommonParams(params);
        //Log.e("HttpClient", "加密后 " + params.toString());
        client.post(url, params, handler);
    }

    /**
     * 添加公共参数
     *
     * @param params
     */
    private static void addCommonParams(@NonNull RequestParams params) {
        Map<String, String> secretKey = new HashMap<>();
        if (!"".equals(params.toString())) {
            String strArr[] = params.toString().split("&");
            for (int i = 0; i < strArr.length; i++) {
                String[] tmpArr = strArr[i].split("=");
                if (tmpArr.length < 2) {
                    secretKey.put(tmpArr[0], "");
                } else {
                    secretKey.put(tmpArr[0], tmpArr[1]);
                }
            }
        }

        String time = System.currentTimeMillis() + ""; // 时间戳

//        params.put("sign", MD5Encryption.getSecretKey(secretKey, time));
//        params.put("time", time);
//        params.put("mark", Constants.PHONE_MARK);
    }

}
