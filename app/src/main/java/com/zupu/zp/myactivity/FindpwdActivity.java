package com.zupu.zp.myactivity;

import android.content.Intent;
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
import com.zupu.zp.bean.DxBean;
import com.zupu.zp.bean.GPwdbean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.utliss.TimeCount;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.zz;

import java.util.HashMap;

public class FindpwdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backs;
    private RelativeLayout retitle;
    private TextView titletext;
    private EditText ed_phone;
    private EditText ed_pwd;
    private Button wang;
    private EditText ed_newpwd;
    private LinearLayout liner;
    private Button start_loging;
    private TimeCount time;
    int codeId ;
    String newpwd;
    String phone;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wang:
                String phone = ed_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, R.string.qsrphone, Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("phone",phone);
                map.put("codeto","3");
                persenterimpl.posthttp(UrlCount.URL_Dx,map, DxBean.class);
                break;
            case R.id.start_loging:
                submit();
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

        String pwd = ed_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, R.string.qsrdtpwd, Toast.LENGTH_SHORT).show();
            return;
        }

         newpwd = ed_newpwd.getText().toString().trim();
        if (TextUtils.isEmpty(newpwd)) {
            Toast.makeText(this, R.string.qsrpwd, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!zz.isMobileNO(phone)) {
            Toast.makeText(getApplicationContext(), R.string.zhgscw, Toast.LENGTH_SHORT).show();
            return;
        }
  /*  if (!zz.isPasswordNO(newpwd)) {
            Toast.makeText(getApplicationContext(), "密码格式错误请输入6-18位密码", Toast.LENGTH_SHORT).show();
            return;
        }*/
        // TODO validate success, do something

            HashMap<String, Object> map = new HashMap<>();
            map.put("codeId",codeId);
            map.put("putyzm",pwd);
            persenterimpl.posthttp(UrlCount.URL_DtLoging,map, PhoneBean.class);


    }

    @Override
    public int initlayout() {
        return R.layout.activity_findpwd;
    }

    @Override
    public void initview() {
        backs = (ImageView) findViewById(R.id.backs);
        retitle = (RelativeLayout) findViewById(R.id.retitle);
        titletext = (TextView) findViewById(R.id.titletext);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        wang = (Button) findViewById(R.id.wang);
        ed_newpwd = (EditText) findViewById(R.id.ed_newpwd);
        liner = (LinearLayout) findViewById(R.id.liner);
        start_loging = (Button) findViewById(R.id.start_loging);

        wang.setOnClickListener(this);
        start_loging.setOnClickListener(this);
        time = new TimeCount(60000,1000,wang);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void initlisenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {
        if (o instanceof DxBean) {
            DxBean dxBean = (DxBean) o;
            codeId = dxBean.getCodeId();
            if (dxBean.getCode()==0)
            {//开始计时
                time.start();
            }else {
                Toast.makeText(this, dxBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        if (o instanceof PhoneBean) {
            PhoneBean phoneBean = (PhoneBean) o;
            int code = phoneBean.getCode();
            if (code==0){
                HashMap<String, Object> map = new HashMap<>();
                map.put("phone",phone);
                map.put("pwd",newpwd);
                persenterimpl.posthttp(UrlCount.URL_Gpwd,map, GPwdbean.class);

            }else {
                Toast.makeText(this, phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
         //改密
        if (o instanceof GPwdbean) {
            GPwdbean gPwdbean = (GPwdbean) o;
            if (gPwdbean.getCode()==0){
                Toast.makeText(this, R.string.xgcg, Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, R.string.xgsb, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
