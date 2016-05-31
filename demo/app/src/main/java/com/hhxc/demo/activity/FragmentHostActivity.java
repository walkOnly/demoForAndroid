package com.hhxc.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.hhxc.demo.R;
import com.hhxc.demo.view.MyAppTitle;

import butterknife.Bind;
import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.util.Utils;
import me.walkonly.lib.view.IProgressView;

public class FragmentHostActivity extends BaseActivity implements IProgressView {
    private static final String TAG = "FragmentHostActivity";

    public static final String ACTIVITY_INTENT_FLAG = "activity_intent_flag";

    private static final String FRAGMENT_CLASS_NAME = "fragment_class_name";
    private static final String NEED_SAVE_DATA = "need_save_data";

    private Fragment fragment;
    private Bundle needSaveData;

    @Bind(R.id.app_title)
    MyAppTitle myAppTitle;
    @Bind(R.id.progress_view)
    View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle;
        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();
        } else {
            bundle = savedInstanceState.getBundle(NEED_SAVE_DATA);
        }
        needSaveData = bundle;

        Utils.initProgressView(progressView);

        String fragmentClassName = bundle.getString(FRAGMENT_CLASS_NAME);
        try {
            fragment = (Fragment) Class.forName(fragmentClassName).newInstance();
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Log.e(TAG, "onSaveInstanceState()");
        outState.putBundle(NEED_SAVE_DATA, needSaveData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(): requestCode = " + requestCode);
    }

    public static void startFragment(Context context, Class<? extends Fragment> clazz) {
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_CLASS_NAME, clazz.getName());
        startFragment(context, bundle);
    }

    public static void startFragment(Context context, Class<? extends Fragment> clazz, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle must not be null");
        }
        bundle.putString(FRAGMENT_CLASS_NAME, clazz.getName());
        startFragment(context, bundle);
    }

    private static void startFragment(Context context, Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException("Bundle is null");
        }

        String name = bundle.getString(FRAGMENT_CLASS_NAME, null);
        if (name == null) {
            throw new IllegalArgumentException("Fragment name is null");
        }

        int intentFlag = bundle.getInt(ACTIVITY_INTENT_FLAG, 0);

        Intent intent = new Intent(context, FragmentHostActivity.class);
        intent.putExtras(bundle);
        if (intentFlag > 0)
            intent.setFlags(intentFlag);
        context.startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {
        replaceFragment(R.id.container, fragment, false, "");
    }

    private void replaceFragment(@IdRes int viewId, Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment == null || fragment == fragmentManager.findFragmentById(viewId))
            return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(viewId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public MyAppTitle getAppTitle() {
        return myAppTitle;
    }

    @Override
    public View getProgressView() {
        return progressView;
    }

}
