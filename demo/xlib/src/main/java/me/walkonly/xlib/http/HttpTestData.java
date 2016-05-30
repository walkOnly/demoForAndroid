package me.walkonly.xlib.http;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import me.walkonly.xlib.util.ResUtils;

/**
 * 测试数据，对应的json文件为utf-8编码
 */
public final class HttpTestData {

    private static final String url_file_map[][] = {

            //{Api.HTTP_URL_API_BANNER, "banner.json"},
            //{Api.HTTP_URL_API_LOGIN, "login.json"},
            //{Api.HTTP_URL_API_COUPON_LIST, "coupon.json"},

            {"url", "json"}
    };

    private static final Map<String, String> map = new HashMap<>();

    static {
        for (int i = 0; i < url_file_map.length; i++)
            map.put(url_file_map[i][0], url_file_map[i][1]);
    }

    public static boolean isTest(String url) {
        if (!TextUtils.isEmpty(map.get(url)))
            return true;
        else
            return false;
    }

    public static String getTestData(String url) {
        if (TextUtils.isEmpty(url))
            throw new IllegalArgumentException("url is empty");

        String file = map.get(url);
        if (TextUtils.isEmpty(file))
            throw new IllegalArgumentException("file is empty");

        String testData = ResUtils.getFromAssetsUTF8(file);
        if (TextUtils.isEmpty(testData))
            throw new IllegalArgumentException("testData is empty");

        return testData;
    }

}
