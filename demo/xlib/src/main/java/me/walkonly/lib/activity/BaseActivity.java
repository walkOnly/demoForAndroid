package me.walkonly.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.walkonly.lib.annotation.ActivityAnnotationConfig;
import me.walkonly.lib.http.GsonResponseHandler;
import me.walkonly.xlib.R;

// 框架实现细节：基类 + 注解

public abstract class BaseActivity extends AppCompatActivity {

    private List<GsonResponseHandler> httpResponseHandler = new ArrayList<>();

    private ActivityAnnotationConfig annotationConfig;

    private View defaultTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        annotationConfig = ActivityAnnotationConfig.getConfig(this.getClass());
        if (annotationConfig != null) {
            setContentView(annotationConfig.layoutId, annotationConfig.titleId);
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

    public View getTitleBar() {
        return defaultTitleBar;
    }

    private void setContentView(int layoutId, int titleId) {
        if (layoutId == 0)
            throw new IllegalArgumentException("layoutId == 0");

        if (titleId == 0) {
            setContentView(layoutId);
            return;
        } else {
            LayoutInflater layoutInflater = getLayoutInflater();
            LinearLayout rootView = (LinearLayout) layoutInflater.inflate(R.layout.activity_base, null);
            defaultTitleBar = rootView.findViewById(R.id.default_title_bar);

            View clientView = layoutInflater.inflate(layoutId, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1); // MATCH_PARENT
            rootView.addView(clientView, params);

            setContentView(rootView);
        }
    }

}
