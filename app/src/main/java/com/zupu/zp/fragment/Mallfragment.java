package com.zupu.zp.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.zupu.zp.MainActivity.rg;
import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/5 20:03
 * update: 商城
 */

public class Mallfragment extends BaseFragment {
    View view;
    WebView web;
    String mediaUrl;
    String uuid,userid,userName;
    ZfUtil zfUtil = new ZfUtil();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
   //Button btn;
   ProgressDialog progressDialog;
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.malllaout, container, false);
        return view;
    }

    @Override
    public void initview() {
        RadioGroup rg=(RadioGroup)getActivity().findViewById(R.id.rg);
        web = view.findViewById(R.id.mallweb);
       // btn = view.findViewById(R.id.btn);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null);
        userName =sharedPreferences.getString("nickName",null);
        editor = sharedPreferences.edit();
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void initdata() {
        //进度
        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
             /*   if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.setMessage("宗亲正在拼命加载……" + newProgress + "%");*/
            }
        });

        // 设置WebView的客户端
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;// 返回false
            }
            //重写页面打开和结束的监听。打开时弹出提示，关闭时隐藏菊花
            /**
             *
             * 界面打开的回调
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgress(getActivity(),"加载中...");
             /*   if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //弹出提示
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("提示");
                progressDialog.setMessage("宗亲正在拼命加载……");
                progressDialog.show();*/

            }

            /**
             * 界面打开完毕的回调
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                //隐藏:不为空，正在显示。才隐藏关闭
                dismissProgress(getActivity());
              /*  if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                boolean status = url.contains(UrlCount.Base_Head+"mobile/shopping_mall.html");
                if (status){
                    rg.setVisibility(View.VISIBLE);
                }else {
                    rg.setVisibility(View.GONE);
                }
            }


        });

        final WebSettings webSettings = web.getSettings();
        //打开DOM储存API
        web.getSettings().setDomStorageEnabled(true);
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
        //商城
        web.loadUrl(UrlCount.Base_Head+"mobile/shopping_mall.html");


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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        boo1=1;

    }

    public void exit() {
        if(!isExit) {
            isExit = true;
            web.loadUrl(UrlCount.Base_Head+"mobile/shopping_mall.html");

        } else {
            //getActivity().finish();
        }
    }
    @Override
    public void initlinsenter() {



    }


    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {
        if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                mediaUrl = picbean.getMediaUrl();
                Toast.makeText(getActivity(), R.string.sccg, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "sucecess: 成功" );
                String method ="javascript:androidPic.setPics(\""+mediaUrl+"\")" ;
                web.loadUrl(method);
            }
        }

    }

   /* private class AndroidJs{
        //  <button onclick="AndroidZf.wxShare('js调用安卓方法！')">调用安卓方法</button>
        //4....写调用的方法,被js调用的代码需要加上下面的注解
        @JavascriptInterface
        public void callAndroidMethod(String name,String pwd){

        }
        @JavascriptInterface
        public void wxShare( String uuid,  String subjectId,  String type, String title,  String applyId) {
            zfUtil.shareMethod(uuid,subjectId,type, title,applyId,getActivity());
            Log.e("TAG", "wxShare: "+uuid+subjectId+title);
        }

        @JavascriptInterface
        public void outLoging() {
            editor.putString("userId", null);
            editor.putString("appLoginIdentity",null);
            editor.putString("photoUrl",null);
            editor.putString("nickName",null);
            editor.putString("pushCid",null);
            editor.putString("is_certification",null);
            editor.commit();
            startActivity(new Intent(getActivity(), LogingActivity.class));
            getActivity().finish();
        }

        @JavascriptInterface
        public void wxPay(String json) {
            zfUtil.wxzfMethod(json);
        }
        @JavascriptInterface
        public void zfbPay(String json) {
            zfUtil.zfbmethod(json,getActivity());
        }
        @JavascriptInterface
        public void wxShare(String url,  String title,  String desc,  String head) {
            //zfUtil.shareMethod(url,title,desc, head);
        }
        @JavascriptInterface
        public String loging() {
            return uuid;
        }
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
        if (o instanceof  Bitmap){
            Bitmap bitmap= (Bitmap)o;
            ImageUtli imageUtli = new ImageUtli();
            if (bitmap!=null)
            {
                imageUtli.saveBmp2Gallery(getActivity(),bitmap,"img");
            }
        }
        if (o instanceof String) {
            String a = (String) o;
            web.loadUrl(a);
        }
        if (o instanceof Integer) {
            Integer a = (Integer) o;
            if (a==10){
                String method ="javascript:paySuccess()" ;
                web.loadUrl(method);
            }
        }


    }



}
