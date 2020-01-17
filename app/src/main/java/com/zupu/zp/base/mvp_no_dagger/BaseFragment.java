package com.zupu.zp.base.mvp_no_dagger;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.adapter.LiveItemAdapter;
import com.zupu.zp.base.mvp.IcontClass;
import com.zupu.zp.base.mvp.Persenterimpl;
import com.zupu.zp.bean.DaocterBeans;
import com.zupu.zp.bean.DonwloadBean;
import com.zupu.zp.bean.JsonBean;
import com.zupu.zp.bean.LiveBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.TxBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.fragment.GpsUtils;
import com.zupu.zp.myactivity.CreateZpActivity;
import com.zupu.zp.myactivity.DwActivity;
import com.zupu.zp.myactivity.LogingActivity;
import com.zupu.zp.myactivity.OneMrakActivity;
import com.zupu.zp.myactivity.PdfActivity;
import com.zupu.zp.testpakeyge.PKPlayActivityUI;
import com.zupu.zp.testpakeyge.PlayActivityUI;
import com.zupu.zp.utliss.FileSizeUtil;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.MyProgressDialog;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.WXUtils;
import com.zupu.zp.utliss.ZfUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import taobe.tec.jcc.JChineseConvertor;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.zupu.zp.entity.ZegoApplication.GAODE_PACKAGENAME;
import static com.zupu.zp.entity.ZegoApplication.TENCENT_PACKAGENAME;
import static com.zupu.zp.entity.ZegoApplication.showProgress;
import static com.zupu.zp.entity.ZegoApplication.wxBds;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 14:34
 * update: $date$
 */
public abstract class BaseFragment extends Fragment implements IcontClass.Iview {
    MyProgressDialog    dialog;
    private static final int NOT_NOTICE = 123;//如果勾选了不再询问
    private AlertDialog alertDialog;
    private AlertDialog mDialog;
    private  String jsona;
    public static String jsons;
    public  static int boo1;
    public static   Boolean isExit=false;
    //  进度条
    private ProgressBar mProgressBar;
    //  对话框
    private Dialog mDownloadDialog;
    //  判断是否停止
    private boolean mIsCancel = false;
    //  进度
    private int mProgress;
    //  文件保存路径
    private   String mSavePath;
    //  版本名称
    private String mVersion_name="1.0";
    //  请求链接
    public static String url ="";


    public static final int REQUEST_CALL_PERMISSION = 10086; //拨号请求码

