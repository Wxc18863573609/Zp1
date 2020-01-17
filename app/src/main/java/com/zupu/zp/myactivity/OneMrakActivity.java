package com.zupu.zp.myactivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.ParmesBena;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.CustomDatePicker;
import com.zupu.zp.utliss.DateFormatUtils;
import com.zupu.zp.utliss.UrlCount;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class OneMrakActivity extends BaseActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    Bitmap head=null;
    String mediaUrl;
    TextView jianjie;
    String address;
    String loang;
    String lat;
    String sex;
    Button stars;
    private PopupWindow window;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//【位置管理】
    private TextView titlename;
    private RelativeLayout title;
    private ImageView upxj;
    private TextView b1;
    private RelativeLayout rl;
    private View v1;
    private TextView xm;
    private RelativeLayout rl1;
    private View v2;
    private TextView xm1;
    private RelativeLayout rl2;
    private View v3;
    private TextView xm2;
    private RelativeLayout rl3;
    private View v4;
    private TextView xm3;
    private RelativeLayout rl4;
    private View v5;
    private TextView xm4;
    private RelativeLayout rl5;
    private View v6;
    private TextView xm5;
    private RelativeLayout rl6;
    private View v7;
    private TextView xm6;
    private RelativeLayout rl7;
    private View v8;
    private TextView xm7;
    private RelativeLayout rl8;
    private View v9;
    TextView xz6,shengxz,shiy;
    private CityPicker mCP;
    String city;
    String province;
    String district;
    boolean boo1,boo2;
    ImageView back;
    RadioButton lefts,rights;
    TextView dingwei;
    EditText ed_name;
    int xb=0;
    boolean boo11;
    EditText ed_position;
    private CustomDatePicker mDatePicker, mTimerPicker;
    SharedPreferences sharedPreferences;
    String uuid;
    @Override
    public int initlayout() {
        return R.layout.activity_one_mrak;
    }

    @Override
    public void initview() {
        quanxian();
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        uuid=sharedPreferences.getString("appLoginIdentity",null);
        parmesBena.setUuid(uuid);
        titlename = (TextView) findViewById(R.id.titlename);
        title = (RelativeLayout) findViewById(R.id.title);
        upxj = (ImageView) findViewById(R.id.upxj);
        b1 = (TextView) findViewById(R.id.b1);
        rl = (RelativeLayout) findViewById(R.id.rl);
        v1 = (View) findViewById(R.id.v1);
        xm = (TextView) findViewById(R.id.xm);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        v2 = (View) findViewById(R.id.v2);
        xm1 = (TextView) findViewById(R.id.xm1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        v3 = (View) findViewById(R.id.v3);
        xm2 = (TextView) findViewById(R.id.xm2);
        rl3 = (RelativeLayout) findViewById(R.id.rl3);
        v4 = (View) findViewById(R.id.v4);
        xm3 = (TextView) findViewById(R.id.xm3);
        rl4 = (RelativeLayout) findViewById(R.id.rl4);
        v5 = (View) findViewById(R.id.v5);
        xm4 = (TextView) findViewById(R.id.xm4);
        rl5 = (RelativeLayout) findViewById(R.id.rl5);
        v6 = (View) findViewById(R.id.v6);
        xm5 = (TextView) findViewById(R.id.xm5);
        rl6 = (RelativeLayout) findViewById(R.id.rl6);
        v7 = (View) findViewById(R.id.v7);
        xm6 = (TextView) findViewById(R.id.xm6);
        rl7 = (RelativeLayout) findViewById(R.id.rl7);
        v8 = (View) findViewById(R.id.v8);
        xm7 = (TextView) findViewById(R.id.xm7);
        rl8 = (RelativeLayout) findViewById(R.id.rl8);
        v9 = (View) findViewById(R.id.v9);
        xz6=(TextView)findViewById(R.id.xz6);
        shengxz=(TextView)findViewById(R.id.shengxz);
        shiy=(TextView)findViewById(R.id.shiy);
        jianjie=(TextView)findViewById(R.id.jianjie);
        back=(ImageView)findViewById(R.id.abc);
        lefts=(RadioButton)findViewById(R.id.lefts);
        rights=(RadioButton)findViewById(R.id.rights);
        dingwei=(TextView)findViewById(R.id.dingwei);
        stars=(Button) findViewById(R.id.stars);
        ed_name=(EditText) findViewById(R.id.ed_name);
        ed_position=(EditText) findViewById(R.id.ed_position);
        initDatePicker();
        initTimerPicker();
    }

    @Override
    public void initdata() {
        //关闭页面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initlisenter() {
        //简介
        jianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneMrakActivity.this,EdjieActivity.class);
                startActivityForResult(intent,1001);
            }
        });


        //开始下一步
        stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = getIntent();
                int flag = intent1.getIntExtra("flag", 0);
                submit();
                if (flag==1){
                Intent intent = new Intent(OneMrakActivity.this,XuanZemarkActivity.class);
                    intent.putExtra("flags",flag+"");
                    intent.putExtra("uuid",parmesBena.getUuid());
                    intent.putExtra("pavilionType",flag+"");
                    intent.putExtra("lateName",ed_name.getText().toString());
                    intent.putExtra("lateGender",parmesBena.getLateGender());
                    intent.putExtra("lateBirthTime",parmesBena.getLateBirthTime());
                    intent.putExtra("latePassingTime",parmesBena.getLatePassingTime());
                    intent.putExtra("latePicture",mediaUrl);
                    intent.putExtra("latePresentation",parmesBena.getLatePresentation());
                    intent.putExtra("cemeteryLocation",parmesBena.getCemeteryLocation());
                    intent.putExtra("combstonePosition ",ed_position.getText());
                    intent.putExtra("cemeteryName",address);
                    intent.putExtra("lng",loang);
                    intent.putExtra("lat",lat);
                startActivity(intent);
                }else if (flag==2){
                    Intent intent = new Intent(OneMrakActivity.this,TowmarkActivity.class);
                    intent.putExtra("flags",flag);
                    intent.putExtra("uuid",parmesBena.getUuid());
                    intent.putExtra("pavilionType",flag+"");
                    intent.putExtra("lateName",ed_name.getText().toString());
                    intent.putExtra("lateGender",parmesBena.getLateGender());
                    intent.putExtra("lateBirthTime",parmesBena.getLateBirthTime());
                    intent.putExtra("latePassingTime",parmesBena.getLatePassingTime());
                    intent.putExtra("latePicture",mediaUrl);
                    intent.putExtra("latePresentation",parmesBena.getLatePresentation());
                    intent.putExtra("cemeteryLocation",parmesBena.getCemeteryLocation());
                    intent.putExtra("combstonePosition ",ed_position.getText());
                    intent.putExtra("cemeteryName",address);
                    intent.putExtra("lng",loang);
                    intent.putExtra("lat",lat);
                    startActivity(intent);
                }
            }
        });
        //点击上传图片
          upxj.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                              || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                          ActivityCompat.requestPermissions(OneMrakActivity.this, new String[]{
                                  Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
                      } else {
                          Log.e("aa", "相机权限已开启");
                          //检测是否有写的权限
                          int permission = ActivityCompat.checkSelfPermission(OneMrakActivity.this,
                                  "android.permission.WRITE_EXTERNAL_STORAGE");
                          if (permission != PackageManager.PERMISSION_GRANTED) {
                              // 没有写的权限，去申请写的权限，会弹出对话框
                              ActivityCompat.requestPermissions(OneMrakActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                          }else {

                              View view = LayoutInflater.from(OneMrakActivity.this).inflate(R.layout.pop, null);
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
                              View inflate = LayoutInflater.from(OneMrakActivity.this).inflate(R.layout.activity_main, null);
                              window.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

                          }


                      }
                  } else {
                      Log.e("tag", "版本较低 ");
                  }


              }
          });
        //定位跳转
       dingwei.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(OneMrakActivity.this, DwActivity.class);
               startActivityForResult(intent,1002);
           }
       });
        //默认男性
        lefts.setChecked(true);
        sex="男";
        parmesBena.setLateGender(sex);
        //男
        lefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lefts.setChecked(true);
                rights.setChecked(false);
                 sex="男";
                parmesBena.setLateGender(sex);
                xb=0;
            }
        });
        //女
        rights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lefts.setChecked(false);
                rights.setChecked(true);
                sex="女";
                parmesBena.setLateGender(sex);
                xb=1;
            }
        });


        //选择地理位置
        rl6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYunCityPicher();
                mCP.show();
            }
        });
        //设置出生时间
        shengxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show("请选择");
                boo1=true;
                boo2=false;
            }
        });
        //设置死亡时间
        shiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show("请选择");
                boo2=true;
                boo1=false;
            }
        });


    }

    @Override
    public void persenter() {

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
    }
    public void mYunCityPicher() {
        mCP = new CityPicker.Builder(OneMrakActivity.this)
                .textSize(20)
                //地址选择
                .title("地址选择")
                .backgroundPop(0xa0000000)
                //文字的颜色
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滑轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省滑轮是否循环显示
                .provinceCyclic(true)
                //市滑轮是否循环显示
                .cityCyclic(false)
                //地区（县）滑轮是否循环显示
                .districtCyclic(false)
                //滑轮显示的item个数
                .visibleItemsCount(7)
                //滑轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        //监听
        mCP.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省
                province = citySelected[0];
                //市
                city = citySelected[1];
                //区。县。（两级联动，必须返回空）
                district = citySelected[2];
                //邮证编码
                String code = citySelected[3];
                xz6.setText(province + city + district);
                parmesBena.setCemeteryLocation(province + city + district);
//cemeteryLocation
            }

            @Override
            public void onCancel() {


            }
        });
    }
  //得到时间
    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();
        if (boo1){
            shengxz.setText(DateFormatUtils.long2Str(endTimestamp, false));
            parmesBena.setLateBirthTime(DateFormatUtils.long2Str(endTimestamp, false));

        }
        if (boo2){
            shiy.setText(DateFormatUtils.long2Str(endTimestamp, false));
            parmesBena.setLatePassingTime(DateFormatUtils.long2Str(endTimestamp, false));
        }

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                if (boo1){
                    shengxz.setText(DateFormatUtils.long2Str(timestamp, false));
                    parmesBena.setLateBirthTime(DateFormatUtils.long2Str(timestamp, false));
                }
                if (boo2){
                    shiy.setText(DateFormatUtils.long2Str(timestamp, false));
                    parmesBena.setLatePassingTime(DateFormatUtils.long2Str(timestamp, false));
                }


            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    private void initTimerPicker() {
        String beginTime = "2018-10-17 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        shengxz.setText("请选择");
        shiy.setText("请选择");

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                if (boo1){
                    shengxz.setText(DateFormatUtils.long2Str(timestamp, true));
                }
                if (boo2){
                    shiy.setText(DateFormatUtils.long2Str(timestamp, true));
                }

            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
    }
    public void quanxian(){
        lm = (LocationManager) getApplicationContext().getSystemService(OneMrakActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(OneMrakActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG","没有权限");
               // Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(OneMrakActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

                // 有权限了，去放肆吧。
//                        Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("BRG","系统检测到未开启GPS定位服务");
            Toast.makeText(OneMrakActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }





    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(OneMrakActivity.this, "定位权限被禁止，相关地图功能无法使用！",Toast.LENGTH_LONG).show();
                }

            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK) {
                jianjie.setText(data.getStringExtra("jieshao"));
                    //latePresentation
                    parmesBena.setLatePresentation(data.getStringExtra("jieshao"));

                }
                break;
            case 1002:
                if (resultCode == RESULT_OK) {
                    lat = data.getStringExtra("lat");
                    loang = data.getStringExtra("loang");
                    address = data.getStringExtra("address");
                    dingwei.setText(data.getStringExtra("address"));
                    parmesBena.setLat(lat);
                    parmesBena.setLng(loang);


                }
                break;
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
                    upxj.setImageBitmap(bitmap);
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
                    head = extras.getParcelable("data");
                    upxj.setImageBitmap(head);
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
    public void gethttp(File file){
        boo11=true;
        persenterimpl.puthttp(UrlCount.URL_UpPic,file, Picbean.class);
    }

    private void submit() {
        // validate
        String title = ed_name.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (boo11==false) {
            Toast.makeText(this, "请先上传图片", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}
