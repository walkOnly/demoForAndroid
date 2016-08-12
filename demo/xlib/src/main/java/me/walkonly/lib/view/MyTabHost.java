package me.walkonly.lib.view;

import android.view.View;

import me.walkonly.xlib.R;

public class MyTabHost {

    private static final int _KEY = R.id.default_title_bar;

    private View mViews[];
    private int mCurrentTab;
    private OnTabChangeListener mOnTabChangeListener;

    public MyTabHost(View views[], int index, OnTabChangeListener l) {
        mViews = views;
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

        mOnTabChangeListener.onTabChanged(index);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag(_KEY);

            if (index == mCurrentTab) return;
            mCurrentTab = index;
            mOnTabChangeListener.onTabChanged(index);
        }
    };

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public void setCurrentTab(int index) {
        if (index == mCurrentTab) return;
        mCurrentTab = index;
        mOnTabChangeListener.onTabChanged(index);
    }

    public interface OnTabChangeListener {
        void onTabChanged(int index);
    }

}
