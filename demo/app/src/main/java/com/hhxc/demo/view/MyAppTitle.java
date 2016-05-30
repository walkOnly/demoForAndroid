package com.hhxc.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhxc.demo.R;

/**
 * Created by cloud_wang on 16/4/5.
 * * <p/>
 * 自定义标题栏
 */
public class MyAppTitle extends LinearLayout {
    private OnLeftButtonClickListener mLeftButtonClickListener;
    private OnLeftButton2ClickListener mLeftButton2ClickListener;
    private OnRightButtonClickListener mRightButtonClickListener;
    private OnRightDividerButtonClickListener mRightDividerButtonClickListener;
    private MyViewHolder mViewHolder;
    private View viewAppTitle;

    public MyAppTitle(Context context) {
        super(context);

        init();
    }

    public MyAppTitle(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MyAppTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.my_widget_app_title, null);
        this.addView(viewAppTitle, layoutParams);

        mViewHolder = new MyViewHolder(this);
        mViewHolder.mLeftButtonWraperLine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftButtonClickListener != null) {
                    mLeftButtonClickListener.onLeftButtonClick(v);
                }
            }
        });

        mViewHolder.mLeftButton2WraperLine.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mLeftButton2ClickListener != null) {
                    mLeftButton2ClickListener.onLeftButton2Click(v);
                }
            }
        });

        mViewHolder.mRightButtonWrapperLine.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mRightButtonClickListener != null) {
                    mRightButtonClickListener.OnRightButtonClick(v);
                }
            }
        });

        mViewHolder.mRightDividerImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mRightDividerButtonClickListener != null) {
                    mRightDividerButtonClickListener.OnRightDividerButtonClick(v);
                }
            }
        });
    }

    /**
     * 设置背景颜色
     *
     * @param @param color
     * @return void
     * @throws
     */
    public void setAppBackground(int color) {
        viewAppTitle.setBackgroundColor(color);
    }

    public void setLeftButton(int leftButtonDrawbleId, String title) {
        if (leftButtonDrawbleId > 0) {
            mViewHolder.mLeftButtonImg.setImageResource(leftButtonDrawbleId);
        }
        if (!TextUtils.isEmpty(title)) {
            mViewHolder.mLeftTitleTv.setText(title);
        }
    }

    public void setRightButton(int rightButtonDrawbleId, String title) {
        if (rightButtonDrawbleId > 0) {
            mViewHolder.mRightButtonImg.setImageResource(rightButtonDrawbleId);
        }
        if (!TextUtils.isEmpty(title)) {
            mViewHolder.mRightButtonTv.setText(title);
        }
    }

    public void setLeftButton2(int leftButton2WrapperBackgroundDrawbleId, int leftButton2DrawbleId, String title) {
        if (leftButton2DrawbleId > 0) {
            mViewHolder.mLeftButton2Img.setImageResource(leftButton2DrawbleId);
        }
        if (!TextUtils.isEmpty(title)) {
            mViewHolder.mLeftTitle2Tv.setText(title);
        }
        if (leftButton2WrapperBackgroundDrawbleId > 0) {
            mViewHolder.mLeftButton2InnerWrapperLine.setBackgroundResource(leftButton2WrapperBackgroundDrawbleId);
        }
    }

    public void setRightButtonTitleOrImage(Boolean isOnlyText, String title, int imageDrawbleId) {
        if (isOnlyText) {
            mViewHolder.mRightButtonTv.setText(title);
            if (title.length() >= 6) {
                mViewHolder.mRightButtonTv.setTextSize(14);
            }
        } else {
            if (imageDrawbleId >= 0) {
                mViewHolder.mRightButtonTv.setCompoundDrawablesWithIntrinsicBounds(imageDrawbleId, 0, 0, 0);
            }
        }
    }

    public void setRightDividerButton(int rightDividerButtonDrawbleId) {
        if (rightDividerButtonDrawbleId > 0) {
            mViewHolder.mRightDividerImg.setImageResource(rightDividerButtonDrawbleId);
        }
    }

    public void setAppTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mViewHolder.mAppTitleTv.setText(title);
        }
    }

    public void setAppTitleColor(int color) {
        if (color != 0) {
            mViewHolder.mAppTitleTv.setTextColor(color);
        }
    }

    public void setAppTitleBackground(int resid) {
        if (resid != 0) {
            mViewHolder.mRootView.setBackgroundResource(resid);
        }
    }

    public void initViewsVisible(Boolean isLeftButtonVisile, Boolean isLeftButton2visible, Boolean isTitleVisible, Boolean isRightDividerVisible, Boolean isRightButtonVisilbe) {
        if (isLeftButtonVisile)
            mViewHolder.mLeftButtonWraperLine.setVisibility(View.VISIBLE);
        else
            mViewHolder.mLeftButtonWraperLine.setVisibility(View.GONE);

        if (isLeftButton2visible)
            mViewHolder.mLeftButton2WraperLine.setVisibility(View.VISIBLE);
        else
            mViewHolder.mLeftButton2WraperLine.setVisibility(View.GONE);

        if (isTitleVisible)
            mViewHolder.mAppTitleTv.setVisibility(View.VISIBLE);
        else
            mViewHolder.mAppTitleTv.setVisibility(View.GONE);

        if (isRightDividerVisible)
            mViewHolder.mRightDividerImg.setVisibility(View.VISIBLE);
        else
            mViewHolder.mRightDividerImg.setVisibility(View.GONE);

        if (isRightButtonVisilbe)
            mViewHolder.mRightButtonWrapperLine.setVisibility(View.VISIBLE);
        else
            mViewHolder.mRightButtonWrapperLine.setVisibility(View.GONE);
    }

    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listen) {
        mLeftButtonClickListener = listen;
    }

    public void setOnLeftButton2ClickListener(OnLeftButton2ClickListener listen) {
        mLeftButton2ClickListener = listen;
    }

    public void setOnRightButtonClickListener(OnRightButtonClickListener listen) {
        mRightButtonClickListener = listen;
    }

    public void setOnRightDividerButtonClickListener(OnRightDividerButtonClickListener listen) {
        mRightDividerButtonClickListener = listen;
    }

    public TextView getRightButton() {
        return mViewHolder.mRightButtonTv;
    }

    static class MyViewHolder {
        View mRootView;
        LinearLayout mLeftButtonWraperLine;
        ImageView mLeftButtonImg;
        TextView mLeftTitleTv;
        LinearLayout mLeftButton2WraperLine;
        LinearLayout mLeftButton2InnerWrapperLine;
        ImageView mLeftButton2Img;
        TextView mLeftTitle2Tv;
        TextView mAppTitleTv;
        ImageView mRightDividerImg;
        LinearLayout mRightButtonWrapperLine;
        TextView mRightButtonTv;
        ImageView mRightButtonImg;

        public MyViewHolder(View v) {
            mRootView = v.findViewById(R.id.myapptitle_rootview);
            mLeftButtonWraperLine = (LinearLayout) v.findViewById(R.id.myapptitle_leftbutton_wraper);
            mLeftButtonImg = (ImageView) v.findViewById(R.id.myapptitle_leftbutton_image);
            mRightButtonImg = (ImageView) v.findViewById(R.id.myapptitle_Rightbutton_image);
            mLeftTitleTv = (TextView) v.findViewById(R.id.myapptitle_LeftTitle);
            mLeftButton2WraperLine = (LinearLayout) v.findViewById(R.id.myapptitle_leftbutton2_wraper);
            mLeftButton2InnerWrapperLine = (LinearLayout) v.findViewById(R.id.myapptitle_leftbutton2_inner_wraper);
            mLeftButton2Img = (ImageView) v.findViewById(R.id.myapptitle_leftbutton2_image);
            mLeftTitle2Tv = (TextView) v.findViewById(R.id.myapptitle_leftbutton2_textview);
            mAppTitleTv = (TextView) v.findViewById(R.id.myapptitle_Title);
            mRightDividerImg = (ImageView) v.findViewById(R.id.myapptitle_Divider1_imageview);
            mRightButtonWrapperLine = (LinearLayout) v.findViewById(R.id.myapptitle_RightButtonWraper);
            mRightButtonTv = (TextView) v.findViewById(R.id.myapptitle_RightButton_textview);
        }
    }

    public static abstract interface OnLeftButtonClickListener {
        public abstract void onLeftButtonClick(View v);
    }

    public static abstract interface OnLeftButton2ClickListener {
        public abstract void onLeftButton2Click(View v);
    }

    public static abstract interface OnRightButtonClickListener {
        public abstract void OnRightButtonClick(View v);
    }

    public static abstract interface OnRightDividerButtonClickListener {
        public abstract void OnRightDividerButtonClick(View v);
    }
}
