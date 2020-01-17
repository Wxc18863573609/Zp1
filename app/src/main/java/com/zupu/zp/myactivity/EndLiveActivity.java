package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.entity.ZegoApplication;

public class EndLiveActivity extends BaseActivity {

    String starttime, endtime;
    int money, number, hdnumber;
    SharedPreferences sharedPreferences;
    String phone, nickName, photoUrl;
    private ImageView endhead;
    private TextView end_name;
    private TextView end_id;
    private View end_v1;
    private TextView end_money, endtimes, starttimes;
    private TextView moneybs;
    private View end_v2;
    private TextView hdcs;
    private TextView hdbs;
    private TextView ljgk;
    private RelativeLayout reright;
    Button btn;

    @Override
    public int initlayout() {
        return R.layout.activity_end_live;
    }

    @Override
    public void initview() {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        Intent intent = getIntent();
        starttime = intent.getStringExtra("starttime");
        endtime = intent.getStringExtra("endtime");
        money = intent.getIntExtra("money", 0);
        number = intent.getIntExtra("number", 0);
        hdnumber = intent.getIntExtra("hdnumber", 0);
        phone = sharedPreferences.getString("phone", "0");
        nickName = sharedPreferences.getString("nickName", "贾宝玉");
        photoUrl = sharedPreferences.getString("photoUrl", null);
        endhead = (ImageView) findViewById(R.id.endhead);
        end_name = (TextView) findViewById(R.id.end_name);
        end_id = (TextView) findViewById(R.id.end_id);
        end_v1 = (View) findViewById(R.id.end_v1);
        end_money = (TextView) findViewById(R.id.end_money);
        moneybs = (TextView) findViewById(R.id.moneybs);
        end_v2 = (View) findViewById(R.id.end_v2);
        hdcs = (TextView) findViewById(R.id.hdcs);
        hdbs = (TextView) findViewById(R.id.hdbs);
        ljgk = (TextView) findViewById(R.id.ljgk);
        reright = (RelativeLayout) findViewById(R.id.reright);
        endtimes = findViewById(R.id.endtimes);
        starttimes = findViewById(R.id.starttimes);
        btn = findViewById(R.id.btn);
    }


    @Override
    public void initdata() {
        //头像
        Glide.with(this).load(photoUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(endhead);
        //姓名
        end_name.setText(nickName);
        //id
        end_id.setText(R.string.zbid + phone + "");

        end_money.setText(money + "");

        hdcs.setText(hdnumber + "");

        ljgk.setText(number + "");

        starttimes.setText(R.string.starttime + starttime);

        endtimes.setText(R.string.endtime + endtime);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initlisenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {

    }


}
