package com.example.ckz.demo1.activity.news;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;

public class WebActivity extends BaseActivity implements View.OnClickListener{
    /**
     * UI
     */
    private ImageView mBackBtn;
    private WebView mWebView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        url = getIntent().getStringExtra("webUrl");
        initView();
    }

    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.web_back_btn);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        mWebView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient(){

        });

        mBackBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_back_btn:
                finish();
                break;
        }

    }
}
