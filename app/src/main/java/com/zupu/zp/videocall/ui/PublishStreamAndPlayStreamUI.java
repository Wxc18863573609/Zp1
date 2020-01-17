package com.zupu.zp.videocall.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.bean.GetuiBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.databinding.PublishStreamAndPlayStreamBinding;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.service.Myservice;
import com.zupu.zp.testpakeyge.BaseActivitys;
import com.zupu.zp.utliss.CountDownTimer;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.PollingUtil;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.model.PublishStreamAndPlayStreamLayoutModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.zupu.zp.entity.ZegoApplication.getContexta;

public class PublishStreamAndPlayStreamUI extends BaseActivitys {
    private TextView mTvCountdowntimer;
    //1小时换算成毫秒
    String contentId;
    private int timeStemp;
    private CountDownTimer timer;
    String roomid;
    Runnable runnable;
    PollingUtil pollingUtil;
    int flag00=1;


    /**
     * 获取当前Activity绑定的DataBinding
     *
     * @return
     */

    public PublishStreamAndPlayStreamBinding getPublishStreamAndPlayStreamBinding() {
        return publishStreamAndPlayStreamBinding;
    }

    //这里使用Google官方的MVVM框架DataBinding来实现UI逻辑，开发者可以根据自己的情况使用别的方式编写UI逻辑
    protected PublishStreamAndPlayStreamBinding publishStreamAndPlayStreamBinding;

    // 这里为防止多个设备测试时相同流id冲推导致的推流失败，这里使用时间戳的后4位来作为随机的流id，开发者可根据业务需要定义业务上的流id
    private String mPublishStreamid = "s-streamid-" + new Date().getTime() % (new Date().getTime() / 10000);

    // 推拉流布局模型
    PublishStreamAndPlayStreamLayoutModel mPublishStreamAndPlayStreamLayoutModel;
    // 当拉多条流时，把流id的引用放到ArrayList里
    private ArrayList<String> playStreamids = new ArrayList<>();
    public static LinearLayout layout1, layout2;
    //默认本地视频宽度  90dp
    private int defaultLocalwidth = 100;
    private int defaultLocalHeight;
    //默认本地视频边距  20dp
    private int defaultLocalMargin = 20;
    private TextView jieusername, fausername;
    private ImageView jiehead, fahead;
    RelativeLayout retime;
    //默认本地视图的状态
    private boolean mSate = true;
    //本地视图大小
    private static RelativeLayout rlRemote;
    public static RelativeLayout rlLocal, reyaoqing, rejie, gua, gua1;
    ImageView guaduan, jieting, call, guaimg, guaimg1;
    private Handler uiHandler;
    String flag, myuserid;
    Httputlis1 instance = Httputlis1.getInstance();
    Myservice.Mymendth mymendth;
    ServiceConnection serviceConnection;
    String HUserid;
    String userid;
    String targetuserid;
    SharedPreferences sharedPreferences;
    TextView texta,tagetname;
    ImageView imga,targethead;

    /*    private IntentFilter intentFilter;
        private MyBroadcastReceiver myBroadcastReceiver;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用DataBinding加载布局
        publishStreamAndPlayStreamBinding = DataBindingUtil.setContentView(this, R.layout.publish_stream_and_play_stream);
        EventBus.getDefault().register(this);
        initview();
       /* //注册广播
        intentFilter = new IntentFilter();
        intentFilter.addAction("broadcast_demo.MaimAcivity");
        myBroadcastReceiver = new MyBroadcastReceiver();
        PublishStreamAndPlayStreamUI.this.registerReceiver(myBroadcastReceiver, intentFilter);*/

