package com.zupu.zp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.DaocterBeans;
import com.zupu.zp.bean.DonwloadBean;
import com.zupu.zp.bean.JsonBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.APKVersionCodeUtils;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.PhotoView;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.ui.PublishStreamAndPlayStreamUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.zupu.zp.MainActivity.rg;
import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/5 20:03
 * update: 我的
 */

public class Myfragment extends BaseFragment {
    Bitmap bitmap;
    boolean boo;
    private PhotoView img;
    //  进度条
    private ProgressBar mProgressBar;
    //  对话框
    private Dialog mDownloadDialog;
    //  判断是否停止
    private boolean mIsCancel = false;
    //  进度
    private int mProgress;
    //  版本名称
    private String mVersion_name="1.0";



    public static final int REQUEST_CALL_PERMISSION = 10086; //拨号请求码
    PopupWindow window;
    String mediaUrl;
    Bitmap head=null;
    String srcurl;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    PopupWindow window1;
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings =new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;
    View view;
    WebView web;
    ZfUtil zfUtil = new ZfUtil();
    ProgressDialog progressDialog;
    String uuid,userid,userName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.myfragmentlaout, container, false);
        return view;
    }

    @Override
    public void initview() {
        web=view.findViewById(R.id.markweb);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null); ;
        userName =sharedPreferences.getString("nickName",null); ;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initdata() {
        //initNavi();
        //进度
        //配置权限
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);

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
                showProgress(getActivity(), "加载中...");
             /*   if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                //弹出提示
              /*  progressDialog = new ProgressDialog(getActivity());
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
               /* if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                boolean status = url.contains(UrlCount.Base_Head+"mobile/geren_zhongxin.html");
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
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
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
        web.loadUrl(UrlCount.Base_Head+"mobile/geren_zhongxin.html");


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
    public void initlinsenter() {
        img = view.findViewById(R.id.img);

    /*    but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBitmap();
            }
        });*/


    }

    @Override
    public void persenter() {

    }


    @Override
    public void onResume() {
        super.onResume();
       // web.loadUrl(UrlCount.Base_Head+"mobile/geren_zhongxin.html");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void sucecess(Object o) {

        if (o instanceof JsonBean){
            dismissProgress(getActivity());
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

            }else {
                Toast.makeText(getActivity(), phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }

        /*if (o instanceof JsonBean){
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



        if (o instanceof DonwloadBean){
            DonwloadBean donwloadBean= (DonwloadBean)o;
            if (donwloadBean.getCode()==0)
            {
                APKVersionCodeUtils apkVersionCodeUtils = new APKVersionCodeUtils();
                double versionCode = apkVersionCodeUtils.getVersionCode(getActivity());
                 url=donwloadBean.getCurrentLink();
                Log.e("版本", "initdata: "+versionCode );
              //  Double.doubleToLongBits(versionCode) > Double.doubleToLongBits(Double.parseDouble(donwloadBean.getCurrentVer()))
                //对比版本号
                if(sharedPreferences.getLong("appvsen",Double.doubleToLongBits(1.0))==Double.doubleToLongBits(1.0))
                {
                    editor.putLong("appvsen",Double.doubleToLongBits(1.0));
                    editor.commit();
                }
               if (sharedPreferences.getLong("appvsen",Double.doubleToLongBits(1.0))>Double.doubleToLongBits(Double.parseDouble(donwloadBean.getCurrentVer()))){
                   new AlertDialog.Builder( getActivity())
                           .setTitle(R.string.gxts)
                           .setMessage(R.string.isgx)
                           .setNegativeButton(R.string.back_qx,
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog,
                                                           int which) {
                                       }
                                   })
                           .setPositiveButton(R.string.shuor,
                                   new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int whichButton) {
                                           url=donwloadBean.getCurrentLink();
                                           editor.putLong("appvsen",Double.doubleToLongBits(Double.parseDouble(donwloadBean.getCurrentVer())));
                                           editor.commit();
                                           mIsCancel=false;
                                           //展示对话框
                                           showDownloadDialog();
                                       }
                                   }).show();
               }else{
                   Toast.makeText(getActivity(), R.string.newbb, Toast.LENGTH_SHORT).show();
               }

            }else {
                Toast.makeText(getActivity(), donwloadBean.getMsg(), Toast.LENGTH_SHORT).show();

            }


        }

        if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                mediaUrl = picbean.getMediaUrl();
                Toast.makeText(getActivity(), R.string.sccg, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "sucecess: 成功" );
                Log.e("测试", picbean.getMediaUrl());
                String method ="javascript:androidPic.setPics(\""+mediaUrl+"\")" ;
                web.loadUrl(method);
            }
        }



       /* if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                //js方法
                srcurl = picbean.getMediaUrl();
                Toast.makeText(getActivity(), "视频上传成功", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "sucecess: 视频成功"+mediaUrl );
                web.evaluateJavascript("androidPic.setPics("+mediaUrl+")", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });


            }
        }*/

    }
   @SuppressLint("SetJavaScriptEnabled")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
        if (o instanceof  Picbean){
            Picbean picbean = (Picbean) o;
            mediaUrl = picbean.getMediaUrl();
            Toast.makeText(getActivity(), R.string.sccg, Toast.LENGTH_SHORT).show();
            String method ="javascript:androidPic.setPics(\""+mediaUrl+"\")" ;
            web.loadUrl(method);

        }
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
            boolean statusd = a.contains(UrlCount.Base_Head);
            if (statusd){
                web.loadUrl(a);
            }else {
              //  String method ="javascript:androidPic.setPics(\""+mediaUrl+"\")" ;
             //Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
                web.loadUrl("javascript:WxMessage1("+a+")");
               /* String method ="javascript:WxMessage1()" ;
                web.loadUrl(method);*/
            /*   String method ="javascript:WxMessage(\""+a+"\")" ;
                web.loadUrl(method);*/
                Log.e("测试345",a.toString() );
            }

        }
        if (o instanceof Integer) {
            Integer a = (Integer) o;
            if (a==10){
                String method ="javascript:paySuccess()" ;
                web.loadUrl(method);
            }else if (a==3001){
                myBitmap();
            }
        }

    }
