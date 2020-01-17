package com.zupu.zp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.DaocterBeans;
import com.zupu.zp.bean.JsonBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.ui.PublishStreamAndPlayStreamUI;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.zupu.zp.MainActivity.rg;
import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/5 20:03
 * update: 找医生
 */

public class Doctorfragment extends BaseFragment {
     String mediaUrl,srcurl;
    View view;
    WebView web;
    ProgressDialog progressDialog;
    ZfUtil zfUtil = new ZfUtil();
    String uuid,userid,userName ,userHead;
    SharedPreferences sharedPreferences;
    Date curDate;
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.doctorlaout, container, false);
        return view;
    }

    @Override
    public void initview() {
        web=view.findViewById(R.id.dorctorweb);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null); ;
        userName =sharedPreferences.getString("nickName",null); ;
        userHead=sharedPreferences.getString("photoUrl",null);
    }

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
                /*if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                showProgress(getActivity(), "加载中...");
             /*   //弹出提示
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
                dismissProgress(getActivity());
               /* //隐藏:不为空，正在显示。才隐藏关闭
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                boolean status = url.contains(UrlCount.Base_Head+"mobile/find_doctor.html");
                if (status){
                    rg.setVisibility(View.VISIBLE);
                }else {
                    rg.setVisibility(View.GONE);
                }
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
        web.loadUrl(UrlCount.Base_Head+"mobile/find_doctor.html");
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
        boo1=5;

    }
    public void exit() {
        if(!isExit) {
            isExit = true;
            web.loadUrl(UrlCount.Base_Head+"mobile/find_doctor.html");

        } else {
            //getActivity().finish();
        }
    }

    @Override
    public void initlinsenter() {

        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        curDate =  new Date(System.currentTimeMillis());
       //获取当前时间

    }

    @Override
    public void persenter() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void sucecess(Object o) {

        if (o instanceof Picbean) {
            Picbean picbean = (Picbean) o;
            int code = picbean.getCode();
            if (code == 0) {
                mediaUrl = picbean.getMediaUrl();
                srcurl = picbean.getMediaUrl() + "," + srcurl;
                Log.e("TAG", "sucecess: 上传成功");
                String method = "javascript:androidPic.setPics(\"" + mediaUrl + "\")";
                web.loadUrl(method);
            }
        }
        if (o instanceof JsonBean){
          //  dismissProgress(ZegoApplication.getContexta());
            Gson gson = new Gson();
            DaocterBeans daocterBeans = gson.fromJson(jsons, DaocterBeans.class);
            JsonBean phoneBean= (JsonBean)o;
            if (phoneBean.getCode()==0){
                ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent news = new Intent(getActivity(), PublishStreamAndPlayStreamUI.class);
                        news.putExtra("roomID",daocterBeans.getRoomID()+"");
                        news.putExtra("flag","0");
                        news.putExtra("username",daocterBeans.getUsername());
                        news.putExtra("myuserid",userid);
                        news.putExtra("userid",daocterBeans.getUserid()+"");
                        news.putExtra("userhead",daocterBeans.getUserHead());
                        news.putExtra("isvoice",daocterBeans.isIsvoice());
                        news.putExtra("time",daocterBeans.getTime());
                        news.putExtra("contentId",phoneBean.getId()+"");
                        getActivity().startActivity(news);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);//3秒后执行TimeTask的run方法

            }

        }
       /* if (o instanceof JsonBean){
            dismissProgress(getActivity());
            Gson gson = new Gson();
            DaocterBeans daocterBeans = gson.fromJson(jsons, DaocterBeans.class);
            JsonBean phoneBean= (JsonBean)o;
            Log.e("房间号2", daocterBeans.getRoomID()+"" );
            if (phoneBean.getCode()==0){
                ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent news = new Intent(getActivity(), PublishStreamAndPlayStreamUI.class);
                        news.putExtra("roomID",daocterBeans.getRoomID()+"");
                        Log.e("房间号2", daocterBeans.getRoomID()+"" );
                        news.putExtra("flag","0");
                        news.putExtra("username",daocterBeans.getUsername());
                        news.putExtra("myuserid",userid);
                        news.putExtra("userid",daocterBeans.getUserid()+"");
                        news.putExtra("userhead",daocterBeans.getUserHead());
                        news.putExtra("isvoice",daocterBeans.isIsvoice());
                        news.putExtra("time",daocterBeans.getTime());
                        news.putExtra("contentId",phoneBean.getId()+"");
                        getActivity().startActivity(news);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);//3秒后执行TimeTask的run方法
            }else {
                Toast.makeText(getActivity(), phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
                
            }

        }*/


    }
 /*   private class AndroidJs{

        //4....写调用的方法,被js调用的代码需要加上下面的注解
        @JavascriptInterface
        public void wxShare( String uuid,  String subjectId,  String type, String title,  String applyId) {
            zfUtil.shareMethod(uuid,subjectId,type, title,applyId,getActivity());
            Log.e("TAG", "wxShare: "+uuid+subjectId+title);
        }

        @JavascriptInterface
        public void startCall(String json){
            Log.e("TAG", "++++++"+json );
             jsons=json;
            Gson gson = new Gson();
            DaocterBeans daocterBeans = gson.fromJson(json, DaocterBeans.class);
            //roomID 房间号        userid 医生id          username 医生名称         userHead //医生头像         isvoice  //是否是语音通话
            // 购买时长  time
           if (qxMendth()){
               JSONObject root = new JSONObject();
               try {
                   root.put("title", "-1");
                   root.put("userName", userName);
                   root.put("userHead",userHead);
                   root.put("userid",userid);
                   root.put("roomid",daocterBeans.getRoomID());
                   root.put("isvoice",daocterBeans.isIsvoice());
                   root.put("time",daocterBeans.getTime());
                   root.put("gettime",curDate.getTime());
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               // pushManager.initialize(getActivity(),null);
               HashMap<String, Object> map = new HashMap<>();
               map.put("uid", Integer.parseInt(userid));
               map.put("targetuId", daocterBeans.getUserid());
               map.put("pushContent","有人向你发起通话请求");
               map.put("uri","000");
               map.put("title","-1");
               map.put("text","1");
               map.put("url",root.toString());
               persenterimpl.posthttp(UrlCount.URL_GtSend, map, JsonBean.class);
           }
        }

        @JavascriptInterface
        public String loging() {
            return uuid;
        }

        @JavascriptInterface
        public void wxPay(String json) {
            zfUtil.wxzfMethod(json);
        }
        @JavascriptInterface
        public void zfbPay(String json) {
            zfUtil.zfbmethod(json,getActivity());
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


    }
}
