package com.hhxc.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.util.Tip;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private long mBackClickTime = 0;

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - mBackClickTime > 3000) {
            Tip.show("再按一次退出程序");
            mBackClickTime = now;
            return;
        } else {
            super.onBackPressed();
        }
    }

}
