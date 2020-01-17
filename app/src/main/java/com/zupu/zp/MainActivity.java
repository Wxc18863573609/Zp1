package com.zupu.zp;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.DaocterBeans;
import com.zupu.zp.bean.JsonBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.fragment.Doctorfragment;
import com.zupu.zp.fragment.Livefragment;
import com.zupu.zp.fragment.Mallfragment;
import com.zupu.zp.fragment.Markfragment;
import com.zupu.zp.fragment.Museumfragment;
import com.zupu.zp.fragment.Myfragment;
import com.zupu.zp.fragment.Profragment;
import com.zupu.zp.fragment.Racefragment;
import com.zupu.zp.fragment.Xuefragment;
import com.zupu.zp.myactivity.EdjieTextActivity;
import com.zupu.zp.myactivity.LogingActivity;
import com.zupu.zp.myactivity.PdfActivity;
import com.zupu.zp.service.DemoIntentService;
import com.zupu.zp.service.DemoPushService;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.PKPlayActivityUI;
import com.zupu.zp.testpakeyge.PlayActivityUI;
import com.zupu.zp.utliss.BitmapUtil;
import com.zupu.zp.utliss.DensityUtil;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.PollingUtil;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.ui.PublishStreamAndPlayStreamUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.zupu.zp.base.mvp_no_dagger.BaseFragment.boo1;
import static com.zupu.zp.base.mvp_no_dagger.BaseFragment.jsons;
import static com.zupu.zp.entity.ZegoApplication.boos;
import static com.zupu.zp.entity.ZegoApplication.dismissProgress;

public class MainActivity extends BaseActivity {

    int count = 0;
    BitmapUtil bitmapUtil = new BitmapUtil();
    String mediaUrl;
    String srcurl = "";
    private long exitTime = 0;
    private String TAG = "mainactivity";
    private FrameLayout fragments;
    Runnable runnable;
    PollingUtil pollingUtil;
    public static Context mContext;
    public static RadioGroup rg;
    boolean boo = false;
    ZfUtil zfUtil = new ZfUtil();
    private ArrayList<Fragment> mList = new ArrayList<>();
    Livefragment livefragment;
    Mallfragment mallfragment;
    Markfragment markfragment;
    Museumfragment museumfragment;
    public static Myfragment myfragment;
    Profragment profragment;
    Racefragment racefragment;
    Xuefragment xuefragment;
    Doctorfragment doctorfragment;
    private FragmentManager manager;
    private RadioButton btn1;
    private RadioButton btn2;
    private RadioButton btn3;
    private RadioButton btn4;
    private RadioButton btn6;
    private RadioButton btn7;
    private RadioButton btn8;
    private RadioButton btn9;
    private RadioButton btn5;
    private Button btn10,btnss;
    private RelativeLayout rebithday;
    private Timer timer;
    String uuid;
    RelativeLayout rootView;
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings = new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;

