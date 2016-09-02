package me.walkonly.lib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import me.walkonly.xlib.R;

/**
 * 根据宽度算高度 或 根据高度算宽度
 */
public class MyImageView extends ImageView {

    private Context mContext;
    private int aspect_ratio_type; // 1:宽高比 2:高宽比
    private String aspect_ratio_value; // 这里用分数，不用小数。比如 360/480
    private int n1, n2;

    public MyImageView(Context context) {
        super(context);
        initView(null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        if (attrs == null)
            throw new IllegalArgumentException("attrs不能为null");

        mContext = getContext();

        final TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.MyImageView);

        aspect_ratio_type = a.getInteger(R.styleable.MyImageView_aspect_ratio_type, 0);
        aspect_ratio_value = a.getString(R.styleable.MyImageView_aspect_ratio_value);

        if (aspect_ratio_type == 0)
            throw new IllegalArgumentException("aspect_ratio_type值不对");

        if (TextUtils.isEmpty(aspect_ratio_value) || !aspect_ratio_value.contains("/"))
            throw new IllegalArgumentException("aspect_ratio_value值不对");
        String[] tmpArr = aspect_ratio_value.split("/");
        if (tmpArr.length != 2)
            throw new IllegalArgumentException("aspect_ratio_value值不对");
        n1 = Integer.valueOf(tmpArr[0]);
        n2 = Integer.valueOf(tmpArr[1]);
        if (n1 <= 0 || n2 <= 0)
            throw new IllegalArgumentException("aspect_ratio_value值不对");

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("AAA", "onMeasure(): " + MeasureSpec.getSize(widthMeasureSpec) + " " + MeasureSpec.getSize(heightMeasureSpec));

        if (aspect_ratio_type == 1) {
            int measuredWidth = measureWidth(widthMeasureSpec);
            int newHeight = measuredWidth * n2 / n1 + 1;
            Log.e("AAA", "onMeasure(): newHeight = " + newHeight);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);
        } else {
            int measuredHeight = measureHeight(heightMeasureSpec);
            int newWidth = measuredHeight * n2 / n1 + 1;
            Log.e("AAA", "onMeasure(): newWidth = " + newWidth);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
        }

        //setMeasuredDimension(newWidth, newHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY)
            return specSize;
        else
            throw new IllegalArgumentException("layout_width必须是确定值");
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY)
            return specSize;
        else
            throw new IllegalArgumentException("layout_height必须是确定值");
    }

}
