package com.zupu.zp.lianmai.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.callback.im.IZegoIMCallback;
import com.zego.zegoliveroom.entity.ZegoBigRoomMessage;
import com.zego.zegoliveroom.entity.ZegoRoomMessage;
import com.zego.zegoliveroom.entity.ZegoUserState;
import com.zupu.zp.R;
import com.zupu.zp.databinding.ActivityJoinLiveMainBinding;
import com.zupu.zp.entity.RoomInfo;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.lianmai.adapter.RoomListAdapter;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.BaseActivitys;
import com.zupu.zp.testpakeyge.CustomDialog;
import com.zupu.zp.testpakeyge.ZegoUtil;
import com.zupu.zp.utliss.InitSdk;

import java.util.ArrayList;

public class JoinLiveMainActivityUI extends BaseActivitys {
    Button stars;
    private RoomListAdapter roomListAdapter = new RoomListAdapter();
    ZegoLiveRoom zegoLiveRoom = new ZegoLiveRoom();
    /**
     * 即构demo常用的一个库，用于简单的请求房间列表。
     */
   // private ZGAppSupportHelper zgAppSupportHelper;

    // 是否初始化成功
    private boolean isInitSuccess = false;

    private ActivityJoinLiveMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_live_main);
        binding.roomList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 设置adapter
        binding.roomList.setAdapter(roomListAdapter);
        // 设置Item添加和移除的动画
        binding.roomList.setItemAnimator(new DefaultItemAnimator());

         stars= findViewById(R.id.starts);
       //开始
        stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinLiveAudienceUI.actionStart(JoinLiveMainActivityUI.this, "1","1");

            }
        });

        // 设置下拉刷新事件监听
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //zgAppSupportHelper.api().updateRoomList(ZegoUtil.getAppID());
            }
        });

        // 设置当前 UI 界面左上角的点击事件，点击之后结束当前 Activity 并释放 SDK
        binding.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 初始化SDK
        initSDK();
       /* InitSdk initSdk = new InitSdk();*/
        //initsdks(this,"publish","1","1");
        // 对房间列表的点击事件监听
        roomListAdapter.setOnItemClickListener(new RoomListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, RoomInfo roomInfo) {

           /*     if (roomInfo.getStreamInfo().size() > 0) {
                    // 登录主播所在的房间并拉流，此demo对房间加了前缀 "JoinLiveRoom-"
                    JoinLiveAudienceUI.actionStart(JoinLiveMainActivityUI.this, ZGJoinLiveHelper.PREFIX+roomInfo.getRoomId(), roomInfo.getAnchorIdName());
                } else {
                    AppLogger.getInstance().i(JoinLiveMainActivityUI.class, getString(R.string.room_no_publish));
                    Toast.makeText(JoinLiveMainActivityUI.this, R.string.room_no_publish, Toast.LENGTH_LONG).show();
                }*/
            }

        });
    }

    @Override
    public void finish() {
        super.finish();

        // 释放 SDK
        ZGJoinLiveHelper.sharedInstance().releaseZegoLiveRoom();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化SDK逻辑
     */
    private void initSDK() {
        AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "初始化ZEGO SDK");

        /**
         * 需要在 initSDK 之前设置 SDK 环境，此处设置为测试环境；
         * 从官网申请的 AppID 默认是测试环境，而 SDK 初始化默认是正式环境，所以需要在初始化 SDK 前设置测试环境，否则 SDK 会初始化失败；
         * 当 App 集成完成后，再向 ZEGO 申请开启正式环境，改为正式环境再初始化。
         */
        ZegoLiveRoom.setTestEnv(ZegoUtil.getIsTestEnv());
        AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "test env: " +ZegoUtil.getIsTestEnv());

        // 初始化SDK
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().initSDK(ZegoUtil.getAppID(), ZegoUtil.getAppSign(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {

                if (errorCode == 0) {
                      run();
                    // 初始化成功
                    isInitSuccess = true;

                    // 初始化完成后需要刷新房间列表
                    //zgAppSupportHelper.api().updateRoomList(ZegoUtil.getAppID());

                    AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "初始化ZEGO SDK成功");
                } else {
                    // 初始化失败
                    isInitSuccess = false;
                    AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "初始化ZEGO SDK失败 errorCode : %d", errorCode);
                }
            }
        });

        //zgAppSupportHelper = ZGAppSupportHelper.create(this);

