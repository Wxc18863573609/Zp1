package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;



import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.giftutli.util.glide.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import static com.zupu.zp.MainActivity.rg;

public class IssueActivity extends BaseActivity {

    WebView web;
    SharedPreferences sharedPreferences;
    String uuid,userid,userName;
    @Override
    public int initlayout() {
        return R.layout.activity_issue;
    }

    @Override
    public void initview() {

        web=findViewById(R.id.web1);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null); ;
        userName =sharedPreferences.getString("nickName",null); ;
    }

    @Override
    public void initdata() {

        // 设置WebView的客户端
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;// 返回false
            }
        });

        WebSettings webSettings = web.getSettings();
        web.getSettings().setJavaScriptEnabled(true);//启用js
        web.getSettings().setBlockNetworkImage(false);//解决图片不显示
        // 允许从任何来源加载内容，即使起源是不安全的；
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //打开DOM储存API
        web.getSettings().setDomStorageEnabled(true);

        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_NO_CACHE);

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
        web.loadUrl("https://zupu.honarise.com/mobile/course_panel.html");
        //监听返还上一个html
        web.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                    if (web!=null&&web.canGoBack()){
                        web.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        web.addJavascriptInterface(new AndroidJs(), "AndroidZf");

    }

    @Override
    public void initlisenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {

    }
    private class AndroidJs{

        //4....写调用的方法,被js调用的代码需要加上下面的注解
        @JavascriptInterface
        public String loging() {
            return uuid;
        }
        @JavascriptInterface
        public void gone() {
            rg.setVisibility(View.GONE);
        }
        @JavascriptInterface
        public void visible() {
            rg.setVisibility(View.VISIBLE);
        }

        @JavascriptInterface
        public void morePic() {
            List<String> list = null;
            persenterimpl.sendPicp1("",list, Picbean.class);


        }
    }



}


