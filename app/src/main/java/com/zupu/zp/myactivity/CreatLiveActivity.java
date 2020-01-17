package com.zupu.zp.myactivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.adapter.LiveSearchAdapter;
import com.zupu.zp.adapter.UserAdapter;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.CreatRoomBean;
import com.zupu.zp.bean.GetuiBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.SearchBean;
import com.zupu.zp.bean.WEbena;
import com.zupu.zp.bean.WxUserbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.testpakeyge.PKPublishActivityUI;
import com.zupu.zp.testpakeyge.PublishActivityUI;
import com.zupu.zp.testpakeyge.ShareUtil;
import com.zupu.zp.utliss.ShareUtils;
import com.zupu.zp.utliss.UrlCount;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
public class CreatLiveActivity extends BaseActivity {
    PushManager instance = PushManager.getInstance();
    String clientid ;
    View view;
    String list="" ;
    int liveType=0;
    String mediaUrl;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    boolean boo1=false;
    boolean boo2=false,ishs=false;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    Bitmap head=null;
    String randomSuffix=null;
    int ispk=0;
    private Spinner spType;
    private RelativeLayout title;
    private ImageView mfimg;
    private RelativeLayout que;
    private EditText ed_title;
    private View view1;
    private TextView xztitle;
    private LinearLayout textlinner1;
    private LinearLayout textlinner2;
    private LinearLayout textlinner3;
    private LinearLayout textlinner4;
    private View view2;
    private EditText ed_shaoppingname;
    private RelativeLayout adc;
    private TextView jieguo;
    private RecyclerView shaoppingrecycelview;
    SharedPreferences sharedPreferences;
    private RadioButton noshare;
    private RadioButton yesshare;
    private LinearLayout biaoshi;
    private TextView biaoshi2;
    private Spinner sp_type;
    LiveSearchAdapter  liveSearchAdapter;
    private float mDensity;
    private int mHiddenViewMeasuredHeight;
    LinearLayout moree;
    int origHeight;
    ImageView mIv;
    Button creatbtn;
    PopupWindow     window1;
    private TextView texta,textb,textc,textd,selectshaopping;
    ArrayList<SearchBean.ListBean> listBeans = new ArrayList<>();
    boolean isNo=false;
    private PopupWindow window;
    private String locationProvider;
    String uuid,userid;
    private int requestCode;
    private int resultCode;
    private Intent data;
    ImageView backz;
    @Override
    public int initlayout() {
        return R.layout.activity_creat_live;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initview() {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        que=findViewById(R.id.que);
        spType = findViewById(R.id.sp_type);
        title = (RelativeLayout) findViewById(R.id.title);
        mfimg = (ImageView) findViewById(R.id.mfimg);
        ed_title = (EditText) findViewById(R.id.ed_title);
        view1 = (View) findViewById(R.id.view1);
        xztitle = (TextView) findViewById(R.id.xztitle);
        backz=findViewById(R.id.backz);
        textlinner1 = (LinearLayout) findViewById(R.id.textlinner1);
        textlinner2 = (LinearLayout) findViewById(R.id.textlinner2);
        textlinner3 = (LinearLayout) findViewById(R.id.textlinner3);
        textlinner4 = (LinearLayout) findViewById(R.id.textlinner4);
        view2 = (View) findViewById(R.id.view2);
        ed_shaoppingname = (EditText) findViewById(R.id.ed_shaoppingname);
        adc = (RelativeLayout) findViewById(R.id.adc);
        jieguo = (TextView) findViewById(R.id.jieguo);
        shaoppingrecycelview = (RecyclerView) findViewById(R.id.shaoppingrecycelview);
        noshare = (RadioButton) findViewById(R.id.noshare);
        yesshare = (RadioButton) findViewById(R.id.yesshare);
        biaoshi = (LinearLayout) findViewById(R.id.biaoshi);
        biaoshi2 = (TextView) findViewById(R.id.biaoshi2);
        sp_type = (Spinner) findViewById(R.id.sp_type);
        texta=(TextView)findViewById(R.id.texta);
        textb=(TextView)findViewById(R.id.textb);
        textc=(TextView)findViewById(R.id.textc);
        textd=(TextView)findViewById(R.id.textd);
        selectshaopping=(TextView)findViewById(R.id.selectshaopping);
        moree=(LinearLayout)findViewById(R.id.moree);
        mIv = (ImageView) findViewById(R.id.my_iv);
        mDensity = getResources().getDisplayMetrics().density;
        mHiddenViewMeasuredHeight = (int) (mDensity * 145 + 0.5);
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        creatbtn=(Button) findViewById(R.id.creatbtn);
        view = LayoutInflater.from(CreatLiveActivity.this).inflate(R.layout.popshare, null);
        window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
         clientid=sharedPreferences.getString("pushCid",null);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void initdata() {
        if (instance!=null)
        {
            instance.getClientid(CreatLiveActivity.this);
        }

        showGPSContacts();
        //设置默认不分享
        noshare.setChecked(true);
        //请求商品数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",1);
        map.put("limit","");
        map.put("kw","");
        persenterimpl.posthttp(UrlCount.URL_Search,map, SearchBean.class);
        //创建适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shaoppingrecycelview.setLayoutManager(linearLayoutManager);
        if (listBeans!=null){
        liveSearchAdapter = new LiveSearchAdapter(listBeans, this);
        //设置适配器
        shaoppingrecycelview.setAdapter(liveSearchAdapter);
        }

        //条目点击事件
        liveSearchAdapter.setOnItemclick(new LiveSearchAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                int id = listBeans.get(position).getId();
                list=id+","+list;
               /* Log.e("TAG", "getposition:?????"+id );
                Log.e("TAG", "打印"+list.substring(0,list.length()-1) );*/
            }
        });
};

