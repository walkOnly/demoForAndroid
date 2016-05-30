package me.walkonly.xlib.util;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import me.walkonly.xlib.BaseApplication;

public class ResUtils {

    private static final Resources resources = BaseApplication.getContext().getResources();

    private ResUtils() {
    }

    /**
     * 根据字符串id获取字符串
     */
    public static String getString(@StringRes int id) throws Resources.NotFoundException {
        return resources.getString(id);
    }

    public static int getColor(@ColorRes int id) throws Resources.NotFoundException {
        return resources.getColor(id);
    }

    /**
     * 从raw文件中读取数据
     */
    public static String getFromRaw(@RawRes int resId) {
        try {
            InputStreamReader inputReader = new InputStreamReader(resources.openRawResource(resId));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 从assets文件中读取数据
     */
    public static String getFromAssets(String filename) {
        try {
            InputStreamReader inputReader = new InputStreamReader(resources.getAssets().open(filename));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 从assets文件中读取数据，文件编码为UTF-8
     */
    public static String getFromAssetsUTF8(String filename) {
        try {
            InputStreamReader inputReader = new InputStreamReader
                    (resources.getAssets().open(filename), Charset.forName("UTF-8"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
