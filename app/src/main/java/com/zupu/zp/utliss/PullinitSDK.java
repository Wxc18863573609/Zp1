package com.zupu.zp.utliss;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoRoomCallback;
import com.zego.zegoliveroom.callback.im.IZegoIMCallback;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.AuxData;
import com.zego.zegoliveroom.entity.ZegoBigRoomMessage;
import com.zego.zegoliveroom.entity.ZegoPlayStreamQuality;
import com.zego.zegoliveroom.entity.ZegoPublishStreamQuality;
import com.zego.zegoliveroom.entity.ZegoRoomMessage;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoUserState;
import com.zupu.zp.R;
import com.zupu.zp.adapter.CommentsAdapter;
import com.zupu.zp.entity.PlaySettingActivityUI;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.lianmai.ui.JoinLiveAudienceUI;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.CustomDialog;
import com.zupu.zp.testpakeyge.InitSDKPlayActivityUI;
import com.zupu.zp.testpakeyge.LoginRoomPlayActivityUI;
import com.zupu.zp.testpakeyge.PlayActivityUI;
import com.zupu.zp.testpakeyge.ZegoUtil;

import static com.zupu.zp.fragment.Livefragment.audienceView1s1;
import static com.zupu.zp.fragment.Livefragment.mBigView2;
import static com.zupu.zp.fragment.Livefragment.play_view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/10/23 15:38
 * update: $date$
 */
public class PullinitSDK {
    String   mRoomID="1";
    private String mPublishStreamID = "2";
    // 是否连麦
    private boolean isJoinedLive = false;
    private ArrayList<String> mPlayStreamIDs = new ArrayList<>();
    private String mAnchorID="1";
    ZegoLiveRoom zegoLiveRoom = new ZegoLiveRoom();
    private int roomRole = ZegoConstants.RoomRole.Audience;
    public static ZegoStreamInfo[] zegoStreamInfo;
    String flag;
    public void pullinitsdk(final Activity context, String flags, final String roomID, final String streamID){
        flag=flags;
        AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "点击 初始化SDK按钮");
//        boolean testEnv = binding.testEnv.isChecked();
        // 防止用户点击，弹出加载对话框
     //   CustomDialog.createDialog("初始化SDK中...", context).show();

        // 调用sdk接口, 初始化sdk
        boolean results = ZGBaseHelper.sharedInstance().initZegoSDK(null,null,null,ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {

                // 关闭加载对话框
                CustomDialog.createDialog(context).cancel();

                // errorCode 非0 代表初始化sdk失败
                // 具体错误码说明请查看<a> https://doc.zego.im/CN/308.html </a>
                if (errorCode == 0) {
                    AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "初始化zegoSDK成功");
                    //Toast.makeText(context, getString(R.string.tx_init_success), Toast.LENGTH_SHORT).show();
                    //调用监听消息回调方法
                    Log.e("TAG", "onInitSDK: 初始化成功" );
                    // 设置拉流的视图列表
                     //initViewList();
                    // 设置SDK相关的回调监听
                    //initSDKCallback();

                    // 初始化成功，跳转到登陆房间页面。
                    jumpLoginRoom(context,roomID,streamID);
                } else {
                    AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "初始化sdk失败 错误码 : %d", errorCode);
                    //Toast.makeText(context, getString(R.string.tx_init_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 如果接口调用失败，也需要关闭对话框
        if (!results) {
            // 关闭加载对话框
            CustomDialog.createDialog(context).cancel();
        }
    }
    /**
     * 跳转登陆页面
     */
    private void jumpLoginRoom(Activity activity,String roomID,String streamID) {
        // flag, 需要传递给登陆房间页面。
       // LoginRoomPlayActivityUI.actionStart(activity, flag);
     //   flag = getIntent().getStringExtra("flag");
        // 如果用户标记的动作是推流，则房间角色为 主播 否则为观众角色
        if ("publish".equals(flag)) {
            roomRole = ZegoConstants.RoomRole.Anchor;
        } else {
            roomRole = ZegoConstants.RoomRole.Audience;
        }
        // 恢复sdk默认设置.
        PlaySettingActivityUI.clearPlayConfig();
        onLoginRoom(activity, roomID, streamID);
    }
    /**
     * button 点击事件
     * 登陆房间
     *
     * @param
     */
    public void onLoginRoom(final Activity activity, String roomID, final String streamID) {
        //  String roomId =// binding.edRoomId.getText().toString();
       // edroomid= findViewById(R.id.ed_room_id);
        final String roomId =roomID;

        final String finalRoomId = roomId;
        // 防止用户点击，弹出加载对话框
       // CustomDialog.createDialog("登陆房间中...", activity).show();

        // 登陆房间
        boolean isLoginRoomSuccess = ZGBaseHelper.sharedInstance().loginRoom(roomId, roomRole, new IZegoLoginCompletionCallback() {
            @Override
            public void onLoginCompletion(int errorCode, final ZegoStreamInfo[] zegoStreamInfos) {
                // 关闭加载对话框
             //   CustomDialog.createDialog(activity).cancel();

                if (errorCode == 0) {
                    loginRoomCompletion(true, finalRoomId, errorCode,activity,streamID);
                    zegoStreamInfo=zegoStreamInfos;

           /*        // doc(roomId,zegoStreamInfos );
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            doc(roomId,zegoStreamInfos );
                        }
                    },1000);*/
                } else {
                    loginRoomCompletion(false, finalRoomId, errorCode,activity,streamID);
                }
            }
        });

        if (!isLoginRoomSuccess) {
            loginRoomCompletion(false, finalRoomId, -1,activity,streamID);
        }
    }


