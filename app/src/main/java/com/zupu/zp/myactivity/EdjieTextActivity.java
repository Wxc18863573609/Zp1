package com.zupu.zp.myactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.WxUserbean;
import com.zupu.zp.bean.Wxbean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.UrlCount;
import com.zupu.zp.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

public class EdjieTextActivity extends BaseActivity {
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
               switch (msg.what){
                   case 1:
                       Toast.makeText(EdjieTextActivity.this, R.string.bccg, Toast.LENGTH_SHORT).show();
                       EventBus.getDefault().post(460);
                       finish();
                       break;
                   case 2:
                       Toast.makeText(EdjieTextActivity.this, R.string.bcsb, Toast.LENGTH_SHORT).show();
                       break;

               }

        }
    };
    SharedPreferences sharedPreferences;
    private ImageView backss;
    private TextView titlename;
    private View views1;
    private RelativeLayout title;
    private EditText ed_jieshao;
    private Button btnbc;
    String type;
   int flag;
    String content;
   String treeid;
    @Override
    public int initlayout() {
        return R.layout.activity_edjietext;
    }

    @Override
    public void initview() {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        backss = (ImageView) findViewById(R.id.backss);
        titlename = (TextView) findViewById(R.id.titlename);
        views1 = (View) findViewById(R.id.views1);
        title = (RelativeLayout) findViewById(R.id.title);
        ed_jieshao = (EditText) findViewById(R.id.ed_jieshao);
        btnbc = (Button) findViewById(R.id.btnbc);
        Intent intent = getIntent();
        content=intent.getStringExtra("content");
        treeid = intent.getStringExtra("treeid");
        flag = intent.getIntExtra("flag", 0);
        type = intent.getStringExtra("type");
        String textwb = intent.getStringExtra("textwb");
        if (content!=null&&!content.equals("")&&!content.equals("未录入")){
            ed_jieshao.setText(content);
        }

    }

    @Override
    public void initdata() {
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initlisenter() {
          //保存按钮
        btnbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("jieshao",ed_jieshao.getText().toString());     //把获取的值传到第一个页面
                setResult(RESULT_OK,intent);
                if (flag==2){
             /*       HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("editorialBoard",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);
*/
                    getsave("editorialBoard",ed_jieshao.getText().toString());
                }else if (flag==3){
                  /*  HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("preface",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("preface",ed_jieshao.getText().toString());
                }else if (flag==4){
                 /*   HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("surnameSources",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("surnameSources",ed_jieshao.getText().toString());
                }else if (flag==5){
                   /* HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("familyDiscipline",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("familyDiscipline",ed_jieshao.getText().toString());
                }else if (flag==6){
             /*       HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("biography",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("biography",ed_jieshao.getText().toString());
                }else if (flag==7){
                  /*  HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("greatChronicle",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("greatChronicle",ed_jieshao.getText().toString());
                }else if (flag==8){
                    /*HashMap<String, Object> map = new HashMap<>();
                    map.put("uuid",sharedPreferences.getString("appLoginIdentity",null));
                    map.put("id",treeid);
                    map.put("epilogue",ed_jieshao.getText().toString());
                    persenterimpl.posthttp(UrlCount.URL_SeveFm,map, PhoneBean.class);*/
                    getsave("epilogue",ed_jieshao.getText().toString());
                } }
        });
    }

    @Override
    public void persenter() {

    }


    @Override
    public void sucecess(Object o) {
       /* if (o instanceof PhoneBean){
            PhoneBean phoneBean=(PhoneBean)o;
            if (phoneBean.getCode()==0){
                EventBus.getDefault().post(460);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }*/
    }




    private void   getsave( String a, String b) {
         String Url=null;
        if (type.equals("1"))
        {
               Url=UrlCount.Base_Head+"zuPuFamily/setFamilyBook";
        }else if (type.equals("2")){
              Url=UrlCount.Base_Head+"zuPuFamily/setGenealogy";
        }else {
               Url=null;
        }

        String finalUrl = Url;
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Log.e("请求", a+"" );
                    URL url = new URL(finalUrl);
                    //建立连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置方法
                    urlConnection.setRequestMethod("POST");
                    //可读
                    urlConnection.setDoInput(true);
                    //可写
                    urlConnection.setDoOutput(true);
                    //参数拼接
                    String parmes="uuid="+ URLEncoder.encode(sharedPreferences.getString("appLoginIdentity",null),"UTF-8")+"&id="+URLEncoder.encode(treeid,"UTF-8")
                            +"&"+a+"="+URLEncoder.encode(b,"UTF-8");
                    OutputStream outputStream = urlConnection.getOutputStream();
                    //参数写入
                    outputStream.write(parmes.getBytes());
                    //刷新
                    outputStream.flush();
                    //关闭
                    outputStream.close();
                    //得到请求码
                    int responseCode = urlConnection.getResponseCode();
                    Log.e("请求",responseCode+"" );
                    if (responseCode==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        int len=-1;
                        byte[] bytes = new byte[1024];
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((len=inputStream.read(bytes))!=-1){
                            String s = new String(bytes,0,len);
                            stringBuffer.append(s);
                        }
                        Message message = new Message();
                        message.what=1;
                        handler.sendMessage(message);
                    }else{
                        Message message = new Message();
                        message.what=2;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }
}