    PopupWindow window;
    String mediaUrl;
    Bitmap head=null;
    Date curDate;
    ZfUtil zfUtil = ZfUtil.getInstance();
    public abstract View initlayout(LayoutInflater inflater,ViewGroup container);
    public abstract void initview();
    public abstract void initdata();
    public abstract void initlinsenter();
    public abstract void persenter();
    private boolean isviewble;
    private boolean isviewcreate;
    public Persenterimpl persenterimpl;
    List<LiveBean.ListBean> liveBeanList;;
    SharedPreferences sharedPreferences;
    String uuid;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    PopupWindow window1;
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> strings =new ArrayList<>();
    private static final String[] authBaseArr = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int authBaseRequestCode = 1;
    String userid,userName,srcurl,userHead;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  initlayout(inflater,container) ;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        persenterimpl=new Persenterimpl();
        persenterimpl.onAtach(this);
        isviewcreate=true;
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        userid=sharedPreferences.getString("userId",null);
        userName =sharedPreferences.getString("nickName",null);
        userHead=sharedPreferences.getString("photoUrl",null);
        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        curDate =  new Date(System.currentTimeMillis());
        laozy();
        initview();
        initdata();
        initlinsenter();
        initNavi();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isviewble=isVisibleToUser;
        laozy();
    }
    //传值
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setdata(Object o){
        /*if (o instanceof String){
               String str=(String) o;
        }*/
    }
    public void  laozy(){
        if (isviewcreate&&isviewble){
            persenter();
            isviewcreate=false;
            isviewble=false;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        persenterimpl.onDtach();
    }

    public void getdata(String livetype,int page){
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("page", page+"");
        map.put("limit", "10");
        map.put("type", livetype);
        map.put("kw", "");
        //  instance1.posthttps(UrlCount.Base_Huifang, map, new Http
        persenterimpl.posthttp(UrlCount.Base_Live,map, LiveBean.class);
        showProgress(getActivity(), "加载中...");
        //第二个参数不填即不显示文字
    }
    public void setdata(final List<LiveBean.ListBean> liveBeanList, LiveItemAdapter liveItemAdapter ){
        liveItemAdapter.setOnItemclick(new LiveItemAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                String roomNumber = liveBeanList.get(position).getRoomNumber();
                Log.e("TAG", liveBeanList.get(position).getExtPara1()+"参数是否pk");

                if (liveBeanList.get(position).getExtPara1().equals("0")){
                    Intent intent = new Intent(getActivity(), PlayActivityUI.class);
                    intent.putExtra("roomID",roomNumber);
                    intent.putExtra("streamID",liveBeanList.get(position).getExtPara3()+"");
                    intent.putExtra("pull","pull");
                    intent.putExtra("isplayBack",Integer.parseInt(liveBeanList.get(position).getIsplayback()));
                    intent.putExtra("replayurl",liveBeanList.get(position).getReplayurl());
                    getActivity().startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), PKPlayActivityUI.class);
                    intent.putExtra("roomID",roomNumber);
                    intent.putExtra("streamID",liveBeanList.get(position).getExtPara3()+"");
                    intent.putExtra("pull","pull");
                    intent.putExtra("isplayBack",Integer.parseInt(liveBeanList.get(position).getIsplayback()));
                    intent.putExtra("replayurl",liveBeanList.get(position).getReplayurl());
                    getActivity().startActivity(intent);
                }


            }
        });
    }

    /**
     * 判断是否有某项权限
     * @param string_permission 权限
     * @param request_code 请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission,int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(getActivity(), string_permission) == PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{string_permission}, request_code);
        }
        return flag;
    }




    public boolean qxMendth(){
        NotificationManagerCompat notification = NotificationManagerCompat.from(getActivity());
        boolean isEnabled = notification.areNotificationsEnabled();
        if (!isEnabled) {
            //未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.tishi)
                    .setMessage(R.string.plesetzqx)
                    .setNegativeButton(R.string.back_qx, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton(R.string.goset, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", getActivity().getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", getActivity().getPackageName());
                                intent.putExtra("app_uid", getActivity().getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                            }
                            startActivity(intent);

                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            return false;
        }else {
            return true;

        }
    }
    public  class AndroidJs{
        //4....写调用的方法,被js调用的代码需要加上下面的注解
       // RadioButton btn5=(RadioButton)getActivity().findViewById(R.id.btn5);
          //图片缩放
        @JavascriptInterface
        public void sfBitmap() {
            Log.e("缩放", "sfBitmap: " );
            EventBus.getDefault().post(3001);
        }
           //绑定微信
        @JavascriptInterface
        public void wxBd() {
            if (WXUtils.success(getActivity())) {//请求第三方登录
                wxBds=789;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wx_login_duzun";
                WXUtils.reg(getActivity()).sendReq(req);
            }
        }

        //跳转课程
        @JavascriptInterface
        public void keCheng(){
            RadioButton btn1=(RadioButton)getActivity().findViewById(R.id.btn1);
            btn1.setChecked(true);
            EventBus.getDefault().post(001);
        }
        //添加联系人
        @JavascriptInterface
        public void linkMan(String json){
            jsona=json;
            //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1001);
            } else {
                myRequetPermission(json);
            }


        }

        @JavascriptInterface
        public void imgBc(String url){
            ImageUtli imageUtli = new ImageUtli();
            Bitmap bitmap = imageUtli.returnBitMap(url);
        }

        @JavascriptInterface
        public void phone(String json) {
            call("tel:"+json);
        }


        @JavascriptInterface
        public void textFz(String json) {
            zfUtil.Textviews(getActivity(),json);
            Log.e("11", "textFz: " );
        }
        @JavascriptInterface
        public void outLoging() {
            editor.putString("userId", null);
            editor.putString("appLoginIdentity",null);
            editor.putString("photoUrl",null);
            editor.putString("nickName",null);
            editor.putString("pushCid",null);
            editor.putString("is_certification",null);
            editor.commit();
            startActivity(new Intent(getActivity(), LogingActivity.class));
           /* RadioButton btn6=(RadioButton)getActivity().findViewById(R.id.btn6);
            btn6.setChecked(true);*/
            getActivity().finish();
        }


        @JavascriptInterface
        public void showZp(int a) {
             Intent intent = new Intent(getActivity(), CreateZpActivity.class);
             intent.putExtra("flag",a);
             startActivity(intent);
        }
        //下载族谱
        @JavascriptInterface
        public void donwload(String url) {
            Log.e("zzz", url+"??");
            if(url==null||url.equals("")){
                Toast.makeText(getActivity(), R.string.donlaodnos, Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getActivity(), PdfActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }

        }
        @JavascriptInterface
        public String loging() {
            return uuid;
        }
        @JavascriptInterface
        public void wxPay(String json) {
            zfUtil.wxzfMethod(json);
        }
        @JavascriptInterface
        public void zfbPay(String json) {
            zfUtil.zfbmethod(json,getActivity());
        }
        @JavascriptInterface
        public void markAndroid( String flag) {
            Intent intent = new Intent(getActivity(), OneMrakActivity.class);
            intent.putExtra("flag",Integer.parseInt(flag));
            startActivity(intent);
        }
        @JavascriptInterface
         //得到经纬度
        public  void getGeoPointBystr(String str) throws IOException {

            GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
            geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                @Override
                public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                }

                @Override
                public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double lantitude = geocodeAddress.getLatLonPoint().getLatitude();
                        double longitude = geocodeAddress.getLatLonPoint().getLongitude();
                        if (str!=null&&!str.equals("")){
                            setview(lantitude,longitude,str);
                        }else {
                            Toast.makeText(getActivity(), R.string.daohangdz, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            GeocodeQuery geocodeQuery = new GeocodeQuery(str, str);
            geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
        }
        @JavascriptInterface
        public void updw() {
            Intent intent = new Intent(getActivity(), DwActivity.class);
            startActivity(intent);
        }
        @JavascriptInterface
        public String upvideo() {
            getmorePic();
            return mediaUrl;
        }
        @JavascriptInterface
        public String upMorePic(int a) {
            MultiImageSelector.create(getActivity())
                    .showCamera(true) // 是否显示相机. 默认为显示
                    .count(a) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                    .single() // 单选模式
                    .multi() // 多选模式, 默认模式;
                    .origin(strings) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(getActivity(), REQUEST_IMAGE3);
            return srcurl;
        }
        @JavascriptInterface
        public int getLanGuage(){
        return  sharedPreferences.getInt("lanGuage", 1);
        }
        @JavascriptInterface
        public void setLanGuage(){
            lanGuage();
        }
        @JavascriptInterface
        public String uponePic() {
            oneUpPic();
            return mediaUrl;
        }
        @JavascriptInterface
        public void wxShare( String uuid,  String subjectId,  String type, String title,  String applyId) {
            zfUtil.shareMethod(uuid,subjectId,type, title,applyId,getActivity());
            Log.e("TAG", "wxShare: "+uuid+subjectId+title);
        }
        @JavascriptInterface
        public void startCall(String json){
            Log.e("TAG", "++++++"+json );
            /*{"roomID":"184","userid":"118","username":"王长远",
            "userHead":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png
            ","isvoice":"false","time":"300000","parameters":"1"}*/

            jsons=json;
            Gson gson = new Gson();
            DaocterBeans daocterBeans = gson.fromJson(json, DaocterBeans.class);
            //roomID 房间号        userid 医生id          username 医生名称         userHead //医生头像         isvoice  //是否是语音通话
            // 购买时长  time
            if (qxMendth()){
                JSONObject root = new JSONObject();
                try {
                    root.put("title", "-1");
                    root.put("userName", userName);
                    root.put("userHead",userHead);
                    root.put("userid",userid);
                    root.put("roomid",daocterBeans.getRoomID());
                    root.put("isvoice",daocterBeans.isIsvoice());
                    root.put("time",daocterBeans.getTime());
                  //  root.put("gettime",curDate.getTime());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // pushManager.initialize(getActivity(),null);
                HashMap<String, Object> map = new HashMap<>();
                map.put("uid", Integer.parseInt(userid));
                map.put("targetuId", daocterBeans.getUserid());
                map.put("pushContent","有人向你发起通话请求");
                map.put("uri","000");
                map.put("title","通知");
                map.put("text","1");
                map.put("url",root.toString());
           //     map.put("sound","call.mp3");
                persenterimpl.posthttp(UrlCount.URL_GtSend, map, JsonBean.class);
              //  showProgress(ZegoApplication.getContexta(), "加载中...");
            }
        }

        @JavascriptInterface
        public void updata() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("","");
            persenterimpl.posthttp(UrlCount.URL_updata,map, DonwloadBean.class);
        }

    }
    public void getmorePic(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null);
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window.setContentView(view);
        window.setOutsideTouchable(true);
        TextView xj = view.findViewById(R.id.xj);
        TextView xc = view.findViewById(R.id.xc);
        TextView qx = view.findViewById(R.id.qx);
        xj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                Uri fileUri = null;
                try {
                    fileUri = Uri.fromFile(createMediaFile()); // create a file to save the video
                } catch (IOException e) {
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);//限制录制时间10秒
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
                // start the Video Capture Intent
                startActivityForResult(intent, 106);

                window.dismiss();
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 66);
                window.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        window.showAtLocation(inflate, Gravity.BOTTOM, 0, 30);

    }
    public void oneUpPic(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null);
        window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
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
                window1.dismiss();
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                window1.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

    }
    private File createMediaFile() throws IOException {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "CameraDemo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "VID_" + timeStamp;
        String suffix = ".mp4";
        File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
        return mediaFile;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initNavi() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        // 申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = getActivity().getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, getActivity().getPackageName()) != PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: >>>>>>>>>>>");
        if (resultCode == RESULT_OK) {
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
                        cropPhoto(Uri.fromFile(temp));//裁剪图片
                        Log.e("TAG", "图执行" );
                     //   upLoad(new File(path));
                    }
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras == null) {
                            return;
                        }
                        head = extras.getParcelable("data");
                        setPicToView(head);
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
                        upLoad(new File(path));
                    }


                    break;
                case 5:
                    if (requestCode == REQUEST_IMAGE3) {
                        if (resultCode == RESULT_OK) {
//                            List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            strings = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                            Log.e("TAG", "图:"+strings);
                            Httputlis1 instance = Httputlis1.getInstance();
                            for (int i = 0; i <strings.size() ; i++) {
                                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(strings.get(i)), Picbean.class);
                            }

                        }
                    }
                    break;
            }
        }
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String   VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            Log.e("TAG", "视频路径:"+VIDEOPATH);
            FileSizeUtil fileSizeUtil = new FileSizeUtil();
            double fileOrFilesSize = fileSizeUtil.getFileOrFilesSize(VIDEOPATH, 3);
            if(fileOrFilesSize>5.0){
                Toast.makeText(getActivity(), R.string.vidono5, Toast.LENGTH_SHORT).show();
            }else {
                persenterimpl.puthttp(UrlCount.URL_UpPic,new File(VIDEOPATH), Picbean.class);
                Toast.makeText(getActivity(),  R.string.viedoupz, Toast.LENGTH_SHORT).show();

            }


        }
        if (requestCode == 106) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                if (data.getData()!=null){
                    Uri data1 = data.getData();
                    //拿到视频保存地址
                    String s = data1.toString();
                    String[] split = s.split(":");
                    Log.e("TAG", "拍摄视频路径" + split[1].substring(2));

                    persenterimpl.puthttp(UrlCount.URL_UpPic,new File(split[1].substring(2)), Picbean.class);
                    Toast.makeText(getActivity(), R.string.viedoupz, Toast.LENGTH_SHORT).show();

                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            }
        }

        if(requestCode==NOT_NOTICE){
            myRequetPermission(jsona);//由于不知道是否选择了允许所以需要再次判断
        }
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
    private void upLoad(File file) {
        gethttp(file);
    }
    public void gethttp(File file){
        persenterimpl.puthttp(UrlCount.URL_UpPic,file, Picbean.class);
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




    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PERMISSION_GRANTED) {//失败
                    Toast.makeText(getActivity(),R.string.yxqxhzs,Toast.LENGTH_SHORT).show();
                } else {//成功
                    call("tel:"+"10086");
                }
                break;
            case 1001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myRequetPermission(jsona);
                } else {
                    Toast.makeText(getActivity(), R.string.getqxsb, Toast.LENGTH_SHORT).show();
                }
                break;


        }
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {//选择了“始终允许”

                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])){//用户选择了禁止不再询问

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("permission")
                                .setMessage(R.string.selectetxl)
                                .setPositiveButton(R.string.gouyx, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);//注意就是"package",不用改成自己的包名
                                        intent.setData(uri);
                                        startActivityForResult(intent, NOT_NOTICE);
                                    }
                                });
                        mDialog = builder.create();
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.show();



                    }else {//选择禁止
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("permission")
                                .setMessage(R.string.selecteapp)
                                .setPositiveButton(R.string.gouyx, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (alertDialog != null && alertDialog.isShowing()) {
                                            alertDialog.dismiss();
                                        }
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                    }
                                });
                        alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }

                }
            }
        }


    }
    /**
     * 拨打电话（直接拨打）
     * @param telPhone 电话
     */
    public void call(String telPhone){
        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)){
            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(telPhone));
            startActivity(intent);
        }
    }


    /*
     * 显示正在下载对话框
     */
    protected void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.donlaodez);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton(R.string.back_qx, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏当前对话框
                dialog.dismiss();
                // 设置下载状态为取消
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 下载文件
        downloadAPK();
    }
    /*
     * 开启新线程下载apk文件
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "jikedownload";
                        Log.e("版本", mSavePath);
                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion_name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float)count/length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0){
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private Handler mUpdateProgressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
                    Toast.makeText(getActivity(), R.string.donlaod, Toast.LENGTH_SHORT).show();
                    //installApk();
            }
        };
    };


    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        if (getActivity() == null || TextUtils.isEmpty(mSavePath)) {
            return;
        }
        File apkFile = new File(mSavePath, mVersion_name);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//      安装完成后，启动app（源码中少了这句话）
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        //  intent.setDataAndType(uri, "application/vnd.android.package-archive");



        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            Log.v("TAG","7.0以上，正在安装apk...");
            //provider authorities
            //  Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.luminal.mjptouch.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            Log.v("TAG","7.0以下，正在安装apk...");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }

        getActivity().startActivity(intent);
    }
   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
        if (o instanceof  Bitmap){
            Bitmap bitmap= (Bitmap)o;
            ImageUtli imageUtli = new ImageUtli();
            if (bitmap!=null)
            {
                imageUtli.saveBmp2Gallery(getActivity(),bitmap,"img");
            }
        }else {
            Bitmap bitmap= (Bitmap)o;
            ImageUtli imageUtli = new ImageUtli();
            if (bitmap!=null)
            {
                imageUtli.saveBmp2Gallery(getActivity(),bitmap,"img");
            }
        }


    }*/

    public void outLogings() {
        editor.putString("userId", null);
        editor.putString("appLoginIdentity",null);
        editor.putString("photoUrl",null);
        editor.putString("nickName",null);
        editor.putString("pushCid",null);
        editor.putString("is_certification",null);
        editor.commit();
        startActivity(new Intent(getActivity(), LogingActivity.class));
        getActivity().finish();
    }


    /**
     * 根据电话号码取得联系人姓名
     */
    public static String getContactNameByPhoneNumber(Context context, String address) {
        String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER };

        // 将自己添加到 msPeers 中
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, // Which columns to return.
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
                        + address + "'", // WHERE clause.
                null, // WHERE clause value substitution
                null); // Sort order.

        if (cursor == null) {
            Log.d("TAG", "getPeople null");
            return null;
        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            // 取得联系人名字
            int nameFieldColumnIndex = cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String name = cursor.getString(nameFieldColumnIndex);
            return name;
        }
        return null;
    }
    private void myRequetPermission(String json) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            Gson gson = new Gson();
            TxBean txBean = gson.fromJson(json, TxBean.class);
                if (getContactNameByPhoneNumber(getActivity(),txBean.getPhone()+"")!=null&&getContactNameByPhoneNumber(getActivity(),txBean.getPhone()).equals(txBean.getRealName()))
                {
                    Toast.makeText(getActivity(), R.string.yczwxcf, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, txBean.getRealName()+"");
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE,  txBean.getPhone()+"");
                    startActivity(intent);
                }


        }
    }


    /**
     * 百度导航
     * @param context
     * @param location location[0]纬度lat，location[1]经度lon
     */
    public static void baiduGuide(Context context, double[] location,String targetname) {
        double[] baiduLoc = GpsUtils.gcj02_To_Bd09(location[0], location[1]);

        if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
            try {
//intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + baiduLoc[0] + "," + baiduLoc[1] + "|name:"+targetname +        //终点
                        "&mode=driving" +          //导航路线方式
                        "&region=" +           //
                        "&src=" +
                        context.getResources().getString(R.string.app_name) +
                        "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, R.string.noanzhuangbd, Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 高德导航
     * @param context
     * @param location
     */
    public static void gaodeGuide(Context context, double[] location,String targetname) {
        if (isAvilible(context, GAODE_PACKAGENAME)) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=" +
                        context.getResources().getString(R.string.app_name) +
                        "&poiname="+targetname +
                        "&lat=" + location[0] +
                        "&lon=" + location[1] +
                        "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, R.string.noanzhuanggd, Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


    /**
     * 腾讯导航
     * @param context
     * @param location
     */
    public static void tencentGuide(Context context, double[] location,String targetname) {
        String downloadUri = "http://softroute.map.qq.com/downloadfile?cid=00001";
        String baseUrl = "qqmap://map/";
        String searchPlace = "search?keyword=酒店&bound=39.907293,116.368935,39.914996,116.379321";
        String searchAround = "search?keyword=肯德基&center=39.908491,116.374328&radius=1000";
        String busPlan = "routeplan?type=bus&from=我的家&fromcoord=39.980683,116.302&to=柳巷&tocoord=39.9836,116.3164&policy=2";
        String drivePlan = "routeplan?type=drive&from=&fromcoord=&to=&tocoord=" + location[0] + "," + location[1] + "&policy=1";
        String tencnetUri = baseUrl + drivePlan + "&referer=" + context.getResources().getString(R.string.app_name);

        if (isAvilible(context, TENCENT_PACKAGENAME)) {
            Intent intent;
            try {
                intent = Intent.parseUri(tencnetUri, 0);
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            //直接下载
//            Intent intent;
//            try {
//                intent = Intent.parseUri(downloadUri, 0);
//                context.startActivity(intent);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
            //市场下载
            Toast.makeText(context, R.string.noanzhuangtx, Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=" + TENCENT_PACKAGENAME);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }



    private void setview(double lat,double lon,String targetname) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] strs = {String.valueOf(R.string.gaode), String.valueOf(R.string.baidu),String.valueOf(R.string.tengxun), String.valueOf(R.string.back_qx)};
        builder.setItems(strs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        double[] doubles0 = {lat,lon};
                        gaodeGuide(getActivity(),doubles0,targetname);
                        break;
                    case 1:
                        double[] doubles1 = {lat,lon};
                        baiduGuide(getActivity(),doubles1,targetname);
                        break;
                    case 2:
                        double[] doubles2 = {lat,lon};
                        tencentGuide(getActivity(),doubles2,targetname);
                        break;
                    case 3:

                        break;
                }
            }
        }).create();

        builder.show();
    }

    /***
     * 启动
     */
    public  void showProgress(Context context, String message) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.showMessage(message);
    }

    /***
     * 关闭
     */
    public  void dismissProgress(Context context) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.dismiss();
    }

    //简体转成繁体
    public String change(String changeText) {
        try {
            JChineseConvertor jChineseConvertor = JChineseConvertor
                    .getInstance();
            changeText = jChineseConvertor.s2t(changeText);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return changeText;
    }

    //繁体转成简体
    public String change1(String changeText) {
        try {
            JChineseConvertor jChineseConvertor = JChineseConvertor
                    .getInstance();
            changeText = jChineseConvertor.t2s(changeText);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return changeText;
    }


    //窗口显示转换
    public void lanGuage(){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();

        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.titless))
                .setMessage(getResources().getString(R.string.dialog_title))
                .setNegativeButton(getResources().getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       /* type = ENGLISH;
                        set(type);
                        Toast.makeText(mContext, getResources().getString(R.string.toast_set_en), Toast.LENGTH_SHORT).show();*/
                        //or 繁体中文
                        config.locale = Locale.TRADITIONAL_CHINESE;
                        resources.updateConfiguration(config, dm);
                        getActivity().recreate();
                        cler();
                        editor.putInt("lanGuage",2);
                        editor.commit();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
       /*                 type = CHINESE;
                        set(type);
                        Toast.makeText(mContext, getResources().getString(R.string.toast_set_ch), Toast.LENGTH_SHORT).show();*/
                        //简体中文
                        config.locale = Locale.SIMPLIFIED_CHINESE;
                        resources.updateConfiguration(config, dm);
                        getActivity().recreate();
                        cler();
                        editor.putInt("lanGuage",1);
                        editor.commit();
                    }
                })
                .create();
        dialog.show();
    }

//刷新activity的文字

    public void cler(){
      /*  Intent intent = new Intent(this, MainActivity.class);//跳到你的登录页面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
