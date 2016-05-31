package com.hhxc.demo.activity;

import android.os.Bundle;
import android.os.Handler;

import me.walkonly.lib.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(TestActivity.class);
            }

        }, 3000);
    }

}
