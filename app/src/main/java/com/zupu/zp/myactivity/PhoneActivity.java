package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.DlogingBean;
import com.zupu.zp.bean.DxBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.PhoneLoginBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.TimeCount;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.zz;

import java.util.HashMap;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;

public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backs;
    private  TextView phone_login;
    private EditText ed_phone;
    private Button start_loging;
    int codeId;
    String phone;
    String flags;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public int initlayout() {
        return R.layout.activity_phone;
    }

    @Override
    public void initview() {
        backs = (ImageView) findViewById(R.id.backs);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        start_loging = (Button) findViewById(R.id.start_loging);
        phone_login=findViewById(R.id.phone_login);
        start_loging.setOnClickListener(this);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
    }

    @Override
    public void initdata() {
        //time = new TimeCount(60000,1000,wang);
        Intent intent = getIntent();
        flags = intent.getStringExtra("flags");
        phone_login.setText(flags);

    }

    @Override
    public void initlisenter() {

        backs.setOnClickListener(new View.OnClickListener() {
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
            case R.id.start_loging:
                if (isFastClick()){
                    submit();
                }
                break;
        }
    }

    private void submit() {
        // validate
         phone = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.qsrphone, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!zz.isMobileNO(phone)) {
            Toast.makeText(getApplicationContext(), R.string.zhgscw, Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        if (flags.equals("注册")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone",phone);
            map.put("codeto","2");
            persenterimpl.posthttp(UrlCount.URL_Dx1,map, DxBean.class);
            showProgress(this, "加载中...");
        }else  if (flags.equals("绑定手机")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone",phone);
            map.put("codeto","2");
            persenterimpl.posthttp(UrlCount.URL_Dx1,map, DxBean.class);
            showProgress(this, "加载中...");

        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone",phone);
            map.put("codeto","2");
            persenterimpl.posthttp(UrlCount.URL_Dx,map, DxBean.class);
            showProgress(this, "加载中...");
        }


    }

    @Override
    public void sucecess(Object o) {
        dismissProgress(this);
        if (o instanceof DxBean) {
            DxBean dxBean = (DxBean) o;
            codeId = dxBean.getCodeId();
            if (dxBean.getCode()==0)
            {//开始计时
               // time.start();
                Toast.makeText(this, "已发送验证码", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PhoneActivity.this,Note3Activity.class);
                    intent.putExtra("phone",phone);
                    intent.putExtra("codeId",dxBean.getCodeId());
                    intent.putExtra("flags",flags);
                    startActivity(intent);
                    finish();
            }else {
                Toast.makeText(this, dxBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
