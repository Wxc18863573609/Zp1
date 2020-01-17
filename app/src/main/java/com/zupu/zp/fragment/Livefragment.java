package com.zupu.zp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zego.zegoliveroom.entity.ZegoRoomMessage;
import com.zupu.zp.R;
import com.zupu.zp.adapter.CommentsAdapter;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.CliveBean;
import com.zupu.zp.bean.CreatRoomBean;
import com.zupu.zp.bean.NameRzBean;
import com.zupu.zp.bean.OrderBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.ZbFBean;
import com.zupu.zp.bean.ZtBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.lianmai.constants.JoinLiveView;
import com.zupu.zp.myactivity.CreatLiveActivity;
import com.zupu.zp.testpakeyge.PlayActivityUI;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.InitSdk;
import com.zupu.zp.utliss.PullinitSDK;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.videocall.ZGVideoCommunicationHelper;
import com.zupu.zp.videocall.ui.PublishStreamAndPlayStreamUI;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/5 20:03
 * update: 直播
 */

public class Livefragment extends BaseFragment {
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings = new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;


    private static String path = "/sdcard/head.jpg";//sd路径
    public static JoinLiveView mBigView, mBigView2;
    final InitSdk initSdk = new InitSdk();
    ;
    Button but;
    final PullinitSDK pullinitSDK = new PullinitSDK();
    PlayActivityUI playActivityUI = new PlayActivityUI();
    SharedPreferences.Editor editor;
    private PopupWindow window;
    ;
    // WebView web;
    ProgressDialog progressDialog;
    View view;
    ImageView jump;
    String userName = "", userHead;
    ;
    String roomID = "1";
    String streamID = "1";
    ZegoLiveRoom zegoLiveRoom;
    public static RecyclerView mLvComments = null;
    public static RecyclerView zbrecycouview1 = null;
    Button la, la2;
    public static CommentsAdapter mCommentsAdapter = null;
    public static CommentsAdapter mCommentsAdapter2 = null;
    public static TextureView audienceView1s, preview, play_view, audienceView1s1;
    private int roomRole = ZegoConstants.RoomRole.Audience;
    ArrayList<Fragment> list = new ArrayList<>();
    TabLayout tab;
    ViewPager vp;
    SharedPreferences sharedPreferences;
    String uuid, userid;
    int ispk;
    PushManager pushManager = PushManager.getInstance();
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private File tempFile;
    private static final String PHOTO_FILE_NAME = "headPhoto.png";

    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.livefragmentlaout, container, false);
        return view;
    }

    @Override
    public void initview() {
        but = view.findViewById(R.id.btns);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        uuid = sharedPreferences.getString("appLoginIdentity", null);
        userid = sharedPreferences.getString("userId", null);
        ;
        userName = sharedPreferences.getString("nickName", null);
        ;
        userHead = sharedPreferences.getString("photoUrl", null);
        editor = sharedPreferences.edit();
        vp = view.findViewById(R.id.vp);
        tab = view.findViewById(R.id.tab);
        View view2 = View.inflate(getContext(), R.layout.activity_publish, null);
        preview = view2.findViewById(R.id.preview);
        audienceView1s = view2.findViewById(R.id.audienceView1);
        mBigView = new JoinLiveView(preview, false, "");


        View view3 = View.inflate(getContext(), R.layout.activity_play, null);
        audienceView1s1 = view3.findViewById(R.id.audienceViews);
        play_view = view3.findViewById(R.id.play_view);
        mBigView2 = new JoinLiveView(play_view, false, "");

        mCommentsAdapter = new CommentsAdapter(getContext(), new ArrayList<ZegoRoomMessage>());
        mCommentsAdapter2 = new CommentsAdapter(getContext(), new ArrayList<ZegoRoomMessage>());
        View views1 = View.inflate(getContext(), R.layout.activity_publish, null);
        zbrecycouview1 = views1.findViewById(R.id.zbrecycouview);
        View views = View.inflate(getContext(), R.layout.activity_play, null);
        mLvComments = views.findViewById(R.id.lv_comments);

        /*  web = view.findViewById(R.id.liveweb);*/
        jump = view.findViewById(R.id.jump);
     /*   la =view.findViewById(R.id.la);
        la2 =view.findViewById(R.id.la2);*/
        zegoLiveRoom = new ZegoLiveRoom();
        list.clear();
        LiveChildFragment liveChildFragment = new LiveChildFragment();
        LiveChildhostFragment liveChildhostFragment = new LiveChildhostFragment();
        LiveChildfindFragment liveChildfindFragment = new LiveChildfindFragment();
        LiveChildCourseFragment liveChildCourseFragment = new LiveChildCourseFragment();
        list.add(liveChildFragment);
        list.add(liveChildhostFragment);
        list.add(liveChildfindFragment);
        list.add(liveChildCourseFragment);

    }

    @Override
    public void initdata() {
        //36.4570202778,115.9853071091
        //导航
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setview(39.7,116.3 );
            }
        });

        vp.setOffscreenPageLimit(2);
        //设置适配器
        vp.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });


        tab.addTab(tab.newTab().setText(R.string.tab_gz));
        tab.addTab(tab.newTab().setText(R.string.tab_remen));
        tab.addTab(tab.newTab().setText(R.string.tab_fax));
        tab.addTab(tab.newTab().setText(R.string.tab_kechentg));
        //点击tab监听
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(R.string.tab_gz)) {
                    vp.setCurrentItem(0);
                } else if (tab.getText().equals(R.string.tab_remen)) {
                    vp.setCurrentItem(1);
                } else if (tab.getText().equals(R.string.tab_fax)) {
                    vp.setCurrentItem(2);
                } else {
                    vp.setCurrentItem(3);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initlinsenter() {
        tab.getTabAt(1).select();

        //滑动监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        tab.getTabAt(0).select();
                        break;
                    case 1:
                        tab.getTabAt(1).select();
                        break;
                    case 2:
                        tab.getTabAt(2).select();
                        break;
                    case 3:
                        tab.getTabAt(3).select();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        //创建房间
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // InitSDKPublishActivityUI.actionStart(getActivity());
                // initSdk.initsdks(getActivity(),"publish","1","1");

              /*  Intent intent = new Intent(getActivity(), PublishActivityUI.class);
                intent.putExtra("publish","publish");
                intent.putExtra("roomID","1");
                intent.putExtra("starmID","1");
                startActivity(intent);*/
                String anchorStatus = sharedPreferences.getString("anchorStatus", null);
                if (anchorStatus != null) {
                    if (anchorStatus.equals("3")) {
                        getActivity().startActivity(new Intent(getActivity(), CreatLiveActivity.class));
                    } else if (anchorStatus.equals("1")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userId", Integer.parseInt(userid));
                        persenterimpl.posthttp(UrlCount.URL_Zt, map, ZtBean.class);
                        showProgress(getActivity(),"加载中...");
                    } else {
                        Toast.makeText(getActivity(), R.string.shenhe, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        /*la.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           final String randomSuffix = "-" + new Date().getTime()%(new Date().getTime()/1000);
                JSONObject root = new JSONObject();
                try {
                    root.put("title", "-1");
                    root.put("userName", userName);
                    root.put("userHead",userHead);
                    root.put("userid",userid);
                    root.put("roomid",randomSuffix);
                    root.put("isvoice",false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // pushManager.initialize(getActivity(),null);
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(userid));
                map.put("targetuId", Integer.parseInt("203"));
                map.put("pushContent","有人向你发起通话请求");
                map.put("uri","000");
                map.put("title","-1");
                map.put("text","1");
                map.put("url",root.toString());
                persenterimpl.posthttp(UrlCount.URL_GtSend, map, PhoneBean.class);
                ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent news = new Intent(getActivity(), PublishStreamAndPlayStreamUI.class);
                        news.putExtra("roomID",randomSuffix);
                        news.putExtra("flag","0");
                        news.putExtra("username","张三");
                        news.putExtra("myuserid",userid);
                        news.putExtra("userid","203");
                        news.putExtra("userhead",userHead);
                        news.putExtra("isvoice",false);
                        getActivity().startActivity(news);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);//3秒后执行TimeTask的run方法
              *//*  Intent intent = new Intent(getActivity(), PKPublishActivityUI.class);
                intent.putExtra("publish","publish");
                intent.putExtra("roomID","1");
                intent.putExtra("starmID","1");
               getActivity().startActivity(intent);*//*
            }
        });
        la2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              *//*  Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.dir/person");
                intent.setType("vnd.android.cursor.dir/contact");
                intent.setType("vnd.android.cursor.dir/raw_contact");

                startActivity(intent);*//*
            }
        });*/
    }

    @Override
    public void persenter() {
    }

    @Override
    public void sucecess(Object o) {
        //成功返回
        if (o instanceof CliveBean) {
            CliveBean cliveBean = (CliveBean) o;
            dismissProgress(getActivity());
            if (cliveBean.getCode() == 0) {
                CliveBean.UserBean user = cliveBean.getUser();
                Log.e("money", user.getIntegral() + "");
                editor.putString("integral", user.getIntegral() + "");
                editor.commit();
                Toast.makeText(getActivity(), R.string.chenggong, Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), CreatLiveActivity.class));
            } else {
                Toast.makeText(getActivity(), cliveBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }

        if (o instanceof OrderBean) {
            dismissProgress(getActivity());
            OrderBean orderBean = (OrderBean) o;
            if (orderBean.getCode() == 0) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.zbsq)
                        .setMessage(R.string.ktkc + orderBean.getOrderInfo().getProductPrice() + "" + R.string.iskt)
                        .setNegativeButton(R.string.back_qx,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                })
                        .setPositiveButton(R.string.shuor,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("orderId", orderBean.getOrderInfo().getId());
                                        persenterimpl.posthttp(UrlCount.URL_LviewZF, map, CliveBean.class);
                                    }
                                }).show();
            } else {
                Toast.makeText(getActivity(), orderBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        //支付回调
        if (o instanceof ZbFBean) {
            dismissProgress(getActivity());
            ZbFBean zbFBean = (ZbFBean) o;
            if (zbFBean.getCode() == 0) {
                if (zbFBean.getStatus().equals("1001")) {
                    Toast.makeText(getActivity(), R.string.logingsx, Toast.LENGTH_SHORT).show();
                    outLogings();

                }/*else if (zbFBean.getStatus().equals("1002")){
                  Toast.makeText(getActivity(), "未实名认证", Toast.LENGTH_SHORT).show();
                  btn5.setChecked(true);
              }else if (zbFBean.getStatus().equals("1003")){

              }else if (zbFBean.getStatus().equals("1004")){
              }*/ else if (zbFBean.getStatus().equals("2000")) {
                    editor.putString("anchorStatus", "3");
                    editor.commit();
                    getActivity().startActivity(new Intent(getActivity(), CreatLiveActivity.class));
                } else if (zbFBean.getStatus().equals("2002")) {
                    Toast.makeText(getActivity(), R.string.nx_dd, Toast.LENGTH_SHORT).show();
                } else if (zbFBean.getStatus().equals("3001")) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid", uuid);
                    persenterimpl.posthttp(UrlCount.URL_ZbFf, map, OrderBean.class);
                }
            } else {
                Toast.makeText(getActivity(), zbFBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }


        if (o instanceof ZtBean) {
            dismissProgress(getActivity());
            RadioButton btn5 = (RadioButton) getActivity().findViewById(R.id.btn5);
            ZtBean ztBean = (ZtBean) o;
            if (ztBean.getCode() == 0) {
                ZtBean.UserBean user = ztBean.getUser();
                editor.putString("is_certification", user.getIsCertification());
                if (user.getIsCertification().equals("1")) {
                    //跳转认证
                    Toast.makeText(getActivity(), R.string.wsm, Toast.LENGTH_SHORT).show();
                    btn5.setChecked(true);
                } else if (user.getIsCertification().equals("2")) {
                    Toast.makeText(getActivity(), R.string.shenhezhong, Toast.LENGTH_SHORT).show();
                } else if (user.getIsCertification().equals("3")) {
                    Toast.makeText(getActivity(), R.string.shweitg, Toast.LENGTH_SHORT).show();
                    //跳转认证
                    btn5.setChecked(true);
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid", uuid);
                    persenterimpl.posthttp(UrlCount.URL_ZbFf, map, ZbFBean.class);
                    // getActivity().startActivity(new Intent(getActivity(),CreatLiveActivity.class));
                }
            }

        }

        if (o instanceof NameRzBean) {
            NameRzBean nameRzBean = (NameRzBean) o;
            if (nameRzBean.getCode() == 0) {

            } else {
                Toast.makeText(playActivityUI, R.string.xsmrenz, Toast.LENGTH_SHORT).show();
            }
        }

        if (o instanceof PhoneBean) {
            PhoneBean phoneBean = (PhoneBean) o;
            if (phoneBean.getCode() == 0) {
                Log.e("TAG", "sucecess: 推送");
            }
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        zegoLiveRoom.logoutRoom();
        zegoLiveRoom.stopPlayingStream(streamID);
        zegoLiveRoom = null;
    }

    public void gethttp(File file) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("title", "----------------");
        map.put("status", "1");
        map.put("cover", "http://vod-aliyun.honarise.com/record/2019-09-23/honarise/2019092313524110/2019-09-23-13:04:34_2019-09-23-13:05:03.mp4");
        map.put("proIds", "1,2");
        map.put("activityId", "1");
        map.put("isPK", ispk);
        map.put("iscity", "深圳");
        persenterimpl.puthttp(UrlCount.Base_CreatRoom, file, CreatRoomBean.class);
    }


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        getActivity().startActivityForResult(intent, 3);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == getActivity().RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == getActivity().RESULT_OK) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    jump.setImageBitmap(bitmap);
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                    upLoad(new File(path));
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    Bitmap head = extras.getParcelable("data");
                    jump.setImageBitmap(head);
                    setPicToView(head);
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
                    upLoad(new File(path));
                }
                break;


            case 5:
                if (requestCode == REQUEST_IMAGE3) {
                    if (resultCode == getActivity().RESULT_OK) {
//                            List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        Log.e("TAG", "图:" + strings);
                        Httputlis1 instance = Httputlis1.getInstance();
                        for (int i = 0; i < strings.size(); i++) {
                            instance.uploadPic(UrlCount.URL_UpPic, new File(strings.get(i)), new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Log.e("TAG", "多" + json);
                                }
                            });
                        }
                    }
                }
                break;
        }

    }

    //上传
    private String upUrl = "/small/user/verify/v1/modifyHeadPic";//上传

    private void upLoad(File file) {
        gethttp(file);
    }

    //保存到SD卡
    private void setPicToView(Bitmap mBitmap) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 把数据写入文件
            //关闭流
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o) {
        if (o instanceof Integer) {
            int a = (int) o;
            if (a == 001) {
                tab.getTabAt(3).select();
            }
        }

    }



}
