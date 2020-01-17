package com.zupu.zp.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.bean.GetuiBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.ui.PublishStreamAndPlayStreamUI;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.zupu.zp.entity.ZegoApplication.getContexta;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/21 17:02
 * update: 接收推送消息
 */
public class DemoIntentService extends GTIntentService {
    PublishStreamAndPlayStreamUI publishStreamAndPlayStreamUI = new PublishStreamAndPlayStreamUI();
    Message message = new Message();
    SharedPreferences sharedPreferences;
    String uuid,userid;
    public DemoIntentService() {
       // EventBus.getDefault().register(this);
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.e(TAG, "onReceiveMessageData: 收到推送消息" );
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        userid=sharedPreferences.getString("userId",null); ;
        // 透传消息的处理方式，详看SDK demo
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.d(TAG, "receiver payload = "+ data);
            try {
                    ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();
                    Gson gson = new Gson();
                    GetuiBean getuiBean = gson.fromJson(data, GetuiBean.class);
                if (getuiBean.getTitle().equals("-1")){
                    final Intent news = new Intent(this, PublishStreamAndPlayStreamUI.class);
                    news.putExtra("roomID",getuiBean.getRoomid().toString());
                    news.putExtra("flag","1");
                    news.putExtra("myuserid",userid);
                    news.putExtra("isvoice",getuiBean.isIsvoice());
                    news.putExtra("userid",getuiBean.getUserid().toString());
                    news.putExtra("username",getuiBean.getUserName().toString());
                    news.putExtra("userhead",getuiBean.getUserHead().toString());
                    //news.putExtra("time",getuiBean.getTime());
                    news.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Service跳转到Activity 要加这个标记
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(news);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 300);//3秒后执行TimeTask的run方法
                }

                //接听
                if (getuiBean.getTitle().equals("0")){
                    EventBus.getDefault().post(0);
                }
                //挂断
                if (getuiBean.getTitle().equals("1")){
                    EventBus.getDefault().post(1);
                }
                //取消
                if (getuiBean.getTitle().equals("2")){
                    EventBus.getDefault().post(2);
                }
                if (getuiBean.getTitle().equals("3")){
                    EventBus.getDefault().post(3);
                }

                //sendMessage(data, 0);//这里对透传消息进行发送 通过App中的方法进行处理
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {

        Log.e(TAG, "onReceiveOnlineState:在线状态 " );
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {



    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, final GTNotificationMessage msg) {
       /* Log.e(TAG, "发过来的消息1: "+msg.getContent()+"标题"+msg.getTitle() );
        boolean background = isBackground(this);
        if (background){
            Log.e(TAG, "onNotificationMessageArrived:后台" );
            createNotificationChannel(this);
        }else {
            createNotificationChannel(this);
            Log.e(TAG, "onNotificationMessageArrived:前台" );
        }*/
        Log.e(TAG, "onNotificationMessageArrived: 普通" );
        if (msg.getTitle().equals("-1")){
            ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();
             String content = msg.getContent();
             Gson gson = new Gson();
             Object o = gson.fromJson(content,GetuiBean.class );
             EventBus.getDefault().post(o);
             GetuiBean getuiBean = gson.fromJson(content, GetuiBean.class);
             final Intent news = new Intent(this, PublishStreamAndPlayStreamUI.class);
            Log.e(TAG, "结果: "+getuiBean.getRoomid() );
            news.putExtra("roomID",getuiBean.getRoomid().toString());
            news.putExtra("flag","1");
            news.putExtra("userid",getuiBean.getUserid().toString());
            news.putExtra("username",getuiBean.getUserName().toString());
            news.putExtra("userhead",getuiBean.getUserHead().toString());
            news.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Service跳转到Activity 要加这个标记
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    startActivity(news);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 300);//3秒后执行TimeTask的run方法
        }


    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    public void createNotificationChannel(Context context ) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,
                new Intent(context, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        String id = "channel_001";
        String name = "name";
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//判断API
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle("通知")
                    .setContentText("有人呼叫")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher_round).build();
        }else{
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("通知")
                    .setContentText("有人呼叫")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();

        }

        notificationManager.notify(1,notification);

    }



}
