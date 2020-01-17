package com.zupu.zp.myactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.utliss.UrlCount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class XuanZemarkActivity extends BaseActivity {
    WebView web;
    Intent intent1;
    @Override
    public int initlayout() {
        return R.layout.activity_xuan_zemark;
    }

    @Override
    public void initview() {
        web=findViewById(R.id.web);
          intent1 = getIntent();
               /* Intent intent1 = getIntent();;
                HashMap<String, Object> map = new HashMap<>();
                map.put("uuid",intent1.getIntExtra("uuid",0));
                map.put("pavilionType", intent1.getStringExtra("pavilionType"));
                map.put("lateName", intent1.getStringExtra("lateName"));
                map.put("lateGender",intent1.getStringExtra("lateGender"));
                map.put("lateBirthTime",intent1.getStringExtra("lateBirthTime"));
                map.put("latePassingTime",intent1.getStringExtra("latePassingTime"));
                map.put("latePresentation",intent1.getStringExtra("latePresentation"));
                map.put("cemeteryLocation",intent1.getStringExtra("cemeteryLocation"));
                map.put("combstonePosition",intent1.getStringExtra("combstonePosition"));
                map.put("latePicture",intent1.getStringExtra("latePicture"));
                map.put("lat",intent1.getStringExtra("lat"));
                map.put("lng",intent1.getStringExtra("lng"));
                map.put("lateNametwo",intent1.getStringExtra("lateNametwo"));
                map.put("lateGendertwo",intent1.getStringExtra("lateGendertwo"));
                map.put("lateBirthTimetwo",intent1.getStringExtra("lateBirthTimetwo"));
                map.put("latePassingTimetwo",intent1.getStringExtra("latePassingTimetwo"));
                map.put("latePresentationtwo",intent1.getStringExtra("latePresentationtwo"));
                map.put("latePicturetwo",intent1.getStringExtra("latePicturetwo"));
                map.put("cemeteryName",intent1.getStringExtra("cemeteryName"));*/


                /*
                * @param memorialHallId 纪念馆id
                 * @param whetherOpen 是否公开逝者信息(1.公开 2.隐藏)
                 * @param visitPwd     否时访问密码
                 *
                 * @param isxztitle 馆标题
                * */

              //  persenterimpl.posthttp(UrlCount.URL_guan,map);
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
        web.loadUrl(UrlCount.Base_Head+"mobile/memorial.html");

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
    public class AndroidJs{

        //4....写调用的方法,被js调用的代码需要加上下面的注解
    /*    @JavascriptInterface
        public void Andback() {
            Toast.makeText(XuanZemarkActivity.this, "1111111111", Toast.LENGTH_SHORT).show();
            if (web.canGoBack()) {
                web.goBack();//返回上个页面
                return;
            } else {
               // finish();
            }
        }*/
        @JavascriptInterface
        public void finishs() {
             finish();
        }
        @JavascriptInterface
        public String getdata() {
            JSONObject root = new JSONObject();
            try {
                root.put("flags",intent1.getStringExtra("flags"));
                root.put("uuid", intent1.getStringExtra("uuid"));
                root.put("pavilionType",  intent1.getStringExtra("pavilionType"));
                root.put("lateName",intent1.getStringExtra("lateName"));
                root.put("lateGender",intent1.getStringExtra("lateGender"));
                root.put("lateBirthTime",intent1.getStringExtra("lateBirthTime"));
                root.put("latePassingTime",intent1.getStringExtra("latePassingTime"));
                root.put("latePresentation",intent1.getStringExtra("latePresentation"));
                root.put("cemeteryLocation",intent1.getStringExtra("cemeteryLocation"));
                root.put("combstonePosition",intent1.getStringExtra("combstonePosition"));
                root.put("latePicture",intent1.getStringExtra("latePicture"));
                root.put("lat",intent1.getStringExtra("lat"));
                root.put("lng",intent1.getStringExtra("lng"));
                root.put("lateNametwo",intent1.getStringExtra("lateNametwo"));
                root.put("lateGendertwo",intent1.getStringExtra("lateGendertwo"));
                root.put("lateBirthTimetwo",intent1.getStringExtra("lateBirthTimetwo"));
                root.put("latePassingTimetwo",intent1.getStringExtra("latePassingTimetwo"));
                root.put("latePresentationtwo",intent1.getStringExtra("latePresentationtwo"));
                root.put("latePicturetwo",intent1.getStringExtra("latePicturetwo"));
                root.put("cemeteryName",intent1.getStringExtra("cemeteryName"));

                Log.e("数据啊锕", root.toString() );
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  root.toString();
        }

    }
}
