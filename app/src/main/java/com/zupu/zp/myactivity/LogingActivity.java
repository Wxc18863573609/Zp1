package com.zupu.zp.myactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.LiveBean;
import com.zupu.zp.bean.PhoneLoginBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.utliss.WXUtils;
import com.zupu.zp.utliss.zz;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.showProgress;

public class LogingActivity extends BaseActivity implements View.OnClickListener {

    private TextView registtext;
    public  Context mContext;
    private RelativeLayout retitle;
    private TextView titletext;
    private EditText ed_phone;
    private EditText ed_pwd;
    private TextView wang;
    private LinearLayout liner;
    private Button start_loging;
    private ImageView wximg;
    private RelativeLayout wxliner;
    private TextView phone_login;
    private RelativeLayout ks_loging;
    private TextView left;
    private TextView servicexy;
    private TextView he;
    private TextView yszc;
    private ImageView jby;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    public int initlayout() {
        return R.layout.activity_loging;
    }

    @Override
    public void initview() {
        registtext = (TextView) findViewById(R.id.registtext);
        retitle = (RelativeLayout) findViewById(R.id.retitle);
        titletext = (TextView) findViewById(R.id.titletext);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_phone.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        ed_pwd = (EditText) findViewById(R.id.ed_pwd);
        wang = (TextView) findViewById(R.id.wang);
        liner = (LinearLayout) findViewById(R.id.liner);
        start_loging = (Button) findViewById(R.id.start_loging);
        wximg = (ImageView) findViewById(R.id.wximg);
        wxliner = (RelativeLayout) findViewById(R.id.wxliner);
        phone_login = (TextView) findViewById(R.id.phone_login);
        ks_loging = (RelativeLayout) findViewById(R.id.ks_loging);
        left = (TextView) findViewById(R.id.left);
        jby=findViewById(R.id.jby);
        start_loging.setOnClickListener(this);
        mContext=LogingActivity.this;
    }

    @Override
    public void initdata() {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
    }

    @Override
    public void initlisenter() {
        //注册
        registtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogingActivity.this, PhoneActivity.class);
                intent.putExtra("flags","注册");
                startActivity(intent);
                //跳转注册页面
               // startActivity(new Intent(LogingActivity.this,RegisterActivity.class));
            }
        });
        //忘记密码跳转
        wang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogingActivity.this, PhoneActivity.class);
                intent.putExtra("flags","修改密码");
                startActivity(intent);
            }
        });

        //快速登录
        ks_loging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("userId","123");
                editor.putString("appLoginIdentity","zupu-youke-7773962c0fa242114");
                editor.putString("photoUrl","");
                editor.putString("nickName","贾宝玉");
                editor.putString("pushCid",null);
                editor.putString("is_certification",null);
                editor.putString("integral",null);
                editor.putString("birthday",null);
                editor.putString("anchorStatus",null);
                editor.commit();
                startActivity(new Intent(LogingActivity.this, MainActivity.class));
                finish();
            }
        });
        //微信登录
        wxliner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WXUtils.success(LogingActivity.this)) {//请求第三方登录
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wx_login_duzun";
                    WXUtils.reg(LogingActivity.this).sendReq(req);
                }


            }
        });

        //手机动态密码登录
       phone_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(LogingActivity.this, PhoneActivity.class);
               intent.putExtra("flags","短信快捷登录");
               startActivity(intent);
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
        String phone = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.qsrphone, Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = ed_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, R.string.qsrpwd, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!zz.isMobileNO(phone)) {
            Toast.makeText(getApplicationContext(), R.string.zhgscw, Toast.LENGTH_SHORT).show();
            return;
        }
      /*  if (!zz.isPasswordNO(pwd)) {
            Toast.makeText(getApplicationContext(), "密码格式错误请输入6-18位密码", Toast.LENGTH_SHORT).show();
            return;
        }*/
        // TODO validate success, do something

        PushManager instance = PushManager.getInstance();
        String clientid = instance.getClientid(this);
        Log.e("TAG", ">>>>>>>>>>>>>>>>"+clientid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("phoneNum", phone);
        map.put("pwd", pwd);
        map.put("pushCid", clientid+"");
        map.put("loginSystemType", "2");
        persenterimpl.posthttp(UrlCount.Base_Login, map, PhoneLoginBean.class);
        showProgress(mContext, "加载中...");
    }

    @Override
    public void sucecess(Object o) {
        if (o instanceof PhoneLoginBean) {
            dismissProgress(mContext);
            PhoneLoginBean phoneLoginBean = (PhoneLoginBean) o;
            PhoneLoginBean.UserBean user = phoneLoginBean.getUser();
            if (phoneLoginBean.getCode()==0){
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
                Log.e("TAG", ">>>>>>>>>>>>>>>>"+user.getPushCid());
                Log.e("money", user.getIntegral()+"" );
                editor.commit();
                Toast.makeText(this, R.string.logingcg, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogingActivity.this, MainActivity.class));
                finish();
            }else {
                Toast.makeText(this, phoneLoginBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
            dismissProgress(mContext);
            mContext=null;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o ) {
        if (o instanceof Integer) {
            Integer a = (Integer) o;
           if (a==1215){
             finish();
            }
        }

    }
}
