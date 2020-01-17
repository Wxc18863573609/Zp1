package com.zupu.zp.myactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.igexin.sdk.PushManager;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.CreatuserBean;
import com.zupu.zp.bean.DlogingBean;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.weight.VerifyCodeView;
import com.zupu.zp.wxapi.WXEntryActivity;

import java.util.HashMap;

public class Note3Activity extends BaseActivity {
    VerifyCodeView verifyCodeView;
    String note = null, phone;
    int codeId;
    Button start_loging;
    boolean boo = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String flags;

    @Override
    public int initlayout() {
        return R.layout.activity_note3;
    }

    @Override
    public void initview() {
        verifyCodeView = (VerifyCodeView) findViewById(R.id.verify_code_view);
        start_loging = findViewById(R.id.start_loging);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor = sharedPreferences.edit();
    }

    @Override
    public void initdata() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        codeId = intent.getIntExtra("codeId", 0);
        flags = intent.getStringExtra("flags");


        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                note = verifyCodeView.getEditContent();
                boo = true;

            }

            @Override
            public void invalidContent() {
                boo = false;
            }
        });
    }

    @Override
    public void initlisenter() {
        //下一步
        start_loging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boo) {
                    Toast.makeText(Note3Activity.this, R.string.qsrpwd, Toast.LENGTH_SHORT).show();
                } else {
                    if (isFastClick()){
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("codeId", codeId);
                        map.put("putyzm", note);
                        persenterimpl.posthttp(UrlCount.URL_DtLoging, map, PhoneBean.class);
                    }


                }

            }
        });

    }

    @Override
    public void persenter() {


    }


    @Override
    public void sucecess(Object o) {
        if (o instanceof PhoneBean) {
            PhoneBean phoneBean = (PhoneBean) o;
            int code = phoneBean.getCode();
            if (code == 0) {
                //  startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                if (flags.equals("注册")||flags.equals("修改密码")) {
                    Intent intent = new Intent(Note3Activity.this,RegisterActivity.class);
                    intent.putExtra("phone",phone);
                    intent.putExtra("flags",flags);
                    startActivity(intent);
                    finish();
                } else if (flags.equals("绑定手机")){
                    String openid = sharedPreferences.getString("openid", null);
                    String photoUrl = sharedPreferences.getString("photoUrl", null);
                    String nickName = sharedPreferences.getString("nickName", null);
                    String sex = sharedPreferences.getString("sex", null);
                    PushManager instance = PushManager.getInstance();
                    String clientid = instance.getClientid(Note3Activity.this);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("nickName", nickName);
                    map.put("realName", "");
                    map.put("photoUrl", photoUrl);
                    map.put("gender", sex);
                    map.put("phone", phone);
                    map.put("role", "4");
                    map.put("wechatLoginIdentity", openid);
                    map.put("pushCid", clientid);
                    map.put("loginSystemType", "2");
                    persenterimpl.posthttp(UrlCount.URL_SaveUser, map, CreatuserBean.class);
                } else {
                    PushManager instance = PushManager.getInstance();
                    String clientid = instance.getClientid(this);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("phone", phone);
                    map.put("pushCid", clientid);
                    map.put("loginSystemType", "2");
                    persenterimpl.posthttp(UrlCount.URL_DLoging, map, DlogingBean.class);
                }
            } else {
                Toast.makeText(this, phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        if (o instanceof CreatuserBean) {
            CreatuserBean creatuserBean = (CreatuserBean) o;
            if(creatuserBean.getCode()==0){
                editor.putString("appLoginIdentity",creatuserBean.getUuid());
                editor.commit();
                startActivity(new Intent(Note3Activity.this,MainActivity.class));
                finish();
            }
        }

        if (o instanceof DlogingBean) {
            DlogingBean dlogingBean = (DlogingBean) o;
            DlogingBean.UserBean user = dlogingBean.getUser();
            if (dlogingBean.getCode() == 0) {
                PushManager instance = PushManager.getInstance();
                String clientid = instance.getClientid(this);
                editor.putString("userId", user.getId() + "");
                editor.putString("appLoginIdentity", user.getAppLoginIdentity() + "");
                editor.putString("photoUrl", user.getPhotoUrl() + "");
                editor.putString("nickName", user.getNickName() + "");
                editor.putString("pushCid", clientid);
                editor.putString("is_certification",user.getIsCertification());
                editor.putString("integral",user.getIntegral()+"");
                editor.putString("birthday",user.getBirthday());
                editor.putString("anchorStatus",user.getAnchorStatus());
                editor.putString("phone",user.getPhone());
                editor.commit();
                Toast.makeText(this, R.string.logingcg, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Note3Activity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, dlogingBean.getMsg(), Toast.LENGTH_SHORT).show();
            }


        }

    }
}
