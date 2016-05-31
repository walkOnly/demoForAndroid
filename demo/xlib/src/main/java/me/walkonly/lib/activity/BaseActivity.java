package me.walkonly.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.walkonly.lib.annotation.ActivityAnnotationConfig;
import me.walkonly.lib.http.GsonResponseHandler;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityAnnotationConfig annotationConfig;

    private List<GsonResponseHandler> httpResponseHandler = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        annotationConfig = ActivityAnnotationConfig.getConfig(this.getClass());
        if (annotationConfig != null) {
            if (annotationConfig.layoutId > 0)
                setContentView(annotationConfig.layoutId);
            if (annotationConfig.eventBus)
                EventBus.getDefault().register(this);
        }

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 友盟统计
    }

    @Override
    protected void onDestroy() {
        for (GsonResponseHandler handler : httpResponseHandler) {
            handler.cancel();
        }
        httpResponseHandler.clear();

        if (annotationConfig != null) {
            if (annotationConfig.eventBus)
                EventBus.getDefault().unregister(this);
        }

        ButterKnife.unbind(this);

        super.onDestroy();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addHttpResponseHandler(GsonResponseHandler handler) {
        httpResponseHandler.add(handler);
    }

}
