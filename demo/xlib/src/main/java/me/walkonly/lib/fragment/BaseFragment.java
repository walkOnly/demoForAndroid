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
import me.walkonly.lib.annotation.FragmentAnnotationConfig;

public abstract class BaseFragment extends Fragment {

    private FragmentAnnotationConfig annotationConfig;

    private View rootView;

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
        startActivity(clazz, null);
    }

    public void startActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

}
