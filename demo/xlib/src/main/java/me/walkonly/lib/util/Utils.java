package me.walkonly.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.view.ProgressViewHolder;

public class Utils {

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private Utils() {
    }

    public static void postDelayed(Runnable r, long delayTime) {
        sHandler.postDelayed(r, delayTime);
    }

    public static void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }

    public static long randomNumber() {
        long current = System.currentTimeMillis();
        double r = Math.random() + 0.001;
        return (long) (current * r);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断activity是否已销毁
     */
    public static boolean isActivityDestroyed(Activity activity) {
        return activity.isFinishing() || ((BaseActivity) activity).isActivityDestroyed();
    }

    public static void setTextColor(TextView tv, int r, int g, int b) {
        int color = Color.rgb(r, g, b);
        tv.setTextColor(color);
    }

    public static void setHZBold(TextView tv) {
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
    }

    public static String genHttpTag(Object obj) {
        return obj.getClass().getSimpleName() + "_" + randomNumber();
    }

    // timestamp 单位：秒
    public static Date getDateByPhpTimestamp(String timestamp) {
        return new Date(Long.valueOf(timestamp) * 1000);
    }

    public static void setMoney(TextView textView, int money) {
        textView.setText("¥" + money);
    }

    public static void setMoney(TextView textView, double money) {
        textView.setText("¥" + money);
    }

    public static void setMoney(TextView textView, String money) {
        textView.setText("¥" + money);
    }

    public static boolean weChatInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String packageName = installedPackages.get(i).packageName;
                if (packageName.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    // payMethod: 1 支付宝 2 微信
    public static int getPayMethod(int payWay) {
        int payMethod = 0;
//        if (payWay == Constants.PAY_WAY_WX)
//            payMethod = 2;
//        else if (payWay == Constants.PAY_WAY_ALI)
//            payMethod = 1;
//        else
//            throw new IllegalArgumentException("payWay 参数不对");

        return payMethod;
    }

    public static void loadImageByUrl(Context context, ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url))
            Picasso.with(context).load(url).into(imageView);
    }

    public static void loadImageByUrl(Context context, ImageView imageView, String url, int placeholderResId) {
        if (!TextUtils.isEmpty(url))
            Picasso.with(context).load(url).placeholder(placeholderResId).into(imageView);
    }

    public static void initProgressView(View progressView) {
        ProgressViewHolder vh = new ProgressViewHolder();
//        vh.progressBar = (ProgressBar) progressView.findViewById(R.id.progress_bar);
//        vh.emptyView = progressView.findViewById(R.id.empty_view);
//        vh.failView = progressView.findViewById(R.id.fail_view);

        progressView.setTag(vh);

        // 拦截点击事件
        progressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AAA", "onClick()");
            }
        });
    }

    public static void showProgressBar(View progressView) {
        Log.e("AAA", "showProgressBar()");
        ProgressViewHolder vh = (ProgressViewHolder) progressView.getTag();

        progressView.setVisibility(View.VISIBLE);
        vh.failView.setVisibility(View.GONE);
        vh.emptyView.setVisibility(View.GONE);
        vh.progressBar.setVisibility(View.VISIBLE);
    }

    public static void dismissProgressBar(View progressView) {
        Log.e("AAA", "dismissProgressBar()");
        progressView.setVisibility(View.GONE);
    }

    public static void showFailView(View progressView) {
        Log.e("AAA", "showFailView()");
        ProgressViewHolder vh = (ProgressViewHolder) progressView.getTag();

        progressView.setVisibility(View.VISIBLE);
        vh.progressBar.setVisibility(View.GONE);
        vh.emptyView.setVisibility(View.GONE);
        vh.failView.setVisibility(View.VISIBLE);
    }

    public static void showEmptyView(View progressView) {
        Log.e("AAA", "showEmptyView()");
        ProgressViewHolder vh = (ProgressViewHolder) progressView.getTag();

        progressView.setVisibility(View.VISIBLE);
        vh.progressBar.setVisibility(View.GONE);
        vh.failView.setVisibility(View.GONE);
        vh.emptyView.setVisibility(View.VISIBLE);
    }

    public static void showMainView(View progressView) {
        Log.e("AAA", "showMainView()");
        progressView.setVisibility(View.GONE);
    }

    public static void setEmptyImage(View emptyView, int resId) {
//        ImageView emptyImg = (ImageView) emptyView.findViewById(R.id.empty_img);
//        emptyImg.setImageResource(resId);
    }

    public static void setEmptyText(View emptyView, String emptyText) {
//        TextView emptyTV = (TextView) emptyView.findViewById(R.id.empty_text);
//        emptyTV.setText(emptyText);
    }

}
