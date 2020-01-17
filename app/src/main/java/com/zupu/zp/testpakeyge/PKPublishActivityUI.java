package com.zupu.zp.testpakeyge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoEndJoinLiveCallback;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoLivePlayerCallback;
import com.zego.zegoliveroom.callback.IZegoLivePublisherCallback;
import com.zego.zegoliveroom.callback.IZegoLoginCompletionCallback;
import com.zego.zegoliveroom.callback.IZegoResponseCallback;
import com.zego.zegoliveroom.callback.IZegoRoomCallback;
import com.zego.zegoliveroom.callback.im.IZegoIMCallback;
import com.zego.zegoliveroom.callback.im.IZegoRoomMessageCallback;
import com.zego.zegoliveroom.constants.ZegoBeauty;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.constants.ZegoFilter;
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
import com.zupu.zp.adapter.BeautyAdapter;
import com.zupu.zp.adapter.CommentsAdapter;
import com.zupu.zp.adapter.FilterAdapter;
import com.zupu.zp.adapter.UserAdapter;
import com.zupu.zp.bean.CreatRoomBean;
import com.zupu.zp.bean.Filterbean;
import com.zupu.zp.bean.GetUserCount;
import com.zupu.zp.bean.GetuserCountBean;
import com.zupu.zp.bean.GiftJsonBean;
import com.zupu.zp.bean.QueryGiftBean;
import com.zupu.zp.bean.QueryPKGiftBean;
import com.zupu.zp.databinding.ActivityPkpublishBinding;
import com.zupu.zp.databinding.PublishInputStreamIdLayoutBinding;
import com.zupu.zp.entity.SDKConfigInfo;
import com.zupu.zp.entity.StreamQuality;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.entity.ZGConfigHelper;
import com.zupu.zp.entity.ZGManager;
import com.zupu.zp.entity.ZGPublishHelper;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.giftutli.GiftControl;
import com.zupu.zp.giftutli.widget.CustormAnim;
import com.zupu.zp.giftutli.widget.GiftModel;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.lianmai.constants.JoinLiveUserInfo;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.lianmai.ui.JoinLiveAnchorUI;
import com.zupu.zp.lianmai.ui.JoinLiveMainActivityUI;
import com.zupu.zp.myactivity.EndLiveActivity;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.KeyboardStateObserver;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.zego.zegoavkit2.ZegoConstants.PublishChannelIndex.MAIN;
import static com.zupu.zp.R.layout.activity_pkpublish;
import static com.zupu.zp.R.layout.activity_publish;
import static com.zupu.zp.entity.ZegoApplication.getContexts;


public class PKPublishActivityUI extends BaseActivitys {
    ZfUtil zfUtil = ZfUtil.getInstance();
    ImageView fx;
    String title,status,cover,proIds, uuid1;;
    String starttime,endtime;
    int isPK=0,activityId,money;;
    Button removelm;
    boolean islm;
    int numberss=0;
    int hdnumber=0;
     long baseTimer = 0;
    TextView  timerView ;
    int index=0;
    String lmuserid;
    private boolean currentStart;
    Handler myhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (0 == baseTimer) {
                baseTimer = SystemClock.elapsedRealtime();
            }