    @Override
    public void initlisenter() {
        //关闭
        backz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     /*   //弹窗关闭
        window1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                noshare.setChecked(true);
                yesshare.setChecked(false);
                window1.dismiss();
            }
        });*/

        //创建房间
        creatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFastClick()){
                   // randomSuffix = "-" + new Date().getTime()%(new Date().getTime()/1000);
                    String title = ed_title.getText().toString().trim();
                    if (TextUtils.isEmpty(title)) {
                        Toast.makeText(CreatLiveActivity.this, R.string.xzhd, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // TODO validate success, do something
                    if (boo1==false) {
                        Toast.makeText(CreatLiveActivity.this, R.string.xzfm, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updata();
                }
            }
        });
        //选择直播类型
        yesshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //submit();
               //  if (boo1&&ed_title.getText().toString()!=null){
                     noshare.setChecked(false);
                     yesshare.setChecked(true);
                     liveType=1;
                 /*    window1.setContentView(view);
                     window1.setOutsideTouchable(true);
                     LinearLayout wx = view.findViewById(R.id.wxx);
                     RelativeLayout pyq = view.findViewById(R.id.wxxx);
                     Button qxx = view.findViewById(R.id.qxx);
                     wx.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             //调用url分享方法

                             noshare.setChecked(true);
                             yesshare.setChecked(false);
                             window1.dismiss();
                         }
                     });
                     pyq.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                             noshare.setChecked(true);
                             yesshare.setChecked(false);
                         }
                     });
                     qxx.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             noshare.setChecked(true);
                             yesshare.setChecked(false);
                             window1.dismiss();
                         }
                     });
                     View inflate = LayoutInflater.from(CreatLiveActivity.this).inflate(R.layout.activity_main, null);
                     window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);*/

              /*   }else {
                     yesshare.setChecked(false);
                     Toast.makeText(CreatLiveActivity.this, "请先上传封面和标题", Toast.LENGTH_SHORT).show();
                 }*/

            }
        });
        //不分享
        noshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesshare.setChecked(false);
                noshare.setChecked(true);
                liveType=0;
            }
        });
        //上传图片
        mfimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 6.0及以上的系统需要在运行时申请CAMERA RECORD_AUDIO权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CreatLiveActivity.this, new String[]{
                                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
                    } else {
                        Log.e("TAG", "相机权限已开启");
                        //检测是否有写的权限
                        int permission = ActivityCompat.checkSelfPermission(CreatLiveActivity.this,
                                "android.permission.WRITE_EXTERNAL_STORAGE");
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            // 没有写的权限，去申请写的权限，会弹出对话框
                            ActivityCompat.requestPermissions(CreatLiveActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                        }else {

                            View view = LayoutInflater.from(CreatLiveActivity.this).inflate(R.layout.pop, null);
                            window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                            window.setContentView(view);
                            window.setOutsideTouchable(true);
                            TextView xj = view.findViewById(R.id.xj);
                            TextView xc = view.findViewById(R.id.xc);
                            TextView qx = view.findViewById(R.id.qx);
                            xj.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                            "head.jpg")));
                                    startActivityForResult(intent2, 2);//采用ForResult打开
                                    window.dismiss();
                                }
                            });
                            xc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(intent1, 1);
                                    window.dismiss();
                                }
                            });
                            qx.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    window.dismiss();
                                }
                            });
                            View inflate = LayoutInflater.from(CreatLiveActivity.this).inflate(R.layout.activity_main, null);
                            window.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                        }


                    }
                } else {
                    Log.e("TAG", "版本较低 ");
                }





            }
        });



         //点击搜素更新列表
        selectshaopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = ed_shaoppingname.getText().toString();
                gethttps(s);
            }
        });

        //设置直播标题
        texta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = texta.getText().toString().trim();
                ed_title.setTextColor(Color.WHITE);
                ed_title.setText(trim);
                boo2=true;
            }
        });
        //设置直播标题
        textb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textb.getText().toString().trim();
                ed_title.setTextColor(Color.WHITE);
                ed_title.setText(trim);
                boo2=true;
            }
        });
        //设置直播标题
        textc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = textc.getText().toString().trim();
                ed_title.setTextColor(Color.WHITE);
                ed_title.setText(trim);
                boo2=true;
            }
        });
        //设置直播标题
        textd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim= textd.getText().toString().trim();
                ed_title.setTextColor(Color.WHITE);
                ed_title.setText(trim);
                boo2=true;
            }
        });
        //下拉列表监听
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) spType.getSelectedItem();
                if (info.equals("普通房间")){
                    ispk=0;
                }else {
                    ispk=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void persenter() {

    }
    private void submit() {
        // validate
        String title = ed_title.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, R.string.xzhd, Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (boo1==false) {
            Toast.makeText(this, R.string.xzfm, Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public void sucecess(Object o) {
        if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                mediaUrl = picbean.getMediaUrl();
                Log.e("TAG", "sucecess: 图片成功" );

            }
        }
        if (o instanceof CreatRoomBean){
            CreatRoomBean creatRoomBean= (CreatRoomBean)o;
            if (creatRoomBean.getCode()==0){
                if (ispk==0)
                {
                    Intent intent = new Intent(CreatLiveActivity.this, PublishActivityUI.class);
                    intent.putExtra("publish","publish");
                    intent.putExtra("roomID",creatRoomBean.getRoomNumber());
                    intent.putExtra("starmID",creatRoomBean.getEntity().getExtPara3()+"");
                   // intent.putExtra("ids",list);
             /*       intent.putExtra("uuid",uuid);
                    intent.putExtra("title",ed_title.getText().toString());
                    intent.putExtra("status","1");
                    intent.putExtra("cover",mediaUrl);
                    intent.putExtra("proIds","1,2");
                    intent.putExtra("activityId",-1);
                    intent.putExtra("isPK",ispk);*/
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(CreatLiveActivity.this, PKPublishActivityUI.class);
                    intent.putExtra("publish","publish");
                    intent.putExtra("roomID",creatRoomBean.getRoomNumber());
                    intent.putExtra("starmID",creatRoomBean.getEntity().getExtPara3()+"");
                   // intent.putExtra("ids",list);
                    startActivity(intent);
                    finish();
                }
              /*  Intent intent = new Intent(CreatLiveActivity.this, PublishActivityUI.class);
                intent.putExtra("publish","publish");
                intent.putExtra("roomID",ed_title.getText().toString());
                intent.putExtra("starmID",clientid);
                startActivity(intent);*/
            }else {
                Toast.makeText(this, creatRoomBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

            if (o instanceof SearchBean){
                listBeans.clear();
                SearchBean searchBean=(SearchBean)o;
                if (searchBean.getCode()==0){
                    List<SearchBean.ListBean> searchBeanList = searchBean.getList();
                    listBeans.addAll(searchBeanList);
                    if (listBeans.size()==0){
                        que.setVisibility(View.VISIBLE);
                    }else {
                        que.setVisibility(View.GONE);
                    }
                    liveSearchAdapter.notifyDataSetChanged();

                }
            }
            if (o instanceof  WEbena){
                WEbena o1 = (WEbena) o;
                if(o1.getCode()==0){
                    Log.e("TAG", "经纬度上传成功" );
                }
            }

    }

    public void onClick(View v) {
        if (isNo==false) {
            animateOpen(shaoppingrecycelview);
            animationIvOpen();
            isNo=true;
        } else {
            animateClose(shaoppingrecycelview);
            animationIvClose();
            isNo=false;

        }
    }

    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
      //  isNo=true;
        ValueAnimator animator = createDropAnimator(v,  origHeight,
                mHiddenViewMeasuredHeight*2);
        animator.start();

    }

    private void animationIvOpen() {
        RotateAnimation animation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        mIv.startAnimation(animation);
    }

    private void animationIvClose() {
        RotateAnimation animation = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setFillAfter(true);
        animation.setDuration(100);
        mIv.startAnimation(animation);
    }

    private void animateClose(final View view) {
        view.setVisibility(View.VISIBLE);
        //  isNo=true;
        ValueAnimator animator = createDropAnimator(view,  origHeight,
                mHiddenViewMeasuredHeight);
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);

            }
        });
        return animator;
    }
    public void gethttps(String kw){
        //请求商品数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",1);
        map.put("limit","");
        map.put("kw",kw);
        persenterimpl.posthttp(UrlCount.URL_Search,map, SearchBean.class);

    }

    public void gethttp(File file){
        boo1=true;
        persenterimpl.puthttp(UrlCount.URL_UpPic,file, Picbean.class);
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
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    mfimg.setImageBitmap(bitmap);
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
                    Log.e("TAG", "图"+extras );
                    head = extras.getParcelable("data");
                    ishs=true;
                    mfimg.setImageBitmap(head);
                    setPicToView(head);
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
                    upLoad(new File(path));
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
    public void updata(){
      /*  if (ispk==1){*/
            HashMap<String, Object> map = new HashMap<>();
            map.put("uuid", uuid);
            map.put("title",ed_title.getText().toString());
            map.put("status","1");
            map.put("cover",mediaUrl);
            if (list!=""){
                map.put("proIds",list.substring(0,list.length()-1));
            }else {
                map.put("proIds","");
            }
            map.put("activityId",-1);
            map.put("isPK",ispk);
            map.put("iscity","");
            map.put("liveType",liveType);
            persenterimpl.posthttp(UrlCount.Base_CreatRoom, map,CreatRoomBean.class);
        }/*else {
            Intent intent = new Intent(CreatLiveActivity.this, PKPublishActivityUI.class);
            intent.putExtra("publish","publish");
            intent.putExtra("roomID",ed_title.getText().toString());
            intent.putExtra("starmID",clientid);
            intent.putExtra("uuid",uuid);
            intent.putExtra("title",ed_title.getText().toString());
            intent.putExtra("status","1");
            intent.putExtra("cover",mediaUrl);
            intent.putExtra("proIds","1,2");
            intent.putExtra("activityId",-1);
            intent.putExtra("isPK",ispk);
            startActivity(intent);
        }*/

 /*       HashMap<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("title",ed_title.getText().toString());
        map.put("status","1");
        map.put("cover",mediaUrl);
        map.put("proIds","1,2");
        map.put("activityId",-1);
        map.put("isPK",ispk);
        map.put("iscity","");
        persenterimpl.posthttp(UrlCount.Base_CreatRoom,map,CreatRoomBean.class);*/

   // }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (ishs){
            if (head!= null && !head.isRecycled())
            {
                head=null;
                ishs=false;
            }
        }else {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager   lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS,
                            BAIDU_READ_PHONE_STATE);
                } else {
                    getLocation();//getLocation为定位方法
                }
            } else {
                getLocation();//getLocation为定位方法
            }
        } else {
            Toast.makeText(this, R.string.gpswkq, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }

    /**
     * 获取具体位置的经纬度
     */
    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        updateLocation(location);
    }

    /**
     * 获取到当前位置的经纬度
     * @param location
     */
    private void updateLocation(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("lat",latitude+"");
            map.put("log",longitude+"");
            persenterimpl.posthttp(UrlCount.URL_WE,map, WEbena.class);

            Log.e("TAG", "维度：" + latitude + "\n经度" + longitude );
        } else {
            Log.e("TAG", "无法获取到位置信息 " );
        }
    }
    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                //如果用户取消，permissions可能为null.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {  //有权限
                    // 获取到权限，作相应处理
                    getLocation();
                } else {
                    showGPSContacts();
                }
                break;
            default:
                break;
        }
    }



}
