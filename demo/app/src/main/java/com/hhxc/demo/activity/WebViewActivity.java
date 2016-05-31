package com.hhxc.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hhxc.demo.R;

import butterknife.Bind;
import me.walkonly.lib.activity.BaseActivity;
import me.walkonly.lib.annotation.C_Activity;

@C_Activity(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {

    private static final String TAG = "WebViewActivity";

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progress_view)
    View progressView;
//    @Bind(R.id.app_title)
//    MyAppTitle app_title;

    private String title, url;
    private boolean hideMenu = false;

    public static void start(Context context, String url, String title, boolean hideMenu) {
        Log.d(TAG, "url = " + url);
        if (TextUtils.isEmpty(url))
            throw new IllegalArgumentException("Url must not be empty");
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("hide", hideMenu);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void start(Context context, String url, String title) {
        start(context, url, title, false);
    }

    public static void start(Context context, String url) {
        start(context, url, null);
    }

    //@Override
    public void onIntentFetched(Intent intent) {
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        hideMenu = intent.getBooleanExtra("hide", false);
//        app_title.initViewsVisible(true, false, true, false, true);
//        app_title.setLeftButton(R.drawable.arrow_back, "");
//        app_title.setAppTitle(title);
//        app_title.setOnLeftButtonClickListener(new MyAppTitle.OnLeftButtonClickListener() {
//            @Override
//            public void onLeftButtonClick(View v) {
//                onBackPressed();
//            }
//        });
        setupWebView(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupWebView(String webUrl) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!wannaOverrideURL(url)) view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebViewActivity.this.url = url;
                if (progressView != null)
                    progressView.setVisibility(View.GONE);
            }

        });

        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(webUrl);
    }

    public boolean wannaOverrideURL(String url) {
        if (url.contains("hohoxc://package")) {
            return true;
        }
        return false;
    }

    private String findPackageID(String scheme) {
        return scheme.replaceAll("[^0-9]+", "");
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isURL(String s) {
        if (TextUtils.isEmpty(s)) return false;
        return s.contains("http") || s.contains("https") || s.contains("com");
    }
}
