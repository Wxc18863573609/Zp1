package com.zupu.zp.testpakeyge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import com.zupu.zp.adapter.CzAdapter;
import com.zupu.zp.adapter.FilterAdapter;
import com.zupu.zp.adapter.GiftAdapter;
import com.zupu.zp.adapter.NumberAdapter;
import com.zupu.zp.adapter.ProductAdapter;
import com.zupu.zp.adapter.UserAdapter;
import com.zupu.zp.bean.Codebean;
import com.zupu.zp.bean.CzBean;
import com.zupu.zp.bean.Filterbean;
import com.zupu.zp.bean.GetuserCountBean;
import com.zupu.zp.bean.GiftJsonBean;
import com.zupu.zp.bean.Giftbeans;
import com.zupu.zp.bean.Giftnumberbean;
import com.zupu.zp.bean.Givebean;
import com.zupu.zp.bean.ListBean;
import com.zupu.zp.bean.ProductBean;
import com.zupu.zp.bean.QueryGZbean;
import com.zupu.zp.bean.TowAroute;
import com.zupu.zp.bean.WxPayBean;
import com.zupu.zp.bean.Wxmorebean;
import com.zupu.zp.bean.ZanBean;
import com.zupu.zp.bean.ZbddBean;
import com.zupu.zp.bean.ZfbPayBean;
import com.zupu.zp.bean.ZtBean;
import com.zupu.zp.databinding.ActivityPkPlayBinding;
import com.zupu.zp.databinding.PlayInputStreamIdLayoutBinding;
import com.zupu.zp.entity.PlaySettingActivityUI;
import com.zupu.zp.entity.SDKConfigInfo;
import com.zupu.zp.entity.StreamQuality;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.entity.ZGConfigHelper;
import com.zupu.zp.entity.ZGManager;
import com.zupu.zp.entity.ZGPlayHelper;
import com.zupu.zp.entity.ZGPublishHelper;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.giftutli.GiftBean;
import com.zupu.zp.giftutli.GiftControl;
import com.zupu.zp.giftutli.GiftPanelControl;
import com.zupu.zp.giftutli.widget.CustormAnim;
import com.zupu.zp.giftutli.widget.GiftModel;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.lianmai.ui.JoinLiveAudienceUI;
import com.zupu.zp.myactivity.ShaoppingmallAivity;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.KeyboardStateObserver;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.ZfUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

import static com.zego.zegoavkit2.ZegoConstants.PublishChannelIndex.MAIN;
/*
 * 拉流页面
 * */

public class PKPlayActivityUI extends BaseActivitys {
    SharedPreferences.Editor editor;
    ArrayList<ListBean> prodacylist = new ArrayList<>();
    int flag1;
    ImageView fx;
    ZfUtil zfUtil = ZfUtil.getInstance();
    TextView numbers;
    LinearLayout linertow;
    int anthidTow, anthid;
    private SeekBar seekbar, seekbar2;
    // private ArrayList<String> mPlayStreamIDs = new ArrayList<>();
    boolean islm;
    long baseTimer = 0;
    boolean isNo;
    TextView timerView;
    int index = 0;
    FilterAdapter scoreTeamAdapter;
    Handler myhandler = new Handler() {
        public void handleMessage(Message msg) {
            if (0 == baseTimer) {
                baseTimer = SystemClock.elapsedRealtime();
            }

            int time = (int) ((SystemClock.elapsedRealtime() - baseTimer) / 1000);
            String mm = new DecimalFormat("00").format(time / 60);
            String ss = new DecimalFormat("00").format(time % 60);
            if (null != timerView) {
                timerView.setText(index++ + "s");
            }
            Message message = Message.obtain();
            message.what = 0x0;
            sendMessageDelayed(message, 1000);
        }
    };
    ArrayList<GetuserCountBean> userlist = new ArrayList<>();
    String uuid;
    String roomnum;
    String userid;
    String userName;
    TextView zan1text, zan2text;
    ImageView zan1, zan2;
    int zan1number, zan2number;
    private CommentsAdapter mCommentsAdapter = null;
    private RecyclerView mLvComments = null;
    private RecyclerView mLvCommentsd = null;
    ZegoLiveRoom zegoLiveRoom = new ZegoLiveRoom();
    private int roomRole = ZegoConstants.RoomRole.Audience;
    public static ZegoStreamInfo[] zegoStreamInfo;
    String flag;
    // 主播房间ID
    private String mRoomID = "1";
    // 主播ID
    private String mAnchorID = "";
    TextView ednumber;
    String imgurl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3323361914,1254877484&fm=26&gp=0.jpg";
    // 推流流名
    // private String mPublishStreamID = ZegoUtil.getPublishStreamID();
    private String mPublishStreamID = "";
    // 是否连麦
    private boolean isJoinedLive = false;
    // 已拉流流名列表
    private ArrayList<String> mPlayStreamIDs = new ArrayList<>();

