package me.walkonly.lib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.Hashtable;
import java.util.Map;

public abstract class BaseApplication extends Application {

    private static Context mContext;
    private static Resources mResources;
    private static Activity mainActivity;

    /**
     * 在页面之间、线程之间传递数据
     */
    private static Map<String, Object> mData = new Hashtable<>();

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mResources = getResources();

        LogLevel logLevel;
        if (Config.DEBUG)
            logLevel = LogLevel.FULL;
        else
            logLevel = LogLevel.NONE;
        Logger.init().methodCount(1).logLevel(logLevel);

        // 初始化hawk：不加密模式，使用SharedPref保存
        Hawk.init(mContext)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .build();
    }

    public static Context context() {
        return mContext;
    }

    public static Resources resources() {
        return mResources;
    }

    public static Activity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(Activity mainActivity) {
        BaseApplication.mainActivity = mainActivity;
    }

    public static Object getData(String key) {
        return mData.get(key);
    }

    public static void setData(String key, Object value) {
        mData.put(key, value);
    }

    public static void clearData() {
        mData.clear();
    }

}
