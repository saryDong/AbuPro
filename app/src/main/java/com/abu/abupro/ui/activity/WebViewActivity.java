package com.abu.abupro.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.abu.abupro.R;
import com.abu.abupro.app.Constant;

import butterknife.BindView;


public class WebViewActivity extends BaseActivity{

    @BindView(R.id.tcWebView)WebView tcWebView;

    private String mUrl;
    private WebSettings webSettings;
    private android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void init() {
        super.init();
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);             // 给左上角图标的左边加上一个返回的图标"《" 。对应ActionBar.DISPLAY_HOME_AS_UP
        mActionBar.setDisplayShowHomeEnabled(true);             //使左上角图标可点击，对应id为android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME false 则图标无法点击
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle("资讯详情");
        // false 就无法点击
        mUrl = getIntent().getStringExtra(Constant.ARTICLE_URL);
        settingView();
        showWebView();
        tcWebView.loadUrl(mUrl);
    }

    private void showWebView() {
        tcWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onStop() {
        super.onStop();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        webSettings.setJavaScriptEnabled(false);
        tcWebView.destroy();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void settingView() {
        webSettings = tcWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
    }

    @Override public void onBackPressed() {
        if (tcWebView.canGoBack()) {
            tcWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

