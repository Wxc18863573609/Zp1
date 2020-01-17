package com.zupu.zp.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zupu.zp.R;

public class EdjieActivity extends AppCompatActivity {

    private ImageView backss;
    private TextView titlename;
    private TextView shure;
    private View views1;
    private RelativeLayout title;
    private EditText ed_jieshao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edjie);
        initView();
        //确定
        shure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        backss = (ImageView) findViewById(R.id.backss);
        titlename = (TextView) findViewById(R.id.titlename);
        shure = (TextView) findViewById(R.id.shure);
        views1 = (View) findViewById(R.id.views1);
        title = (RelativeLayout) findViewById(R.id.title);
        ed_jieshao = (EditText) findViewById(R.id.ed_jieshao);
    }

    private void submit() {
        // validate
        String jieshao = ed_jieshao.getText().toString().trim();
        if (TextUtils.isEmpty(jieshao)) {
            Toast.makeText(this, "请填写人物介绍", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("jieshao",jieshao);     //把获取的值传到第一个页面
        setResult(RESULT_OK,intent);
        finish();

        // TODO validate success, do something


    }
}