    // 大view
    private JoinLiveView mBigView = null;
    ImageView numberxz;
    String TAG = "PlayActivityUI";
    boolean boo, b = false;
    EditText ed_text;
    private ZGBaseHelper.ZGBaseState zgBaseState = ZGBaseHelper.ZGBaseState.WaitInitState;
    List<ZegoRoomMessage> listTextMsg = new ArrayList<>();
    Httputlis1 instance1 = Httputlis1.getInstance();
    private ActivityPkPlayBinding binding;
    private PlayInputStreamIdLayoutBinding layoutBinding;
    private String mStreamID;
    private StreamQuality streamQuality = new StreamQuality();
    private SDKConfigInfo sdkConfigInfo = new SDKConfigInfo();
    EditText edstram;
    Context mContext;
    ImageView headimg, songli, headimg1;
    ImageView guanzhu, kill, guanzhu1;
    RecyclerView userrecycel;
    int giftnum;
    int number = 0;
    private ArrayList<Giftbeans.ListBean> listBeans = new ArrayList<>();
    RelativeLayout sendmessgetext;
    PopupWindow popupWindow, giftpopWindow, numberpopWindow, ed_popupWindow, popupWindow1,shaoppingpopWindow,chongPopupwindown;
    View view, giftview, numberview, ed_view,shaoppingview,chongview;
    LinearLayout linearLayoutnoe, linearLayoutow;
    TextureView play;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    private LinearLayout hideorshow, lone, ltow;
    RelativeLayout lian;
    TextureView play_view, audienceView1s1;
    private boolean boonumber = false;
    private LinearLayout giftLayout;
    private LinearLayout ll_portrait;
    private LinearLayout ll_landscape,lner2;
    private TextView tvGiftNum;
    private ImageView btnGift;
    private ViewPager mViewpager;
    private LinearLayout mDotsLayout;
    private String mGiftur2 = "https://www.baidu.com/link?url=upTp823bIRYrBa3wMRMKt8OZiyW2yvHAEKvGtHl1_mzcCyuEG5K5WU6xB52j8Z4SSTOQ-H9TyaTgSGozrnNNtW2HJB0mzrrWBZ2Sf5__-TCaXatCou64QRG8qRIPz1Vr0vEmNB2wlOxLVh1DVI8yR_16By_5hOsitwN8RdXf5TbaBrnHusf-WCvXrUauNLu2iLDaoKYiNrPz0_us_x7SwLMgyaaXaXltI0GHV1f1bmPOQv02hs1i82_kEimr5tZrJ-JzppDfgkI2pOy32x0fGWdQ8dEaXCQRizslHECJVKe5At-dL3GNglyOJeMsGtBZUILqSMHWpDFTmsZXeL0fbyHrzvDqalSpVF81RTUnAmQVES61vu8Nq9oqKqIbh0jvh834iQ3TBRHluf3fZotb483MdKnA2zLQVRLXWUhjXH7aE5zcFZH44iCV0oWddUTNezXBS7Kv4aLTP8mEuKU6_hdXP4S5SS17Vc6hw-AknV06ixClo3CEHoqNd-BENL8omid1KHPZH7JONhPTY0edlad06ODVOjdRdHoLO-al0qVr-ol-71roJL3f3x0zlPG-5ITf7bIeapIx0qveGFt3lfJAiKxAXVzzEQ2XSYOGC6kg5-243BucFfn1ftacp0jkVcU4ODx8IV847ArttkkbA8aoMHFf2QUT53VfDw2BixI0jbganrbjPHqeiCR8IA7h&timg=&click_t=1573117503429&s_info=1479_759&wd=&eqid=c8cffdda000004e4000000025dc3de39";
    private String mGifturl = "https://www.baidu.com/link?url=upTp823bIRYrBa3wMRMKt8OZiyW2yvHAEKvGtHl1_mzcCyuEG5K5WU6xB52j8Z4SSTOQ-H9TyaTgSGozrnNNtW2HJB0mzrrWBZ2Sf5__-TCaXatCou64QRG8qRIPz1Vr0vEmNB2wlOxLVh1DVI8yR_16By_5hOsitwN8RdXf5TbaBrnHusf-WCvXrUauNLu2iLDaoKYiNrPz0_us_x7SwLMgyaaXaXltI0GHV1f1bmPOQv02hs1i82_kEimr5tZrJ-JzppDfgkI2pOy32x0fGWdQ8dEaXCQRizslHECJVKe5At-dL3GNglyOJeMsGtBZUILqSMHWpDFTmsZXeL0fbyHrzvDqalSpVF81RTUnAmQVES61vu8Nq9oqKqIbh0jvh834iQ3TBRHluf3fZotb483MdKnA2zLQVRLXWUhjXH7aE5zcFZH44iCV0oWddUTNezXBS7Kv4aLTP8mEuKU6_hdXP4S5SS17Vc6hw-AknV06ixClo3CEHoqNd-BENL8omid1KHPZH7JONhPTY0edlad06ODVOjdRdHoLO-al0qVr-ol-71roJL3f3x0zlPG-5ITf7bIeapIx0qveGFt3lfJAiKxAXVzzEQ2XSYOGC6kg5-243BucFfn1ftacp0jkVcU4ODx8IV847ArttkkbA8aoMHFf2QUT53VfDw2BixI0jbganrbjPHqeiCR8IA7h&timg=&click_t=1573117503429&s_info=1479_759&wd=&eqid=c8cffdda000004e4000000025dc3de39";
    private String mGiftName = "";
    TextView zbname, zbname1;
    private String mGiftPrice = "";
    private RecyclerView mRecyclerView;
    private GiftControl giftControl;
    private RecyclerView recyclerView;
    private com.zupu.zp.giftutli.GiftMsgAdapter adapter;
    private GiftModel giftModel;
    private boolean currentStart;
    private int position1 = -1;
    String roomID;
    String streamID;
    private TextView usernumber;
    LinearLayout giftParent;
    UserAdapter userAdapter;
    SharedPreferences sharedPreferences;
    String headPic;
    RelativeLayout beauty, filter, camera, stop;
    int anthidone, anthitow;
    String mAnchornameone = "", mAnchornametow = "";
    String integral;
    ImageView  shaopping;
    int isplayBack;
    String  replayurl;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pk_play);
        shaopping=findViewById(R.id.shaopping);
        fx = findViewById(R.id.fx);
        lner2=findViewById(R.id.lner2);
        linertow=findViewById(R.id.linertow);
        play_view = findViewById(R.id.play_view);
        beauty = findViewById(R.id.beauty);
        filter = findViewById(R.id.filter);
        camera = findViewById(R.id.camera);
        zbname = findViewById(R.id.zbname);
        zbname1 = findViewById(R.id.zbname1);
        zan1 = findViewById(R.id.zan1);
        zan2 = findViewById(R.id.zan2);
        zan2text = findViewById(R.id.zan2text);
        zan1text = findViewById(R.id.zan1text);
        lone = findViewById(R.id.lnoe);
        ltow = findViewById(R.id.ltow);
        stop = findViewById(R.id.stop);
        timerView = (TextView) this.findViewById(R.id.timerView);
        mContext = this;
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.rec_video);
        Intent intent = getIntent();
        isplayBack = intent.getIntExtra("isplayBack", 0);
        replayurl = intent.getStringExtra("replayurl");

        Log.e("是否直播", isplayBack + "?");
        // 开始拉流
        //getmidplaer();

        Log.e("地址", replayurl+"??");
        if(isplayBack==1&&replayurl!=null){
            play_view.setVisibility(View.GONE);
            lner2.setVisibility(View.GONE);
            jzVideoPlayerStandard.setVisibility(View.VISIBLE);
            jzVideoPlayerStandard.setUp(replayurl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            jzVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.timg);
        }
        roomID = intent.getStringExtra("roomID");
        roomnum = roomID;
        streamID = intent.getStringExtra("streamID");
        flag = intent.getStringExtra("pull");
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        uuid = sharedPreferences.getString("appLoginIdentity", null);
        userid = sharedPreferences.getString("userId", null);
        ;
        userName = sharedPreferences.getString("nickName", null);
        headPic = sharedPreferences.getString("photoUrl", null);
        integral = sharedPreferences.getString("integral", null);
        editor = sharedPreferences.edit();
        mPublishStreamID = userid;
        showGiftMsgList();
        initGiftLayout();
        final List<GiftBean.GiftListBean> giftListBeen = fromNetData();//来自网络礼物图片
        List<GiftModel> giftModels = toGiftModel(giftListBeen);//转化为发送礼物的集合

        GiftPanelControl giftPanelControl = new GiftPanelControl(this, mViewpager, mRecyclerView, mDotsLayout);
        // giftPanelControl.init(giftModels);//这里如果为null则加载本地礼物图片
        giftPanelControl.setGiftListener(new GiftPanelControl.GiftListener() {
            @Override
            public void getGiftInfo(String giftPic, String giftName, String giftPrice) {
                mGifturl = giftPic;
                mGiftName = giftName;
                mGiftPrice = giftPrice;
            }
        });
        giftParent = (LinearLayout) findViewById(R.id.ll_gift_parent);
        giftControl = new GiftControl(this);
        giftControl.setGiftLayout(giftParent, 3)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
        usernumber = findViewById(R.id.usernumber);
        mLvComments = findViewById(R.id.lv_comments);
        mCommentsAdapter = new CommentsAdapter(this, new ArrayList<ZegoRoomMessage>());

        //查询是否关注过主播
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userid);
        map.put("anthId", anthidone);
        instance1.posthttps(UrlCount.URL_Query_isAttention, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                // gson.fromJson(json, );
            }
        });

        //初始化sdk
        pullinitsdk(giftParent, mContext, usernumber);
        hideorshow = findViewById(R.id.hideorshow);
        lian = findViewById(R.id.lian);
        kill = findViewById(R.id.kill);
        play = findViewById(R.id.play_view);
        songli = findViewById(R.id.songli);
        audienceView1s1 = findViewById(R.id.audienceViews);
        mBigView = new JoinLiveView(play_view, false, "");
        initViewList();
        KeyboardStateObserver.getKeyboardStateObserver(this).
                setKeyboardVisibilityListener(new KeyboardStateObserver.OnKeyboardVisibilityListener() {
                    @Override
                    public void onKeyboardShow() {
                        //  Toast.makeText(PlayActivityUI.this,"键盘弹出",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onKeyboardHide() {
                        popupWindow.dismiss();

                    }
                });
        mLvComments = (RecyclerView) findViewById(R.id.lv_comments);
        edstram = findViewById(R.id.ed_stream_id);
        linearLayoutnoe = findViewById(R.id.linnerlaoutnoe);
        linearLayoutow = findViewById(R.id.linnerlaouttow);

        //聊天弹窗
        view = LayoutInflater.from(this).inflate(R.layout.sendmessgewindow, null);
        giftview = LayoutInflater.from(this).inflate(R.layout.giftlaout, null);
        numberview = LayoutInflater.from(this).inflate(R.layout.number_popwind, null);
        chongview = LayoutInflater.from(this).inflate(R.layout.chonglaout, null);
        ed_view = LayoutInflater.from(this).inflate(R.layout.ed_popwind, null);
        shaoppingview = LayoutInflater.from(this).inflate(R.layout.shaoppinglaout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 200);
        ed_popupWindow = new PopupWindow(ed_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        chongPopupwindown = new PopupWindow(chongview, ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        giftpopWindow = new PopupWindow(giftview, ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        numberpopWindow = new PopupWindow(numberview, 360, 450);
        shaoppingpopWindow = new PopupWindow(shaoppingview, 360, 450);
/*        //设置弹出窗体需要软键盘，
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);*/
        giftpopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chongPopupwindown.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // layoutBinding = binding.layout;
        //    layoutBinding.startButton.setText(getString(R.string.tx_start_play));
        // 利用DataBinding 可以通过bean类驱动UI变化。
        // 方便快捷避免需要写一大堆 setText 等一大堆臃肿的代码。
        binding.setQuality(streamQuality);
        binding.setConfig(sdkConfigInfo);
        headimg = findViewById(R.id.headimg);
        headimg1 = findViewById(R.id.headimg1);
        userrecycel = findViewById(R.id.userrecycel);
        guanzhu = findViewById(R.id.guanzhu);
        guanzhu1 = findViewById(R.id.guanzhu1);
        ImageView textView = findViewById(R.id.sadas);
        sendmessgetext = findViewById(R.id.sendmessgetext);
        streamQuality.setRoomID(String.format("RoomID : %s", getIntent().getStringExtra("roomID")));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        userrecycel.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(userlist, this);
        //设置适配器
        userrecycel.setAdapter(userAdapter);
        getdate();
        getzbmsg();
        //点击分享
        fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zfUtil.myshare(UrlCount.Base_Head+"mobile/live_share.html?roomId="+roomnum+"&streamId="+streamID+"&isPlayBack="+isplayBack+"&isPK="+1+"&playUrl="+replayurl,"直播分享","有人向你分享了直播",sharedPreferences.getString("photoUrl",null),PKPlayActivityUI.this);
            }
        });

        //退出直播间
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //  String streamID = getIntent().getStringExtra("roomID");

        // 隐藏输入StreamID布局