    String userId, userName, userHead;
    View v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public int initlayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initview() {
        mContext = this;
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor = sharedPreferences.edit();
        // uuid=sharedPreferences.getString("appLoginIdentity",null);
        userId = sharedPreferences.getString("userId", null);
        ;
        userName = sharedPreferences.getString("nickName", null);
        userHead = sharedPreferences.getString("photoUrl", null);
        uuid = sharedPreferences.getString("appLoginIdentity", null);
        JSONObject root = new JSONObject();
        try {
            root.put("nickName", userName);
            root.put("photoUrl", userHead);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 使用Zego sdk前必须先设置SDKContext。
        ZGBaseHelper.sharedInstance().setSDKContextEx(userId, root.toString(), null, null, 10 * 1024 * 1024, getApplication());

        AppLogger.getInstance().i(ZegoApplication.class, "SDK version : %s", ZegoLiveRoom.version());
        AppLogger.getInstance().i(ZegoApplication.class, "VE version : %s", ZegoLiveRoom.version2());

        // bugly初始化用户id
        CrashReport.initCrashReport(getApplicationContext(), "7ace07528f", false);
        CrashReport.setUserId(userId);
        ZegoLiveRoom.setUser(userId, root.toString());
        // DemoPushService 为【步骤2.6】自定义的推送服务
        com.igexin.sdk.PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
// DemoIntentService 为第三方自定义的推送服务事件接收类
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
        btnss=findViewById(R.id.btnss);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v8 = findViewById(R.id.v8);
        v9 = findViewById(R.id.v9);
        v10 = findViewById(R.id.v10);
        v11 = findViewById(R.id.v11);
        rebithday = findViewById(R.id.rebithday);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
       // btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
      //  btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        fragments = findViewById(R.id.fragment);
        rg = findViewById(R.id.rg);
        manager = getSupportFragmentManager();
        ;

        hideAllFragement();
        if (profragment == null) {
            profragment = new Profragment();
            manager.beginTransaction().add(R.id.fragment, profragment).show(profragment).commit();

        } else {
            manager.beginTransaction().show(profragment).commit();
        }

    }

    @Override
    public void initdata() {
        Intent intent6 = getIntent();
        Uri uri = intent6.getData();
        if (uri!=null){
            String roomId = uri.getQueryParameter("roomId");
            String stroanId = uri.getQueryParameter("streamId");
            String isplayBack = uri.getQueryParameter("isPlayBack");
            String isPk = uri.getQueryParameter("isPK");
            String playUrl = uri.getQueryParameter("playUrl");
            Log.e("参数", roomId+":id  "+stroanId+"流id  "+isplayBack+"是否回放   " +isPk+"是否pk:    "+playUrl+"       地址");
            if (isPk.equals("0")){
                Intent intent1 = new Intent(MainActivity.this, PlayActivityUI.class);
                intent1.putExtra("roomID",roomId);
                intent1.putExtra("streamId",stroanId+"");
                intent1.putExtra("pull","pull");
                intent1.putExtra("isplayBack",Integer.parseInt(isplayBack));
                boolean equals = playUrl.equals("(null)");
                boolean equals1 = playUrl.equals("");
               // if (!equals&&!equals1)
                intent1.putExtra("replayurl",playUrl);
                startActivity(intent1);
            }else {
                Intent intent1 = new Intent(MainActivity.this, PKPlayActivityUI.class);
                intent1.putExtra("roomID",roomId);
                intent1.putExtra("streamID",stroanId+"");
                intent1.putExtra("pull","pull");
                intent1.putExtra("isplayBack",Integer.parseInt(isplayBack));
                boolean equals = playUrl.equals("(null)");
                boolean equals1 = playUrl.equals("");
               // if (!equals&&!equals1)
                intent1.putExtra("replayurl",playUrl);
                startActivity(intent1);
            }


        }
        timer = new Timer();
// 注意javax.swing包中也有一个Timer类，如果import中用到swing包,要注意名字的冲突。
        TimerTask task = new TimerTask() {
            public void run() {
                //每次需要执行的代码放到这里面。
                Log.e(TAG, "run: >>>>>>>>>>>>>");
            }
        };
        // 6.0及以上的系统需要在运行时申请CAMERA RECORD_AUDIO权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
            } else {
                Log.e(TAG, "相机权限已开启");
            }
        } else {
            Log.e(TAG, "版本较低 ");
        }
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(this,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }

        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();

        if (!isEnabled) {
            //未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请在“通知”中打开通知权限")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", MainActivity.this.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", MainActivity.this.getPackageName());
                                intent.putExtra("app_uid", MainActivity.this.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                            }
                            startActivity(intent);

                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);


        }
      //点击国际化
        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // lanGuage();
               // initI18();
            }
        });
