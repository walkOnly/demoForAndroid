package me.walkonly.lib.view;

import android.view.View;

import me.walkonly.xlib.R;

public class MyTabHost {

    private static final int _KEY = R.id.default_title_bar;

    private View mViews[];
    private int mLastTab;
    private int mCurrentTab;
    private OnTabChangeListener mOnTabChangeListener;

    public MyTabHost(View views[], int index, OnTabChangeListener l) {
        mViews = views;
        mLastTab = -1;
        mCurrentTab = index;
        mOnTabChangeListener = l;

        initItems(index);
    }

    private void initItems(int index) {
        for (int i = 0; i < mViews.length; i++) {
            View view = mViews[i];
            view.setTag(_KEY, Integer.valueOf(i));
            view.setOnClickListener(mListener);
        }

        mOnTabChangeListener.onTabChanged(mLastTab, mCurrentTab);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag(_KEY);

            if (index == mCurrentTab) return;
            mLastTab = mCurrentTab;
            mCurrentTab = index;
            mOnTabChangeListener.onTabChanged(mLastTab, mCurrentTab);
        }
    };

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public void setCurrentTab(int index) {
        if (index == mCurrentTab) return;
        mLastTab = mCurrentTab;
        mCurrentTab = index;
        mOnTabChangeListener.onTabChanged(mLastTab, mCurrentTab);
    }

    public interface OnTabChangeListener {
        /**
         * oldIndex 第一次是 -1
         */
        void onTabChanged(int oldIndex, int newIndex);
    }

}
