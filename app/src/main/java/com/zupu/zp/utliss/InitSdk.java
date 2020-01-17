package com.zupu.zp.utliss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.TextureView;
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
import com.zego.zegoliveroom.constants.ZegoIM;
import com.zego.zegoliveroom.constants.ZegoVideoViewMode;
import com.zego.zegoliveroom.entity.AuxData;
import com.zego.zegoliveroom.entity.ZegoBigRoomMessage;
import com.zego.zegoliveroom.entity.ZegoPlayStreamQuality;
import com.zego.zegoliveroom.entity.ZegoPublishStreamQuality;
import com.zego.zegoliveroom.entity.ZegoRoomMessage;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;
import com.zego.zegoliveroom.entity.ZegoUserState;
import com.zupu.zp.R;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.lianmai.constants.JoinLiveUserInfo;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.lianmai.ui.JoinLiveAnchorUI;
import com.zupu.zp.lianmai.ui.JoinLiveLoginPublishUI;
import com.zupu.zp.lianmai.ui.JoinLiveMainActivityUI;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.CustomDialog;
import com.zupu.zp.testpakeyge.LoginRoomPublishActivityUI;
import com.zupu.zp.testpakeyge.PublishActivityUI;
import com.zupu.zp.testpakeyge.PublishSettingActivityUI;
import com.zupu.zp.testpakeyge.ZegoUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static com.zupu.zp.fragment.Livefragment.audienceView1s;
import static com.zupu.zp.fragment.Livefragment.mBigView;
import static com.zupu.zp.fragment.Livefragment.preview;
import static com.zupu.zp.utliss.App.getContext;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/10/23 11:39
 * update: $date$
 */
public class InitSdk {
    private int roomRole = ZegoConstants.RoomRole.Anchor;
    private String flag;
    private  String mRoomID;
    // 主播流名
    private String anchorStreamID = "1";
    public static ZegoStreamInfo[] zegoStreamInfo1;
    public void initsdks(final Activity context, String flags, final String roomID, final String starmID){
        AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "初始化ZEGO SDK");

        /**
         * 需要在 initSDK 之前设置 SDK 环境，此处设置为测试环境；
         * 从官网申请的 AppID 默认是测试环境，而 SDK 初始化默认是正式环境，所以需要在初始化 SDK 前设置测试环境，否则 SDK 会初始化失败；
         * 当 App 集成完成后，再向 ZEGO 申请开启正式环境，改为正式环境再初始化。
         */
        ZegoLiveRoom.setTestEnv(ZegoUtil.getIsTestEnv());
        AppLogger.getInstance().i(JoinLiveMainActivityUI.class, "test env: " +ZegoUtil.getIsTestEnv());