//getlanguge()



    }

    @Override
    public void initlisenter() {


        btn6.setChecked(true);
        //生日校验
        String birthday = sharedPreferences.getString("birthday", null);
        if (birthday != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            if (birthday.equals(simpleDateFormat.format(date))) {
                rebithday.setVisibility(View.VISIBLE);
            }
        }
        //点击关闭
        rebithday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebithday.setVisibility(View.GONE);
            }
        });


        pollingUtil = new PollingUtil(new Handler(getMainLooper()));
        runnable = new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", userId);
                persenterimpl.posthttp(UrlCount.URL_onLine, map, PhoneBean.class);
            }
        };
        pollingUtil.startPolling(runnable, 10000, true);


        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boo) {
                    // manager.beginTransaction().replace(R.id.fragment, mList.get(0)).commit();
                    btn6.setChecked(true);
                    v2.setVisibility(View.GONE);
                    v3.setVisibility(View.GONE);
                    v4.setVisibility(View.GONE);
//                    v5.setVisibility(View.GONE);
                    v6.setVisibility(View.VISIBLE);
                    v7.setVisibility(View.VISIBLE);
                    v8.setVisibility(View.VISIBLE);
//                    v9.setVisibility(View.VISIBLE);
                    v10.setVisibility(View.VISIBLE);
                    v11.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
//                    btn4.setVisibility(View.GONE);
                    btn6.setVisibility(View.VISIBLE);
                    btn7.setVisibility(View.VISIBLE);
                    btn8.setVisibility(View.VISIBLE);
//                    btn9.setVisibility(View.VISIBLE);
                    boo = false;
                    // Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
                } else {
                    //  manager.beginTransaction().replace(R.id.fragment, mList.get(6)).commit();
                    btn1.setChecked(true);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.VISIBLE);
                   // v5.setVisibility(View.VISIBLE);
                    v6.setVisibility(View.GONE);
                    v7.setVisibility(View.GONE);
                    v8.setVisibility(View.GONE);
                   // v9.setVisibility(View.GONE);
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
//                    btn4.setVisibility(View.VISIBLE);
                    btn6.setVisibility(View.GONE);
                    btn7.setVisibility(View.GONE);
                    btn8.setVisibility(View.GONE);
                 //   btn9.setVisibility(View.GONE);
                    boo = true;
                    //  Toast.makeText(MainActivity.this, "fale", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //点击切换碎片
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn1:
                        //   manager.beginTransaction().replace(R.id.fragment, mList.get(6)).commit();
                        // fragments.setCurrentItem(6);
                        hideAllFragement();
                        if (livefragment == null) {
                            livefragment = new Livefragment();
                            manager.beginTransaction().add(R.id.fragment, livefragment).show(livefragment).commit();
                        } else {
                            manager.beginTransaction().show(livefragment).commit();
                        }

                        break;
                    case R.id.btn2:
                        //  manager.beginTransaction().replace(R.id.fragment, mList.get(8)).commit();
                        hideAllFragement();
                        if (xuefragment == null) {
                            xuefragment = new Xuefragment();
                            manager.beginTransaction().add(R.id.fragment, xuefragment).show(xuefragment).commit();
                        } else {
                            manager.beginTransaction().show(xuefragment).commit();
                        }
                        break;
                    case R.id.btn3:
                        //  manager.beginTransaction().replace(R.id.fragment, mList.get(5)).commit();
                        hideAllFragement();
                        if (doctorfragment == null) {
                            doctorfragment = new Doctorfragment();
                            manager.beginTransaction().add(R.id.fragment, doctorfragment).show(doctorfragment).commit();
                        } else {
                            manager.beginTransaction().show(doctorfragment).commit();
                        }
                        break;
                   /* case R.id.btn4:
                        // manager.beginTransaction().replace(R.id.fragment, mList.get(7)).commit();
                        hideAllFragement();
                        if (museumfragment == null) {
                            museumfragment = new Museumfragment();
                            manager.beginTransaction().add(R.id.fragment, museumfragment).show(museumfragment).commit();
                        } else {
                            manager.beginTransaction().show(museumfragment).commit();
                        }
                        break;*/
                    case R.id.btn6:

                        // manager.beginTransaction().replace(R.id.fragment, mList.get(0)).commit();
                        hideAllFragement();
                        if (profragment == null) {
                            profragment = new Profragment();
                            manager.beginTransaction().add(R.id.fragment, profragment).show(profragment).commit();
                        } else {
                            manager.beginTransaction().show(profragment).commit();
                        }

                        break;
                    case R.id.btn7:
                        // manager.beginTransaction().replace(R.id.fragment, mList.get(1)).commit();
                        hideAllFragement();
                        if (mallfragment == null) {
                            mallfragment = new Mallfragment();
                            manager.beginTransaction().add(R.id.fragment, mallfragment).show(mallfragment).commit();
                        } else {
                            manager.beginTransaction().show(mallfragment).commit();
                        }

                        break;
                    case R.id.btn8:
                        // manager.beginTransaction().replace(R.id.fragment, mList.get(2)).commit();
                        hideAllFragement();
                        if (racefragment == null) {
                            racefragment = new Racefragment();
                            manager.beginTransaction().add(R.id.fragment, racefragment).show(racefragment).commit();
                        } else {
                            manager.beginTransaction().show(racefragment).commit();
                        }
                        break;
                    case R.id.btn5:
                        //manager.beginTransaction().replace(R.id.fragment, mList.get(4)).commit();
                        hideAllFragement();
                        if (myfragment == null) {
                            myfragment = new Myfragment();
                            manager.beginTransaction().add(R.id.fragment, myfragment).show(myfragment).commit();
                        } else {
                            manager.beginTransaction().show(myfragment).commit();
                        }
                        break;
                 /*   case R.id.btn9:
                        //manager.beginTransaction().replace(R.id.fragment, mList.get(3)).commit();
                        hideAllFragement();
                        if (markfragment == null) {
                            markfragment = new Markfragment();
                            manager.beginTransaction().add(R.id.fragment, markfragment).show(markfragment).commit();
                        } else {
                            manager.beginTransaction().show(markfragment).commit();
                        }
                        break;*/

                }
            }
        });


    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {

        if (o instanceof PhoneBean) {
            PhoneBean phoneBean = (PhoneBean) o;
            if (phoneBean.getCode() == 0) {
                Log.e(TAG, "在线");
            }

        }
        if (o instanceof Picbean) {
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                EventBus.getDefault().post(picbean);
            }
        }
    }


    private void hideAllFragement() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (profragment != null) {
            transaction.hide(profragment);
        }
        if (mallfragment != null) {
            transaction.hide(mallfragment);
        }
        if (racefragment != null) {
            transaction.hide(racefragment);
        }
        if (markfragment != null) {
            transaction.hide(markfragment);
        }
        if (myfragment != null) {
            transaction.hide(myfragment);
        }
        if (doctorfragment != null) {
            transaction.hide(doctorfragment);
        }
        if (livefragment != null) {
            transaction.hide(livefragment);
        }
        if (museumfragment != null) {
            transaction.hide(museumfragment);
        }
        if (xuefragment != null) {
            transaction.hide(xuefragment);
        }

        transaction.commit();

    }

    /**
     * 这个函数在Activity创建完成之后会调用。
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }


    @Override
    protected void onStop() {
        super.onStop();
       /* mThread.interrupt();
        mThread = null;*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pollingUtil.endPolling(runnable);
        myfragment=null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (boo1==1){
                mallfragment.exit();
            }else if (boo1==2){
                profragment.exit();
            }else if (boo1==3){
                myfragment.exit();
            }else if (boo1==4){
                myfragment.exit();
            }else if (boo1==5){
                racefragment.exit();
            }else if (boo1==5){
                doctorfragment.exit();
            }else if (boo1==6){
                markfragment.exit();
            }else if (boo1==7){
                xuefragment.exit();
            }else if (boo1==8){
                museumfragment.exit();
            }
            exit();
            return false;

        }

        return super.onKeyDown(keyCode, event);

    }


    public void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {

            Toast.makeText(getApplicationContext(), "再按一次退出程序",

                    Toast.LENGTH_SHORT).show();

            exitTime = System.currentTimeMillis();

        } else {

            finish();

            System.exit(0);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 5:
                    if (requestCode == REQUEST_IMAGE3) {
                        if (resultCode == RESULT_OK) {
                            strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            Log.e("TAG", "图:" + strings);
                            for (int i = 0; i < strings.size(); i++) {
                                persenterimpl.puthttp(UrlCount.URL_UpPic, new File(bitmapUtil.compressImage(strings.get(i))), Picbean.class);
                            }

                        }
                    }
                    break;
            }
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: " );
        Intent intent2 = getIntent();
        Uri uri = intent2.getData();
        Log.e(TAG, uri+"" );
        if (uri!=null){
            String roomId = uri.getQueryParameter("roomId");
            String stroanId = uri.getQueryParameter("streamId");
            String isplayBack = uri.getQueryParameter("isPlayBack");
            String isPk = uri.getQueryParameter("isPK");
            String playUrl = uri.getQueryParameter("playUrl");
            Log.e("参数", roomId+":id  "+stroanId+"流id  "+isplayBack+"是否回放   " +isPk+"是否pk:    "+playUrl+"       地址");
            if (isPk.equals("0")){
                Intent intent1 = new Intent(MainActivity.this, PlayActivityUI.class);
                intent1.putExtra("roomID",roomId);
                intent1.putExtra("streamId",stroanId+"");
                intent1.putExtra("pull","pull");
                intent1.putExtra("isplayBack",Integer.parseInt(isplayBack));
                boolean equals = playUrl.equals("(null)");
                boolean equals1 = playUrl.equals("");
                // if (!equals&&!equals1)
                intent1.putExtra("replayurl",playUrl);
                startActivity(intent1);
            }else {
                Intent intent1 = new Intent(MainActivity.this, PKPlayActivityUI.class);
                intent1.putExtra("roomID",roomId);
                intent1.putExtra("streamID",stroanId+"");
                intent1.putExtra("pull","pull");
                intent1.putExtra("isplayBack",Integer.parseInt(isplayBack));
                boolean equals = playUrl.equals("(null)");
                boolean equals1 = playUrl.equals("");
                // if (!equals&&!equals1)
                intent1.putExtra("replayurl",playUrl);
                startActivity(intent1);
            }


        }
    }

    public void cler(){
      /*  Intent intent = new Intent(this, MainActivity.class);//跳到你的登录页面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void getlanguge(){
        int lanGuage = sharedPreferences.getInt("lanGuage", 1);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (lanGuage==1){
            config.locale = Locale.SIMPLIFIED_CHINESE;
            resources.updateConfiguration(config, dm);
            recreate();
            cler();

        }else if (lanGuage==2){
            config.locale = Locale.TRADITIONAL_CHINESE;
            resources.updateConfiguration(config, dm);
            recreate();
            cler();
        }

    }
        @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
          if (o instanceof  Integer){
              int a=(int)o;
              if (a==10011){
                  getlanguge();
              }
          }
        }
}
