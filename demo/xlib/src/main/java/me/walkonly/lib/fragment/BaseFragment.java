package me.walkonly.lib.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.annotation.FragmentAnnotationConfig;

public abstract class BaseFragment extends Fragment {

    private FragmentAnnotationConfig annotationConfig;
    private View rootView;
    private boolean doSomethingWhenBackPressed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        annotationConfig = FragmentAnnotationConfig.getConfig(this.getClass());
        if (annotationConfig != null) {
            if (annotationConfig.eventBus)
                EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (annotationConfig == null || annotationConfig.layoutId == 0)
            return null;

        rootView = inflater.inflate(annotationConfig.layoutId, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        if (annotationConfig != null) {
            if (annotationConfig.eventBus)
                EventBus.getDefault().unregister(this);
        }
    }

    public View getRootView() {
        return rootView;
    }

    public void startActivity(Class<? extends Activity> clazz) {
        ((BaseActivity) getActivity()).startActivity(clazz);
    }

    public void startActivity(Class<? extends Activity> clazz, Bundle bundle) {
        ((BaseActivity) getActivity()).startActivity(clazz, bundle);
    }

    public void startActivityAndClearTask(Class<? extends Activity> clazz) {
        ((BaseActivity) getActivity()).startActivityAndClearTask(clazz);
    }

    public void startActivityAndClearTask(Class<? extends Activity> clazz, Bundle bundle) {
        ((BaseActivity) getActivity()).startActivityAndClearTask(clazz, bundle);
    }

    public void startActivityAndClearTop(Class<? extends Activity> clazz) {
        ((BaseActivity) getActivity()).startActivityAndClearTop(clazz);
    }

    public void startActivityAndClearTop(Class<? extends Activity> clazz, Bundle bundle) {
        ((BaseActivity) getActivity()).startActivityAndClearTop(clazz, bundle);
    }

    public void onBackPressed() {

    }

    public boolean isDoSomethingWhenBackPressed() {
        return doSomethingWhenBackPressed;
    }

    public void setDoSomethingWhenBackPressed(boolean doSomethingWhenBackPressed) {
        this.doSomethingWhenBackPressed = doSomethingWhenBackPressed;
    }

    public static void showView(View v) {
        if (v != null)
            v.setVisibility(View.VISIBLE);
    }

    public static void goneView(View v) {
        if (v != null)
            v.setVisibility(View.GONE);
    }

}