        // 在退出重进房间的时候UI上记录上一次摄像头开关和麦克风开关的状态
        publishStreamAndPlayStreamBinding.MicrophoneState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgMicState());
        publishStreamAndPlayStreamBinding.CameraState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgCameraState());

        // 设置麦克风和摄像头的点击事件
        setCameraAndMicrophoneStateChangedOnClickEvent();

        // 从VideoCommunicationMainUI的Activtity中获取传过来的RoomID，以便登录登录房间并马上推流

        Intent intent = new Intent(PublishStreamAndPlayStreamUI.this, Myservice.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mymendth = (Myservice.Mymendth) service;
                mymendth.play();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        //自己的userid
        // myuserid=sharedPreferences.getString("myuserid",null); ;
        Intent it = getIntent();
        myuserid = it.getStringExtra("myuserid");
        roomid = it.getStringExtra("roomID");
        flag = it.getStringExtra("flag");
        if (flag.equals("0")) {

            if (it.getBooleanExtra("isvoice", false)) {
                ZGVideoCommunicationHelper.sharedInstance().enableCamera(false);
            }
            String username = it.getStringExtra("username");
            String userhead = it.getStringExtra("userhead");
             contentId = it.getStringExtra("contentId");
            int time = it.getIntExtra("time", 0);
            timeStemp=time;
            userid = it.getStringExtra("userid");
            Glide.with(this).load(userhead).into(fahead);
            Glide.with(this).load(userhead).into(imga);
            fausername.setText(username);
            texta.setText(username);
            gua.setVisibility(View.GONE);
            gua1.setVisibility(View.GONE);
            ZGVideoCommunicationHelper.sharedInstance().enableMic(false);
            layout2.setVisibility(View.GONE);
            reyaoqing.setVisibility(View.VISIBLE);
            rejie.setVisibility(View.GONE);
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

            /** 倒计时60秒，一次1秒 */
            CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    if (flag00==1){

                        HashMap<String, Object> map3 = new HashMap<>();
                        map3.put("id", roomid);
                        map3.put("consultResult", 1);
                        instance.posthttps(UrlCount.URL_techerzt, map3, new Httputlis1.Mycallbacks() {
                            @Override
                            public void sucess(String json) {

                                finish();
                            }
                        });
                    }
                }
            }.start();


        } else {
            if (it.getBooleanExtra("isvoice", false)) {
                ZGVideoCommunicationHelper.sharedInstance().enableCamera(false);
            }
            String username = it.getStringExtra("username");
            String userhead = it.getStringExtra("userhead");
            int time = it.getIntExtra("time", 0);
            timeStemp=time;
            jieusername.setText(username);
            Glide.with(this).load(userhead).into(jiehead);
            Glide.with(this).load(userhead).into(imga);
            Glide.with(this).load(userhead).into(targethead);
            tagetname.setText(username);
            texta.setText(username);
            userid = it.getStringExtra("userid");
            gua.setVisibility(View.GONE);
            gua1.setVisibility(View.GONE);
            ZGVideoCommunicationHelper.sharedInstance().enableMic(false);
            layout2.setVisibility(View.GONE);
            rejie.setVisibility(View.VISIBLE);
            reyaoqing.setVisibility(View.GONE);
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

        }
        // 设置当前UI界面左上角的点击事件，点击之后结束当前Activity，退出房间，SDK内部在退出房间的时候会自动停止推拉流
        publishStreamAndPlayStreamBinding.goBackToVideoCommunicationInputRoomidUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ZGVideoCommunicationHelper.sharedInstance().setZGVideoCommunicationHelperCallback(new ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback() {

            @Override
            public TextureView addRenderViewByStreamAdd(ZegoStreamInfo listStream) {

                if (flag.equals("0")){
                    jieting();
                }

                TextureView playRenderView = PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.addStreamToViewInLayout(listStream.streamID);
                PublishStreamAndPlayStreamUI.this.playStreamids.add(listStream.streamID);
                return playRenderView;
            }

            @Override
            public void onLoginRoomFailed(int errorcode) {

                if (ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback.NUMBER_OF_PEOPLE_EXCEED_LIMIT == errorcode) {
                    Toast.makeText(PublishStreamAndPlayStreamUI.this, "房间已满人，只能2人通讯", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PublishStreamAndPlayStreamUI.this, "登录房间失败，请检查网络", Toast.LENGTH_LONG).show();
                }
                PublishStreamAndPlayStreamUI.this.onBackPressed();

            }

            @Override
            public void onPublishStreamFailed(int errorcode) {

                Toast.makeText(PublishStreamAndPlayStreamUI.this, "开启视频通话失败，检查网络", Toast.LENGTH_LONG).show();
                onBackPressed();

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void removeRenderViewByStreamDelete(ZegoStreamInfo streamInfo) {
                PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.removeStreamToViewInLayout(streamInfo.streamID, PublishStreamAndPlayStreamUI.this);
                PublishStreamAndPlayStreamUI.this.playStreamids.remove(streamInfo.streamID);
                guaduan();
                finish();

            }


        });

        // 这里创建多人连麦的Model的实例
        this.mPublishStreamAndPlayStreamLayoutModel = new PublishStreamAndPlayStreamLayoutModel(this);

        // 这里在登录房间之后马上推流并做本地推流的渲染
        if (flag.equals("0")){
            TextureView localPreviewView = PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.addStreamToViewInLayout(PublishStreamAndPlayStreamUI.this.mPublishStreamid);
            // 这里进入当前Activty之后马上登录房间，在登录房间的回调中，若房间已经有其他流在推，从登录回调中获取拉流信息并拉这些流
            ZGVideoCommunicationHelper.sharedInstance().startVideoCommunication(roomid, localPreviewView, mPublishStreamid);
        }



        defaultLocalMargin = DisplayUtil.dip2px(this, defaultLocalMargin);
        defaultLocalwidth = DisplayUtil.dip2px(this, defaultLocalwidth);
        defaultLocalHeight = (int) (DisplayUtil.getScreenHeight(this) / (float) DisplayUtil.getScreenWidth(this) * defaultLocalwidth);


        uiHandler = new Handler();
        postDelayedCloseBtn();
        zoomOpera1(rlRemote, rlLocal);





        //接听
        jieting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag00=0;
                pollingUtil = new PollingUtil(new Handler(getMainLooper()));
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id", roomid);
                        instance.posthttps(UrlCount.URL_callzhong, map, new Httputlis1.Mycallbacks() {
                            @Override
                            public void sucess(String json) {
                                Log.e("TAG", "sucess: 通话中");
                            }
                        });

                    }
                };
                pollingUtil.startPolling(runnable, 10000, true);


                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("id", roomid);
                map1.put("type", "1");
                instance.posthttps(UrlCount.URL_CllZt, map1, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        PhoneBean phoneBean = gson.fromJson(json, PhoneBean.class);
                        if (phoneBean.getCode() == 0) {
                            Log.e("TAG", "sucess:" + json);
                        }
                    }
                });

                HashMap<String, Object> map6 = new HashMap<>();
                map6.put("id", roomid);
                map6.put("consultResult", 3);
                instance.posthttps(UrlCount.URL_techerzt, map6, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Log.e("TAG", "老师");
                       // getCountDownTime();
                        mTvCountdowntimer.setVisibility(View.GONE);

                    }
                });
                retime.setVisibility(View.VISIBLE);
                zoomOpera(rlLocal, rlRemote);
                mSate = true;
                mymendth.pause();
                gua.setVisibility(View.GONE);
                gua1.setVisibility(View.VISIBLE);
                rejie.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.VISIBLE);
                ZGVideoCommunicationHelper.sharedInstance().enableMic(true);
              /*  JSONObject root = new JSONObject();
                try {
                    root.put("title", "0");
                    root.put("userName", null);
                    root.put("userHead", null);
                    root.put("userid", null);
                    root.put("roomid", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                zoomOpera(rlLocal, rlRemote);
                mSate = true;
                mymendth.pause();
                gua.setVisibility(View.GONE);
                gua1.setVisibility(View.VISIBLE);
                rejie.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.VISIBLE);
                ZGVideoCommunicationHelper.sharedInstance().enableMic(true);
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(myuserid));
                map.put("targetuId", Integer.parseInt(userid));
                map.put("pushContent", "username:");
                map.put("uri", "000");
                map.put("title", "-1");
                map.put("text", "1");
                map.put("url", root.toString());
                instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {

                    }
                });*/
                TextureView localPreviewView = PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.addStreamToViewInLayout(PublishStreamAndPlayStreamUI.this.mPublishStreamid);
                ZGVideoCommunicationHelper.sharedInstance().startVideoCommunication(roomid, localPreviewView, mPublishStreamid);
            }
        });
        //接听时的挂断
        guaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //getguaduan(myuserid, userid);
                JSONObject root = new JSONObject();
                try {
                    root.put("title", "1");
                    root.put("userName", null);
                    root.put("userHead", null);
                    root.put("userid", null);
                    root.put("roomid", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(myuserid));
                map.put("targetuId", Integer.parseInt(userid));
                map.put("pushContent", "username:");
                map.put("uri", "000");
                map.put("title", "-1");
                map.put("text", "1");
                map.put("url", root.toString());
                // map.put("sound","call.mp3");
                instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        finish();
                    }
                });
          /*      HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(myuserid));
                map.put("targetuId", Integer.parseInt(userid));
                map.put("pushContent","");
                map.put("uri","");
                map.put("title","1");
                map.put("text","1");
                   instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                       @Override
                       public void sucess(String json) {
                           finish();
                       }
                   });*/

            }
        });
        String randomSuffix = "-" + new Date().getTime() % (new Date().getTime() / 1000);
        //通话时挂断
        guaimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map7 = new HashMap<>();
                map7.put("id", roomid);
                map7.put("consultResult", 5);
                instance.posthttps(UrlCount.URL_techerzt, map7, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Log.e("TAG", "老师");
                    }
                });

                getguaduan(myuserid, userid);
            }
        });
        //通话时挂断
        guaimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getguaduan(myuserid, userid);
                HashMap<String, Object> map11 = new HashMap<>();
                map11.put("id", roomid);
                map11.put("consultResult", 4);
                instance.posthttps(UrlCount.URL_techerzt, map11, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Log.e("TAG", "学生");
                    }
                });
             /*   HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt("110"));
                map.put("targetuId", Integer.parseInt("203"));
                map.put("pushContent","username:");
                map.put("uri","");
                map.put("title","3");
                map.put("text","1");
                instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        finish();
                    }
                });*/
            }
        });
        //点击取消
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("0")) {
                    HashMap<String, Object> map4 = new HashMap<>();
                    map4.put("id", roomid);
                    map4.put("consultResult", 4);
                    instance.posthttps(UrlCount.URL_techerzt, map4, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            Log.e("TAG", "老师");
                        }
                    });
                    HashMap<String, Object> mapz = new HashMap<>();
                    mapz.put("contentId",contentId);
                    mapz.put("pushId", Integer.parseInt(userid));
                    Log.e("TAG", "撤销"+contentId+userid+"" );
                    instance.posthttps(UrlCount.URL_GtCx, mapz, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            Log.e("TAG", "撤销" );
                        }
                    });

                } else {
                    HashMap<String, Object> map41 = new HashMap<>();
                    map41.put("id", roomid);
                    map41.put("consultResult", 5);
                    instance.posthttps(UrlCount.URL_techerzt, map41, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            Log.e("TAG", "老师");
                        }
                    });
                }

                JSONObject root = new JSONObject();
                try {
                    root.put("title", "2");
                    root.put("userName", null);
                    root.put("userHead", null);
                    root.put("userid", null);
                    root.put("roomid", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(myuserid));
                map.put("targetuId", Integer.parseInt(userid));
                map.put("pushContent", "用户取消通话");
                map.put("uri", "000");
                map.put("title", "通知");
                map.put("text", "1");
                map.put("url", root.toString());
                // map.put("sound","call.mp3");
                instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        finish();
                    }
                });
            }
        });


        //大视图
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mSate) {
                    zoomOpera(rlRemote, rlLocal);
                    mSate = true;
                    gua.setVisibility(View.VISIBLE);
                    gua1.setVisibility(View.GONE);
                } else {
                    //changeStatus();
                }
            }
        });

        //小视图
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mSate) {
                    zoomOpera(rlLocal, rlRemote);
                    mSate = false;
                    gua.setVisibility(View.GONE);
                    gua1.setVisibility(View.VISIBLE);
                } else {
                    //  changeStatus();
                }

            }
        });

    }

    /**
     * 定义设置麦克风和摄像头开关状态的点击事件
     */
    private void setCameraAndMicrophoneStateChangedOnClickEvent() {

        // 设置摄像头开关的点击事件
        this.publishStreamAndPlayStreamBinding.CameraState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    ZGVideoCommunicationHelper.sharedInstance().enableCamera(true);
                } else {
                    ZGVideoCommunicationHelper.sharedInstance().enableCamera(false);

                }

            }
        });

        // 设置麦克风开关的点击事件
        this.publishStreamAndPlayStreamBinding.MicrophoneState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    ZGVideoCommunicationHelper.sharedInstance().enableMic(true);
                } else {
                    ZGVideoCommunicationHelper.sharedInstance().enableMic(false);
                }

            }
        });
    }

    /**
     * 当返回当前Activity的时候应该停止推拉流并退出房间，此处作为参考
     */
    @Override
    public void onBackPressed() {

        this.mPublishStreamAndPlayStreamLayoutModel.removeAllStreamToViewInLayout();
        ZGVideoCommunicationHelper.sharedInstance().quitVideoCommunication(this.playStreamids);
        super.onBackPressed();
    }

    /**
     * 供其他Activity调用，进入当前Activity进行推拉流
     *
     * @param activity
     */
    public static void actionStart(Activity activity, String roomID) {
        Intent intent = new Intent(activity, PublishStreamAndPlayStreamUI.class);
        intent.putExtra("roomID", roomID);

        activity.startActivity(intent);
    }

    /**
     * 返回到输入房间id的UI界面
     *
     * @param v 点击返回的按钮
     */
    public void goBackToVideoCommunicationInputRoomidUI(View v) {
        this.onBackPressed();
    }


    /**
     * 大小视图切换 （小视图在前面、大视图在后面）
     */

    private void zoomOpera1(View sourcView,
                            View detView) {
        RelativeLayout paretview = (RelativeLayout) sourcView.getParent();
        paretview.removeView(detView);
        paretview.removeView(sourcView);

        //设置远程大视图RelativeLayout 的属性
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        sourcView.setLayoutParams(params1);

        //设置本地小视图RelativeLayout 的属性
        params1 = new RelativeLayout.LayoutParams(defaultLocalwidth, defaultLocalHeight);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.setMargins(0, defaultLocalMargin, defaultLocalMargin, 0);
        detView.setLayoutParams(params1);

        paretview.addView(sourcView);
        paretview.addView(detView);
    }

    /**
     * 开启取消延时动画
     */
    private void postDelayedCloseBtn() {
        uiHandler.removeCallbacksAndMessages(null);
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  llCallContainer.setVisibility(View.GONE);
                hideStatusBar();
            }
        }, 5000);
    }

    /**
     * 隐藏标题栏
     */
    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }


    private static void zoomOpera(View sourcView, View detView) {
        int LocalMargin = DisplayUtil.dip2px(getContexta(), 20);
        int width = DisplayUtil.dip2px(getContexta(), 100);
        int Height = (int) (DisplayUtil.getScreenHeight(getContexta()) / (float) DisplayUtil.getScreenWidth(getContexta()) * width);

        RelativeLayout paretview = (RelativeLayout) sourcView.getParent();
        paretview.removeView(detView);
        paretview.removeView(sourcView);

        //设置远程大视图RelativeLayout 的属性
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
     /*   beforeview.setZOrderMediaOverlay(true);
        beforeview.getHolder().setFormat(PixelFormat.TRANSPARENT);*/
        sourcView.setLayoutParams(params1);

        //设置本地小视图RelativeLayout 的属性
        params1 = new RelativeLayout.LayoutParams(width, Height);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.setMargins(0, LocalMargin, LocalMargin, 0);
        //在调用setZOrderOnTop(true)之后调用了setZOrderMediaOverlay(true)  遮挡问题
      /*  afterview.setZOrderOnTop(true);
        afterview.setZOrderMediaOverlay(true);
        afterview.getHolder().setFormat(PixelFormat.TRANSPARENT);*/
        detView.setLayoutParams(params1);

        paretview.addView(sourcView);
        paretview.addView(detView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pollingUtil!=null)
        {
            pollingUtil.endPolling(runnable);
        }
        EventBus.getDefault().unregister(this);
        //释放sdk
        ZGVideoCommunicationHelper.sharedInstance().releaseZGVideoCommunicationHelper();
        unbindService(serviceConnection);
        if (timer!= null) {
            timer.cancel();
        }
        timer= null;
    }

    public void jieting() {
        flag00=0;
        retime.setVisibility(View.VISIBLE);
        getCountDownTime();
        gua.setVisibility(View.VISIBLE);
        gua1.setVisibility(View.GONE);
        reyaoqing.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.VISIBLE);
        mymendth.pause();
        zoomOpera(rlLocal, rlRemote);
        ZGVideoCommunicationHelper.sharedInstance().enableMic(true);
    }

    public void guaduan() {
        Toast.makeText(getContexta(), "通话结束", Toast.LENGTH_SHORT).show();
        finish();
     /*   HashMap<String, Object> map1 = new HashMap<>();
        map1.put("id", roomid);
        map1.put("type", "2");
        instance.posthttps(UrlCount.URL_CllZt, map1, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                PhoneBean phoneBean = gson.fromJson(json, PhoneBean.class);
                if (phoneBean.getCode() == 0) {
                    Log.e("TAG", "sucess:" + json);
                    Toast.makeText(getContexta(), "通话结束", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });*/

    }

    public void quxiao() {
        createNotificationChannel(this);
        Toast.makeText(this, "对方已经取消", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

// 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.jg)
                    .setMessage(R.string.isjsdqth)
                    .setNegativeButton(R.string.back_qx,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            })
                    .setPositiveButton(R.string.shuor,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                    JSONObject root = new JSONObject();
                                    try {
                                        root.put("title", "1");
                                        root.put("userName", null);
                                        root.put("userHead", null);
                                        root.put("userid", null);
                                        root.put("roomid", null);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("uid", Integer.parseInt(myuserid));
                                    map.put("targetuId", Integer.parseInt(userid));
                                    map.put("pushContent", "username:");
                                    map.put("uri", "");
                                    map.put("title", "-1");
                                    map.put("text", "1");
                                    map.put("url", root.toString());
                                    // map.put("sound","call.mp3");
                                    instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
                                        @Override
                                        public void sucess(String json) {
                                            finish();
                                        }
                                    });
                                }
                            }).show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o) {
        if (o instanceof Integer){
            int a = (Integer) o;
            if (a == 0) {
            } else if (a == 1) {
                guaduan();
            } else if (a == 2) {
                quxiao();

            } else if (a == 3) {
                //  guaduan();
            }else if (a==10){
                timeStemp=timeStemp+1800000;
                timer.cancel();
                getCountDownTime();
                Log.e("time", "时间:"+timeStemp);
            }else if(a==77){
                finish();
            }
        }

        if (o instanceof GetuiBean) {
            GetuiBean getuibean = (GetuiBean) o;
            Glide.with(this).load(getuibean.getUserHead()).into(jiehead);
            jieusername.setText(getuibean.getUserName());
            HUserid = getuibean.getUserid();
           // Log.e("TAG", "reEventBus:>>> ??" + HUserid);
        }



    }

    public void getguaduan(String myid, String heid) {
        finish();
       /* JSONObject root = new JSONObject();
        try {
            root.put("title", "1");
            root.put("userName", null);
            root.put("userHead", null);
            root.put("userid", null);
            root.put("roomid", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", Integer.parseInt(myid));
        map.put("targetuId", Integer.parseInt(heid));
        map.put("pushContent", "username:");
        map.put("uri", "000");
        map.put("title", "-1");
        map.put("text", "1");
        map.put("url", root.toString());
        instance.posthttps(UrlCount.URL_GtSend, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                finish();
            }
        });*/

    }

    /* public class MyBroadcastReceiver extends BroadcastReceiver {
         @Override
         public void onReceive(Context context, Intent intent) {
             Toast.makeText(PublishStreamAndPlayStreamUI.this, intent.getStringExtra("username")+"??", Toast.LENGTH_SHORT).show();
         }
     }*/
    private void getCountDownTime() {

        timer = new CountDownTimer(timeStemp, 1000) {
            @Override
            public void onTick(long l) {
                long day = l / (1000 * 24 * 60 * 60); //单位天
                long hour = (l - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                long minute = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                long second = (l - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒

                mTvCountdowntimer.setText(hour + "小时" + minute + "分钟" + second + "秒");

                //判断当前时间如果小于五分钟提示充值
                if (day==0&&hour==0&&minute==5&&second==0&&flag.equals("0")) {
                    Log.e("time", "时间:"+timeStemp);
                    AlertDialog.Builder bider=new AlertDialog.Builder(PublishStreamAndPlayStreamUI.this)
                            .setTitle(R.string.jg)
                            .setMessage(R.string.iscz)
                            .setNegativeButton(R.string.back_qx,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                        }
                                    })
                            .setPositiveButton(R.string.shuor,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            //进行充值
                                            new AlertDialog.Builder( PublishStreamAndPlayStreamUI.this)
                                                    .setTitle(R.string.cz1)
                                                    .setMessage(R.string.isgm30)
                                                    .setNegativeButton(R.string.back_qx,
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog,
                                                                                    int which) {
                                                                }
                                                            })
                                                    .setPositiveButton(R.string.shuor,
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                    //进行充值
                                                                    ZfUtil zfUtil = new ZfUtil();
                                                                    zfUtil.zfMethod(PublishStreamAndPlayStreamUI.this,roomid);
                                                                }
                                                            }).show();
                                        }
                                    });

                    if(!(PublishStreamAndPlayStreamUI.this).isFinishing())
                    {
                        //show dialog
                        bider.show();
                    }

                }
           /*     if (timeStemp <= 0) {
                    finish();
                    Toast.makeText(PublishStreamAndPlayStreamUI.this, "通话时间不足,通话结束", Toast.LENGTH_SHORT).show();
                }*/

            }

            @Override
            public void onFinish() {
                finish();
                Toast.makeText(PublishStreamAndPlayStreamUI.this, "通话时间不足,通话结束", Toast.LENGTH_SHORT).show();
                //倒计时为0时执行此方法
            }
        };

        timer.start();
    }

    public void initview() {
        tagetname=(TextView) findViewById(R.id.targetname);
        targethead=findViewById(R.id.tagethead);
        reyaoqing = findViewById(R.id.reyaoqing);
        jieusername = findViewById(R.id.user2name);
        jiehead = findViewById(R.id.head2);
        retime = findViewById(R.id.retime);
        fausername = findViewById(R.id.username);
        fahead = findViewById(R.id.head);
        rejie = findViewById(R.id.rejie);
        jieting = findViewById(R.id.jieting);
        guaduan = findViewById(R.id.guaduan);
        guaimg = findViewById(R.id.guaimg);
        gua = findViewById(R.id.gua);
        guaimg1 = findViewById(R.id.guaimg1);
        texta = findViewById(R.id.texta);
        imga = findViewById(R.id.imga);
        gua1 = findViewById(R.id.gua1);
        call = findViewById(R.id.call);
        mTvCountdowntimer = (TextView) findViewById(R.id.tv_countdowntimer);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        layout1 = findViewById(R.id.ll_view_container_0);
        layout2 = findViewById(R.id.ll_view_container_1);
        rlRemote = (RelativeLayout) findViewById(R.id.rl_remote);
        rlLocal = (RelativeLayout) findViewById(R.id.rl_local);

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
                    .setContentText("您有一个未接通话")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher_round).build();
        }else{
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("通知")
                    .setContentText("您有一个未接通话")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setOngoing(true)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();

        }

        notificationManager.notify(1,notification);

    }

}
