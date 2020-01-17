package com.zupu.zp.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.zupu.zp.R;

import org.greenrobot.eventbus.EventBus;

public class Myservice extends Service {
    Mymendth mymendth = new Mymendth();
    MediaPlayer mediaPlayer;
    public class Mymendth extends Binder {
        public  void  pause(){
                mediaPlayer.stop();
        }

        public  void  play(){
             mediaPlayer = MediaPlayer.create(Myservice.this, R.raw.xinan);
             mediaPlayer.start();
          /*  mediaPlayer.reset();*/
            /**
             * 循环
             */
             mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                 @Override
                 public void onCompletion(MediaPlayer mp) {
                     mediaPlayer.start();
                     mediaPlayer.setLooping(true);
                     //EventBus.getDefault().post(00);
                 }
             });
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
      /*  myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.bw.APP");
        registerReceiver(myBroadcast,intentFilter);*/
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mymendth;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(myBroadcast);

        if (mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;

        }
    }
}

