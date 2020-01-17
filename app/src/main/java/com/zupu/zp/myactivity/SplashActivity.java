package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.adapter.Prageradapternoe;
import com.zupu.zp.entity.ZegoApplication;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private ViewPager vp;
    private RadioGroup rg;
    private Button btn;
    private RelativeLayout reyd;
    Prageradapternoe prageradapternoe;
    ArrayList<Integer> list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        vp = (ViewPager) findViewById(R.id.vp);
        rg = (RadioGroup) findViewById(R.id.rg);
        btn =  findViewById(R.id.btn);
        reyd=(RelativeLayout)findViewById(R.id.reyd);



        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor=sharedPreferences.edit();
        String appLoginIdentity = sharedPreferences.getString("appLoginIdentity", null);
        boolean one = sharedPreferences.getBoolean("one", false);
        if (appLoginIdentity==null){
            if (!one){
                reyd.setVisibility(View.VISIBLE);
                //点击进入
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putBoolean("one",true);
                        editor.commit();
                        startActivity(new Intent(SplashActivity.this,LogingActivity.class));
                        finish();
                    }
                });

            }else {
                startActivity(new Intent(SplashActivity.this,LogingActivity.class));
                finish();
            }

        }else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }


        //准备数据
        list.add(R.drawable.s1);
        list.add(R.drawable.s2);
        list.add(R.drawable.s3);
        list.add(R.drawable.s4);
        list.add(R.drawable.s5);
        //创建适配器
        prageradapternoe = new Prageradapternoe(list,SplashActivity.this);
        //设置适配器
        vp.setAdapter(prageradapternoe);

        //滑动监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                    rg.check(R.id.btn1);
                        btn.setVisibility(View.INVISIBLE);

                    break;
                    case 1:
                        rg.check(R.id.btn2);
                        btn.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        rg.check(R.id.btn3);
                        btn.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        rg.check(R.id.btn4);
                        btn.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        rg.check(R.id.btn5);
                        btn.setVisibility(View.VISIBLE);
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });










    }
}