            int time = (int) ((SystemClock.elapsedRealtime() - baseTimer) / 1000);
            String mm = new DecimalFormat("00").format(time / 60);
            String ss = new DecimalFormat("00").format(time % 60);
            if (null != timerView) {
                timerView.setText(index+++"s");
            }
            Message message = Message.obtain();
            message.what = 0x0;
            sendMessageDelayed(message, 1000);
        }
    };
    private CommentsAdapter   mCommentsAdapter=null;
    View view;
    EditText ed_text;
    boolean boo = false;
    ArrayList<GetuserCountBean> userlist = new ArrayList<>();
    String uuid ;
    int number=0;
    public  String TAG="族谱";
    String roomnum ;
    String userid ;
    String userName;
    String roomID;
    int anthid;
    UserAdapter userAdapter;
    private int roomRole = ZegoConstants.RoomRole.Anchor;
    private String flags;
    boolean isNo;
    String starmID;
    ImageView kill;
    TextView zbname;
    // 主播流名
    private LinearLayout hideorshow;
    private String anchorStreamID = "1";
    public static ZegoStreamInfo[] zegoStreamInfo1;
    SharedPreferences sharedPreferences;
    TextureView view1;
    // 大view
    private JoinLiveView mBigView = null;
    private String mRoomID = "";
    // SDK配置，麦克风和摄像头
    private SDKConfigInfo sdkConfigInfo = new SDKConfigInfo();
    public  TextureView preview;
    RecyclerView zbrecycouview = null;
    ZegoLiveRoom zegoLiveRoom = new ZegoLiveRoom();
    List<ZegoRoomMessage> listTextMsg = new ArrayList<>();
    private ActivityPkpublishBinding binding;
    private PublishInputStreamIdLayoutBinding layoutBinding;
    private StreamQuality streamQuality = new StreamQuality();
  //  private SDKConfigInfo sdkConfigInfo = new SDKConfigInfo();
    private String streamID="1";
    private SeekBar seekbar,seekbar2;
    private EditText edstramid;
    private ImageView meiy;
    private ImageView lvjing;
    private Button send;
    LinearLayout giftParent;
    private GiftControl giftControl;
    RecyclerView userrecycel;
    private TextView usernumber;
     int flag=0;
    PopupWindow popupWindow = new PopupWindow();//滤镜弹窗
    PopupWindow popupWindow1;//评论弹窗
    FilterAdapter scoreTeamAdapter;
    Context mContext;
    private GiftModel giftModel;
    TextView timetext;
    ImageView headimg;
    private  CommentsAdapter CommentsAdapterz=null;
    String imgurl="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3323361914,1254877484&fm=26&gp=0.jpg";
    ZegoStreamInfo usermesge;
    RelativeLayout camera,pl,beauty,filter,stoplian;
    Httputlis1 instance1 = Httputlis1.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, activity_pkpublish);
        fx = findViewById(R.id.fx);
        removelm=findViewById(R.id.removelm);
        timerView = (TextView) this.findViewById(R.id.timerView);
        usernumber = findViewById(R.id.usernumber);
        beauty=findViewById(R.id.beauty);
        stoplian=findViewById(R.id.stoplian);
        filter=findViewById(R.id.filter);
        zbname=findViewById(R.id.zbname);
        headimg = findViewById(R.id.headimg);
        mContext=this;
        userrecycel = findViewById(R.id.userrecycel);
        zbrecycouview=findViewById(R.id.zbrecycouview);
        popupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 200);
        //设置弹出窗体需要软键盘，
        popupWindow1.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //聊天弹窗
        view = LayoutInflater.from(this).inflate(R.layout.sendmessgewindow, null);
        meiy = findViewById(R.id.meiy);
        camera=findViewById(R.id.camera);
        kill=findViewById(R.id.kill);
        edstramid = findViewById(R.id.ed_stream_id);
        lvjing = findViewById(R.id.lvj);
        preview=findViewById(R.id.preview);
        pl=findViewById(R.id.pl);
        hideorshow = findViewById(R.id.hideorshow);
        mBigView = new JoinLiveView(preview, false, "");
        view1=findViewById(R.id.audienceView1);
        // 利用DataBinding 可以通过bean类驱动UI变化。
        // 方便快捷避免需要写一大堆 setText 等一大堆臃肿的代码。
        binding.setQuality(streamQuality);
        binding.setConfig(sdkConfigInfo);
        binding.swMic.setChecked(true);
        binding.swCamera.setChecked(true);
        layoutBinding = binding.layout;
        layoutBinding.startButton.setText("开始视频直播");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        starttime=simpleDateFormat.format(date);
        streamQuality.setRoomID(String.format("RoomID : %s", getIntent().getStringExtra("roomID")));
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        mCommentsAdapter = new CommentsAdapter(this, new ArrayList<ZegoRoomMessage>());
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null); ;
        userName =sharedPreferences.getString("nickName",null); ;
         //取值
         Intent intent = getIntent();
         flags= intent.getStringExtra("publish");
         roomID= intent.getStringExtra("roomID");
         starmID=intent.getStringExtra("starmID");
        uuid1 = intent.getStringExtra("uuid");
        title = intent.getStringExtra("title");
        status = intent.getStringExtra("status");
        cover = intent.getStringExtra("cover");
        proIds = intent.getStringExtra("proIds");
        activityId = intent.getIntExtra("activityId",0);
        isPK = intent.getIntExtra("isPK",0);
         roomnum=roomID;
         mRoomID=roomID;

        giftParent = (LinearLayout) findViewById(R.id.ll_gift_parent);
        giftControl = new GiftControl(this);
        giftControl.setGiftLayout(giftParent, 3)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
         getdate();
        //初始化sdk
        initPublishiSdk(giftParent,mContext,usernumber);
        // 设置视图列表
           initViewList();

        // 设置SDK相关的回调监听
        //initSDKCallback();
        // 登录房间并推流
      //  startPublish();
        // 设置预览 view，主播自己推流采用全屏视图
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setPreviewView(binding.preview);
        // 调用sdk 开始预览接口 设置view 启用预览
        ZGPublishHelper.sharedInstance().startPreview(binding.preview);


        // 设置拉流回调监听
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int stateCode, String streamID) {
                // 拉流状态更新，errorCode 非0 则说明拉流失败
                // 拉流常见错误码请看文档: <a>https://doc.zego.im/CN/491.html</a>

                if (stateCode == 0) {
                    //doc("1",zegoStreamInfo);
                    AppLogger.getInstance().i(PKPublishActivityUI.class, "拉流成功, streamID : %s", streamID);
                } else {
                    AppLogger.getInstance().i(PKPublishActivityUI.class, "拉流失败, streamID : %s, errorCode : %d", streamID, stateCode);
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



        // 监听摄像头与麦克风开关
        binding.swCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    sdkConfigInfo.setEnableCamera(isChecked);
                    ZGConfigHelper.sharedInstance().enableCamera(isChecked);
                }
            }
        });

        binding.swMic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    sdkConfigInfo.setEnableMic(isChecked);
                    ZGConfigHelper.sharedInstance().enableMic(isChecked);
                }

            }
        });




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        userrecycel.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(userlist, this);
        //设置适配器
        userrecycel.setAdapter(userAdapter);

       //监听软键盘关闭
        KeyboardStateObserver.getKeyboardStateObserver(this).
                setKeyboardVisibilityListener(new KeyboardStateObserver.OnKeyboardVisibilityListener() {
                    @Override
                    public void onKeyboardShow() {
                        //  Toast.makeText(PlayActivityUI.this,"键盘弹出",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onKeyboardHide() {
                        popupWindow1.dismiss();

                    }
                });

        //移除当前连麦用户
        removelm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //点击分享
        fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfUtil.myshare(UrlCount.Base_Head+"mobile/live_share.html?roomId="+roomnum+"&streamId="+streamID+"&isPlayBack="+0+"&isPK="+1+"&playUrl="+"null","直播分享","有人向你分享了直播",sharedPreferences.getString("photoUrl",null),PKPublishActivityUI.this);
            }
        });
        //设置返回按钮
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder( PKPublishActivityUI.this)
                        .setTitle(R.string.jg)
                        .setMessage(R.string.sftc)
                        .setNegativeButton(R.string.back_qx,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                })
                        .setPositiveButton(R.string.shuor,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        finish();
                                    }
                                }).show();
            }
        });

        //停止连麦
        stoplian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZGBaseHelper.sharedInstance().getZegoLiveRoom().endJoinLive(lmuserid, new IZegoEndJoinLiveCallback() {
                    @Override
                    public void onEndJoinLive(int i, String s) {
                        Toast.makeText(PKPublishActivityUI.this, "已结束", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //滤镜
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
               // hideInputStreamIDLayout();
            }
        });
        //美颜监听按钮
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBeautyPopupWindow();
              //  hideInputStreamIDLayout();
            }
        });
        //美颜监听按钮
        meiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBeautyPopupWindow();
               // hideInputStreamIDLayout();
            }
        });
        //点击弹出滤镜窗口
        lvjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置滤镜
            //    zegoliveRoom.setFilter(ZegoFilter.Halo); //此处滤镜类型采用黑白
                showPopupWindow();
            }
        });

        //转换摄像头
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNo){
                    isNo=false;
                    ZGManager.sharedInstance().api().setFrontCam(true);
                }else {
                    isNo=true;
                    ZGManager.sharedInstance().api().setFrontCam(false);

                }
            }
        });


        //啧啧
        // 开启美颜
        ZGBaseHelper.sharedInstance().getZegoLiveRoom().enableBeautifying(ZegoBeauty.POLISH | ZegoBeauty.WHITEN); //true 表示打开美颜，false 表示关闭  美颜

        //设置适配器
        zbrecycouview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        zbrecycouview.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        zbrecycouview.setItemAnimator(new DefaultItemAnimator());
         //发送评论
         pl.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showPopupWindows();
                 hideorshow.setVisibility(View.GONE);
             }
         });




    }

    @Override
    protected void onResume() {
        //  某些华为手机上，应用在后台超过2分钟左右，华为系统会把摄像头资源给释放掉，并且可能会断开你应用的网络连接,
        //  需要做前台服务来避免这种情况https://blog.csdn.net/Crazy9599/article/details/89842280
        //  所以当前先关闭再重新启用摄像头来规避该问题
        if (binding.swCamera.isChecked()) {
            ZGConfigHelper.sharedInstance().enableCamera(false);
            ZGConfigHelper.sharedInstance().enableCamera(true);
        }
        if (binding.swMic.isChecked()) {
            ZGConfigHelper.sharedInstance().enableMic(false);
            ZGConfigHelper.sharedInstance().enableMic(true);
        }
        super.onResume();
    }

    public void goSetting(View view) {
        PublishSettingActivityUI.actionStart(this);
    }

    @Override
    protected void onDestroy() {
        listTextMsg.clear();
        // 停止所有的推流和拉流后，才能执行 logoutRoom
        ZGPublishHelper.sharedInstance().stopPreviewView();
        ZGPublishHelper.sharedInstance().stopPublishing();

        // 当用户退出界面时退出登录房间
        ZGBaseHelper.sharedInstance().loginOutRoom();
        ZGBaseHelper.sharedInstance().unInitZegoSDK();
        super.onDestroy();
    }

    /**
     * button 点击事件
     * 开始推流
     */
    public void onStart(View view) {
        // streamID = layoutBinding.edStreamId.getText().toString();
       // streamID = getIntent().getStringExtra("streamID");
        // 隐藏输入StreamID布局

        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("title",title);
        map.put("status",status);
        map.put("cover",cover);
        map.put("proIds",proIds);
        map.put("activityId",-1);
        map.put("isPK",isPK);
        map.put("iscity","");
        instance1.posthttps(UrlCount.Base_CreatRoom, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                CreatRoomBean creatRoomBean = gson.fromJson(json, CreatRoomBean.class);
                if (creatRoomBean.getCode()==0){

                    hideInputStreamIDLayout();

                    streamQuality.setStreamID(String.format("StreamID : %s", streamID));

                    // 开始推流
                    boolean isPublishSuccess = ZGPublishHelper.sharedInstance().startPublishing(streamID, "", ZegoConstants.PublishFlag.JoinPublish);
                    // ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPublishing(anchorStreamID, "anchor", ZegoConstants.PublishFlag.JoinPublish);
                    if (!isPublishSuccess) {
                        AppLogger.getInstance().i(ZGPublishHelper.class, "推流失败啊锕, streamID : %s", streamID);
                        // 修改标题状态拉流失败状态
                        binding.title.setTitleName(getString(R.string.tx_publish_fail));
                    }
                }
            }
        });



    }

    private void hideInputStreamIDLayout() {
        // 隐藏InputStreamIDLayout布局
        layoutBinding.getRoot().setVisibility(View.GONE);
        binding.publishStateView.setVisibility(View.VISIBLE);
        hideKeyboard(ZegoApplication.zegoApplication, layoutBinding.edStreamId);
    }

    private void showInputStreamIDLayout() {
        // 显示InputStreamIDLayout布局
        layoutBinding.getRoot().setVisibility(View.VISIBLE);
        binding.publishStateView.setVisibility(View.GONE);
    }

    //隐藏虚拟键盘
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        }
    }


    public static void actionStart(Activity activity, String roomID,String streamID) {
        Intent intent = new Intent(activity, PKPublishActivityUI.class);
        intent.putExtra("roomID", roomID);
        intent.putExtra("streamID", streamID);
        activity.startActivity(intent);
    }

    public void goCodeDemo(View view) {
        //WebActivity.actionStart(this, "https://doc.zego.im/CN/209.html", getString(R.string.tx_publish_guide));
    }
    //美颜弹窗
    private void showBeautyPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
        seekbar=view.findViewById(R.id.seekBar);
        seekbar2=view.findViewById(R.id.seekBar2);
         final TextView tv1= view.findViewById(R.id.tv1);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.select);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加数据
        ArrayList<Filterbean> beautylist = new ArrayList<>();
        Filterbean beautylist1 = new Filterbean();
        beautylist1.setFname("磨皮");
        beautylist1.setImage(R.drawable.mopi);
        Filterbean beautylist2 = new Filterbean();
        beautylist2.setFname("美白");
        beautylist2.setImage(R.drawable.meibai);
        beautylist.add(beautylist1);
        beautylist.add(beautylist2);
        final BeautyAdapter beautyAdapter = new BeautyAdapter(beautylist,this);
        //设置适配器
        recyclerView.setAdapter(beautyAdapter);
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, 606);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(lvjing,50,510);

        beautyAdapter.setOnItemclick(new BeautyAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                if (position==0){
                    seekbar.setVisibility(View.VISIBLE);
                    seekbar2.setVisibility(View.GONE);
                    beautyAdapter.setColor(position);
                    // 开启美颜
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishStep(2);

                }else {
                    beautyAdapter.setColor(1);

                    //zegoliveRoom.initSDK(appId,appSign);
                    seekbar2.setVisibility(View.VISIBLE);
                    seekbar.setVisibility(View.GONE);
                  //  zegoliveRoom.enableBeautifying(	ZegoBeauty.WHITEN);//true 表示打开美颜，false 表示关闭  美颜
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishFactor(16, MAIN);//此处采样颜色系数采用默认值 4.0
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.5);//此处美白亮度修正系数采用默认值 0.5
                }
            }
        });


        seekbar.setProgress(4);
        tv1.setText(4+"");
        ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishStep(4);
        // 设置美颜美白的亮度修正参数
      //  zegoliveRoom.setWhitenFactor((float) 0.5);//此处美白亮度修正系数采用默认值 0.5
       // zegoliveRoom.setWhitenFactor((float) 0.5);//此处美白亮度修正系数采用默认值 0.5;
        //ZegoLiveRoom.setUser(userID, userName);
        //监听滑动数值
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //zegoliveRoom.initSDK(appId,appSign);
                tv1.setText(progress+"");
                // 设置美颜采样颜色阈值
                  //    zegoliveRoom.setPolishFactor(progress); //此处采样颜色系数采用默认值 4.0
                ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishStep(progress);//此处磨皮的采样半径采用默认值 4.0

                Log.e("TAG", progress+"" );
                    // 设置锐化参数
                  //  zegoliveRoom.setSharpenFactor(progress);//此处锐化系数采用默认值 0.2



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //美白参数
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  //zegoliveRoom.initSDK(appId,appSign);

                if (progress<=10){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 1.0); //此处美白亮度修正系数采用默认值 0.5
                } else if (10<progress&&progress<=20){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.9); //此处美白亮度修正系数采用默认值 0.5
                }else if (progress<20&&progress<=30){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.8); //此处美白亮度修正系数采用默认值 0.5
                }else if (progress<30&&progress<=40){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.7); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress<40&&progress<=50){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.6); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress<50&&progress<=60){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.5); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress<60&&progress<=70){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.4); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress<70&&progress<=80){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.3); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress<80&&progress<=90){
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.2); //此处美白亮度修正系数采用默认值 0.5
                }
                else {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.1); //此处美白亮度修正系数采用默认值 0.5
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //滤镜弹窗
    private void showPopupWindow() {
        final View view = LayoutInflater.from(this).inflate(R.layout.popwindowlvjing, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.select);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加数据
        ArrayList<Filterbean> filterlist = new ArrayList<>();
        Filterbean filterbean1 = new Filterbean();
        filterbean1.setFname("无");
        filterbean1.setImage(R.drawable.wu);
        Filterbean filterbean2 = new Filterbean();
        filterbean2.setFname("蓝调");
        filterbean2.setImage(R.drawable.landiao);
        Filterbean filterbean3 = new Filterbean();
        filterbean3.setFname("夜色");
        filterbean3.setImage(R.drawable.yese);
        Filterbean filterbean4 = new Filterbean();
        filterbean4.setFname("淡雅");
        filterbean4.setImage(R.drawable.danya);
        Filterbean filterbean5 = new Filterbean();
        filterbean5.setFname("哥特");
        filterbean5.setImage(R.drawable.gete);
        Filterbean filterbean6 = new Filterbean();
        filterbean6.setFname("光晕");
        filterbean6.setImage(R.drawable.guangyun);
        Filterbean filterbean7 = new Filterbean();
        filterbean7.setFname("青柠");
        filterbean7.setImage(R.drawable.sujing);
        Filterbean filterbean8 = new Filterbean();
        filterbean8.setFname("简洁");
        filterbean8.setImage(R.drawable.jianjie);
        Filterbean filterbean9 = new Filterbean();
        filterbean9.setFname("黑白");
        filterbean9.setImage(R.drawable.heibai);
        Filterbean filterbean10 = new Filterbean();
        filterbean10.setFname("老化");
        filterbean10.setImage(R.drawable.laohua);
        Filterbean filterbean11 = new Filterbean();
        filterbean11.setFname("浪漫");
        filterbean11.setImage(R.drawable.langman);
        Filterbean filterbean12 = new Filterbean();
        filterbean12.setFname("锐色");
        filterbean12.setImage(R.drawable.ruise);
        Filterbean filterbean13 = new Filterbean();
        filterbean13.setFname("酒红");
        filterbean13.setImage(R.drawable.jiuhong);
        filterlist.add(filterbean1);
        filterlist.add(filterbean2);
        filterlist.add(filterbean3);
        filterlist.add(filterbean4);
        filterlist.add(filterbean5);
        filterlist.add(filterbean6);
        filterlist.add(filterbean7);
        filterlist.add(filterbean8);
        filterlist.add(filterbean9);
        filterlist.add(filterbean10);
        filterlist.add(filterbean11);
        filterlist.add(filterbean12);
        filterlist.add(filterbean13);
        scoreTeamAdapter = new FilterAdapter(filterlist,this);
        recyclerView.setAdapter(scoreTeamAdapter);
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, 606);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(lvjing,50,510);

        //点击切换效果
        scoreTeamAdapter.setOnItemclick(new FilterAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                if (position==0)
                {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.None);//不滤镜
                }else  if(position==1){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Blue);//蓝
                }else  if(position==2){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Dark);//夜色
                }else  if(position==3){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Fade);//淡雅
                }else  if(position==4){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Gothic);//哥特
                }else  if(position==5){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Halo);//光晕
                }else  if(position==6){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Illusion);//梦幻
                }else  if(position==7){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Lomo);//青柠
                }else  if(position==8){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.BlackWhite);//简洁
                }else  if(position==9){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.OldStyle);//黑白
                }else  if(position==10){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Romantic);//老化
                }
                else  if(position==11){
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.SharpColor);//锐色
                }else {
                    scoreTeamAdapter.setColor(12);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Wine);//酒红
                }
            }
        });

  /*      //监听弹窗关闭
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (flag==0){
                    showInputStreamIDLayout();
                }else {

                }
            }
        });*/

    }
    /**
     * 房间聊天消息.
     */
    public void handleRecvRoomMsg(LinearLayout giftParent,Context mContext,String roomID, ZegoRoomMessage[] listMsg) {
        CommentsAdapterz=new CommentsAdapter(mContext, new ArrayList<ZegoRoomMessage>());
       // View views1= View.inflate(mContext, R.layout.activity_publish, null);
       // zbrecycouview=views1.findViewById(R.id.zbrecycouview);

    /*    giftControl = new GiftControl(mContext);
        giftControl.setGiftLayout(giftParent, 3)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画*/


        zbrecycouview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        zbrecycouview.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        zbrecycouview.setItemAnimator(new DefaultItemAnimator());

        for (ZegoRoomMessage message : listMsg) {
            //礼物消息
            if (message.messageType == ZegoIM.MessageType.Text && message.messageCategory == ZegoIM.MessageCategory.Gift) {
                Log.e(TAG, "handleRecvRoomMsg: 礼物+:"+message.content);
                Gson gson = new Gson();
                GiftJsonBean giftJsonBean = gson.fromJson(message.content, GiftJsonBean.class);
                money=Integer.parseInt(giftJsonBean.getSendCount())*giftJsonBean.getAmount();
                giftModel = new GiftModel();
                giftModel.setGiftId(giftJsonBean.getGiftId()+"").setGiftName(giftJsonBean.getGiftName()).setGiftCount(Integer.parseInt(giftJsonBean.getSendCount())).setGiftPic(giftJsonBean.getGiftImage())
                        .setSendUserId(0+"").setSendUserName(giftJsonBean.getUserName()).setAnroute(giftJsonBean.getGiftKey()+"").setSendUserPic(giftJsonBean.getUserIcon()).setSendGiftTime(System.currentTimeMillis())
                        .setCurrentStart(currentStart);
                if (currentStart) {
                    giftModel.setHitCombo(Integer.parseInt(giftJsonBean.getSendCount()));
                }
//                            giftModel.setJumpCombo(10);
                giftControl.loadGift(giftModel);
            /*    //查询礼物接口
                HashMap<String, Object> map = new HashMap<>();
                map.put("num",roomnum);
                instance1.posthttps(UrlCount.Base_Querygiftpk, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        QueryPKGiftBean queryGiftBean = gson.fromJson(json, QueryPKGiftBean.class);
                        if(queryGiftBean.getCode()==0){
                           giftModel = new GiftModel();
                           giftModel.setGiftId(queryGiftBean.getList1().get(2)).setGiftName(queryGiftBean.getList1().get(3)).setGiftCount(Integer.parseInt(queryGiftBean.getList1().get(5))).setGiftPic(queryGiftBean.getList1().get(4))
                                   .setSendUserId(userid).setSendUserName(queryGiftBean.getList1().get(0)).setAnroute(queryGiftBean.getUserName()).setSendUserPic(queryGiftBean.getList1().get(1)).setSendGiftTime(System.currentTimeMillis())
                                   .setCurrentStart(currentStart);
                           if (currentStart) {
                               giftModel.setHitCombo(Integer.parseInt(queryGiftBean.getList1().get(4)));
                           }
              //           giftModel.setJumpCombo(10);
                           giftControl.loadGift(giftModel);

                       }else {
                           Toast.makeText(PKPlayActivityUI.this, queryGiftBean.getMsg(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });*/
            }


            // 文字聊天消息
            if (message.messageType == ZegoIM.MessageType.Text && message.messageCategory == ZegoIM.MessageCategory.Chat) {
                hdnumber++;
                listTextMsg.clear();
                listTextMsg.add(message);
                if (listTextMsg.size() > 0) {
                    mCommentsAdapter.addMsgList(listTextMsg);
                    zbrecycouview.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
                    Log.e("TAG", "可以执行" );
                    zbrecycouview.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", "handleRecvRoomMsg: 后" );
                            // 滚动到最后一行
                            zbrecycouview.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);

                        }
                    });
                }
                //}
            }
        }

    }

    protected void doSendRoomMsg(final String msg) {
        zbrecycouview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        zbrecycouview.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        zbrecycouview.setItemAnimator(new DefaultItemAnimator());
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "空的", Toast.LENGTH_SHORT).show();
            return;
        }

        ZegoRoomMessage roomMessage = new ZegoRoomMessage();
        roomMessage.fromUserID = userid;
        roomMessage.fromUserName = userName;
        roomMessage.content = msg;
        roomMessage.messageType = ZegoIM.MessageType.Text;
        roomMessage.messageCategory = ZegoIM.MessageCategory.Chat;
        roomMessage.messagePriority = ZegoIM.MessagePriority.Default;

        mCommentsAdapter.addMsg(roomMessage);
        zbrecycouview.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "onSendRoomMessage: " + "执行最后一行");
                // 滚动到最后一行
                zbrecycouview.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
            }
        });

        ZGBaseHelper.sharedInstance().getZegoLiveRoom().sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Chat, ZegoIM.MessagePriority.Default, msg, new IZegoRoomMessageCallback() {
            @Override
            public void onSendRoomMessage(int errorCode, String roomID, long messageID) {
                if (errorCode == 0) {
                    Log.e("TAG", "onSendRoomMessage: " + "成功");
                  /*  mTvMessage.setText("");
                    recordLog(MY_SELF + ": 发送房间消息成功, roomID:" + roomID);*/
                } else {
                    Log.e("TAG", "onSendRoomMessage: " + "shibai");
                    //  recordLog(MY_SELF + ": 发送房间消息失败, roomID:" + roomID + ", messageID:" + messageID);
                }
            }
        });
    }

    protected void doSendRoomMsguser(String username,String userid, final String msg) {
        zbrecycouview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        zbrecycouview.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        zbrecycouview.setItemAnimator(new DefaultItemAnimator());
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "空的", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals(userName)){
            return;
        }

        ZegoRoomMessage roomMessage = new ZegoRoomMessage();
        roomMessage.fromUserID = userid;
        roomMessage.fromUserName = username;
        roomMessage.content = msg;
        roomMessage.messageType = ZegoIM.MessageType.Text;
        roomMessage.messageCategory = ZegoIM.MessageCategory.Chat;
        roomMessage.messagePriority = ZegoIM.MessagePriority.Default;

        mCommentsAdapter.addMsg(roomMessage);
        zbrecycouview.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "onSendRoomMessage: " + "执行最后一行");
                // 滚动到最后一行
                zbrecycouview.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
            }
        });

        ZGBaseHelper.sharedInstance().getZegoLiveRoom().sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Chat, ZegoIM.MessagePriority.Default, msg, new IZegoRoomMessageCallback() {
            @Override
            public void onSendRoomMessage(int errorCode, String roomID, long messageID) {
                if (errorCode == 0) {
                    Log.e("TAG", "onSendRoomMessage: " + "成功");
                  /*  mTvMessage.setText("");
                    recordLog(MY_SELF + ": 发送房间消息成功, roomID:" + roomID);*/
                } else {
                    Log.e("TAG", "onSendRoomMessage: " + "shibai");
                    //  recordLog(MY_SELF + ": 发送房间消息失败, roomID:" + roomID + ", messageID:" + messageID);
                }
            }
        });
    }
    //---------------------------------------------------------------------------------------------------------------------

    // 设置视图列表
    protected void initViewList(){

        mBigView = new JoinLiveView(preview, false, "");
        mBigView.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        // 添加可用的视图，此demo展示共四个视图，一个全屏视图+3个小视图
        ArrayList<JoinLiveView> mJoinLiveView = new ArrayList<>();

        final JoinLiveView view1 = new JoinLiveView(binding.audienceView1, false, "");
        view1.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());


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
               /* Toast.makeText(PKPublishActivityUI.this, "点到", Toast.LENGTH_SHORT).show();
                view1.exchangeView(mBigView);*/
            }
        });
        //点击开始倒计时
        timerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myhandler.sendMessageDelayed(Message.obtain(myhandler, 1), 1000);
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
    @Override
    public void finish() {
        super.finish();
      //  getremoveUser();
        // 在退出页面时停止推流
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPublishing();
        // 停止预览
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPreview();

        // 将所有视图设置为可用
        ZGJoinLiveHelper.sharedInstance().freeAllJoinLiveView();
        // 清空已连麦列表
        ZGJoinLiveHelper.sharedInstance().resetJoinLiveAudienceList();

        // 登出房间
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().logoutRoom();
        // 去除 SDK 相关的回调监听
        releaseSDKCallback();
        //获取开始时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        endtime=simpleDateFormat.format(date);
        Intent intent = new Intent(PKPublishActivityUI.this,EndLiveActivity.class);
        intent.putExtra("money",money);
        intent.putExtra("starttime",starttime);
        intent.putExtra("endtime",endtime);
        intent.putExtra("number",numberss);
        intent.putExtra("hdnumber",hdnumber);
        startActivity(intent);
    }

    // 去除SDK相关的回调监听
    public void releaseSDKCallback(){
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePublisherCallback(null);
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePlayerCallback(null);
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoRoomCallback(null);
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoIMCallback(null);
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
     //   ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPublishing(anchorStreamID, "anchor", ZegoConstants.PublishFlag.JoinPublish);

        // 修改视图列表中的视图信息
        mBigView.streamID = anchorStreamID;
        mBigView.isPublishView = true;
        ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView);

        // 拉取其它主播流，即进入其它主播房间进行直播
        for (ZegoStreamInfo streamInfo : zegoStreamInfos) {

            if (streamInfo != null) {
                Toast.makeText(this, "streamInfo != null", Toast.LENGTH_SHORT).show();
                // 修改已连麦用户列表，此demo只展示三人连麦，此处根据连麦人数限制进行拉流
                if (ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers().size() < ZGJoinLiveHelper.MaxJoinLiveNum) {

                    // 获取可用的视图
                    JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();

                    if (freeView != null){
                        Toast.makeText(this, "streamInfo != null", Toast.LENGTH_SHORT).show();
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
        // 设置推流回调
        ZGPublishHelper.sharedInstance().setPublisherCallback(new IZegoLivePublisherCallback() {
            // 推流回调文档说明: <a>https://doc.zego.im/API/ZegoLiveRoom/Android/html/index.html</a>
            @Override
            public void onPublishStateUpdate(int errorCode, String streamID, HashMap<String, Object> hashMap) {
                // 推流状态更新，errorCode 非0 则说明推流失败
                // 推流常见错误码请看文档: <a>https://doc.zego.im/CN/308.html</a>
                if (errorCode == 0) {
                    binding.title.setTitleName(getString(R.string.tx_publish_success));
                    AppLogger.getInstance().i(ZGPublishHelper.class, "推流成功, streamID : %s", streamID);
                    //Toast.makeText(PKPublishActivityUI.this, getString(R.string.tx_publish_success), Toast.LENGTH_SHORT).show();
                } else {
                    binding.title.setTitleName(getString(R.string.tx_publish_fail));
                    AppLogger.getInstance().i(ZGPublishHelper.class, "推流失败, streamID : %s, errorCode : %d", streamID, errorCode);
                    Toast.makeText(PKPublishActivityUI.this, "前面失败", Toast.LENGTH_SHORT).show();
                    // 当推流失败时需要显示布局
                    showInputStreamIDLayout();
                }

            }


            @Override
            public void onJoinLiveRequest(final int i, String userid, String username, String s2) {
                /**
                 * 房间内有人申请加入连麦时会回调该方法
                 * 观众端可通过 {@link ZegoLiveRoom#requestJoinLive(IZegoResponseCallback)}
                 *  方法申请加入连麦
                 * **/
               if (!islm){
                   lmuserid=userid;
                   Gson gson = new Gson();
                   GetuserCountBean getuserCountBean = gson.fromJson(username, GetuserCountBean.class);
                   AlertDialog.Builder builder = new AlertDialog.Builder(PKPublishActivityUI.this);
                   builder.setTitle(R.string.sftc);
                   builder.setMessage(R.string.nsdlyh+ getuserCountBean.getNickName()+R.string.dlmqq);
                   builder.setNegativeButton(R.string.jj, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           zegoLiveRoom.respondJoinLiveReq(i,1);
                       }
                   });
                   builder.setPositiveButton(R.string.ty, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           zegoLiveRoom.respondJoinLiveReq(i,0);
                       }
                   });
                   builder.show();
               }

            }

            @Override
            public void onPublishQualityUpdate(String s, ZegoPublishStreamQuality zegoPublishStreamQuality) {
                /**
                 * 推流质量更新, 回调频率默认3秒一次
                 * 可通过 {@link ZegoLiveRoom#setPublishQualityMonitorCycle(long)} 修改回调频率
                 */
                streamQuality.setFps(String.format("帧率: %f", zegoPublishStreamQuality.vnetFps));
                streamQuality.setBitrate(String.format("码率: %f kbs", zegoPublishStreamQuality.vkbps));
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
                // 当采集时分辨率有变化时，sdk会回调该方法
                streamQuality.setResolution(String.format("分辨率: %dX%d", width, height));
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
        hideInputStreamIDLayout();
        boolean isPublishSuccess = ZGPublishHelper.sharedInstance().startPublishing(streamID, "", ZegoConstants.PublishFlag.JoinPublish);
    }

    public void initPublishiSdk(final LinearLayout giftParent , final Context mContext, final TextView usernumber){

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
        CustomDialog.createDialog("初始化SDK中...",  this).show();
        // 调用sdk接口, 初始化sdk
        boolean results = ZGBaseHelper.sharedInstance().initZegoSDK(null,giftParent,mContext,ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {
                // 关闭加载对话框
                CustomDialog.createDialog(PKPublishActivityUI.this).cancel();
                // errorCode 非0 代表初始化sdk失败
                // 具体错误码说明请查看<a> https://doc.zego.im/CN/308.html </a>
                if (errorCode == 0) {
                    AppLogger.getInstance().i(this.getClass(), "初始化zegoSDK成功");
                    Toast.makeText(PKPublishActivityUI.this, "初始化成功", Toast.LENGTH_SHORT).show();
                    run(giftParent,mContext,usernumber);
                    //initViewList();
                    //initSDKCallback();
                    // 初始化成功，跳转到登陆房间页面。
                    jumpLoginRoom(roomID,starmID);
                } else {
                    AppLogger.getInstance().i(PKPublishActivityUI.this.getClass(), "初始化sdk失败 错误码 : %d", errorCode);
                    Toast.makeText(PKPublishActivityUI.this, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 如果接口调用失败，也需要关闭加载对话框
        if (!results) {
            CustomDialog.createDialog(PKPublishActivityUI.this).cancel();
        }
    }


    /**
     * 跳转登陆页面
     */
    private void jumpLoginRoom(String roomID,String steamID) {
        // flag, 需要传递给登陆房间页面。
        // LoginRoomPublishActivityUI.actionStart(activity, flag);

        // flag = getIntent().getStringExtra("flag");
        // 如果用户标记的动作是推流，则房间角色为 主播 否则为观众角色
        if ("publish".equals(flags)) {
            roomRole = ZegoConstants.RoomRole.Anchor;
        } else {
            roomRole = ZegoConstants.RoomRole.Audience;
        }
        // 每次进来都恢复成sdk默认设置
        PublishSettingActivityUI.clearPublishConfig();
        onLoginRoom(roomID,steamID);

    }

    public void onLoginRoom(String roomId, final String steamID) {
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
                    run2(zegoStreamInfos);
                    // run2(zegoStreamInfos);
                    loginRoomCompletion(true, finalRoomId, errorCode,steamID);
                } else {
                    loginRoomCompletion(false, finalRoomId, errorCode,steamID);
                }
            }
        });

        if (!isLoginRoomSuccess) {
            loginRoomCompletion(false, finalRoomId, -1,steamID);
        }
    }

    /**
     * 处理登陆房间成功或失败的逻辑
     *
     * @param isLoginRoom 是否登陆房间成功
     * @param finalRoomId roomID
     * @param errorCode 登陆房间错误码
     */
    private void loginRoomCompletion(boolean isLoginRoom, String finalRoomId, int errorCode,String steamID) {
        // 关闭加载对话框
        //  CustomDialog.createDialog(LoginRoomPublishActivityUI.this).cancel();
        if (isLoginRoom) {
            AppLogger.getInstance().i(LoginRoomPublishActivityUI.class, "登陆房间成功 roomId : %s", finalRoomId);

            // 登陆房间成功，跳转推拉流页面
            //jumpPublish(finalRoomId, activity, steamID);

        } else {
            AppLogger.getInstance().i(LoginRoomPublishActivityUI.class, "登陆房间失败 errorCode : %s", errorCode == -1 ? "api调用失败" : String.valueOf(errorCode));
            Toast.makeText(PKPublishActivityUI.this, "登陆房间失败", Toast.LENGTH_SHORT).show();
        }
    }
    //接收数据
    public void run(final LinearLayout giftParent, final Context mContext, final TextView usernumber){
        // zegoLiveRoom.setRoomConfig(true, true);
        Log.e("TAG", "IM代理执行1" );
        // 设置SDK 房间代理回调。业务侧希望检查当前房间有流更新了，会去自动重新拉流。
        ZGBaseHelper.sharedInstance().setZegoRoomCallback(new IZegoRoomCallback() {
            @Override
            public void onKickOut(int i, String s) {

            }

            @Override
            public void onDisconnect(int i, String s) {
                binding.title.setTitleName("房间与server断开连接");
            }

            @Override
            public void onReconnect(int i, String s) {

            }

            @Override
            public void onTempBroken(int i, String s) {

            }

            @Override
            public void onStreamUpdated(int type, ZegoStreamInfo[] zegoStreamInfos, String s) {
                if (!islm){
                    islm=true;
                }else {
                    islm=false;
                }


/*                //开始计时
                myhandler.sendMessageDelayed(Message.obtain(myhandler, 1), 1000);*/
                // 房间流列表更新
                // 当登录房间成功后，如果房间内中途有人推流或停止推流。房间内其他人就能通过该回调收到流更新通知。
                Log.e("TAG", "initSDKCallback: 监听有流更新" );
                if (roomID.equals(mRoomID)){

                    for (ZegoStreamInfo streamInfo : zegoStreamInfos) {
                        // 当有流新增的时候，拉流
                        if (type == ZegoConstants.StreamUpdateType.Added) {//roomid
                            AppLogger.getInstance().i(JoinLiveAnchorUI.class, "房间: %s 内收到流新增通知. streamID : %s, userName : %s, extraInfo : %s", "1", streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);

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
                                    usermesge=streamInfo;
                                    ZGJoinLiveHelper.sharedInstance().addJoinLiveAudience(userInfo);
                                    //ZGPublishHelper.sharedInstance().startPreview(view1);
                                    //  zegoLiveRoom.setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
                                    // 修改视图信息
                                    freeView.streamID = streamInfo.streamID;
                                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
                                } else {
                                    Toast.makeText(PKPublishActivityUI.this, getString(R.string.has_no_textureView), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(PKPublishActivityUI.this, R.string.join_live_count_overflow, Toast.LENGTH_LONG).show();
                            }
                        }
                        // 当有其他流关闭的时候，停止拉流
                        else if (type == ZegoConstants.StreamUpdateType.Deleted) {//roomid
                            AppLogger.getInstance().i(PKPublishActivityUI.class, "房间：%s 内收到流删除通知. streamID : %s, userName : %s, extraInfo : %s", "1", streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);
                            // 如果此条流删除信息是连麦者的流，做停止拉流的处理
                            for (JoinLiveUserInfo userInfo:ZGJoinLiveHelper.sharedInstance().getHasJoinedUsers()){
                                if (userInfo.userID.equals(streamInfo.userID)){
                                    // 停止拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPlayingStream(streamInfo.streamID);

                                    // 移除此连麦者
                                    ZGJoinLiveHelper.sharedInstance().removeJoinLiveAudience(userInfo);
                                    // 修改视图信息
                                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamInfo.streamID);
                                    //停止计时并且清空时间
                                    myhandler.removeCallbacksAndMessages(null);
                                    index=0;

                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onStreamExtraInfoUpdated(ZegoStreamInfo[] zegoStreamInfos, String s) {

            }

            @Override
            public void onRecvCustomCommand(String s, String s1, String s2, String s3) {


            }
        });
        //IM代理接收消息监听
        ZGBaseHelper.sharedInstance().getZegoLiveRoom().setZegoIMCallback(new IZegoIMCallback() {
            @Override
            public void onUserUpdate(ZegoUserState[] zegoUserStates, int i) {
                //playActivityUI.numberd(mContext,i,zegoUserStates,usernumber);
                numberd(mContext, i, zegoUserStates, usernumber);
                Log.e("TAG", "onUserUpdate: "+i );


            }

            @Override
            public void onRecvRoomMessage(String s, ZegoRoomMessage[] listMsg) {

                // playActivityUI.handleRecvRoomMsg(giftParent,mContext,roomId, listMsg);

                handleRecvRoomMsg(giftParent,mContext,"1", listMsg);
                //  publishActivityUI.handleRecvRoomMsg(giftParent,mContext,roomId,listMsg);
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
    public void getdate() {
        Glide.with(PKPublishActivityUI.this).load( sharedPreferences.getString("photoUrl",null))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(headimg);
        zbname.setText(sharedPreferences.getString("nickName",null));
                //设置成员列表
               // getdata2();


    }
    public void  removedata(String json){
        Gson gson = new Gson();
        GetuserCountBean getuserCountBean = gson.fromJson(json, GetuserCountBean.class);
        // userlist.addAll((Collection<? extends GetuserCountBean>) getuserCountBean);
        for (int i = 0; i <userlist.size() ; i++) {
            if (getuserCountBean.getNickName().equals(userlist.get(i).getNickName())){
                userlist.remove(i);
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    public void getdata2(String json) {
        Gson gson = new Gson();
        GetuserCountBean getuserCountBean = gson.fromJson(json, GetuserCountBean.class);
        userlist.add( getuserCountBean);
        userAdapter.notifyDataSetChanged();
        //条目点击事件
        userAdapter.setOnItemclick(new UserAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {

            }
        });
    }

    /*public void getdata2() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("roomNum", roomnum);
        instance1.posthttps(UrlCount.Base_getCurrentUser, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                GetUserCount getUserCount = gson.fromJson(json, GetUserCount.class);
                List<GetUserCount.ListBean> list = getUserCount.getList();
                userlist.clear();
                userlist.addAll(list);
                userAdapter.notifyDataSetChanged();
                //条目点击事件
                userAdapter.setOnItemclick(new UserAdapter.OnItemclick() {
                    @Override
                    public void getposition(int position) {
                        ZGBaseHelper.sharedInstance().getZegoLiveRoom().inviteJoinLive(userlist.get(position).getId()+"", new IZegoResponseCallback() {
                            @Override
                            public void onResponse(int i, String s, String s1) {
                                if (i==0){
                                    Toast.makeText(PKPublishActivityUI.this, "用户同意", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(PKPublishActivityUI.this, "用户拒绝", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });
            }
        });

    }*/
    //直播间人数
    public void numberd(Context mContext, int i, ZegoUserState[] zegoUserStates, TextView usernumbers) {
      /*  View views = View.inflate(mContext, R.layout.activity_play, null);
        usernumber = views.findViewById(R.id.usernumber);*/
        for (ZegoUserState user : zegoUserStates) {
            Log.e(TAG, "numberd: "+"总人数"+i );
            Log.e(TAG, "角色" + user.roomRole + "名称" + user.userName + "id:" + user.userID + "flag" + user.updateFlag+"总人数"+i);
            if (user.updateFlag == 1) {
                getdata2(user.userName);
                number++;
                numberss++;
                doSendRoomMsguser(userName,user.userID,"来了");
                Log.e(TAG, "number: ++" + number);
                usernumbers.setText(number + "");
            } else {
                removedata(user.userName);
                number--;
                Log.e(TAG, "number: --" + number);
                usernumbers.setText(number + "");

            }

            //Toast.makeText(getContexts(), "人数:" + i + "角色" + user.roomRole + "名称" + user.userName + "id:" + user.userID + "flag" + user.updateFlag, Toast.LENGTH_SHORT).show();
        }
    }
    //移除用户
    public void getremoveUser() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userid);
        map.put("roomNum", roomnum);
        instance1.posthttps(UrlCount.Base_RemoveUser, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Log.e("用户结果", json);
            }
        });
    }
    //评论弹窗
    @SuppressLint("WrongConstant")
    private void showPopupWindows() {
        popupWindow1.setContentView(view);
        final ImageView img = view.findViewById(R.id.sendimg);
        ed_text = view.findViewById(R.id.ed_text);
        showSoftInput(view);
        popupWindow1.setFocusable(true);
        ed_text.setFocusable(true);
        ed_text.setFocusableInTouchMode(true);
        ed_text.requestFocus();
        ed_text.findFocus();
        InputMethodManager inputManager = (InputMethodManager) ed_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed_text, 0);
        popupWindow1.showAsDropDown(pl, 50, -633);

        //发送消息
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boo) {
                    mCommentsAdapter.notifyDataSetChanged();
                    String trim = ed_text.getText().toString().trim();
                    doSendRoomMsg(trim);
                    ed_text.setText(null);
                }
            }
        });

        //监听评论弹窗关闭
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                pl.setVisibility(View.VISIBLE);
                hideorshow.setVisibility(View.VISIBLE);
                colseSoftInputMethod(view);
            }
        });

        //输入框变化监听
        ed_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    img.setImageResource(R.drawable.fei11);
                    boo = true;
                } else {
                    img.setImageResource(R.drawable.fei1);
                    boo = false;
                }
            }
        });


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder( PKPublishActivityUI.this)
                    .setTitle(R.string.jg)
                    .setMessage(R.string.sftc)
                    .setNegativeButton(R.string.back_qx,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            })
                    .setPositiveButton(R.string.shuor,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    finish();
                                }
                            }).show();
            return false;
        }

        return super.onKeyDown(keyCode, event);

    }
}