//        hideInputStreamIDLayout();
        // 更新界面上流名
        streamQuality.setStreamID(String.format("StreamID : %s", streamID));
        // 开始拉流
        boolean isPlaySuccess = ZGPlayHelper.sharedInstance().startPlaying(streamID, binding.playView);
        if (!isPlaySuccess) {
            AppLogger.getInstance().i(ZGPublishHelper.class, "拉流失败, streamID : %s", streamID);
            popupWindow.dismiss();
          //  Toast.makeText(PKPlayActivityUI.this, getString(R.string.tx_play_fail), Toast.LENGTH_SHORT).show();
            // 修改标题状态拉流失败状态
            binding.title.setTitleName(getString(R.string.tx_play_fail));
            // 解除视图占用
            ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);

            // 从已拉流列表中移除该流名
            mPlayStreamIDs.remove(streamID);
        }


        //点赞1
        zan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zan1number++;
                zan1text.setText(zan1number + "");
                JSONObject root = new JSONObject();
                try {
                    root.put("flag", 0);
                    root.put("number", zan1number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ZGBaseHelper.sharedInstance().getZegoLiveRoom().sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Like, ZegoIM.MessagePriority.Default, root.toString(), new IZegoRoomMessageCallback() {
                    @Override
                    public void onSendRoomMessage(int i, String s, long l) {

                    }
                });
            }
        });
        //点赞2
        zan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zan2number++;
                zan2text.setText(zan2number + "");
                JSONObject root = new JSONObject();
                try {
                    root.put("flag", "1");
                    root.put("number", zan2number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ZGBaseHelper.sharedInstance().getZegoLiveRoom().sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Like, ZegoIM.MessagePriority.Default, root.toString(), new IZegoRoomMessageCallback() {
                    @Override
                    public void onSendRoomMessage(int i, String s, long l) {

                    }
                });
            }
        });
      //查询商品数据
        HashMap<String, Object> map00 = new HashMap<>();
        map00.put("roomNum",roomID);
        instance1.posthttps(UrlCount.URL_Queryproduct, map00, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Log.e(TAG, "成功????"+json );
                Gson gson = new Gson();
                ProductBean productBean = gson.fromJson(json, ProductBean.class);

                if (productBean.getCode()==0){
                    Log.e(TAG, "成功??>>>>"+json );
                    for (int i = 0; i < productBean.getList().size(); i++) {
                        prodacylist.add(productBean.getList().get(i));
                    }
                }

            }
        });



        //点击商城
        shaopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shaoppingpopWindow.setContentView(shaoppingview);
                shaoppingpopWindow.setFocusable(true);
                shaoppingpopWindow.showAsDropDown(songli, 0, -600);
                RecyclerView numberrecyview = shaoppingview.findViewById(R.id.shaoping_recyview);
                RelativeLayout req = shaoppingview.findViewById(R.id.req);
                if (prodacylist.size()==0){
                    req.setVisibility(View.VISIBLE);
                }else {
                    req.setVisibility(View.GONE);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PKPlayActivityUI.this);
                linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                numberrecyview.setLayoutManager(linearLayoutManager);
                ProductAdapter productAdapter = new ProductAdapter(prodacylist, PKPlayActivityUI.this);
                numberrecyview.setAdapter(productAdapter);
                productAdapter.setOnItemclick(new ProductAdapter.OnItemclick() {
                    @Override
                    public void getposition(int position) {
                        //跳转商城页面
                        productAdapter.setColor(position);
                        Intent intent1 = new Intent(PKPlayActivityUI.this, ShaoppingmallAivity.class);
                        intent1.putExtra("id",prodacylist.get(position).getId()+"");
                        startActivity(intent1);
                        shaoppingpopWindow.dismiss();
                    }
                });
            }
        });
        //设置关注
        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag1 == 0) {
                    guanzhu.setImageResource(R.drawable.fllow);
                    //根据接口状态请求修改状态
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("flag", flag1);
                    map.put("userId", userid);
                    map.put("anthId", anthid);
                    instance1.posthttps(UrlCount.URL_Guanzhu, map, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            flag1 = 1;
                            Toast.makeText(PKPlayActivityUI.this, R.string.czcg, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    guanzhu.setImageResource(R.drawable.deletefollow);
                    //根据接口状态请求修改状态
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("flag", flag1);
                    map.put("userId", userid);
                    map.put("anthId", anthid);
                    instance1.posthttps(UrlCount.URL_Guanzhu, map, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            flag1 = 0;
                            Toast.makeText(PKPlayActivityUI.this, R.string.czcg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        //给主播送礼
        songli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowgift();
            }
        });
        //连麦
        lian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", Integer.parseInt(userid));
                instance1.posthttps(UrlCount.URL_Zt, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        ZtBean ztBean = gson.fromJson(json, ZtBean.class);
                        ZtBean.UserBean user = ztBean.getUser();
                        if (ztBean.getCode() == 0) {
                            if (user.getIsCertification().equals("1")) {
                                Toast.makeText(PKPlayActivityUI.this, R.string.wsmqsd, Toast.LENGTH_SHORT).show();
                            } else if (user.getIsCertification().equals("2")) {
                                Toast.makeText(PKPlayActivityUI.this, R.string.shzqnxdd, Toast.LENGTH_SHORT).show();
                            } else if (user.getIsCertification().equals("3")) {
                                Toast.makeText(PKPlayActivityUI.this, R.string.rzwtg, Toast.LENGTH_SHORT).show();
                                //跳转认证
                            } else {
                                // 6.0及以上的系统需要在运行时申请CAMERA RECORD_AUDIO权限
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                            || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(PKPlayActivityUI.this, new String[]{
                                                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
                                    } else {
                                        Log.e(TAG, "相机权限已开启");
                                        ZGBaseHelper.sharedInstance().getZegoLiveRoom().requestJoinLive(new IZegoResponseCallback() {
                                            @Override
                                            public void onResponse(int i, String s, String s1) {
                                                if (i == 0) {
                                                    lone.setVisibility(View.GONE);
                                                    ltow.setVisibility(View.VISIBLE);
                                                    Toast.makeText(PKPlayActivityUI.this, R.string.zbtylm, Toast.LENGTH_SHORT).show();
                                                    //提交副主播信息并进行连麦推流
                                                    HashMap<String, Object> map = new HashMap<>();
                                                    map.put("userid", userid);
                                                    map.put("num", roomnum);
                                                    instance1.posthttps(UrlCount.URL_GetAnchorUserId, map, new Httputlis1.Mycallbacks() {
                                                        @Override
                                                        public void sucess(String json) {
                                                            b = true;
                                                            startlm();
                                                        }
                                                    });
                                                } else {
                                                    b = false;
                                                    Toast.makeText(PKPlayActivityUI.this, R.string.zbjjlm, Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }
                                } else {
                                    Log.e(TAG, "版本较低 ");
                                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().requestJoinLive(new IZegoResponseCallback() {
                                        @Override
                                        public void onResponse(int i, String s, String s1) {
                                            if (i == 0) {
                                                lone.setVisibility(View.GONE);
                                                ltow.setVisibility(View.VISIBLE);
                                                Toast.makeText(PKPlayActivityUI.this, R.string.zbtylm, Toast.LENGTH_SHORT).show();
                                                //提交副主播信息并进行连麦推流
                                                HashMap<String, Object> map = new HashMap<>();
                                                map.put("userid", userid);
                                                map.put("num", roomnum);
                                                instance1.posthttps(UrlCount.URL_GetAnchorUserId, map, new Httputlis1.Mycallbacks() {
                                                    @Override
                                                    public void sucess(String json) {
                                                        b = true;
                                                        startlm();
                                                    }
                                                });
                                            } else {
                                                b = false;
                                                Toast.makeText(PKPlayActivityUI.this,  R.string.zbjjlm, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }else {
                            Toast.makeText(PKPlayActivityUI.this,ztBean.getMsg() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //转换摄像头
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNo) {
                    isNo = false;
                    ZGManager.sharedInstance().api().setFrontCam(true);
                } else {
                    isNo = true;
                    ZGManager.sharedInstance().api().setFrontCam(false);
                }
            }
        });
        //停止连麦
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                startlm();
                lone.setVisibility(View.VISIBLE);
                ltow.setVisibility(View.GONE);
            }
        });


        // 开启美颜+磨皮
        ZGBaseHelper.sharedInstance().getZegoLiveRoom().enableBeautifying(ZegoBeauty.POLISH | ZegoBeauty.WHITEN); //true 表示打开美颜，false 表示关闭  美颜
        //滤镜
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowlj();
                // hideInputStreamIDLayout();
            }
        });
        //点击弹出美颜
        //美颜监听按钮
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBeautyPopupWindow();
                //  hideInputStreamIDLayout();
            }
        });
        //点击输入
        sendmessgetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
                hideorshow.setVisibility(View.GONE);
            }
        });

        //监听弹窗关闭
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sendmessgetext.setVisibility(View.VISIBLE);
                hideorshow.setVisibility(View.VISIBLE);
                colseSoftInputMethod(view);
            }
        });
        //监听弹窗关闭
        numberpopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                numberxz.setImageResource(R.drawable.dwon);
                boonumber = false;
            }
        });

        //  mCommentsAdapter = new CommentsAdapter(this, new ArrayList<ZegoRoomMessage>());
        mLvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLvComments.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        mLvComments.setItemAnimator(new DefaultItemAnimator());


    }

    /**
     * Button点击事件, 跳转官网示例代码链接
     *
     * @param view
     */
    public void goCodeDemo(View view) {
        //  WebActivity.actionStart(this, "https://doc.zego.im/CN/217.html", getString(com.zego.common.R.string.tx_play_guide));

    }

    @Override
    protected void onDestroy() {
        if (jzVideoPlayerStandard!=null)
            jzVideoPlayerStandard.releaseAllVideos();
        //getremoveUser();
        //销毁动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }

        listTextMsg.clear();
        // 停止所有的推流和拉流后，才能执行 logoutRoom
        if (mStreamID != null) {
            ZGPlayHelper.sharedInstance().stopPlaying(mStreamID);
        }

        // 当用户退出界面时退出登录房间
        ZGBaseHelper.sharedInstance().loginOutRoom();
        ZGBaseHelper.sharedInstance().unInitZegoSDK();
       /* if(jzVideoPlayerStandard!=null)
        jzVideoPlayerStandard.release();*/
        super.onDestroy();
    }

    /**
     * button 点击事件触发
     * 开始拉流
     *
     * @param view
     */
    public void onStart(View view) {

    }

    /**
     * 跳转到常用界面
     *
     * @param view
     */
    public void goSetting(View view) {
        PlaySettingActivityUI.actionStart(this, mStreamID);
    }


    private void hideInputStreamIDLayout() {
        // 隐藏InputStreamIDLayout布局
//        layoutBinding.getRoot().setVisibility(View.GONE);
        binding.publishStateView.setVisibility(View.VISIBLE);
    }

    private void showInputStreamIDLayout() {
        // 显示InputStreamIDLayout布局
//        layoutBinding.getRoot().setVisibility(View.VISIBLE);
        binding.publishStateView.setVisibility(View.GONE);
    }

    public static void actionStart(Activity activity, String roomID, String steamID) {
        Intent intent = new Intent(activity, PKPlayActivityUI.class);
        intent.putExtra("roomID", roomID);
        intent.putExtra("steamID", steamID);
        activity.startActivity(intent);
    }


    private void showPopupWindowgift() {

     /*   //监听弹窗关闭
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sendmessgetext.setVisibility(View.VISIBLE);
                hideorshow.setVisibility(View.VISIBLE);
                colseSoftInputMethod(view);
            }
        });*/
        TextView more_number = numberview.findViewById(R.id.more_number);
        final EditText ednumbers = numberview.findViewById(R.id.ornumber);
        ednumbers.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        ednumbers.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        RecyclerView giftrecyview = giftview.findViewById(R.id.giftrecyview);
        Button give = giftview.findViewById(R.id.song);
        RelativeLayout layout_gift = giftview.findViewById(R.id.layout_gift);
        Button chongzhi=giftview.findViewById(R.id.chongzhi);
        final TextView num = giftview.findViewById(R.id.giftnumber);
        RecyclerView numberrecyview = numberview.findViewById(R.id.number_recyview);
        numbers = giftview.findViewById(R.id.numbers);
        numbers.setText(integral);
        numberxz = giftview.findViewById(R.id.numberxz);
        //充值按钮
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CzBean czBean1 = new CzBean();
                czBean1.setNumber(10.0);
                czBean1.setPic(R.drawable.qian);
                CzBean czBean2 = new CzBean();
                czBean2.setNumber(20.0);
                czBean2.setPic(R.drawable.qian);
                CzBean czBean3 = new CzBean();
                czBean3.setNumber(30.0);
                czBean3.setPic(R.drawable.qian);
                CzBean czBean4 = new CzBean();
                czBean4.setNumber(40.0);
                czBean4.setPic(R.drawable.qian);
                CzBean czBean5 = new CzBean();
                czBean5.setNumber(50.0);
                czBean5.setPic(R.drawable.qian);
                CzBean czBean6 = new CzBean();
                czBean6.setNumber(60.0);
                czBean6.setPic(R.drawable.qian);
                CzBean czBean7 = new CzBean();
                czBean7.setNumber(70.0);
                czBean7.setPic(R.drawable.qian);
                CzBean czBean8 = new CzBean();
                czBean8.setNumber(80.0);
                czBean8.setPic(R.drawable.qian);
                ArrayList<CzBean> czBeans = new ArrayList<>();
                czBeans.add(czBean1);
                czBeans.add(czBean2);
                czBeans.add(czBean3);
                czBeans.add(czBean4);
                czBeans.add(czBean5);
                czBeans.add(czBean6);
                czBeans.add(czBean7);
                czBeans.add(czBean8);
                //礼物布局管理器
                RecyclerView chongrecyview = chongview.findViewById(R.id.chongrecyview);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(PKPlayActivityUI.this, 2);
                gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
                chongrecyview.setLayoutManager(gridLayoutManager);
                final CzAdapter giftAdapter = new CzAdapter(czBeans, PKPlayActivityUI.this);
                chongrecyview.setAdapter(giftAdapter);
                //设置礼物弹窗展示
                chongPopupwindown.setContentView(chongview);
                chongPopupwindown.setFocusable(true);
                chongPopupwindown.showAsDropDown(songli, 0, 170);
                giftAdapter.notifyDataSetChanged();
                //点击切换效果
                giftAdapter.setOnItemclick(new CzAdapter.OnItemclick() {
                    @Override
                    public void getposition(int position) {
                        giftAdapter.setColor(position);
                       // zfUtil.zbZfMethod(PKPlayActivityUI.this,roomID);
                        zbZfMethod(PKPlayActivityUI.this,czBeans.get(position).getNumber());
                    }
                });
            }
        });
        //点击赠送
        give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position1 != -1) {
                    if (giftnum > 0) {
                        int zprice = listBeans.get(position1).getAmount() * giftnum;
                        if (sharedPreferences.getString("integral", null)!=null&&Integer.parseInt(sharedPreferences.getString("integral", null)) >= zprice) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PKPlayActivityUI.this);
                            builder.setTitle(R.string.qxz);
                            builder.setMessage(R.string.qxznlwzb);
                            builder.setNegativeButton(R.string.zuo, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendGift(uuid, mAnchornameone, anthidone);
                                }
                            });
                            builder.setPositiveButton(R.string.you, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!islm) {
                                        Toast.makeText(PKPlayActivityUI.this, R.string.zwzbqsh, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    sendGift(uuid, mAnchornametow, anthitow);
                                }
                            });
                            builder.show();

                        } else {
                            Toast.makeText(PKPlayActivityUI.this, R.string.yebz, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PKPlayActivityUI.this, R.string.qxzsl, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PKPlayActivityUI.this, R.string.qxzlw, Toast.LENGTH_SHORT).show();

                }
            }
        });


        //点击弹出视图
        layout_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boonumber) {
                    numberxz.setImageResource(R.drawable.dwon);
                    boonumber = false;
                } else {
                    numberxz.setImageResource(R.drawable.up);
                    //设置数量弹窗展示
                    numberpopWindow.setContentView(numberview);
                    numberpopWindow.setFocusable(true);
                    numberpopWindow.showAsDropDown(songli, 0, -50);
                    boonumber = true;
                }
            }
        });

        //礼物布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        giftrecyview.setLayoutManager(gridLayoutManager);
        final GiftAdapter giftAdapter = new GiftAdapter(listBeans, this);
        giftrecyview.setAdapter(giftAdapter);
        //礼物布局管理数量
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        numberrecyview.setLayoutManager(linearLayoutManager);
        //添加数据
        final ArrayList<Giftnumberbean> giftnumberbeans = new ArrayList<>();
        Giftnumberbean giftnumberbean1 = new Giftnumberbean();
        giftnumberbean1.setCount("1314");
        giftnumberbean1.setNumbername(String.valueOf(R.string.ysys));
        Giftnumberbean giftnumberbean2 = new Giftnumberbean();
        giftnumberbean2.setCount("520");
        giftnumberbean2.setNumbername(String.valueOf(R.string.wan));
        Giftnumberbean giftnumberbean3 = new Giftnumberbean();
        giftnumberbean3.setCount("188");
        giftnumberbean3.setNumbername(String.valueOf(R.string.ybb));
        Giftnumberbean giftnumberbean4 = new Giftnumberbean();
        giftnumberbean4.setCount("66");
        giftnumberbean4.setNumbername(String.valueOf(R.string.yqsl));
        Giftnumberbean giftnumberbean5 = new Giftnumberbean();
        giftnumberbean5.setCount("30");
        giftnumberbean5.setNumbername(String.valueOf(R.string.yqsl));
        Giftnumberbean giftnumberbean6 = new Giftnumberbean();
        giftnumberbean6.setCount("10");
        giftnumberbean6.setNumbername(String.valueOf(R.string.sqsm));
        Giftnumberbean giftnumberbean7 = new Giftnumberbean();
        giftnumberbean7.setCount("1");
        giftnumberbean7.setNumbername(String.valueOf(R.string.yxyy));
        giftnumberbeans.add(giftnumberbean1);
        giftnumberbeans.add(giftnumberbean2);
        giftnumberbeans.add(giftnumberbean3);
        giftnumberbeans.add(giftnumberbean4);
        giftnumberbeans.add(giftnumberbean5);
        giftnumberbeans.add(giftnumberbean6);
        giftnumberbeans.add(giftnumberbean7);
        final NumberAdapter numberAdapter = new NumberAdapter(giftnumberbeans, this);
        numberrecyview.setAdapter(numberAdapter);
        //设置礼物弹窗展示
        giftpopWindow.setContentView(giftview);
        giftpopWindow.setFocusable(true);
        giftpopWindow.showAsDropDown(songli, 0, 170);
        giftAdapter.notifyDataSetChanged();
        //点击切换效果
        giftAdapter.setOnItemclick(new GiftAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                position1 = position;
                giftAdapter.setColor(position);
                getdate(position);
            }
        });
        //点击设置数量
        numberAdapter.setOnItemclick(new NumberAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                numberAdapter.setColor(position);
                giftnum = Integer.parseInt(giftnumberbeans.get(position).getCount());
                num.setText(giftnumberbeans.get(position).getCount());

            }
        });




        ednumbers.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    //处理事件
                    Editable text = ednumbers.getText();
                    num.setText(text);
                    giftnum= Integer.parseInt(text.toString());
                    return true;
                }
                return false;
            }
        });

    /*    //number点击
        more_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showedPopwind();
                numberpopWindow.dismiss();
            }
        });*/

    }


    //弹窗
    @SuppressLint("WrongConstant")
    private void showPopupWindow() {
        popupWindow.setContentView(view);
        final ImageView img = view.findViewById(R.id.sendimg);
        ed_text = view.findViewById(R.id.ed_text);
        showSoftInput(view);
        popupWindow.setFocusable(true);
        ed_text.setFocusable(true);
        ed_text.setFocusableInTouchMode(true);
        ed_text.requestFocus();
        ed_text.findFocus();
        //获取焦点
        InputMethodManager inputManager = (InputMethodManager) ed_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ed_text, 0);
        popupWindow.showAsDropDown(sendmessgetext, 50, -633);

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

    /**
     * 房间聊天消息.
     */
    public void handleRecvRoomMsg(LinearLayout giftParent, Context context, String roomID, ZegoRoomMessage[] listMsg) {
        Log.e(TAG, "handleRecvRoomMsg: 有消息");
        mLvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLvComments.setAdapter(mCommentsAdapter);
        // 设置Item添加和移除的动画
        mLvComments.setItemAnimator(new DefaultItemAnimator());
        for (ZegoRoomMessage message : listMsg) {
            if (message.messageType == ZegoIM.MessageType.Text && message.messageCategory == ZegoIM.MessageCategory.Gift) {
                Log.e(TAG, "handleRecvRoomMsg: 礼物+:" + message.content);
                Gson gson = new Gson();
                GiftJsonBean giftJsonBean = gson.fromJson(message.content, GiftJsonBean.class);
                giftModel = new GiftModel();
                giftModel.setGiftId(giftJsonBean.getGiftId() + "").setGiftName(giftJsonBean.getGiftName()).setGiftCount(Integer.parseInt(giftJsonBean.getSendCount())).setGiftPic(giftJsonBean.getGiftImage())
                        .setSendUserId(0 + "").setSendUserName(giftJsonBean.getUserName()).setAnroute(giftJsonBean.getGiftKey() + "").setSendUserPic(giftJsonBean.getUserIcon()).setSendGiftTime(System.currentTimeMillis())
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
//                            giftModel.setJumpCombo(10);
                           giftControl.loadGift(giftModel);

                       }else {
                           Toast.makeText(PKPlayActivityUI.this, queryGiftBean.getMsg(), Toast.LENGTH_SHORT).show();
                       }
                    }
                });*/
            }

            // 文字聊天消息
            if (message.messageType == ZegoIM.MessageType.Text && message.messageCategory == ZegoIM.MessageCategory.Chat) {
                listTextMsg.clear();
                listTextMsg.add(message);
                Log.e("TAG", "handleRecvRoomMsg: " + message.content + "");
                if (listTextMsg.size() > 0) {
                    mCommentsAdapter.addMsgList(listTextMsg);
                    mLvComments.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "handleRecvRoomMsg: 后");
                            // 滚动到最后一行
                            mLvComments.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
                        }
                    });
                }
            }
            // 点赞消息
            if (message.messageType == ZegoIM.MessageType.Text && message.messageCategory == ZegoIM.MessageCategory.Like) {

                Gson gson = new Gson();
                ZanBean zanBean = gson.fromJson(message.content, ZanBean.class);
                if (zanBean.getFlag() == 0) {
                    if (zanBean.getNumber() > zan1number) {
                        zan1text.setText(zanBean.getNumber() + "");
                        zan1number = zanBean.getNumber();
                    } else {
                        zan1number++;
                    }

                } else {
                    if (zanBean.getNumber() > zan2number) {
                        zan2text.setText(zanBean.getNumber() + "");
                        zan2number = zanBean.getNumber();
                    } else {
                        zan2number++;
                    }
                }
            }
        }

    }

    protected void doSendRoomMsg(final String msg) {
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
        mLvComments.post(new Runnable() {
            @Override
            public void run() {
                // 滚动到最后一行
                mLvComments.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
            }
        });

        zegoLiveRoom.sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Chat, ZegoIM.MessagePriority.Default, msg, new IZegoRoomMessageCallback() {
            @Override
            public void onSendRoomMessage(int errorCode, String roomID, long messageID) {
                if (errorCode == 0) {
                    Log.e("TAG", "onSendRoomMessage: " + "成功");

                } else {
                    Log.e("TAG", "onSendRoomMessage: " + "shibai");

                }
            }
        });
    }

    protected void doSendRoomMsguser(String username, String userid, final String msg) {
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "空的", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals(zbname)) {
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
        mLvComments.post(new Runnable() {
            @Override
            public void run() {
                // 滚动到最后一行
                mLvComments.scrollToPosition(mCommentsAdapter.getListMsg().size() - 1);
            }
        });

        zegoLiveRoom.sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Chat, ZegoIM.MessagePriority.Default, msg, new IZegoRoomMessageCallback() {
            @Override
            public void onSendRoomMessage(int errorCode, String roomID, long messageID) {
                if (errorCode == 0) {
                    Log.e("TAG", "onSendRoomMessage: " + "成功");

                } else {
                    Log.e("TAG", "onSendRoomMessage: " + "shibai");
                }
            }
        });
    }


    //直播间人数
    public void numberd(Context mContext, int i, ZegoUserState[] zegoUserStates, TextView usernumbers) {
      /*  View views = View.inflate(mContext, R.layout.activity_play, null);
        usernumber = views.findViewById(R.id.usernumber);*/
        for (ZegoUserState user : zegoUserStates) {
            Log.e(TAG, "numberd: " + "总人数" + i);
            Log.e(TAG, "角色" + user.roomRole + "名称" + user.userName + "id:" + user.userID + "flag" + user.updateFlag + "总人数" + i);
            if (number == 0) {
                mAnchorID = user.userID;
            }
            if (user.updateFlag == 1) {
                getdata2(user.userName);
                number++;
                //doSendRoomMsguser(user.userName,user.userID,"来了");
                Log.e(TAG, "number: ++" + number);
                usernumbers.setText(number + "");
            } else {
                removedata(user.userName);
                number--;
                Log.e(TAG, "number: --" + number);
                usernumbers.setText(number + "");
            }

           // Toast.makeText(getContexts(), "人数:" + i + "角色" + user.roomRole + "名称" + user.userName + "id:" + user.userID + "flag" + user.updateFlag, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void finish() {
        super.finish();
        getremoveUser();
        // 停止正在拉的流
        if (mPlayStreamIDs.size() > 0) {
            for (String streamID : mPlayStreamIDs) {
                ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPlayingStream(streamID);
            }
        }

        // 清空拉流列表
        mPlayStreamIDs.clear();

        // 退出页面时如果是连麦状态则停止推流
        if (isJoinedLive) {
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPublishing();
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPreview();
            isJoinedLive = false;
        }
        // 设置所有视图可用
        ZGJoinLiveHelper.sharedInstance().freeAllJoinLiveView();

        // 退出房间
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().logoutRoom();
        // 去除SDK相关的回调监听
        releaseSDKCallback();
    }

    // 去除SDK相关的回调监听
    public void releaseSDKCallback() {
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePublisherCallback(null);
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoLivePlayerCallback(null);
        ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setZegoRoomCallback(null);
    }

    public void doc(String mRoomID, ZegoStreamInfo[] zegoStreamInfos) {
        AppLogger.getInstance().i(JoinLiveAudienceUI.class, "登录房间成功 roomId : %s", mRoomID);

        // 筛选主播流，主播流采用全屏的视图
        for (ZegoStreamInfo streamInfo : zegoStreamInfos) {
            if (streamInfo.userID.equals(mAnchorID)) {
                // 主播流采用全屏的视图，开始拉流
                ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, mBigView.textureView);
                // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整View，可能有部分被裁减。
                ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                // 向拉流流名列表中添加流名
                mPlayStreamIDs.add(streamInfo.streamID);
                // 修改视图信息
                mBigView.streamID = streamInfo.streamID;
                ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView);

                // 将 "视频连麦" button 置为可见
                //binding.btnApplyJoinLive.setVisibility(View.VISIBLE);
                break;
            }
        }

        // 拉副主播流（即连麦者的流）
        for (ZegoStreamInfo streamInfo : zegoStreamInfos) {

            if (!streamInfo.userID.equals(mAnchorID)) {
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
    protected void initViewList() {

     /*   // 全屏视图用于展示主播流
        mBigView2 = new JoinLiveView(play_view, false, "");
        mBigView2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());
*/
        // 添加可用的连麦者视图
        ArrayList<JoinLiveView> mJoinLiveView = new ArrayList<>();
        final JoinLiveView view1 = new JoinLiveView(audienceView1s1, false, "");
        view1.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        mJoinLiveView.add(mBigView);
        mJoinLiveView.add(view1);
        ZGJoinLiveHelper.sharedInstance().addTextureView(mJoinLiveView);

        /**
         * 设置视图的点击事件
         * 点击小视图时，切换到大视图上展示画面，大视图的画面展示到小视图上
         */
        view1.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.exchangeView(mBigView);
            }
        });

    }

    public void getdate() {
        //添加礼物数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("", "");
        instance1.posthttps(UrlCount.Base_Giftlist, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                Giftbeans giftbeans = gson.fromJson(json, Giftbeans.class);
                List<Giftbeans.ListBean> list = giftbeans.getList();
                listBeans.addAll(list);
            }
        });

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("uuid", uuid);
        map1.put("roomNum", roomnum);
        instance1.posthttps(UrlCount.Base_saveuser, map1, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {

                //设置成员列表
                //  getdata2();
            }
        });

    }

    public void removedata(String json) {
        Gson gson = new Gson();
        GetuserCountBean getuserCountBean = gson.fromJson(json, GetuserCountBean.class);
        // userlist.addAll((Collection<? extends GetuserCountBean>) getuserCountBean);
        for (int i = 0; i < userlist.size(); i++) {
            if (getuserCountBean.getNickName().equals(userlist.get(i).getNickName())) {
                userlist.remove(i);
            }
        }
        userAdapter.notifyDataSetChanged();
    }

    public void getdata2(String json) {
        Gson gson = new Gson();
        GetuserCountBean getuserCountBean = gson.fromJson(json, GetuserCountBean.class);
        userlist.add(getuserCountBean);
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
                // Log.e("用户结果", json);
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

                    }
                });
            }
        });

    }*/

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


    public void getdate(int position) {
        listBeans.get(position).getId();
        //添加礼物数据
      /*  HashMap<String, Object> map = new HashMap<>();
        map.put("", "");
        instance1.posthttps(UrlCount.Base_Giftlist, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Log.e("请求数据", json);
                Gson gson = new Gson();
                Giftbeans giftbeans = gson.fromJson(json, Giftbeans.class);
                List<Giftbeans.ListBean> list = giftbeans.getList();
                listBeans.addAll(list);
            }
        });*/
    }

    //其他数量弹窗
    public void showedPopwind() {
        ed_popupWindow.setContentView(ed_view);
        ed_popupWindow.setFocusable(true);
        InputMethodManager inputManager = (InputMethodManager) ednumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(ednumber, 0);
        ed_popupWindow.showAsDropDown(songli, 0, 0);
    }

    private void showGiftMsgList() {
        //  recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  adapter = new GiftMsgAdapter(this);
        // recyclerView.setAdapter(adapter);
    }

    private void initGiftLayout() {

        giftLayout = (LinearLayout) findViewById(R.id.giftLayout);
        tvGiftNum = (TextView) findViewById(R.id.toolbox_tv_gift_num);
        btnGift = (ImageView) findViewById(R.id.toolbox_iv_face);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_gift);
        mViewpager = (ViewPager) findViewById(R.id.toolbox_pagers_face);
        mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);

   /*     giftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里的作用是消费掉点击事件
            }
        });
        giftLayout.setVisibility(View.GONE);*/
    }

    //模拟从网络获取礼物url的集合
    private List<GiftBean.GiftListBean> fromNetData() {
        List<GiftBean.GiftListBean> list = new ArrayList<>();
        try {
            InputStream in = getAssets().open("gift.json");
            InputStreamReader json = new InputStreamReader(in);
            Gson gson = new Gson();
            GiftBean giftBean = gson.fromJson(json, GiftBean.class);
            list = giftBean.getGiftList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<GiftModel> toGiftModel(List<GiftBean.GiftListBean> datas) {
        List<GiftModel> giftModels = new ArrayList<>();
        GiftModel giftModel;
        for (int i = 0; i < datas.size(); i++) {
            GiftBean.GiftListBean giftListBean = datas.get(i);
            giftModel = new GiftModel();
            giftModel.setGiftName(giftListBean.getGiftName()).setGiftPic(giftListBean.getGiftPic()).setGiftPrice(giftListBean.getGiftPrice());
            giftModels.add(giftModel);
        }
        return giftModels;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
//            Log.e(TAG, "onConfigurationChanged: " + "横屏");
            onConfigurationLandScape();

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Log.e(TAG, "onConfigurationChanged: " + "竖屏");
            onConfigurationPortrait();
        }
    }

    private void onConfigurationPortrait() {
        ll_portrait.setVisibility(View.VISIBLE);
        ll_landscape.setVisibility(View.GONE);
    }

    private void onConfigurationLandScape() {
        ll_portrait.setVisibility(View.GONE);
        ll_landscape.setVisibility(View.VISIBLE);
    }


    public void pullinitsdk(final LinearLayout giftParent, final Context mContext, final TextView usernumber) {
        AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "点击 初始化SDK按钮");
//        boolean testEnv = binding.testEnv.isChecked();
        // 防止用户点击，弹出加载对话框
        CustomDialog.createDialog("初始化SDK中...", PKPlayActivityUI.this).show();
        // 调用sdk接口, 初始化sdk
        boolean results = ZGBaseHelper.sharedInstance().initZegoSDK(usernumber, giftParent, mContext, ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {
                // 关闭加载对话框
                CustomDialog.createDialog(PKPlayActivityUI.this).cancel();

                // errorCode 非0 代表初始化sdk失败

                if (errorCode == 0) {
                    AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "初始化zegoSDK成功");
                    run(giftParent, mContext, usernumber);
                    //Toast.makeText(context, getString(R.string.tx_init_success), Toast.LENGTH_SHORT).show();
                    //调用监听消息回调方法
                    Log.e("TAG", "onInitSDK: 初始化成功");
                    // 初始化成功，跳转到登陆房间页面。
                    jumpLoginRoom();
                } else {
                    AppLogger.getInstance().i(InitSDKPlayActivityUI.class, "初始化sdk失败 错误码 : %d", errorCode);
                    //Toast.makeText(context, getString(R.string.tx_init_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 如果接口调用失败，也需要关闭对话框
        if (!results) {
            // 关闭加载对话框
            CustomDialog.createDialog(this).cancel();
        }
    }

    /**
     * 跳转登陆页面
     */
    private void jumpLoginRoom() {
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
        onLoginRoom();
    }

    /**
     * button 点击事件
     * 登陆房间
     *
     * @param
     */
    public void onLoginRoom() {
        final String roomId = roomID;
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
                    loginRoomCompletion(true, finalRoomId, errorCode, streamID);
                    zegoStreamInfo = zegoStreamInfos;
                    doc("1", zegoStreamInfo);
                    jianting();

                } else {
                    loginRoomCompletion(false, finalRoomId, errorCode, streamID);
                    zegoStreamInfo = zegoStreamInfos;
                }
            }
        });

        if (!isLoginRoomSuccess) {
            loginRoomCompletion(false, finalRoomId, -1, streamID);
        }
    }


    /**
     * 处理登陆房间成功或失败的逻辑
     *
     * @param isLoginRoom 是否登陆房间成功
     * @param finalRoomId roomID
     * @param errorCode   登陆房间错误码
     */
    private void loginRoomCompletion(boolean isLoginRoom, String finalRoomId, int errorCode, String steamID) {
        // 关闭加载对话框
        //   CustomDialog.createDialog(activity).cancel();
        if (isLoginRoom) {
            //Toast.makeText(this, "登录房间成功", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "登录房间成功: ");
            AppLogger.getInstance().i(this, "登陆房间成功 roomId : %s", finalRoomId);

            // 登陆房间成功，跳转推拉流页面
            // jumpPlay();
        } else {
            AppLogger.getInstance().i(this, "登陆房间失败 errorCode : %s", errorCode == -1 ? "api调用失败" : String.valueOf(errorCode));
            Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转拉流页面
     */
    private void jumpPlay(String roomID, Activity activity, String steamID) {
        //PlayActivityUI.actionStart(activity, roomID,steamID);
    }

/*    public void doc(String mRoomID,ZegoStreamInfo[] zegoStreamInfos){
        AppLogger.getInstance().i(JoinLiveAudienceUI.class, "登录房间成功 roomId : %s", mRoomID);

        // 筛选主播流，主播流采用全屏的视图
        for (ZegoStreamInfo streamInfo:zegoStreamInfos){

            if (streamInfo.userID.equals(mAnchorID)){
  *//*          // 主播流采用全屏的视图，开始拉流
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, mBigView2.textureView);
            // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整View，可能有部分被裁减。
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);*//*

                // 向拉流流名列表中添加流名
                mPlayStreamIDs.add(streamInfo.streamID);
          *//*  // 修改视图信息
            mBigView2.streamID = streamInfo.streamID;
            ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView2);
*//*
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

    }*/





 /*   // 设置拉流的视图列表
    protected void initViewList(){

     *//*   // 全屏视图用于展示主播流
        mBigView2 = new JoinLiveView(play_view, false, "");
        mBigView2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());
*//*
        // 添加可用的连麦者视图
        ArrayList<JoinLiveView> mJoinLiveView = new ArrayList<>();
        final JoinLiveView view1 = new JoinLiveView(audienceView1s1, false, "");
        view1.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

*//*
        final JoinLiveView view2 = new JoinLiveView(binding.audienceViewTwo, false, "");
        view2.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());

        final JoinLiveView view3 = new JoinLiveView(binding.audienceViewThree, false, "");
        view3.setZegoLiveRoom(ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom());
*//*

        mJoinLiveView.add(mBigView2);
        mJoinLiveView.add(view1);
  *//*      mJoinLiveView.add(view2);
        mJoinLiveView.add(view3);*//*
        ZGJoinLiveHelper.sharedInstance().addTextureView(mJoinLiveView);

        */

    /**
     * 设置视图的点击事件
     * 点击小视图时，切换到大视图上展示画面，大视图的画面展示到小视图上
     *//*
        view1.textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view1.exchangeView(mBigView2);
            }
        });*/

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
        });
    }*/
    public void jianting() {
        ZGPlayHelper.sharedInstance().setPlayerCallback(new IZegoLivePlayerCallback() {
            @Override
            public void onPlayStateUpdate(int errorCode, String streamID) {
                // 拉流状态更新，errorCode 非0 则说明拉流失败
                // 拉流常见错误码请看文档: <a>https://doc.zego.im/CN/491.html</a>

                if (errorCode == 0) {
                    mStreamID = streamID;
                    AppLogger.getInstance().i(ZGPublishHelper.class, "拉流成功, streamID : %s", streamID);
                    Toast.makeText(PKPlayActivityUI.this, getString(R.string.tx_play_success), Toast.LENGTH_SHORT).show();
              /*      //开始计时
                    myhandler.sendMessageDelayed(Message.obtain(myhandler, 1), 1000);*/
                   /* if (jzVideoPlayerStandard.is)
                    jzVideoPlayerStandard.release();*/
                    linearLayoutnoe.setVisibility(View.VISIBLE);
                    linearLayoutow.setVisibility(View.GONE);
                    // 修改标题状态拉流成功状态
                    binding.title.setTitleName(getString(R.string.tx_playing));
                } else {

                    // 解除视图占用
                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamID);

                    // 从已拉流列表中移除该流名
                    mPlayStreamIDs.remove(streamID);
                    // 当拉流失败 当前 mStreamID 初始化成 null 值
                    mStreamID = null;
                    // 修改标题状态拉流失败状态
                    binding.title.setTitleName(getString(R.string.tx_play_fail));
                    AppLogger.getInstance().i(ZGPublishHelper.class, "拉流失败, streamID : %s, errorCode : %d", streamID, errorCode);
                 //   Toast.makeText(PKPlayActivityUI.this, getString(R.string.tx_play_fail), Toast.LENGTH_SHORT).show();
                    // 当拉流失败时需要显示布局
                    if(isplayBack==1&&replayurl!=null){
                        play_view.setVisibility(View.GONE);
                        lner2.setVisibility(View.GONE);
                        jzVideoPlayerStandard.setVisibility(View.VISIBLE);
                        jzVideoPlayerStandard.setUp(replayurl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                        jzVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.timg);
                    }
                    /*showInputStreamIDLayout();
                    linearLayoutnoe.setVisibility(View.GONE);
                    linearLayoutow.setVisibility(View.VISIBLE);
                    if(isplayBack==1&&replayurl!=null){
                        jzVideoPlayerStandard.setVisibility(View.VISIBLE);
                        jzVideoPlayerStandard.setUp(replayurl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "主播可能不在线当前为回放视频");
                        jzVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.shanping);
                    }*/


                }
            }

            @Override
            public void onPlayQualityUpdate(String s, ZegoPlayStreamQuality zegoPlayStreamQuality) {
                /**
                 * 拉流质量更新, 回调频率默认3秒一次
                 * 可通过 {@link ZegoLiveRoom#setPlayQualityMonitorCycle(long)} 修改回调频率
                 */
                streamQuality.setFps(String.format("帧率: %f", zegoPlayStreamQuality.vdjFps));
                streamQuality.setBitrate(String.format("码率: %f kbs", zegoPlayStreamQuality.vkbps));
            }

            @Override
            public void onInviteJoinLiveRequest(final int seq, String fromUserID, String fromUserName, String roomID) {
                // 观众收到主播的连麦邀请
                // fromUserID 为主播用户id
                // fromUserName 为主播昵称
                // roomID 为房间号。
                // 开发者想要深入了解连麦业务请参考文档: <a>https://doc.zego.im/CN/224.html</a>


                AlertDialog.Builder builder = new AlertDialog.Builder(PKPlayActivityUI.this);
                builder.setTitle(R.string.lmqq);
                builder.setMessage(R.string.nsdzblm);
                builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ZGBaseHelper.sharedInstance().getZegoLiveRoom().respondInviteJoinLiveReq(seq, 1);
                    }
                });
                builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ZGBaseHelper.sharedInstance().getZegoLiveRoom().respondInviteJoinLiveReq(seq, 0);
                        b = true;
                        startlm();
                    }
                });
                builder.show();


            }

            @Override
            public void onRecvEndJoinLiveCommand(String fromUserID, String fromUserName, String roomID) {
                // 连麦观众收到主播的结束连麦信令。
                // fromUserID 为主播用户id
                // fromUserName 为主播昵称
                // roomID 为房间号。
                Log.e(TAG, "onRecvEndJoinLiveCommand: 主播结束指令");
                Toast.makeText(PKPlayActivityUI.this, "主播结束指令", Toast.LENGTH_SHORT).show();
                lone.setVisibility(View.VISIBLE);
                ltow.setVisibility(View.GONE);
                b = false;
                startlm();
            }

            @Override
            public void onVideoSizeChangedTo(String streamID, int width, int height) {
                // 视频宽高变化通知,startPlay后，如果视频宽度或者高度发生变化(首次的值也会)，则收到该通知.
                streamQuality.setResolution(String.format("分辨率: %dX%d", width, height));
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
                    //  doc("1",zegoStreamInfo );
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


        binding.swSpeaker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    sdkConfigInfo.setSpeaker(isChecked);
                    ZGConfigHelper.sharedInstance().enableSpeaker(isChecked);
                }
            }
        });

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
                // 当登陆房间成功后，如果房间内中途有人推流或停止推流。房间内其他人就能通过该回调收到流更新通知。
                Log.e(TAG, "onStreamUpdated: 有流");
                if (!islm) {
                    islm = true;
                    linertow.setVisibility(View.GONE);
                    getzbmsg();
                } else {
                    islm = false;
                    linertow.setVisibility(View.VISIBLE);
                    getzbmsg();
                }


                for (ZegoStreamInfo streamInfo : zegoStreamInfos) {

                    if (streamInfo.streamID.equals(mStreamID)) {
                        if (type == ZegoConstants.StreamUpdateType.Added) {
                            // 当收到房间流新增的时候, 重新拉流
                            ZGPlayHelper.sharedInstance().startPlaying(mStreamID, binding.playView);
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
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPlayingStream(streamInfo.streamID, mBigView.textureView);
                                    // 设置拉流视图模式，此处采用 SDK 默认值--等比缩放填充整个View，可能有部分被裁减。
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setViewMode(ZegoVideoViewMode.ScaleAspectFill, streamInfo.streamID);

                                    // 向拉流流名列表中添加流名
                                    mPlayStreamIDs.add(streamInfo.streamID);

                                    // 修改视图信息
                                    mBigView.streamID = streamInfo.streamID;
                                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(mBigView);
                                }
                            }

                        } else if (type == ZegoConstants.StreamUpdateType.Deleted) {
                            AppLogger.getInstance().i(JoinLiveAudienceUI.class, "房间内收到流删除通知. streamID : %s, userName : %s, extraInfo : %s", streamInfo.streamID, streamInfo.userName, streamInfo.extraInfo);
                            for (String playStreamID : mPlayStreamIDs) {
                                if (playStreamID.equals(streamInfo.streamID)) {

                                    // 停止拉流
                                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPlayingStream(streamInfo.streamID);
                                    mPlayStreamIDs.remove(streamInfo.streamID);

                                    // 修改视图信息
                                    ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(streamInfo.streamID);

                                    // 判断该条关闭流是否为主播
                                    if (streamInfo.userID.equals(mAnchorID)) {
                                        // 界面提示主播已停止直播
                                        Toast.makeText(PKPlayActivityUI.this, getString(R.string.tx_anchor_stoppublish), Toast.LENGTH_SHORT).show();

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
                            //--------------------------------------------------------------------
                            // 当收到房间流删除的时候停止拉流
                            ZGPlayHelper.sharedInstance().stopPlaying(mStreamID);
                            Toast.makeText(PKPlayActivityUI.this, R.string.zbytb, Toast.LENGTH_LONG).show();
                            // 修改标题状态拉流成功状态
                            binding.title.setTitleName(getString(R.string.tx_current_stream_delete));
                            if(isplayBack==1&&replayurl!=null){
                                play_view.setVisibility(View.GONE);
                                lner2.setVisibility(View.GONE);
                                jzVideoPlayerStandard.setVisibility(View.VISIBLE);
                                jzVideoPlayerStandard.setUp(replayurl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                                jzVideoPlayerStandard.thumbImageView.setImageResource(R.drawable.timg);
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

    }


    //接收数据
    public void run(final LinearLayout giftParent, final Context mContext, final TextView usernumber) {
        // zegoLiveRoom.setRoomConfig(true, true);
        Log.e("TAG", "IM代理执行1");

        //IM代理接收消息监听
        ZGBaseHelper.sharedInstance().getZegoLiveRoom().setZegoIMCallback(new IZegoIMCallback() {
            @Override
            public void onUserUpdate(ZegoUserState[] zegoUserStates, int i) {
                //playActivityUI.numberd(mContext,i,zegoUserStates,usernumber);
                numberd(mContext, i, zegoUserStates, usernumber);
                Log.e("TAG", "onUserUpdate: " + i);


            }

            @Override
            public void onRecvRoomMessage(String s, ZegoRoomMessage[] listMsg) {

                // playActivityUI.handleRecvRoomMsg(giftParent,mContext,roomId, listMsg);

                handleRecvRoomMsg(giftParent, mContext, "1", listMsg);
                //  publishActivityUI.handleRecvRoomMsg(giftParent,mContext,roomId,listMsg);
                //JoinLiveAnchorUI joinLiveAnchorUI = new JoinLiveAnchorUI();

                Log.e("TAG", "zegoRoomMessages:有消息 ");
            }

            @Override
            public void onUpdateOnlineCount(String s, int i) {
                number = i;
                usernumber.setText(number + "");
                Log.e("TAG", "onUpdateOnlineCount: " + s + i);
            }

            @Override
            public void onRecvBigRoomMessage(String s, ZegoBigRoomMessage[] zegoBigRoomMessages) {
                Log.e("TAG", "onRecvBigRoomMessage: " + s);
            }
        });
        Log.e("TAG", "IM代理执行2");

    }

    public void startlm() {
        if (b) {
            //Toast.makeText(this, "执行", Toast.LENGTH_SHORT).show();
            b = false;
            // button 说明为"视频连麦"时，执行推流的操作

            if (mPlayStreamIDs.size() == ZGJoinLiveHelper.MaxJoinLiveNum + 1) {
                // 判断连麦人数是否达到上限，此demo只支持展示三人连麦；达到连麦上限时的拉流总数 = 1条主播流 + 三条连麦者的流
                Toast.makeText(PKPlayActivityUI.this, getString(R.string.join_live_count_overflow), Toast.LENGTH_SHORT).show();
            } else {
                // 不满足上述情况则开始连麦，即推流

                // 获取可用的视图
                JoinLiveView freeView = ZGJoinLiveHelper.sharedInstance().getFreeTextureView();

                if (freeView != null) {
                    // 设置预览视图模式，此处采用 SDK 默认值--等比缩放填充整View，可能有部分被裁减。
                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setPreviewViewMode(ZegoVideoViewMode.ScaleAspectFill);
                    // 设置预览 view
                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().setPreviewView(freeView.textureView);
                    // 启动预览
                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPreview();
                    // 开始推流，flag 使用连麦场景
                    ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().startPublishing(mPublishStreamID, "audienceJoinLive", ZegoConstants.PublishFlag.JoinPublish);

                    // 修改视图信息
                    freeView.streamID = mPublishStreamID;
                    freeView.isPublishView = true;
                    ZGJoinLiveHelper.sharedInstance().modifyTextureViewInfo(freeView);
                } else {
                    lone.setVisibility(View.VISIBLE);
                    ltow.setVisibility(View.GONE);
                    Toast.makeText(PKPlayActivityUI.this, R.string.dqzblmz, Toast.LENGTH_LONG).show();
                }

            }
        } else {
          //  Toast.makeText(this, "执行2", Toast.LENGTH_SHORT).show();
            // button 说明为"结束连麦"时，停止推流

            // 停止推流
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPublishing();
            // 停止预览
            ZGJoinLiveHelper.sharedInstance().getZegoLiveRoom().stopPreview();

            isJoinedLive = false;
            // 设置视图可用
            ZGJoinLiveHelper.sharedInstance().setJoinLiveViewFree(mPublishStreamID);
            // 修改 button 的说明为"视频连麦"
            // binding.btnApplyJoinLive.setText(getString(R.string.tx_joinLive));

            AppLogger.getInstance().i(JoinLiveAudienceUI.class, "观众结束连麦");
        }

    }


    //美颜弹窗
    private void showBeautyPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
        seekbar = view.findViewById(R.id.seekBar);
        seekbar2 = view.findViewById(R.id.seekBar2);
        final TextView tv1 = view.findViewById(R.id.tv1);
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
        final BeautyAdapter beautyAdapter = new BeautyAdapter(beautylist, this);
        //设置适配器
        recyclerView.setAdapter(beautyAdapter);
        popupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 606);
        popupWindow1.setContentView(view);
        popupWindow1.setFocusable(true);
        popupWindow1.showAsDropDown(beauty, 50, 10);

        beautyAdapter.setOnItemclick(new BeautyAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                if (position == 0) {
                    seekbar.setVisibility(View.VISIBLE);
                    seekbar2.setVisibility(View.GONE);
                    beautyAdapter.setColor(position);
                    // 开启美颜
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishStep(2);

                } else {
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
        tv1.setText(4 + "");
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
                tv1.setText(progress + "");
                // 设置美颜采样颜色阈值
                //    zegoliveRoom.setPolishFactor(progress); //此处采样颜色系数采用默认值 4.0
                ZGBaseHelper.sharedInstance().getZegoLiveRoom().setPolishStep(progress);//此处磨皮的采样半径采用默认值 4.0

                Log.e("TAG", progress + "");
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

                if (progress <= 10) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 1.0); //此处美白亮度修正系数采用默认值 0.5
                } else if (10 < progress && progress <= 20) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.9); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 20 && progress <= 30) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.8); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 30 && progress <= 40) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.7); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 40 && progress <= 50) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.6); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 50 && progress <= 60) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.5); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 60 && progress <= 70) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.4); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 70 && progress <= 80) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.3); //此处美白亮度修正系数采用默认值 0.5
                } else if (progress < 80 && progress <= 90) {
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setWhitenFactor((float) 0.2); //此处美白亮度修正系数采用默认值 0.5
                } else {
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
    private void showPopupWindowlj() {
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
        scoreTeamAdapter = new FilterAdapter(filterlist, this);
        recyclerView.setAdapter(scoreTeamAdapter);
        popupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 606);
        popupWindow1.setContentView(view);
        popupWindow1.setFocusable(true);
        popupWindow1.showAsDropDown(beauty, 50, 10);

        //点击切换效果
        scoreTeamAdapter.setOnItemclick(new FilterAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                if (position == 0) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.None);//不滤镜
                } else if (position == 1) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Blue);//蓝
                } else if (position == 2) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Dark);//夜色
                } else if (position == 3) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Fade);//淡雅
                } else if (position == 4) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Gothic);//哥特
                } else if (position == 5) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Halo);//光晕
                } else if (position == 6) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Illusion);//梦幻
                } else if (position == 7) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Lomo);//青柠
                } else if (position == 8) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.BlackWhite);//简洁
                } else if (position == 9) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.OldStyle);//黑白
                } else if (position == 10) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Romantic);//老化
                } else if (position == 11) {
                    scoreTeamAdapter.setColor(position);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.SharpColor);//锐色
                } else {
                    scoreTeamAdapter.setColor(12);
                    ZGBaseHelper.sharedInstance().getZegoLiveRoom().setFilter(ZegoFilter.Wine);//酒红
                }
            }
        });


    }

    public void sendGift(String uuid, final String Anroute, int Anrouteid) {
        int zprice = listBeans.get(position1).getAmount() * giftnum;
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roomNum", roomnum);
        map.put("giftId", listBeans.get(position1).getId());
        map.put("giftName", listBeans.get(position1).getGiftName());
        map.put("amount", listBeans.get(position1).getAmount());
        map.put("pic", listBeans.get(position1).getPicture());
        map.put("num", giftnum + "");
        map.put("total", zprice);
        map.put("anchorId", Anrouteid);
        instance1.posthttps(UrlCount.Base_SendGiftpk, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Log.e("礼物", json+"" );
                Gson gson = new Gson();
                Givebean givebean = gson.fromJson(json, Givebean.class);
                if (givebean.getCode() == 0) {
                    Toast.makeText(PKPlayActivityUI.this, "赠送成功", Toast.LENGTH_SHORT).show();
                    numbers.setText( givebean.getUser().getIntegral()+"");
                    giftModel = new GiftModel();
                    giftModel.setGiftId(listBeans.get(position1).getId() + "").setGiftName(listBeans.get(position1).getGiftName()).setGiftCount(giftnum).setGiftPic(listBeans.get(position1).getPicture())
                            .setSendUserId(userid).setSendUserName(userName).setSendUserPic(headPic).setSendGiftTime(System.currentTimeMillis())
                            .setAnroute(Anroute)
                            .setCurrentStart(currentStart);
                    if (currentStart) {
                        giftModel.setHitCombo(giftnum);
                    }
//                            giftModel.setJumpCombo(10);
                    giftControl.loadGift(giftModel);
                    JSONObject root = new JSONObject();
                    try {
                        root.put("userIcon", headPic);
                        root.put("userName", userName);
                        root.put("giftName", listBeans.get(position1).getGiftName());
                        root.put("giftImage", listBeans.get(position1).getPicture());
                        root.put("giftGifImage", listBeans.get(position1).getGiftName());
                        root.put("defaultCount", Anroute);
                        root.put("sendCount", giftnum + "");
                        root.put("giftId", listBeans.get(position1).getId());
                        root.put("giftKey", Anroute);
                        root.put("amount", listBeans.get(position1).getAmount());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    zegoLiveRoom.sendRoomMessage(ZegoIM.MessageType.Text, ZegoIM.MessageCategory.Gift, ZegoIM.MessagePriority.Default, root.toString(), new IZegoRoomMessageCallback() {
                        @Override
                        public void onSendRoomMessage(int errorCode, String roomID, long messageID) {
                            if (errorCode == 0) {

                            } else {
                                Log.e("TAG", "onSendRoomMessage: " + "shibai");
                            }
                        }


                    });
                } else {
                 //   Toast.makeText(PKPlayActivityUI.this, "", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void getzbmsg() {
        HashMap<String, Object> map0 = new HashMap<>();
        map0.put("roomNumber", roomnum);
        Log.e(TAG, "json>>>>>>");
        instance1.posthttps(UrlCount.URL_QueryAnchorUserIdTwo, map0, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Log.e(TAG, "json>>>>>>" + json);
                Gson gson = new Gson();
                TowAroute towAroute = gson.fromJson(json, TowAroute.class);
                if (towAroute.getCode() == 0) {
                    TowAroute.UserBean user = towAroute.getUser();
                    anthidone = user.getId();
                    mAnchornameone = user.getNickName();
                    //设置主播头像-----------------------------------------------------------
                    Glide.with(PKPlayActivityUI.this).load(user.getPhotoUrl())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(headimg);
                    zbname.setText(user.getRealName());
                    anthid = towAroute.getUser().getId();
                    TowAroute.UserTwoBean userTwo = towAroute.getUserTwo();
                    if (userTwo!=null){
                        anthitow = userTwo.getId();
                        mAnchornametow = userTwo.getNickName();
                        anthidTow = towAroute.getUserTwo().getId();
                        //设置主播2头像-----------------------------------------------------------
                        Glide.with(PKPlayActivityUI.this).load(userTwo.getPhotoUrl())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(headimg1);
                        zbname1.setText(userTwo.getRealName());
                    }


                    //查询用户是否关注主播
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", userid);
                    map.put("anthId", anthid);
                    instance1.posthttps(UrlCount.URL_Query_isAttention, map, new Httputlis1.Mycallbacks() {
                        @Override
                        public void sucess(String json) {
                            Log.e(TAG, "关注:" + json);
                            Gson gson = new Gson();
                            QueryGZbean queryGZbean = gson.fromJson(json, QueryGZbean.class);
                            List<?> list = queryGZbean.getList();
                            if (list.size() == 0) {
                                guanzhu.setImageResource(R.drawable.fllow);
                                flag1 = 1;
                            } else {
                                guanzhu.setImageResource(R.drawable.deletefollow);
                                flag1 = 0;
                            }
                        }
                    });
                }

            }
        });
    }

    public void CongZhi(){
        View  view = LayoutInflater.from(PKPlayActivityUI.this).inflate(R.layout.textviewfz, null);
        final PopupWindow    window1 = new PopupWindow(view, 650, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        TextView wx = view.findViewById(R.id.fuzhi);
        TextView wx1 = view.findViewById(R.id.phones);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) PKPlayActivityUI.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(wx1.getText());
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(PKPlayActivityUI.this).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 900);
    }
    public void zbZfMethod(Activity context, Double money) {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor = sharedPreferences.edit();
        String userid=sharedPreferences.getString("userId",null);
        String appLoginIdentity = sharedPreferences.getString("appLoginIdentity", null);
 /*       topupManId: $("#userId").val(),  //积分充值的用户id
                topupFee: amount,  //金额
                status: "1",   //状态
                integral: integral,  //金额
                uuid:T.localStorage("uuid") //当前用户的uuid*/
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("topupManId",userid);
        map1.put("topupFee",money);
        map1.put("status","1");
        int a = new Double(money).intValue();
        map1.put("integral",a);
        map1.put("uuid",appLoginIdentity);
        instance1.posthttps(UrlCount.URL_Zbzf1, map1, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                Codebean codebean = gson.fromJson(json, Codebean.class);
               if (codebean.getCode()==0){
                   HashMap<String, Object> map = new HashMap<>();
                   map.put("uuid", appLoginIdentity);
                   map.put("type", "b");
                   map.put("productPrice", money);
                   map.put("mallNo", 1);
                   map.put("remark", "余额");
                   map.put("isShowBuyer", "1");
                   map.put("mallName", "宗亲平台");
                   map.put("mallPic", "https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png");
                   map.put("productName", "【宗亲】余额充值");
                   map.put("specVal", "【宗亲】余额充值");
                   map.put("productPic", "https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png");
                   map.put("amount", 1);
                   map.put("price", money);
                   map.put("totalPrice", money);
                   map.put("productId", codebean.getProductId());
                   map.put("productNo", "");
                   Httputlis1 instance = Httputlis1.getInstance();
                   View view = LayoutInflater.from(context).inflate(R.layout.zbzflayout, null);
                   final PopupWindow window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                   window1.setContentView(view);
                   window1.setOutsideTouchable(true);
                   RelativeLayout wx = view.findViewById(R.id.wxx);
                   RelativeLayout zfb = view.findViewById(R.id.wxxx);
                   Button qxx = view.findViewById(R.id.qxx);
                   wx.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                               @Override
                               public void sucess(String json) {
                                   Gson gson = new Gson();
                                   ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                                   if (ddBean.getCode() == 0) {
                                       Log.e("微信", json);
                                       int orderInfoId = ddBean.getOrderInfoId();
                                       HashMap<String, Object> map1 = new HashMap<>();
                                       map1.put("uuid", appLoginIdentity);
                                       map1.put("orderIds", orderInfoId);
                                       map1.put("platform", "1");
                                       map1.put("tradeType", "1");
                                       instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                           @Override
                                           public void sucess(String json) {
                                               Log.e("微信2", json);
                                               Gson gson1 = new Gson();
                                               WxPayBean wxPayBean = gson1.fromJson(json, WxPayBean.class);
                                               if (wxPayBean.getCode() == 0) {
                                                   Gson gson2 = new Gson();
                                                   Wxmorebean wxmorebean = gson2.fromJson(json, Wxmorebean.class);
                                                   JSONObject root = new JSONObject();
                                                   try {
                                                       /**
                                                        * appid : wx3c8adc40cdcd29e6
                                                        * noncestr : suanmjvgb9bntpqh
                                                        * package : Sign=WXPay
                                                        * partnerid : 1530323921
                                                        * prepayid : wx13141229251146e555e70b9e1889011800
                                                        * sign : E37C81AE15250F17530C62B7016B8BC3
                                                        * timestamp : 1576217550
                                                        */
                                                       root.put("appid", wxmorebean.getData().getPaystr().getAppid());
                                                       root.put("noncestr", wxmorebean.getData().getPaystr().getNoncestr());
                                                       root.put("package", wxmorebean.getData().getPaystr().getPackageX());
                                                       root.put("partnerid", wxmorebean.getData().getPaystr().getPartnerid());
                                                       root.put("prepayid", wxmorebean.getData().getPaystr().getPrepayid());
                                                       root.put("sign", wxmorebean.getData().getPaystr().getSign());
                                                       root.put("timestamp", wxmorebean.getData().getPaystr().getTimestamp());
                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }
                                                   zfUtil.wxzfMethod(root.toString());
                                                   window1.dismiss();
                                               }
                                           }
                                       });
                                   }
                               }
                           });
                       }
                   });
                   zfb.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                               @Override
                               public void sucess(String json) {
                                   Gson gson = new Gson();
                                   ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                                   if (ddBean.getCode() == 0) {
                                       int orderInfoId = ddBean.getOrderInfoId();
                                       HashMap<String, Object> map1 = new HashMap<>();
                                       map1.put("uuid", appLoginIdentity);
                                       map1.put("orderIds", orderInfoId);
                                       map1.put("platform", "2");
                                       map1.put("tradeType", "1");
                                       instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                           @Override
                                           public void sucess(String json) {
                                               Gson gson1 = new Gson();
                                               ZfbPayBean zfbPayBean = gson1.fromJson(json, ZfbPayBean.class);
                                               if (zfbPayBean.getCode() == 0) {
                                                   zfUtil.zfbmethod(zfbPayBean.getData(), context);
                                   /*     HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("orderid", orderInfoId);
                                        instance.posthttps(UrlCount.URL_Ddzt, map1, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson1 = new Gson();
                                                Log.e("充值余额", json);
                                                window1.dismiss();
                                            }
                                        });*/

                                                   window1.dismiss();
                                               }
                                           }
                                       });
                                   }
                               }
                           });
                       }
                   });
       /* //余额支付
        yex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderInfoId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "3");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Log.e("余额",json );
                                    Gson gson1 = new Gson();
                                    YBean yBean = gson1.fromJson(json, YBean.class);
                                    if ( yBean.getCode()==0){
                                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(10);
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("userId",Integer.parseInt(userid));
                                        instance.posthttps(UrlCount.URL_Zt, map, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson2 = new Gson();
                                                ZtBean ztBean = gson2.fromJson(json, ZtBean.class);
                                                ZtBean.UserBean user = ztBean.getUser();
                                                integral = user.getIntegral()+"";
                                                Log.e(TAG, "sucess: "+json);
                                                //设置余额
                                                numbers.setText(integral);
                                                editor.putString("integral",integral);
                                                editor.commit();

                                            }
                                        });

                                        HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("orderid", orderInfoId);
                                        instance.posthttps(UrlCount.URL_Ddzt, map1, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson1 = new Gson();
                                                Log.e("充值余额", json);
                                                window1.dismiss();
                                            }
                                        });


                                    }else if ( yBean.getCode()==-2){
                                        Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                });

            }
        });*/
                   qxx.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           window1.dismiss();
                       }
                   });
                   View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
                   window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 650);

               }else {
                   Toast.makeText(PKPlayActivityUI.this,codebean.getMsg(), Toast.LENGTH_SHORT).show();
               }
            }
        });


    }

}



