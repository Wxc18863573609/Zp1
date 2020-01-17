package com.zupu.zp.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;

import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zupu.zp.bean.PageBean;
import com.zupu.zp.service.DemoIntentService;
import com.zupu.zp.service.DemoPushService;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.ShareUtil;
import com.zupu.zp.utliss.App;
import com.zupu.zp.utliss.MyProgressDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zego on 2018/10/16.
 */

public class ZegoApplication extends MultiDexApplication {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1011:
                    EventBus.getDefault().post(10011);
                    break;
            }
        }
    };
    public static final String BAIDU_MAP_APP = "com.baidu.BaiduMap";
    public static final String BAIDU_MAP_NAVI_URI = "baidumap://map/navi?query=";

    public final static String BAIDU_PACKAGENAME = "com.baidu.BaiduMap";
    public final static String GAODE_PACKAGENAME = "com.autonavi.minimap";
    public final static String TENCENT_PACKAGENAME = "com.tencent.map";
    public static  Boolean boos=false;
    public  static  int wxBds=1;
    public static int a=0;
    public  static int index=-1;
   public static   ArrayList<PageBean> listindex= new ArrayList<>();
    public static Integer indexa=0;
    static MyProgressDialog    dialog;
    public static ZegoApplication zegoApplication;
    public static  ZegoApplication mApplications;
    private static SharedPreferences sharedPreferences;
    public  static Context mContext;
    public static String APP_ID = "wx3c8adc40cdcd29e6";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        mApplications = this;
        zegoApplication = (ZegoApplication) getApplicationContext();
        sharedPreferences = getSharedPreferences("loging", MODE_PRIVATE);
        String randomSuffix = "-" + new Date().getTime()%(new Date().getTime()/1000);
         //64k
         MultiDex.install(this);
         String userId = DeviceInfoManager.generateDeviceId(this) + randomSuffix;
        //303f33d25f61456eafcc00f42a3b8749
        //de12418798634a40885eb1d630a8da83
       // String userId = sharedPreferences.getString("appLoginIdentity",null);
        String userName = DeviceInfoManager.getProductName() + randomSuffix;
       // String userName =sharedPreferences.getString("nickName",null); ;
       /* String userId = "11";
        String userName = "zhang";
*/
         // DemoPushService 为【步骤2.6】自定义的推送服务
        com.igexin.sdk.PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
// DemoIntentService 为第三方自定义的推送服务事件接收类
        com.igexin.sdk.PushManager.getInstance().registerPushIntentService(getApplicationContext(), DemoIntentService.class);
        //解决7.0拍照问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        // 添加悬浮日志视图
        FloatingView.get().add();

        //buglay日志
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        userStrategy.setAppChannel("myChannel");     //渠道
        userStrategy.setAppVersion("1.0");           //版本
        userStrategy.setAppPackageName("com.zupu.zp");
        CrashReport.initCrashReport(this,"03db46d312",false,userStrategy);
   /*     // 使用Zego sdk前必须先设置SDKContext。
        ZGBaseHelper.sharedInstance().setSDKContextEx(userId, userName, null, null, 10 * 1024 * 1024, this);

        AppLogger.getInstance().i(ZegoApplication.class, "SDK version : %s",  ZegoLiveRoom.version());
        AppLogger.getInstance().i(ZegoApplication.class, "VE version : %s",  ZegoLiveRoom.version2());

        // bugly初始化用户id
        CrashReport.initCrashReport(getApplicationContext(), "7ace07528f", false);
        CrashReport.setUserId(userId);*/
        Message message = new Message();
        message.what=1011;
        handler.sendMessageDelayed(message,1000);
    }
    /**获取Context.
     * @return
     */
    public static Context getContexts(){
        return zegoApplication;
    }
    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContexta() {
        return mApplications;
    }

    //登录后保存数据userid的方法
    public static SharedPreferences Loging(){
        return sharedPreferences;
    }


    /***
     * 启动
     */
    public static void showProgress(Context context, String message) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.showMessage(message);
    }

    /***
     * 关闭
     */
    public static void dismissProgress(Context context) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.dismiss();
    }

}
