package com.zupu.zp.wxapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zupu.zp.MainActivity;
import com.zupu.zp.bean.GPwdbean;
import com.zupu.zp.bean.Main2Activity;
import com.zupu.zp.bean.TokenBean;
import com.zupu.zp.bean.WxUserbean;
import com.zupu.zp.bean.Wxbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.myactivity.PhoneActivity;
import com.zupu.zp.utliss.HttpUtlis;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.zupu.zp.entity.ZegoApplication.wxBds;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/9/9 15:08
 * update: $date$
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private Integer a;
    private IWXAPI iwxapi;
    private String unionid;
    //private String openid;
    private static String APP_ID = "wx3c8adc40cdcd29e6";
    private static String SECRET="a3caab5c3d58a102417a8591dba5392b";
   // String refresh_token;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String Openid;
    Httputlis1 instance;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       getSupportActionBar().hide();
         instance = Httputlis1.getInstance();
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
//        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e("onReq", "onError");
    }
    //成功的回调
    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(baseResp.getType()==ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){
                  //  Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    final String code = ((SendAuth.Resp) baseResp).code;
                    //得到token
                    getResult(code);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }


  /*  @Override
    public void getSuccessData(Object data) {
        if (data instanceof WXBean) {
            WXBean bean = (WXBean) data;
            if (bean != null && bean.getStatus().equals("0000")) {
                Log.e("WXBean", bean.toString());
            }
        }
    }*/
/*

    @Override
    public void getErrorData(String error) {
        ToastUtils.show(this, error);
    }




  /*      if (WXUtils.success(this)) {//请求第三方登录
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wx_login_duzun";
        WXUtils.reg(this).sendReq(req);
    }*/
  //请求token的值
  private void getResult(final String code) {
      final String Url="https://api.weixin.qq.com/sns/oauth2/access_token";
      new Thread(){
          @Override
          public void run() {
              super.run();
              try {
                  URL url = new URL(Url);
                  //建立连接
                  HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                  //设置方法
                  urlConnection.setRequestMethod("GET");
                  //可读
                  urlConnection.setDoInput(true);
                  //可写
                  urlConnection.setDoOutput(true);
                  //参数拼接
                  String parmes="appid="+ URLEncoder.encode(APP_ID,"UTF-8")+"&secret="+URLEncoder.encode(SECRET,"UTF-8")
                          +"&code="+URLEncoder.encode(code,"UTF-8")+"&grant_type="+URLEncoder.encode("authorization_code","UTF-8");
                  OutputStream outputStream = urlConnection.getOutputStream();
                  //参数写入
                  outputStream.write(parmes.getBytes());
                  //刷新
                  outputStream.flush();
                  //关闭
                  outputStream.close();
                  //得到请求码
                  int responseCode = urlConnection.getResponseCode();
                  if (responseCode==200){
                      InputStream inputStream = urlConnection.getInputStream();
                      int len=-1;
                      byte[] bytes = new byte[1024];
                      StringBuffer stringBuffer = new StringBuffer();
                      while ((len=inputStream.read(bytes))!=-1){
                          String s = new String(bytes,0,len);
                          stringBuffer.append(s);
                      }
                      String json = stringBuffer.toString();
                      Log.e("TAG2000000000000000",json );

                      Gson gson = new Gson();
                      TokenBean tokenBean = gson.fromJson(json, TokenBean.class);

                      //用户注册
                       getuser(tokenBean.getAccess_token(),tokenBean.getOpenid());


                    /*  refresh_token = tokenBean.getRefresh_token();
                      openid = tokenBean.getOpenid();*/
                  }


              } catch (Exception e) {
                  e.printStackTrace();
              }


          }
      }.start();

  }
    private void   getuser(final String a, final String b) {
        final String Url="https://api.weixin.qq.com/sns/userinfo";
        final String aaa;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(Url);
                    //建立连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置方法
                    urlConnection.setRequestMethod("GET");
                    //可读
                    urlConnection.setDoInput(true);
                    //可写
                    urlConnection.setDoOutput(true);
                    //参数拼接
                    Log.e("TAG11111", "run: "+a+"???"+b );
                    String parmes="access_token="+ URLEncoder.encode(a,"UTF-8")+"&openid="+URLEncoder.encode(b,"UTF-8");
                    OutputStream outputStream = urlConnection.getOutputStream();
                    //参数写入
                    outputStream.write(parmes.getBytes());
                    //刷新
                    outputStream.flush();
                    //关闭
                    outputStream.close();
                    //得到请求码
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        int len=-1;
                        byte[] bytes = new byte[1024];
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((len=inputStream.read(bytes))!=-1){
                            String s = new String(bytes,0,len);
                            stringBuffer.append(s);
                        }
                        String json = stringBuffer.toString();
                        Log.e("TAG2000000000000000",json );
                        Gson gson = new Gson();
                        WxUserbean wxUserbean = gson.fromJson(json, WxUserbean.class);
                        editor.putString("userId",wxUserbean.getUnionid()+"");
                        editor.putString("openid",wxUserbean.getOpenid()+"");
                        editor.putString("photoUrl",wxUserbean.getHeadimgurl()+"");
                        editor.putString("nickName",wxUserbean.getNickname()+"");
                        editor.putString("sex",wxUserbean.getSex()+"");
                        editor.commit();
                       if (wxBds==789){
                            Log.e("测试00", json );
                            EventBus.getDefault().post(json+"");
                            wxBds=0;
                            finish();
                        }
                        PushManager instance1 = PushManager.getInstance();
                        String clientid = instance1.getClientid(WXEntryActivity.this);
                        Map<String, Object> map = new HashMap();
                        map.put("openId", sharedPreferences.getString("openid",null));
                        map.put("pushCid",clientid);
                        map.put("loginSystemType","2");
                        instance.posthttps(UrlCount.URL_WXDl, map, new Httputlis1.Mycallbacks() {
                            @Override
                            public void sucess(String json) {
                                Log.e("TAG", "onResp:666 "+json );
                                Gson gson = new Gson();
                                Wxbean wxbean = gson.fromJson(json, Wxbean.class);
                                Wxbean.UserBean user = wxbean.getUser();
                                if (wxbean.getCode()==0){
                                    if(user==null&&wxBds!=789&&wxBds!=0){
                                        Intent intent = new Intent(WXEntryActivity.this, PhoneActivity.class);
                                        intent.putExtra("flags","绑定手机");
                                        startActivity(intent);
                                        finish();
                                    }else if (wxBds!=789&&wxBds!=0){
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
                                        editor.commit();
                                        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                                        EventBus.getDefault().post(1215);
                                        finish();
                                    }
                                }
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

}

