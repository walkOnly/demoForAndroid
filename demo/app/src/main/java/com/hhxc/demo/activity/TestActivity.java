package com.hhxc.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hhxc.demo.R;
import com.hhxc.demo.event.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        setContentView(R.layout.activity_test_1);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

//    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3,
//              R.id.btn_4, R.id.btn_5, R.id.btn_6,
//              R.id.btn_7, R.id.btn_8, R.id.btn_9})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                //
                break;
            case R.id.btn_2:
                //
                break;
            case R.id.btn_3:
                //
                break;
            case R.id.btn_4:
                break;
            case R.id.btn_5:
                break;
            case R.id.btn_6:
                break;
            case R.id.btn_7:
                break;
            case R.id.btn_8:
                break;
            case R.id.btn_9:
                break;
        }
    }

    @Subscribe
    public void onEvent(TestEvent event) {
        Log.e(TAG, "onEvent(): code = " + event.code);
    }

}