        mRoomID=roomID;
       /* View views= View.inflate(context, R.layout.activity_publish, null);
         TextureView preview= views.findViewById(R.id.preview);*/
          //  mBigView = new JoinLiveView(preview, false, "");
        // 防止用户点击，弹出加载对话框
        CustomDialog.createDialog("初始化SDK中...",  context).show();
         flag=flags;
        // 调用sdk接口, 初始化sdk
        boolean results = ZGBaseHelper.sharedInstance().initZegoSDK(null,null,context,ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {
                // 关闭加载对话框
                CustomDialog.createDialog(context).cancel();
                // errorCode 非0 代表初始化sdk失败
                // 具体错误码说明请查看<a> https://doc.zego.im/CN/308.html </a>
                if (errorCode == 0) {
                    AppLogger.getInstance().i(context.getClass(), "初始化zegoSDK成功");
                    Toast.makeText(context, "初始化成功", Toast.LENGTH_SHORT).show();
                    //initViewList();
                    //initSDKCallback();
                    // 初始化成功，跳转到登陆房间页面。
                    jumpLoginRoom(roomID,context,starmID);
                } else {
                    AppLogger.getInstance().i(context.getClass(), "初始化sdk失败 错误码 : %d", errorCode);
                    Toast.makeText(context, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 如果接口调用失败，也需要关闭加载对话框
        if (!results) {
            CustomDialog.createDialog(context).cancel();
        }
    }
    /**
     * 跳转登陆页面
     */
    private void jumpLoginRoom(String roomID,Activity activity,String steamID) {
        // flag, 需要传递给登陆房间页面。
       // LoginRoomPublishActivityUI.actionStart(activity, flag);

       // flag = getIntent().getStringExtra("flag");
        // 如果用户标记的动作是推流，则房间角色为 主播 否则为观众角色
        if ("publish".equals(flag)) {
            roomRole = ZegoConstants.RoomRole.Anchor;
        } else {
            roomRole = ZegoConstants.RoomRole.Audience;
        }
        // 每次进来都恢复成sdk默认设置
        PublishSettingActivityUI.clearPublishConfig();
        onLoginRoom(roomID,activity,steamID);

    }

    public void onLoginRoom(String roomId, final Activity activity, final String steamID) {
        // String roomId = binding.edRoomId.getText().toString();
       /* EditText edroom = findViewById(R.id.ed_room_id);
        String roomId = edroom.getText().toString();*/

        final String finalRoomId = roomId;

      /*  // 防止用户点击，弹出加载对话框
        CustomDialog.createDialog("登陆房间中...", LoginRoomPublishActivityUI.this).show();*/
        // 登陆房间
        boolean isLoginRoomSuccess = ZGBaseHelper.sharedInstance().loginRoom(roomId, roomRole, new IZegoLoginCompletionCallback() {
            @Override
            public void onLoginCompletion(int errorCode, ZegoStreamInfo[] zegoStreamInfos) {

                if (errorCode == 0) {
                   // initViewList();
                    zegoStreamInfo1=zegoStreamInfos;
                   // run2(zegoStreamInfos);
                    loginRoomCompletion(true, finalRoomId, errorCode,activity,steamID);
                } else {
                    loginRoomCompletion(false, finalRoomId, errorCode,activity,steamID);
                }
            }
        });

        if (!isLoginRoomSuccess) {
            loginRoomCompletion(false, finalRoomId, -1,activity,steamID);
        }
    }

    /**
     * 处理登陆房间成功或失败的逻辑
     *
     * @param isLoginRoom 是否登陆房间成功
     * @param finalRoomId roomID
     * @param errorCode 登陆房间错误码
     */
    private void loginRoomCompletion(boolean isLoginRoom, String finalRoomId, int errorCode,final Activity activity,String steamID) {
        // 关闭加载对话框
      //  CustomDialog.createDialog(LoginRoomPublishActivityUI.this).cancel();
        if (isLoginRoom) {
            Toast.makeText(activity, "登陆房间成功", Toast.LENGTH_SHORT).show();
            AppLogger.getInstance().i(LoginRoomPublishActivityUI.class, "登陆房间成功 roomId : %s", finalRoomId);

            // 登陆房间成功，跳转推拉流页面
            //jumpPublish(finalRoomId, activity, steamID);

        } else {
            AppLogger.getInstance().i(LoginRoomPublishActivityUI.class, "登陆房间失败 errorCode : %s", errorCode == -1 ? "api调用失败" : String.valueOf(errorCode));
            Toast.makeText(activity, "登陆房间失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转推拉流页面
     */
    private void jumpPublish(String roomID,Activity activity,String steamID) {
        PublishActivityUI.actionStart(activity, roomID,steamID);
    }

    /**
     * button 点击事件
     * <p>
     * 跳转到登陆房间指引webView
     *
     * @param view
     */
    public void goCodeDemo(View view) {
        //WebActivity.actionStart(this, "https://doc.zego.im/CN/625.html", getString(com.zego.common.R.string.tx_login_room_guide));
    }

    public static void actionStart(Activity activity, String flag) {
        Intent intent = new Intent(activity, LoginRoomPublishActivityUI.class);
        // 标记我当前动作是推流还是拉流, 登陆房间后跳转页面需要用到
        intent.putExtra("flag", flag);
        activity.startActivity(intent);
    }

 public void run2(ZegoStreamInfo[] zegoStreamInfos){
     AppLogger.getInstance().i(JoinLiveAnchorUI.class, "登录房间成功 roomId : %s", mRoomID);

     // 设置预览视图模式，此处采用 SDK 默认值--等比缩放填充整View，可能有部分被裁减。
     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
     // 设置预览 view，主播自己推流采用全屏视图
     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setPreviewView(mBigView.textureView);
     // 启动预览
     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPreview();

     // 开始推流，flag 使用连麦场景
     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPublishing(anchorStreamID, "anchor", ZegoConstants.PublishFlag.JoinPublish);

     // 修改视图列表中的视图信息
     mBigView.streamID = anchorStreamID;
     mBigView.isPublishView = true;
     ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView);

     // 拉取其它主播流，即进入其它主播房间进行直播
     for (ZegoStreamInfo streamInfo : zegoStreamInfos) {

         if (streamInfo != null) {
             // 修改已连麦用户列表，此demo只展示三人连麦，此处根据连麦人数限制进行拉流
             if (ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers().size() < ZGJoinLiveHelper.MaxJoinLiveNum) {

                 // 获取可用的视图
                 JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();

                 if (freeView != null){
                     // 拉流
                     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, freeView.textureView);
                     // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个View，可能有部分被裁减。
                     ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                     // 向已连麦列表中添加连麦者
                     JoinLiveUserInfo userInfo = new JoinLiveUserInfo(streamInfo.userID, streamInfo.streamID);
                     ZGJoinLiveHelper.sharedInstance().addJoinLiveAudience(userInfo);

                     // 修改视图信息
                     freeView.streamID = streamInfo.streamID;
                     ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
                 } else {
                    // Toast.makeText(JoinLiveAnchorUI.this, getString(R.string.has_no_textureView), Toast.LENGTH_LONG).show();
                 }
             } else {
                 //Toast.makeText(JoinLiveAnchorUI.this, R.string.join_live_count_overflow, Toast.LENGTH_LONG).show();
             }
         }
     }

 }






    //---------------------------------------------------------------------------------------------------------------------

    // 设置视图列表
    protected void initViewList(){

        mBigView = new JoinLiveView(preview, false, "");
        mBigView.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        // 添加可用的视图，此demo展示共四个视图，一个全屏视图+3个小视图
        ArrayList<JoinLiveView> mJoinLiveView = new ArrayList<>();

        final JoinLiveView view1 = new JoinLiveView(audienceView1s, false, "");
        view1.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

    /*    final JoinLiveView view2 = new JoinLiveView(binding.audienceView2, false, "");
        view2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        final JoinLiveView view3 = new JoinLiveView(binding.audienceView3, false, "");
        view3.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());*/

        mJoinLiveView.add(mBigView);
        mJoinLiveView.add(view1);
/*        mJoinLiveView.add(view2);
        mJoinLiveView.add(view3);*/
        ZGJoinLiveHelper.sharedInstance().addTextureView(mJoinLiveView);

        /**
         * 设置视图的点击事件
         * 点击小视图时，切换到大视图上展示画面，大视图的画面展示到小视图上
         */
        view1.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(PublishActivityUI.this, "点到", Toast.LENGTH_SHORT).show();
                view1.exchangeView(mBigView);
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
    // 设置 SDK 相关的回调监听
    public void initSDKCallback(){
        Log.e("TAG", "initSDKCallback: 执行前" );
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
                // 当登录房间成功后，如果房间内中途有人推流或停止推流。房间内其他人就能通过该回调收到流更新通知。
                Log.e("TAG", "initSDKCallback: 监听有流更新" );
                if (roomID.equals(mRoomID)){

                    for (ZegoStreamInfo streamInfo : zegoStreamInfos) {
                        // 当有流新增的时候，拉流
                        if (type == ZegoConstants.StreamUpdateType.Added) {
                            AppLogger.getInstance().i(JoinLiveAnchorUI.class, "房间: %s 内收到流新增通知. streamID : %s, userName : %s, extraInfo : %s", roomID, streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);

                            // 修改已连麦用户列表，此demo只展示三人连麦，此处根据连麦人数限制进行拉流
                            if (ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers().size() < ZGJoinLiveHelper.MaxJoinLiveNum) {

                                // 获取可用的视图
                                JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();

                                if (freeView != null){
                                    // 拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, freeView.textureView);
                                    // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个View，可能有部分被裁减。
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                                    // 向已连麦列表中添加连麦者
                                    JoinLiveUserInfo userInfo = new JoinLiveUserInfo(streamInfo.userID, streamInfo.streamID);
                                    ZGJoinLiveHelper.sharedInstance().addJoinLiveAudience(userInfo);

                                    // 修改视图信息
                                    freeView.streamID = streamInfo.streamID;
                                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
                                } else {
                                   // Toast.makeText(PublishActivityUI.this, getString(R.string.has_no_textureView), Toast.LENGTH_LONG).show();
                                }
                            } else {
                              //  Toast.makeText(PublishActivityUI.this, R.string.join_live_count_overflow, Toast.LENGTH_LONG).show();
                            }
                        }
                        // 当有其他流关闭的时候，停止拉流
                        else if (type == ZegoConstants.StreamUpdateType.Deleted) {
                            AppLogger.getInstance().i(PublishActivityUI.class, "房间：%s 内收到流删除通知. streamID : %s, userName : %s, extraInfo : %s", roomID, streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);
                            // 如果此条流删除信息是连麦者的流，做停止拉流的处理
                            for (JoinLiveUserInfo userInfo:ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers()){
                                if (userInfo.userID.equals(streamInfo.userID)){
                                    // 停止拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPlayingStream(streamInfo.streamID);

                                    // 移除此连麦者
                                    ZGJoinLiveHelper.sharedInstance().removeJoinLiveAudience(userInfo);
                                    // 修改视图信息
                                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamInfo.streamID);

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

        // 设置推流回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePublisherCallback(new IZegoLivePublisherCallback() {
            // 推流回调文档说明: <a>https://doc.zego.im/API/ZegoLiveRoom/Android/html/index.html</a>

            @Override
            public void onPublishStateUpdate(int errorCode, String streamID, HashMap<String, Object> hashMap) {
                // 推流状态更新，errorCode 非0 则说明推流失败
                // 推流常见错误码请看文档: <a>https://doc.zego.im/CN/308.html</a>

                if (errorCode == 0) {
                    AppLogger.getInstance().i(JoinLiveAnchorUI.class, "推流成功, streamID : %s", streamID);
                  //  Toast.makeText(PublishActivityUI.this, getString(R.string.tx_publish_success), Toast.LENGTH_SHORT).show();
                } else {
                    AppLogger.getInstance().i(JoinLiveAnchorUI.class, "推流失败, streamID : %s, errorCode : %d", streamID, errorCode);
                   // Toast.makeText(PublishActivityUI.this, getString(R.string.tx_publish_fail), Toast.LENGTH_SHORT).show();

                    // 推流失败就解除视图的占用
                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);
                }
            }

            @Override
            public void onJoinLiveRequest(int seq, String fromUserID, String fromUserName, String roomID) {
                /**
                 * 房间内有人申请加入连麦时会回调该方法
                 */
            }

            @Override
            public void onPublishQualityUpdate(String s, ZegoPublishStreamQuality zegoPublishStreamQuality) {
                /**
                 * 推流质量更新, 回调频率默认3秒一次
                 * 可通过 {@link com.zego.zegoliveroom.ZegoLiveRoom#setPublishQualityMonitorCycle(long)} 修改回调频率
                 */
            }

            @Override
            public AuxData onAuxCallback(int i) {
                // aux混音，可以将外部音乐混进推流中。类似于直播中添加伴奏，掌声等音效
                // 另外还能用于ktv场景中的伴奏播放
                // 想深入了解可以进入进阶功能中的-mixing。
                // <a>https://doc.zego.im/CN/253.html</a> 文档中有说明
                return null;
            }

            @Override
            public void onCaptureVideoSizeChangedTo(int width, int height) {

            }

            @Override
            public void onMixStreamConfigUpdate(int i, String s, HashMap<String, Object> hashMap) {
                // 混流配置更新时会回调该方法。
            }

            @Override
            public void onCaptureVideoFirstFrame() {
                // 当SDK采集摄像头捕获到第一帧时会回调该方法

            }

            @Override
            public void onCaptureAudioFirstFrame() {
                // 当SDK音频采集设备捕获到第一帧时会回调该方法
            }
        });

        // 设置拉流回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int stateCode, String streamID) {
                // 拉流状态更新，errorCode 非0 则说明拉流失败
                // 拉流常见错误码请看文档: <a>https://doc.zego.im/CN/491.html</a>

                if (stateCode == 0) {
                    AppLogger.getInstance().i(PublishActivityUI.class, "拉流成功, streamID : %s", streamID);
                    //Toast.makeText(PublishActivityUI.this, getString(R.string.tx_play_success), Toast.LENGTH_SHORT).show();
                } else {
                    AppLogger.getInstance().i(PublishActivityUI.class, "拉流失败, streamID : %s, errorCode : %d", streamID, stateCode);
                   // Toast.makeText(PublishActivityUI.this, getString(R.string.tx_play_fail), Toast.LENGTH_SHORT).show();

                    // 拉流失败，解除视图占用
                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);

                    // 移除此连麦者
                    for (JoinLiveUserInfo userInfo: ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers()) {
                        if (streamID.equals(userInfo.streamID)) {

                            ZGJoinLiveHelper.sharedInstance().removeJoinLiveAudience(userInfo);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onPlayQualityUpdate(String s, ZegoPlayStreamQuality zegoPlayStreamQuality) {

            }

            @Override
            public void onInviteJoinLiveRequest(int i, String s, String s1, String s2) {

            }

            @Override
            public void onRecvEndJoinLiveCommand(String s, String s1, String s2) {

            }

            @Override
            public void onVideoSizeChangedTo(String s, int i, int i1) {

            }
        });

        // 设置房间人数相关信息的回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoIMCallback(new IZegoIMCallback() {
            @Override
            public void onUserUpdate(ZegoUserState[] listUser, int updateType) {
                AppLogger.getInstance().i(JoinLiveAnchorUI.class, "收到房间成员更新通知");
                // 房间成员更新回调，可根据此回调来管理观众列表
                for (ZegoUserState userInfo:listUser){

                    if (ZegoIM.UserUpdateFlag.Added == userInfo.updateFlag){
                        // 房间增加成员，可进行业务相关的处理

                    } else if (ZegoIM.UserUpdateFlag.Deleted == userInfo.updateFlag){
                        // 成员退出房间，可进行业务相关的处理

                    }
                }
            }

            @Override
            public void onRecvRoomMessage(String s, ZegoRoomMessage[] zegoRoomMessages) {

            }

            @Override
            public void onUpdateOnlineCount(String s, int i) {

            }

            @Override
            public void onRecvBigRoomMessage(String s, ZegoBigRoomMessage[] zegoBigRoomMessages) {

            }
        });
    }
}