    /**
     * 处理登陆房间成功或失败的逻辑
     *
     * @param isLoginRoom 是否登陆房间成功
     * @param finalRoomId roomID
     * @param errorCode   登陆房间错误码
     */
    private void loginRoomCompletion(boolean isLoginRoom, String finalRoomId, int errorCode,Activity activity,String steamID) {
        // 关闭加载对话框
     //   CustomDialog.createDialog(activity).cancel();
        if (isLoginRoom) {
            Toast.makeText(activity, "登录房间成功", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "登录房间成功: " );
            AppLogger.getInstance().i(activity, "登陆房间成功 roomId : %s", finalRoomId);

            // 登陆房间成功，跳转推拉流页面
            jumpPlay(finalRoomId,activity,steamID);
        } else {
            AppLogger.getInstance().i(activity, "登陆房间失败 errorCode : %s", errorCode == -1 ? "api调用失败" : String.valueOf(errorCode));
            Toast.makeText(activity, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转拉流页面
     */
    private void jumpPlay(String roomID,Activity activity,String steamID) {
        PlayActivityUI.actionStart(activity, roomID,steamID);
    }

public void doc(String mRoomID,ZegoStreamInfo[] zegoStreamInfos){
    AppLogger.getInstance().i(JoinLiveAudienceUI.class, "登录房间成功 roomId : %s", mRoomID);

    // 筛选主播流，主播流采用全屏的视图
    for (ZegoStreamInfo streamInfo:zegoStreamInfos){

        if (streamInfo.userID.equals(mAnchorID)){
  /*          // 主播流采用全屏的视图，开始拉流
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, mBigView2.textureView);
            // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整View，可能有部分被裁减。
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);*/

            // 向拉流流名列表中添加流名
            mPlayStreamIDs.add(streamInfo.streamID);
          /*  // 修改视图信息
            mBigView2.streamID = streamInfo.streamID;
            ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView2);
*/
            // 将 "视频连麦" button 置为可见
            //binding.btnApplyJoinLive.setVisibility(View.VISIBLE);
            break;
        }
    }

    // 拉副主播流（即连麦者的流）
    for (ZegoStreamInfo streamInfo:zegoStreamInfos) {

        if (!streamInfo.userID.equals(mAnchorID)){
            // 获取可用的视图
            JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();
            if (freeView != null) {
                // 开始拉流
                ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, freeView.textureView);
                // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个 View，可能有部分被裁减。
                ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);
                // 向拉流流名列表中添加流名
                mPlayStreamIDs.add(streamInfo.streamID);

                // 修改视图信息
                freeView.streamID = streamInfo.streamID;
                ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
            }
        }
    }

}





    // 设置拉流的视图列表
    protected void initViewList(){

     /*   // 全屏视图用于展示主播流
        mBigView2 = new JoinLiveView(play_view, false, "");
        mBigView2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());
*/
        // 添加可用的连麦者视图
        ArrayList<JoinLiveView> mJoinLiveView = new ArrayList<>();
        final JoinLiveView view1 = new JoinLiveView(audienceView1s1, false, "");
        view1.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

/*
        final JoinLiveView view2 = new JoinLiveView(binding.audienceViewTwo, false, "");
        view2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        final JoinLiveView view3 = new JoinLiveView(binding.audienceViewThree, false, "");
        view3.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());
*/

        mJoinLiveView.add(mBigView2);
        mJoinLiveView.add(view1);
  /*      mJoinLiveView.add(view2);
        mJoinLiveView.add(view3);*/
        ZGJoinLiveHelper.sharedInstance().addTextureView(mJoinLiveView);

        /**
         * 设置视图的点击事件
         * 点击小视图时，切换到大视图上展示画面，大视图的画面展示到小视图上
         */
        view1.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.exchangeView(mBigView2);
            }
        });

   /*     view2.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view2.exchangeView(mBigView);
            }
        });
        view3.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view3.exchangeView(mBigView);
            }
        });*/
    }

    // 设置 SDK 相关回调的监听
    public void initSDKCallback(){
        // 设置房间回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoRoomCallback(new IZegoRoomCallback() {
            @Override
            public void onKickOut(int reason, String roomID) {

            }

            @Override
            public void onDisconnect(int errorcode, String roomID) {

            }

            @Override
            public void onReconnect(int errorcode, String roomID) {

            }

            @Override
            public void onTempBroken(int errorcode, String roomID) {

            }

            @Override
            public void onStreamUpdated(int type, ZegoStreamInfo[] zegoStreamInfos, String roomID) {
                // 房间流列表更新

                if (roomID.equals(mRoomID)){
                    // 当登录房间成功后，如果房间内中途有人推流或停止推流。房间内其他人就能通过该回调收到流更新通知。

                    for (ZegoStreamInfo streamInfo : zegoStreamInfos) {
                        // 当有流新增的时候，拉流
                        if (type == ZegoConstants.StreamUpdateType.Added) {
                            AppLogger.getInstance().i(JoinLiveAudienceUI.class, "房间内收到流新增通知. streamID : %s, userName : %s, extraInfo : %s", streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);

                            // 获取可用的视图
                            JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();

                            if (freeView != null) {
                                if (!streamInfo.userID.equals(mAnchorID)) {
                                    // 开始拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, freeView.textureView);
                                    // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个View，可能有部分被裁减。
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                                    // 向拉流流名列表中添加流名
                                    mPlayStreamIDs.add(streamInfo.streamID);

                                    // 修改视图信息
                                    freeView.streamID = streamInfo.streamID;
                                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
                                } else {
                                    // 开始拉流，此处处理主播中途断流后重新推流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, mBigView2.textureView);
                                    // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个View，可能有部分被裁减。
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                                    // 向拉流流名列表中添加流名
                                    mPlayStreamIDs.add(streamInfo.streamID);

                                    // 修改视图信息
                                    mBigView2.streamID = streamInfo.streamID;
                                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView2);
                                }
                            }
                        }
                        // 当有其他流关闭的时候，停止拉流
                        else if (type == ZegoConstants.StreamUpdateType.Deleted) {
                            AppLogger.getInstance().i(JoinLiveAudienceUI.class, "房间内收到流删除通知. streamID : %s, userName : %s, extraInfo : %s", streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);
                            for (String playStreamID:mPlayStreamIDs){
                                if (playStreamID.equals(streamInfo.streamID)){

                                    // 停止拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPlayingStream(streamInfo.streamID);
                                    mPlayStreamIDs.remove(streamInfo.streamID);

                                    // 修改视图信息
                                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamInfo.streamID);

                                    // 判断该条关闭流是否为主播
                                    if (streamInfo.userID.equals(mAnchorID)) {
                                        // 界面提示主播已停止直播
                                      //  Toast.makeText(JoinLiveAudienceUI.this, getString(R.string.tx_anchor_stoppublish), Toast.LENGTH_SHORT).show();

                                        // 在已连麦的情况下，主播停止直播连麦观众也停止推流
                                        if (isJoinedLive) {
                                            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPublishing();
                                            // 停止预览
                                            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPreview();

                                            isJoinedLive = false;
                                            // 设置视图可用
                                            ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(mPublishStreamID);
                                        }
                                        // 将 "视频连麦" button 置为不可见
                                        //binding.btnApplyJoinLive.setVisibility(View.INVISIBLE);
                                    }

                                    break;
                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onStreamExtraInfoUpdated(ZegoStreamInfo[] zegoStreamInfos, String roomID) {
                // 流的额外信息更新

            }

            @Override
            public void onRecvCustomCommand(String userID, String userName, String content, String roomID) {
                // 收到自定义信息

            }
        });

        // 设置拉流回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int stateCode, String streamID) {
                // 拉流状态更新，errorCode 非0 则说明拉流失败
                // 拉流常见错误码请看文档: <a>https://doc.zego.im/CN/491.html</a>

                if (stateCode == 0) {
                    AppLogger.getInstance().i(JoinLiveAudienceUI.class, "拉流成功, streamID : %s", streamID);
                   // Toast.makeText(JoinLiveAudienceUI.this, getString(R.string.tx_play_success), Toast.LENGTH_SHORT).show();

                } else {
                    AppLogger.getInstance().i(JoinLiveAudienceUI.class, "拉流失败, streamID : %s, errorCode : %d", streamID, stateCode);
                    //Toast.makeText(JoinLiveAudienceUI.this, getString(R.string.tx_play_fail), Toast.LENGTH_SHORT).show();

                    // 解除视图占用
                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);

                    // 从已拉流列表中移除该流名
                    mPlayStreamIDs.remove(streamID);
                }
            }

            @Override
            public void onPlayQualityUpdate(String streamID, ZegoPlayStreamQuality zegoPlayStreamQuality) {

            }

            @Override
            public void onInviteJoinLiveRequest(int seq, String fromUserID, String fromUserName, String roomID) {

            }

            @Override
            public void onRecvEndJoinLiveCommand(String fromUserID, String fromUserName, String roomID ) {

            }

            @Override
            public void onVideoSizeChangedTo(String streamID, int i, int i1) {

            }
        });

        // 设置推流回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            @Override
            public void onPublishStateUpdate(int errorCode, String streamID, HashMap<String, Object> hashMap) {
                // 推流状态更新，errorCode 非0 则说明推流失败
                // 推流常见错误码请看文档: <a>https://doc.zego.im/CN/308.html</a>

                if (errorCode == 0) {
                    AppLogger.getInstance().i(JoinLiveAudienceUI.class, "推流成功, streamID : %s", streamID);
                   // Toast.makeText(JoinLiveAudienceUI.this, getString(R.string.tx_publish_success), Toast.LENGTH_SHORT).show();

                    isJoinedLive = true;

                    // 修改button的标识为 结束连麦
                    //binding.btnApplyJoinLive.setText(getString(R.string.tx_end_join_live));
                } else {
                    AppLogger.getInstance().i(JoinLiveAudienceUI.class, "推流失败, streamID : %s, errorCode : %d", streamID, errorCode);
                   // Toast.makeText(JoinLiveAudienceUI.this, getString(R.string.tx_publish_fail), Toast.LENGTH_SHORT).show();
                    // 解除视图占用
                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);
                }
            }

            @Override
            public void onJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onPublishQualityUpdate(String s, ZegoPublishStreamQuality zegoPublishStreamQuality) {

            }

            @Override
            public AuxData onAuxCallback(int i) {
                return null;
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int i, int i1) {

            }

            @Override
            public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onCaptureVideoFirstFrame() {

            }

            @Override
            public void onCaptureAudioFirstFrame() {

            }
        });
    }
}