/*        // 监听房间列表的更新
        zgAppSupportHelper.api().setRoomListUpdateListener(new RoomListUpdateListener() {
            @Override
            public void onUpdateRoomList(ArrayList<RoomInfo> arrayList) {
                ArrayList<RoomInfo> joinLiveRooms = new ArrayList<>();
                for (RoomInfo roomInfo: arrayList){
                    if (roomInfo.getRoomId().length() > ZGJoinLiveHelper.PREFIX.length()) {
                        String prefix = roomInfo.getRoomId().substring(0, ZGJoinLiveHelper.PREFIX.length());

                        if (prefix.equals(ZGJoinLiveHelper.PREFIX)) {
                            String tmpRoomID = roomInfo.getRoomId().substring(ZGJoinLiveHelper.PREFIX.length(), roomInfo.getRoomId().length());
                            RoomInfo tmpRoomInfo = new RoomInfo(tmpRoomID,roomInfo.getRoomName(),
                                    roomInfo.getAnchorIdName(),roomInfo.getAnchorNickName(),roomInfo.getStreamInfo());
                            joinLiveRooms.add(tmpRoomInfo);
                        }
                    }
                }
                roomListAdapter.addRoomInfo(joinLiveRooms);
                binding.refreshLayout.setRefreshing(false);
                if (arrayList.size() > 0) {
                    binding.queryRoomState.setVisibility(View.GONE);
                } else {
                    binding.queryRoomState.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onUpdateRoomListError() {
                binding.refreshLayout.setRefreshing(false);
            }
        });*/
    }

    // 创建直播房间
    public void onClickCreateRoom(View view){

        if (isInitSuccess) {
            // 初始化成功跳转到创建并登录房间的页面
            JoinLiveLoginPublishUI.actionStart(JoinLiveMainActivityUI.this);
        } else {
            Toast.makeText(JoinLiveMainActivityUI.this, getString(R.string.tx_init_failure), Toast.LENGTH_SHORT).show();
        }
    }

    // 查看连麦文档
    public void browseDoc(View view){
        //WebActivity.actionStart(this, "https://doc.zego.im/CN/224.html", getString(R.string.tx_joinlive_guide));
    }

    /**
     * 供其他Activity调用，进入本专题的方法
     *
     * @param activity
     */
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinLiveMainActivityUI.class);
        activity.startActivity(intent);
    }

    //接收数据
    public void run(){
        // zegoLiveRoom.setRoomConfig(true, true);
        Log.e("TAG", "IM代理执行1" );

        //IM代理接收消息监听
        zegoLiveRoom.setZegoIMCallback(new IZegoIMCallback() {
            @Override
            public void onUserUpdate(ZegoUserState[] zegoUserStates, int i) {
                //playActivityUI.number(i,zegoUserStates);
                Log.e("TAG", "onUserUpdate: "+i );


            }

            @Override
            public void onRecvRoomMessage(String s, ZegoRoomMessage[] listMsg) {

                //playActivityUI.handleRecvRoomMsg(roomId, listMsg);


                //publishActivityUI.handleRecvRoomMsg(roomId,listMsg);
                //JoinLiveAnchorUI joinLiveAnchorUI = new JoinLiveAnchorUI();

                Log.e("TAG", "zegoRoomMessages:有消息 " );
            }

            @Override
            public void onUpdateOnlineCount(String s, int i) {
                Log.e("TAG", "onUpdateOnlineCount: "+s+i );
            }

            @Override
            public void onRecvBigRoomMessage(String s, ZegoBigRoomMessage[] zegoBigRoomMessages) {
                Log.e("TAG", "onRecvBigRoomMessage: "+s );
            }
        });
        Log.e("TAG", "IM代理执行2" );

    }

}
