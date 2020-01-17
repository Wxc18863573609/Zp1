package com.zupu.zp.myactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.utliss.CustomDatePicker;
import com.zupu.zp.utliss.DateFormatUtils;
import com.zupu.zp.utliss.UrlCount;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class TowmarkActivity extends BaseActivity implements View.OnClickListener {
    boolean boo11;
    String mediaUrl;
    private CustomDatePicker mDatePicker, mTimerPicker;
    String sex;
    boolean boo1,boo2;
    Bitmap head=null;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/head.jpg";
    private ImageView backss;
    private TextView titlename;
    private RelativeLayout title;
    private ImageView upxj;
    private TextView b1;
    private RelativeLayout rl;
    private View v1;
    private TextView xm;
    private EditText ed_name;
    private RelativeLayout rl1;
    private View v2;
    private TextView xm1;
    private TextView shengxz;
    private RelativeLayout rl2;
    private View v3;
    private TextView xm2;
    private TextView shiy;
    private RelativeLayout rl3;
    private View v4;
    private TextView xm3;
    private RadioButton lefts;
    private TextView xx1;
    private RadioButton rights;
    private RelativeLayout rl4;
    private View v5;
    private TextView xm4;
    private TextView jianjie;
    private RelativeLayout rl5;
    private View v6;
    private Button stars;
    private PopupWindow window;
    @Override
    public int initlayout() {
        return R.layout.activity_towmark;
    }

    @Override
    public void initview() {
        backss = (ImageView) findViewById(R.id.backss);
        titlename = (TextView) findViewById(R.id.titlename);
        title = (RelativeLayout) findViewById(R.id.title);
        upxj = (ImageView) findViewById(R.id.upxj);
        b1 = (TextView) findViewById(R.id.b1);
        rl = (RelativeLayout) findViewById(R.id.rl);
        v1 = (View) findViewById(R.id.v1);
        xm = (TextView) findViewById(R.id.xm);
        ed_name = (EditText) findViewById(R.id.ed_name);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        v2 = (View) findViewById(R.id.v2);
        xm1 = (TextView) findViewById(R.id.xm1);
        shengxz = (TextView) findViewById(R.id.shengxz);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        v3 = (View) findViewById(R.id.v3);
        xm2 = (TextView) findViewById(R.id.xm2);
        shiy = (TextView) findViewById(R.id.shiy);
        rl3 = (RelativeLayout) findViewById(R.id.rl3);
        v4 = (View) findViewById(R.id.v4);
        xm3 = (TextView) findViewById(R.id.xm3);
        lefts = (RadioButton) findViewById(R.id.lefts);
        xx1 = (TextView) findViewById(R.id.xx1);
        rights = (RadioButton) findViewById(R.id.rights);
        rl4 = (RelativeLayout) findViewById(R.id.rl4);
        v5 = (View) findViewById(R.id.v5);
        xm4 = (TextView) findViewById(R.id.xm4);
        jianjie = (TextView) findViewById(R.id.jianjie);
        rl5 = (RelativeLayout) findViewById(R.id.rl5);
        v6 = (View) findViewById(R.id.v6);
        stars = (Button) findViewById(R.id.stars);
        initDatePicker();
        initTimerPicker();
        stars.setOnClickListener(this);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void initlisenter() {
        //简介
        jianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TowmarkActivity.this,EdjieActivity.class);
                startActivityForResult(intent,1001);
            }
        });
        //点击上传图片
        upxj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(TowmarkActivity.this).inflate(R.layout.pop, null);
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
                View inflate = LayoutInflater.from(TowmarkActivity.this).inflate(R.layout.activity_main, null);
                window.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);


            }
        });

        //默认男性
        lefts.setChecked(true);
        sex="男";
        parmesBena.setLateGendertwo("男");
        //男
        lefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lefts.setChecked(true);
                rights.setChecked(false);
                sex="男";
                parmesBena.setLateGendertwo(sex);
            }
        });
        //女
        rights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lefts.setChecked(false);
                rights.setChecked(true);
                sex="女";
                parmesBena.setLateGendertwo(sex);
            }
        });
        //设置出生时间
        shengxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(R.string.qxz);
                boo1=true;
                boo2=false;
            }
        });
        //设置死亡时间
        shiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(R.string.qxz);
                boo2=true;
                boo1=false;
            }
        });
        //关闭页面
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void persenter() {

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stars:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String title = ed_name.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, R.string.qrsxm, Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (boo11==false) {
            Toast.makeText(this, R.string.qxsctp, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent1 = getIntent();
        Intent intent = new Intent(TowmarkActivity.this,XuanZemarkActivity.class);
        intent.putExtra("flags",intent1.getStringExtra("flags"));
        intent.putExtra("uuid",intent1.getStringExtra("uuid"));
        intent.putExtra("pavilionType", intent1.getStringExtra("pavilionType"));
        intent.putExtra("lateName",intent1.getStringExtra("lateName"));
        intent.putExtra("lateGender",intent1.getStringExtra("lateGender"));
        intent.putExtra("lateBirthTime",intent1.getStringExtra("lateBirthTime"));
        intent.putExtra("latePassingTime",intent1.getStringExtra("latePassingTime"));
        intent.putExtra("latePicture",intent1.getStringExtra("latePicture"));
        intent.putExtra("latePresentation",intent1.getStringExtra("latePresentation"));
        intent.putExtra("cemeteryLocation",intent1.getStringExtra("cemeteryLocation"));
        intent.putExtra("combstonePosition ",intent1.getStringExtra("combstonePosition"));
        intent.putExtra("cemeteryName",intent1.getStringExtra("cemeteryName"));
        intent.putExtra("lat",intent1.getStringExtra("lat"));
        intent.putExtra("lng",intent1.getStringExtra("lng"));
        intent.putExtra("lateNametwo",ed_name.getText().toString());
        intent.putExtra("lateGendertwo",parmesBena.getLateGendertwo());
        intent.putExtra("lateBirthTimetwo",parmesBena.getLateBirthTimetwo());
        intent.putExtra("latePassingTimetwo",parmesBena.getLatePassingTimetwo());
        intent.putExtra("latePicturetwo",mediaUrl);
        intent.putExtra("latePresentationtwo",parmesBena.getLatePresentationtwo()+"");
        startActivity(intent);

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
                    parmesBena.setLatePresentationtwo(data.getStringExtra("jieshao"));
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
    //得到时间
    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();
        if (boo1){
            shengxz.setText(DateFormatUtils.long2Str(endTimestamp, false));
            parmesBena.setLateBirthTimetwo(DateFormatUtils.long2Str(endTimestamp, false));
        }
        if (boo2){
            shiy.setText(DateFormatUtils.long2Str(endTimestamp, false));
            parmesBena.setLatePassingTimetwo(DateFormatUtils.long2Str(endTimestamp, false));
        }

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                if (boo1){
                    shengxz.setText(DateFormatUtils.long2Str(timestamp, false));
                    parmesBena.setLateBirthTimetwo(DateFormatUtils.long2Str(timestamp, false));
                }
                if (boo2){
                    shiy.setText(DateFormatUtils.long2Str(timestamp, false));
                    parmesBena.setLatePassingTimetwo(DateFormatUtils.long2Str(timestamp, false));
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
        shengxz.setText(R.string.qxz);
        shiy.setText(R.string.qxz);

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
}
