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
import com.zupu.zp.bean.DxBean;
import com.zupu.zp.bean.GPwdbean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.TimeCount;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.zz;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ImageView backs;
    private RelativeLayout retitle;
    private TextView titletext;
    private EditText ed_phone;
    private EditText ed_pwd;
    private Button start_loging;
    private TimeCount time;
    String flags;
    TextView phone_login;
    String phone;
    @Override
    public int initlayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initview() {

        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
        backs = (ImageView) findViewById(R.id.backs);
        retitle = (RelativeLayout) findViewById(R.id.retitle);
        titletext = (TextView) findViewById(R.id.titletext);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        start_loging = (Button) findViewById(R.id.start_loging);
        phone_login=findViewById(R.id.phone_login);
        start_loging.setOnClickListener(this);

    }

    @Override
    public void initdata() {
        Intent intent = getIntent();
        phone=intent.getStringExtra("phone");
        flags=intent.getStringExtra("flags");
        phone_login.setText(flags);
    }

    @Override
    public void initlisenter() {
        //返回上层
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

        String phone1 = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = ed_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入第二次密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!zz.isPasswordNO(phone1)) {
            Toast.makeText(getApplicationContext(), "密码格式错误请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!zz.isPasswordNO(pwd)) {
            Toast.makeText(getApplicationContext(), "密码格式错误请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone1.equals(pwd)){
            Toast.makeText(getApplicationContext(), "请确认两次输入密码是否一致", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        if (flags.equals("注册")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone",phone);
            map.put("pwd",pwd);
            persenterimpl.posthttp(UrlCount.URL_Rigist,map, GPwdbean.class);
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("phone",phone);
            map.put("pwd",pwd);
            persenterimpl.posthttp(UrlCount.URL_Gpwd,map, PhoneBean.class);
        }

    }

    @Override
    public void sucecess(Object o) {

        if (o instanceof PhoneBean) {
            PhoneBean phoneBean= (PhoneBean)o;
            if (phoneBean.getCode()==0)
            {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }
        //注册或改密结果
        if (o instanceof GPwdbean) {
            GPwdbean gPwdbean = (GPwdbean) o;
            if (gPwdbean.getCode()==0){
                GPwdbean.UserBean user = gPwdbean.getUser();
                if (flags.equals("注册")){
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    editor.putString("userId", user.getId()+"");
                    editor.putString("appLoginIdentity",user.getAppLoginIdentity()+"");
                    editor.putString("photoUrl",user.getPhotoUrl()+"");
                    editor.putString("nickName",user.getNickName()+"");
                    editor.putString("pushCid",user.getPushCid());
                    editor.putString("is_certification",user.getIsCertification());
                    editor.putString("integral",user.getIntegral()+"");
                    editor.putString("birthday",user.getBirthday());
                    editor.putString("anchorStatus",user.getAnchorStatus());
                    editor.putString("phone",user.getPhone());
                    Log.e("TAG", ">>>>>>>>>>>>>>>>"+user.getIntegral()+"" );
                    editor.commit();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                finish();
            }else {
                Toast.makeText(this,gPwdbean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
