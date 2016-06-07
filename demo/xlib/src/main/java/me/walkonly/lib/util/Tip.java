package me.walkonly.lib.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import me.walkonly.lib.BaseApplication;

public class Tip {

    private Tip() {
    }

    public static void show(String text) {
        Toast.makeText(BaseApplication.context(), text, Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int strId) {
        show(ResUtils.getString(strId));
    }

    public static void showLong(String text) {
        Toast.makeText(BaseApplication.context(), text, Toast.LENGTH_LONG).show();
    }

    public static void showLong(@StringRes int strId) {
        showLong(ResUtils.getString(strId));
    }

}
