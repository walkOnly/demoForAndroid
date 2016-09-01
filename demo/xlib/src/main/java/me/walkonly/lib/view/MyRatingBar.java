package me.walkonly.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RatingBar;

/**
 * 奇怪的RatingBar ...
 */
public class MyRatingBar extends RatingBar {

    public MyRatingBar(Context context) {
        super(context);
    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        //Log.e("AAA", "onMeasure(): MyRatingBar " + w + " " + h);
        h = w / 5 - 1; // 这里需要根据实际图片宽高调整
        setMeasuredDimension(w, h);
    }

}
