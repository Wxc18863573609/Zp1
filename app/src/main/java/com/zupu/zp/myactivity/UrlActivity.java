package com.zupu.zp.myactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.utliss.UrlCount;

import static com.zupu.zp.MainActivity.rg;

public class UrlActivity extends AppCompatActivity {
    WebView web;
    ProgressDialog progressDialog;
    Boolean  mIsRedirect=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        web=findViewById(R.id.web);


        //进度
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.setMessage("宗亲正在拼命加载……" + newProgress + "%");
            }
        });
        //getmorePic();
        // 设置WebView的客户端
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                super.onScaleChanged(view, oldScale, newScale);

            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;// 返回false
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mIsRedirect = true;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //弹出提示
                progressDialog = new ProgressDialog(UrlActivity.this);
                progressDialog.setTitle("提示");
                progressDialog.setMessage("宗亲正在拼命加载……");
                progressDialog.show();
            }

            /**
             * 界面打开完毕的回调
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                mIsRedirect = false;
                //view.scrollTo(0, moldt);//webview加载完成后直接定位到上次访问的位置
                //隐藏:不为空，正在显示。才隐藏关闭
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
              /*  boolean status = url.contains(UrlCount.Base_Head + "mobile/qinzi_kongjian.html");
                if (status) {
                    rg.setVisibility(View.VISIBLE);
                } else {
                    rg.setVisibility(View.GONE);
                }*/
            }
        });


        WebSettings webSettings = web.getSettings();

        // 让WebView能够执行javaScript

        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        // 允许从任何来源加载内容，即使起源是不安全的；
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        //打开DOM储存API
        web.getSettings().setDomStorageEnabled(true);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        // webSettings.setDefaultFontSize(12);

        // 设置WebView属性，能够执行Javascript脚本
        // mWebView.getSettings().setJavaScriptEnabled(true);
        //3、 加载需要显示的网页
        web.loadUrl(getIntent().getStringExtra("url"));
//https://zupu.honarise.com/mobile/disney_family_museum.html
        //监听返还上一个html
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (web != null && web.canGoBack()) {
                        web.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        web.addJavascriptInterface(new AndroidJs(), "AndroidZf");


    }
    public class AndroidJs{
        @JavascriptInterface
        public void finishs() {
            finish();
        }
    }
}