/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
        if (o instanceof  Bitmap){
            Bitmap bitmap= (Bitmap)o;
            ImageUtli imageUtli = new ImageUtli();
            if (bitmap!=null)
            {
                imageUtli.saveBmp2Gallery(getActivity(),bitmap,"二维码");
            }
        }else {
            Bitmap bitmap= (Bitmap)o;
            ImageUtli imageUtli = new ImageUtli();
            if (bitmap!=null)
            {
                imageUtli.saveBmp2Gallery(getActivity(),bitmap,"二维码");
            }
        }


    }

    public class AndroidJs{
        @JavascriptInterface
        public void textFz(String json) {
           // zfUtil.Textviews(getActivity(),json);
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
        //4....写调用的方法,被js调用的代码需要加上下面的注解
        @JavascriptInterface
        public void imgBc(String url){
            ImageUtli imageUtli = new ImageUtli();
            Bitmap bitmap = imageUtli.returnBitMap(url);
        }

        @JavascriptInterface
        public void phone(String json) {
            call("tel:"+json);
        }
        @JavascriptInterface
        public void wxPay(String json) {
            Log.e("TAG", "wxPay: "+json );
            zfUtil.wxzfMethod(json);
        }
        @JavascriptInterface
        public void zfbPay(String json) {
            zfUtil.zfbmethod(json,getActivity());
        }
        @JavascriptInterface
        public String loging() {
            return uuid;
        }
      */
/*  @JavascriptInterface
        public void gone() {
            RadioGroup rg=(RadioGroup)getActivity().findViewById(R.id.rg);
            rg.setVisibility(View.GONE);
        }*//*


        @JavascriptInterface
        public void updata() {
            //int a = new Double(money).intValue();
            HashMap<String, Object> map = new HashMap<>();
            map.put("","");
            persenterimpl.posthttp(UrlCount.URL_updata,map, DonwloadBean.class);
        }
       */
/* @JavascriptInterface
        public void visible() {
            RadioGroup rg=(RadioGroup)getActivity().findViewById(R.id.rg);
            rg.setVisibility(View.VISIBLE);
        }*//*

        @JavascriptInterface
        public void upvideo() {
            getmorePic();
        }
        @JavascriptInterface
        public String upMorePic() {
            MultiImageSelector.create(getActivity())
                    .showCamera(true) // 是否显示相机. 默认为显示
                    .count(9) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                    .single() // 单选模式
                    .multi() // 多选模式, 默认模式;
                    .origin(strings) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(getActivity(), REQUEST_IMAGE3);
            return srcurl;
        }

        @JavascriptInterface
        public String uponePic() {
            oneUpPic();
            return mediaUrl;
        }
        @JavascriptInterface
        public void wxShare( String uuid,  String subjectId,  String type, String title,  String applyId) {
            zfUtil.shareMethod(uuid,subjectId,type, title,applyId,getActivity());
            Log.e("TAG", "wxShare: "+uuid+subjectId+title);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: >>>>>>>>>>>");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (resultCode == RESULT_OK) {
                        cropPhoto(data.getData());//裁剪图片
                    }
                    break;
                case 2:
                    if (resultCode == RESULT_OK) {
                        File temp = new File(Environment.getExternalStorageDirectory()
                                + "/head.jpg");
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        cropPhoto(Uri.fromFile(temp));//裁剪图片
                        upLoad(new File(path));
                    }
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras == null) {
                            return;
                        }
                        Log.e("TAG", "图"+extras );
                        head = extras.getParcelable("data");
                        setPicToView(head);
                     //   String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
                        upLoad(new File(path));
                    }
                    break;
                case 5:
                    if (requestCode == REQUEST_IMAGE3) {
                        if (resultCode == RESULT_OK) {
//                            List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            Log.e("TAG", "图:"+strings);
                            Httputlis1 instance = Httputlis1.getInstance();
                            for (int i = 0; i <strings.size() ; i++) {
                                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(strings.get(i)), Picbean.class);
                            }

                        }
                    }
                    break;
            }
        }
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String   VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            Log.e("TAG", "视频路径:"+VIDEOPATH);
            FileSizeUtil fileSizeUtil = new FileSizeUtil();
            double fileOrFilesSize = fileSizeUtil.getFileOrFilesSize(VIDEOPATH, 3);
            if(fileOrFilesSize>5.0){
                Toast.makeText(getActivity(), "视频不能大于5MB", Toast.LENGTH_SHORT).show();
            }else {
                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(VIDEOPATH), Picbean.class);
                Toast.makeText(getActivity(), "视频上传中请稍候", Toast.LENGTH_SHORT).show();

            }


        }
        if (requestCode == 106) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                if (data.getData()!=null){
                    Uri data1 = data.getData();
                    //拿到视频保存地址
                    String s = data1.toString();
                    String[] split = s.split(":");
                    Log.e("TAG", "拍摄视频路径" + split[1].substring(2));

                    persenterimpl.puthttp(UrlCount.URL_UpPic,new File(split[1].substring(2)), Picbean.class);
                    Toast.makeText(getActivity(), "视频上传中请稍候", Toast.LENGTH_SHORT).show();

                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            }


        }
    }


    private boolean hasBasePhoneAuth() {
        PackageManager pm = getActivity().getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, getActivity().getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initNavi() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
    }


    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "CameraDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }

    public void getmorePic(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null);
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setContentView(view);
        window.setOutsideTouchable(true);
        TextView xj = view.findViewById(R.id.xj);
        TextView xc = view.findViewById(R.id.xc);
        TextView qx = view.findViewById(R.id.qx);
        xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 66);
                window.dismiss();
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri fileUri = null;
                try {
                    fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);//限制录制时间10秒
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                // start the Video Capture Intent
                startActivityForResult(intent, 106);

                window.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        window.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

    }


    public void oneUpPic(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null);
        window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        TextView xj = view.findViewById(R.id.xj);
        TextView xc = view.findViewById(R.id.xc);
        TextView qx = view.findViewById(R.id.qx);
        xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                startActivityForResult(intent2, 2);//采用ForResult打开
                window1.dismiss();
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                window1.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

    }

    */
/**
     * 调用系统的裁剪
     *
     * @param uri
     *//*

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void upLoad(File file) {
        gethttp(file);
    }
    public void gethttp(File file){
        persenterimpl.puthttp(UrlCount.URL_UpPic,file, Picbean.class);
    }
    //保存到SD卡
    private void setPicToView(Bitmap mBitmap) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 把数据写入文件
            //关闭流
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    */
/**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     *//*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    Toast.makeText(getActivity(),"请允许拨号权限后再试",Toast.LENGTH_SHORT).show();
                } else {//成功
                    call("tel:"+"10086");
                }
                break;
        }
    }
    */
/**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     *//*

    public void call(String telPhone){
        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(telPhone));
            startActivity(intent);
        }
    }


    */
/*
     * 显示正在下载对话框
     *//*

    protected void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("下载中");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 下载文件
        downloadAPK();
    }
    */
/*
     * 开启新线程下载apk文件
     *//*

    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "jikedownload";

                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion_name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float)count/length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0){
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    */
/**
     * 接收消息
     *//*

    private Handler mUpdateProgressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
                    //installApk();
            }
        };
    };


    */
/*
     * 下载到本地后执行安装
     *//*

    protected void installAPK() {
        if (getActivity() == null || TextUtils.isEmpty(mSavePath)) {
            return;
        }
        File apkFile = new File(mSavePath, mVersion_name);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//      安装完成后，启动app（源码中少了这句话）
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        //  intent.setDataAndType(uri, "application/vnd.android.package-archive");



        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            Log.v("TAG","7.0以上，正在安装apk...");
            //provider authorities
            //  Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.luminal.mjptouch.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            Log.v("TAG","7.0以下，正在安装apk...");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        getActivity().startActivity(intent);
    }
*/
@Override
public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    boo1=3;

}
public void exit() {
    if(!isExit) {
        isExit = true;
        web.loadUrl(UrlCount.Base_Head+"mobile/geren_zhongxin.html");

    } else {
        //getActivity().finish();
    }
}
    /**
     * 实现图缩放
     * @param target
     * @return
     */
    public Bitmap zoomBitmap(Bitmap target){
        // 得到图片的高宽
        int width = target.getWidth();
        int height = target.getHeight();
        Matrix matrix = new Matrix();
        // 算出图片的高宽缩放比例
        float scaleWidth = ((float)300)/ width;
        float scaleHeight = ((float)300)/ height;
        matrix.postScale(scaleWidth, scaleHeight);
//      Bitmap result = Bitmap.createBitmap(target,0,0,width,height, matrix,true);
        return Bitmap.createBitmap(target,0,0,width,height, matrix,true);
    }

    //截图
    public static Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.destroyDrawingCache();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    private Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        //w/2 - sW/2   ,   h/2 - sH/2
       // int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
        int cropWidth=w/2-500/2;
       // cropWidth /= 2;
        int cropHeight=h/2-400/2;
       // int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, 0 , h/13, w, h-(h/4), null, false);
    }
public void myBitmap(){
        //截图
    bitmap = loadBitmapFromViewBySystem(web);
    img.setImageBitmap(cropBitmap(bitmap));
    img.setVisibility(View.VISIBLE);
    img.enable();
    img.setBackgroundColor(Color.BLACK);
    boo=false;
       img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   boo=true;
                   img.setVisibility(View.GONE);

           }
       });
}





}
