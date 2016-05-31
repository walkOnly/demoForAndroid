package me.walkonly.xlib;

import android.app.Application;
import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        LogLevel logLevel;
        if (Config.DEBUG)
            logLevel = LogLevel.FULL;
        else
            logLevel = LogLevel.NONE;
        Logger.init().methodCount(1).logLevel(logLevel);

        // 初始化hawk：不加密模式，使用SharedPref保存
        Hawk.init(context)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .build();
    }

    public static Context getContext() {
        return context;
    }

}
