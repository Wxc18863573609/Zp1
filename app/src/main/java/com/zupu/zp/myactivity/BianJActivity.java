package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.CustomDatePicker;
import com.zupu.zp.utliss.DateFormatUtils;
import com.zupu.zp.utliss.UrlCount;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

public class BianJActivity extends BaseActivity {
    SharedPreferences sharedPreferences;
    private ImageView backss;
    private TextView titlename;
    private TextView shure;
    private View views1;
    private RelativeLayout title;
    private EditText ed_1;
    private EditText ed_2;
    private TextView ed_3;
    private TextView ed_4;
    private CustomDatePicker mDatePicker, mTimerPicker;
    private CityPicker mCP;
    String city;
    String province;
    String district;
    String treeid;
    int flag;
    @Override
    public int initlayout() {
        return R.layout.activity_bian_j;
    }

    @Override
    public void initview() {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        backss = (ImageView) findViewById(R.id.backss);
        titlename = (TextView) findViewById(R.id.titlename);
        shure = (TextView) findViewById(R.id.shure);
        views1 = (View) findViewById(R.id.views1);
        title = (RelativeLayout) findViewById(R.id.title);
        ed_1=(EditText)findViewById(R.id.ed_1);
        ed_2=(EditText)findViewById(R.id.ed_2);
        ed_3=(TextView) findViewById(R.id.ed_3);
        ed_4=(TextView) findViewById(R.id.ed_4);
        initDatePicker();
        initTimerPicker();
    }

    @Override
    public void initdata() {
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        treeid = intent.getStringExtra("treeid");
        ed_1.setText(intent.getStringExtra("bookname"));
        ed_2.setText(intent.getStringExtra("wb2"));
        ed_3.setText(intent.getStringExtra("wb1"));
    }

    @Override
    public void initlisenter() {
        //确定
        shure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("bookname",ed_1.getText().toString());     //把获取的值传到第一个页面
                intent.putExtra("wb2",ed_2.getText().toString());
                if (!ed_3.getText().toString().equals("请选择")){
                    intent.putExtra("wb1",ed_3.getText().toString());
                }
                intent.putExtra("ed4",ed_4.getText().toString());
                setResult(RESULT_OK,intent);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("familyName",ed_1.getText().toString());
                    map.put("createrName",ed_2.getText().toString());
                    if (!ed_3.getText().toString().equals("请选择")){
                        map.put("editTime",ed_3.getText().toString());
                    }else {
                        map.put("editTime","");
                    }
                    if (!ed_4.getText().toString().equals("请选择")){
                        map.put("editLocation",ed_4.getText().toString());
                    }else {
                        map.put("editLocation","");
                    }
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);


            }
        });


        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //选择地理位置
        ed_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYunCityPicher();
                mCP.show();
            }
        });
        //设置出生时间
        ed_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show("请选择");
            }
        });

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {
     if (o instanceof  PhoneBean){
         PhoneBean phoneBean=(PhoneBean)o;
         if (phoneBean.getCode()==0){
             EventBus.getDefault().post(460);
             Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
             finish();
         }else {
             Toast.makeText(this, phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
         }
     }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }

    public void mYunCityPicher() {
        mCP = new CityPicker.Builder(BianJActivity.this)
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
                ed_4.setText(province + city + district);
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
        ed_3.setText(DateFormatUtils.long2Str(endTimestamp, false));
            parmesBena.setLateBirthTime(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {

                ed_3.setText(DateFormatUtils.long2Str(timestamp, false));
                    parmesBena.setLateBirthTime(DateFormatUtils.long2Str(timestamp, false));
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
        ed_3.setText("请选择");
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                ed_3.setText(DateFormatUtils.long2Str(timestamp, true));
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
