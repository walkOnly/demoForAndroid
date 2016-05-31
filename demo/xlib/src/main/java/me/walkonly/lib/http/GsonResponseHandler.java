package me.walkonly.lib.http;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;
import me.walkonly.lib.Config;
import me.walkonly.lib.Constant;
import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.util.Utils;
import me.walkonly.lib.view.IProgressView;
import me.walkonly.lib.view.ProgressViewHolder;

public abstract class GsonResponseHandler<T> extends AsyncHttpResponseHandler {

    private static final String TAG = Constant.TAG_HTTP;

    private String url;
    private Type genericType;
    private Context context;

    private boolean isCancelled = false;

    public GsonResponseHandler() {
        genericType = getGenericType();
    }

    public GsonResponseHandler(Context context) {
        this.context = context;
        genericType = getGenericType();
    }

    private Type getGenericType() {
        Type superType = getClass().getGenericSuperclass();
        return ((ParameterizedType) superType).getActualTypeArguments()[0];
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GsonResponseHandler attach(Activity activity) {
        ((BaseActivity) activity).addHttpResponseHandler(this);
        return this;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    protected void handleMessage(Message message) {
        if (!isCancelled)
            super.handleMessage(message);
    }

    @Override
    public void onStart() {
        //super.onStart();
        //Log.d(TAG, "onStart()");
        if (context != null && context instanceof IProgressView) {
            Log.d(TAG, "Utils.showProgressBar()");
            View progressView = ((IProgressView) context).getProgressView();
            if (progressView == null) return; // 有可能因页面返回被ButterKnife.unbind()置空了
            Utils.showProgressBar(progressView);
        }
    }

    @Override
    public void onFinish() {
        //super.onFinish();
        //Log.d(TAG, "onFinish()");
        if (context != null && context instanceof IProgressView) {
            Log.d(TAG, "Utils.dismissProgressBar()");
            View progressView = ((IProgressView) context).getProgressView();
            if (progressView == null) return; // 有可能因页面返回被ButterKnife.unbind()置空了
            Utils.dismissProgressBar(progressView);
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);

        // 测试代码
        if (Config.DEBUG) {
            if (HttpTestData.isTest(url))
                response = HttpTestData.getTestData(url);
        }

        if (TextUtils.isEmpty(response)) {
            failed("请求服务器内容为空");
            return;
        }

        Log.e("response###", response);
        Logger.json(response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            int status = jsonObject.getInt("code");
            String message = jsonObject.getString("message");
            if (status != 1) {
                //failed(message);
                bizFailed(status, message, response);
            } else {
                //Gson gson = new Gson();
                //T obj = gson.fromJson(response, genericType);
                T obj = json2bean(response, genericType);
                succeed(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            failed("Json exception: " + e.getMessage());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "onFailure(): statusCode = " + statusCode + " " + url);
        failed("请求服务器失败");
    }

    public abstract void succeed(T obj);

    public void failed(String error) {
        Log.e(TAG, "failed(): " + error + " " + url);

        if (context != null && context instanceof IProgressView) {
            Utils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Utils.showFailView()");
                    final View progressView = ((IProgressView) context).getProgressView();
                    if (progressView == null) return; // 有可能因页面返回被ButterKnife.unbind()置空了
                    Utils.showFailView(progressView);

                    ProgressViewHolder vh = (ProgressViewHolder) progressView.getTag();
                    vh.failView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.showMainView(progressView);
                            retryLoadData();
                        }
                    });
                }
            }, 100); // 这个时间不要改。
        }
    }

    public void bizFailed(int code, String msg, String response) {
        Log.e(TAG, "bizFailed(): code | msg = " + code + " " + msg);
        failed(msg);
    }

    // 失败后重试
    public void retryLoadData() {
        Log.e(TAG, "retryLoadData()");
    }

    private T json2bean(String str, Type type) {
        Gson gson = new Gson();

        try {
            return gson.fromJson(str, type);
        } catch (JsonSyntaxException e) {
            String msg = e.getMessage();
            /**
             * 对php返回的某些 [] 作自动转换。
             */
            if (msg.contains("Expected BEGIN_OBJECT but was BEGIN_ARRAY")) {
                int p1 = msg.indexOf("column");
                int p2 = msg.indexOf("path", p1);
                String tmp = msg.substring(p1 + 6, p2 - 1).trim();
                String path = msg.substring(p2 + 5).trim();
                int p = Integer.valueOf(tmp);
//                Log.e("AAA", "p=" + p);
//                Log.e("AAA", "path=" + path);

                // 这里似乎差1
                char[] tmpArr = str.toCharArray();
//                Log.e("AAA", "tmpArr[p-1]=" + tmpArr[p-1]);
//                Log.e("AAA", "tmpArr[p]=" + tmpArr[p]);

                if (tmpArr[p - 1] == '[' && tmpArr[p] == ']') {
                    tmpArr[p - 1] = '{';
                    tmpArr[p] = '}';
                    Log.e("AAA", "json 格式转换 " + path + " [] ---> {}");
                    String newStr = new String(tmpArr);
                    return json2bean(newStr, type);
                }
            }

            /**
             * 对php返回的某些 "" 作自动转换。
             */
            if (msg.contains("Expected BEGIN_OBJECT but was STRING")) {
                int p1 = msg.indexOf("column");
                int p2 = msg.indexOf("path", p1);
                String tmp = msg.substring(p1 + 6, p2 - 1).trim();
                String path = msg.substring(p2 + 5).trim();
                int p = Integer.valueOf(tmp);
//                Log.e("AAA", "p=" + p);
//                Log.e("AAA", "path=" + path);

                // 这里似乎差1
                char[] tmpArr = str.toCharArray();
//                Log.e("AAA", "tmpArr[p-1]=" + tmpArr[p-1]);
//                Log.e("AAA", "tmpArr[p]=" + tmpArr[p]);

                if (tmpArr[p - 1] == '\"' && tmpArr[p] == '\"') {
                    tmpArr[p - 1] = '{';
                    tmpArr[p] = '}';
                    Log.e("AAA", "json 格式转换 " + path + " \"\" ---> {}");
                    String newStr = new String(tmpArr);
                    return json2bean(newStr, type);
                }
            }

            throw e;
        }
//        return null;
    }

}
