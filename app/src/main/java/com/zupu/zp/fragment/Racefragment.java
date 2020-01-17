package com.zupu.zp.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.PhotoView;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.zupu.zp.MainActivity.rg;
import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/5 20:03
 * update: 族谱
 */

public class Racefragment extends BaseFragment {

    boolean boo;
    private PhotoView img;
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings =new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;
    View view;
    Bitmap head=null;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    PopupWindow window1;
    String mediaUrl,srcurl;
    WebView web;
    Button btnhc;
    ProgressDialog progressDialog;
    ZfUtil zfUtil = new ZfUtil();
    SharedPreferences sharedPreferences;
    String uuid,userid,userName;
    SharedPreferences.Editor editor;
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.racelaout, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void initview() {
        img = view.findViewById(R.id.img);
        initNavi();
        web=view.findViewById(R.id.webview);
        RadioGroup rg=(RadioGroup)getActivity().findViewById(R.id.rg);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null); ;
        userName =sharedPreferences.getString("nickName",null); ;
        editor = sharedPreferences.edit();
        btnhc=view.findViewById(R.id.webhc);
    }

    @Override
    public void initdata() {

        btnhc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.clearCache(true);
            }
        });
        //进度
        web.setWebChromeClient(new WebChromeClient() {
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
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //弹出提示
                showProgress(getActivity(), "加载中...");
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
                dismissProgress(getActivity());
               /* //隐藏:不为空，正在显示。才隐藏关闭
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                boolean status = url.contains(UrlCount.Base_Head+"mobile/jiatingshu.html");
                boolean status1 = url.contains(UrlCount.Base_Head+"mobile/zupuwufu.html");
                boolean status2 = url.contains(UrlCount.Base_Head+"mobile/super_zp.html");
                if (status||status1||status2){
                    rg.setVisibility(View.VISIBLE);
                }else {
                    rg.setVisibility(View.GONE);
                }
            }
        });
        WebSettings webSettings = web.getSettings();
        //启用数据库
        webSettings.setDatabaseEnabled(true);
      //设置定位的数据库路径
        String dir = getActivity().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
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
        web.loadUrl(UrlCount.Base_Head+"mobile/jiatingshu.html");
//https://zupu.honarise.com/mobile/disney_family_museum.html
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
        boo1=4;

    }
    public void exit() {
        if(!isExit) {
            isExit = true;
            web.loadUrl(UrlCount.Base_Head+"mobile/jiatingshu.html");

        } else {
            //getActivity().finish();
        }
    }

    @Override
    public void initlinsenter() {
/*    cs.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(getContext(),
                        "相加结果："+value, Toast.LENGTH_SHORT).show();
            }
        });


    }
});*/

    }

    @Override
    public void persenter() {

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

    }

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
            boolean status = a.contains(UrlCount.Base_Head);
            if (status){
                web.loadUrl(a);
            }else {
                String method = "javascript:getDw(\"" + a + "\")";
                web.loadUrl(method);
            }

        }
        if (o instanceof Integer) {
            Integer a = (Integer) o;
            if (a==3001){
                myBitmap();
            }
        }

    }
   /* private class AndroidJs{

        //4....写调用的方法,被js调用的代码需要加上下面的注解
        @JavascriptInterface
        public String uponePic() {
            Toast.makeText(getActivity(), "Android", Toast.LENGTH_SHORT).show();
            oneUpPic();
            return mediaUrl;
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
        @JavascriptInterface
        public void wxShare( String uuid,  String subjectId,  String type, String title,  String applyId) {
            zfUtil.shareMethod(uuid,subjectId,type, title,applyId,getActivity());
            Log.e("TAG", "wxShare: "+uuid+subjectId+title);
        }

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
    *//**
     * 调用系统的裁剪
     *
     *
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
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
    }*/


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
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
    }



    public void myBitmap(){
        Bitmap bitmap = loadBitmapFromViewBySystem(web);
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




}
