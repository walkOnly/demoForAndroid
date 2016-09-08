package com.hhxc.demo.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.hhxc.demo.R;

import java.util.ArrayList;
import java.util.List;

import me.walkonly.lib.util.Utils;

public class MySliderLayout extends FrameLayout {

    private Context context;
    private ImageView defaultBanner;
    private SliderLayout sliderLayout;
    private OnSliderClickListener mOnSliderClickListener;

    private int mImgResId;
    private List<String> mImgUrls = new ArrayList<>();

    public MySliderLayout(Context context) {
        super(context);
        initView();
    }

    public MySliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySliderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        context = getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.my_slider_layout, this, true);
        sliderLayout = (SliderLayout) view.findViewById(R.id.slider_layout);
        defaultBanner = (ImageView) view.findViewById(R.id.default_banner);

        // banner属性设置
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        //sliderLayout.setCustomAnimation(new DescriptionAnimation());
        //sliderLayout.addOnPageChangeListener(this);
    }

    public void setDefaultImage(int resId) {
        mImgResId = resId;
    }

    public void addSliders(List<String> imgUrls){
        if (mOnSliderClickListener == null)
            throw new RuntimeException("需要先调用 setOnSliderClickListener() 设置监听器");

        mImgUrls.addAll(imgUrls);

        for (int i = 0; i < imgUrls.size(); i++) {
            BaseSliderView view = makeSliderView(i, imgUrls.get(i));
            sliderLayout.addSlider(view);
        }
    }

    public void removeAllSliders() {
        mImgUrls.clear();
        sliderLayout.removeAllSliders();
    }

    public void setOnSliderClickListener(OnSliderClickListener l){
        mOnSliderClickListener = l;
    }

    public void startAutoCycle(long delay, long duration) {
        if (mImgUrls.size() == 0) {
            if (mImgResId == 0)
                throw new RuntimeException("未设置默认图");

            defaultBanner.setVisibility(View.VISIBLE);
            defaultBanner.setImageResource(mImgResId);
            defaultBanner.setOnClickListener(null);

            sliderLayout.setVisibility(View.GONE);
            sliderLayout.stopAutoCycle();

            return;
        }

        /**
         * 只有一个banner图时，UI规定不能左右滑动，因此使用ImageView代替
         */
        if (mImgUrls.size() == 1) {
            final String url = mImgUrls.get(0);

            defaultBanner.setVisibility(View.VISIBLE);
            Utils.loadImageByUrl(context, defaultBanner, url, mImgResId);
            defaultBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSliderClickListener != null)
                        mOnSliderClickListener.onSliderClick(makeSliderView(0, url));
                }
            });

            sliderLayout.setVisibility(View.GONE);
            sliderLayout.stopAutoCycle();

            return;
        }

        defaultBanner.setVisibility(View.GONE);
        sliderLayout.setVisibility(View.VISIBLE);
        sliderLayout.startAutoCycle(delay, duration, true);
    }

    public void stopAutoCycle(){
        if (mImgUrls.size() > 1)
            sliderLayout.stopAutoCycle();
    }

    private BaseSliderView makeSliderView(int index, String url) {
        TextSliderView textSliderView = new TextSliderView(context);
        textSliderView.description("").
                image(url).
                setScaleType(BaseSliderView.ScaleType.Fit).
                setOnSliderClickListener(mOnSliderClickListener);

        // 可以增加额外传值
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        textSliderView.bundle(bundle);

        return textSliderView;
    }

}
