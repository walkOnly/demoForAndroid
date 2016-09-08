package me.walkonly.lib.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 与 TabFragment 搭配使用
 */
public class MyFragmentTabHost extends MyTabHost {

    public MyFragmentTabHost(AppCompatActivity activity, Fragment fragments[], View views[], int index, OnTabChangeListener l) {
        super(views, index, new MyListener(activity, fragments, l));
    }

    public interface OnTabChangeListener {
        /**
         * oldIndex 第一次是 -1
         */
        void onTabChanged(int oldIndex, int newIndex);
    }

    private static final class MyListener implements MyTabHost.OnTabChangeListener {

        private AppCompatActivity activity;
        private Fragment fragments[];
        private OnTabChangeListener listener;

        public MyListener(AppCompatActivity activity, Fragment fragments[], OnTabChangeListener l) {
            this.activity = activity;
            this.fragments = fragments;
            this.listener = l;

            initFragments(activity, fragments);
        }

        @Override
        public void onTabChanged(int oldIndex, int newIndex) {
            doTabChanged(oldIndex, newIndex);
            if (listener != null)
                listener.onTabChanged(oldIndex, newIndex);
        }

        private void doTabChanged(int oldIndex, int newIndex) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (oldIndex >= 0)
                fragmentTransaction.hide(fragments[oldIndex]);
            fragmentTransaction.show(fragments[newIndex]);
            fragmentTransaction.commitAllowingStateLoss(); // commit()
        }

        private void initFragments(AppCompatActivity activity, Fragment fragments[]) {
            if (fragments == null || fragments.length <= 1)
                throw new IllegalArgumentException("fragments[]数据不对");

            FragmentManager fragmentManager = activity.getSupportFragmentManager();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (int i = 0; i < fragments.length; i++) {
                Fragment f = fragments[i];
                ft.add(f, i + "");
                ft.hide(f);
            }
            ft.commitAllowingStateLoss(); // commit()
        }

    }

}
