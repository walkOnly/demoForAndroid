package me.walkonly.lib.view;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import me.walkonly.xlib.R;

public class MyRadioGroup {

    private static final int _KEY = R.id.default_title_bar;

    private RadioButton mViews[];
    private int mCheckedIndex;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public MyRadioGroup(RadioButton views[], int index, OnCheckedChangeListener l) {
        mViews = views;
        mCheckedIndex = index;
        mOnCheckedChangeListener = l;

        initItems(index);
    }

    private void initItems(int index) {
        for (int i = 0; i < mViews.length; i++) {
            RadioButton view = mViews[i];
            view.setTag(_KEY, Integer.valueOf(i));
            if (i == index) {
                view.setChecked(true);
            } else {
                view.setChecked(false);
            }
            view.setOnCheckedChangeListener(mListener);
        }
        mOnCheckedChangeListener.onCheckedChanged(MyRadioGroup.this, index);
    }

    private RadioButton.OnCheckedChangeListener mListener = new RadioButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean isChecked) {
            if (isChecked) {
                mViews[mCheckedIndex].setChecked(false);

                int index = (Integer) view.getTag(_KEY);
                mCheckedIndex = index;
                mOnCheckedChangeListener.onCheckedChanged(MyRadioGroup.this, index);
            }
        }
    };

    public int getCheckedIndex() {
        return mCheckedIndex;
    }

    public void setCheckedIndex(int index) {
        if (index != mCheckedIndex) {
            mViews[mCheckedIndex].setChecked(false);
            mViews[index].setChecked(true);
            mCheckedIndex = index;
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(MyRadioGroup radioGroup, int index);
    }

}
