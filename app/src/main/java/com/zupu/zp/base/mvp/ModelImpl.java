package com.zupu.zp.base.mvp;









import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.HttpUtlis;
import com.zupu.zp.utliss.Httputlis1;
import com.zupu.zp.utliss.MyProgressDialog;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;



/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:11
 * update: $date$
 */
public class ModelImpl implements IcontClass.Imodel {

    Httputlis1 instance = Httputlis1.getInstance();
    @Override
    public void getm(String url, Map<String, Object> map, final IcontClass.CallBackss callBackss) {

        instance.gethttp(url, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                callBackss.sucecess(json);
            }
        });

    }

    @Override
    public void postm(String url, Map<String, Object> map, final IcontClass.CallBackss callBackss) {
        Log.e("MM", "加载" );
        instance.posthttps(url, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                callBackss.sucecess(json);
            }
        });
    }

    @Override
    public void postpublishm(String url, Map<String, Object> map, final IcontClass.CallBackss callBackss) {
/*        instance.publish_sick(url, map, new HttpUtlis.MyCllBacks() {
            @Override
            public void sucesess(String json) {
                callBackss.sucecess(json);
            }
        });*/
    }

    @Override
    public void deletem(String url, Map<String, Object> map, final IcontClass.CallBackss callBackss) {
/*        instance.Deletehttps(url, map, new HttpUtlis.MyCllBacks() {
            @Override
            public void sucesess(String json) {
                callBackss.sucecess(json);
            }
        });*/
    }

    @Override
    public void putm(String url, File file, final IcontClass.CallBackss callBackss) {
        instance.uploadPic(url,file, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                callBackss.sucecess(json);
            }
        });
    }

    @Override
    public void sendMessgeM(String url, Map<String, Object> map, MultipartBody part,final IcontClass.CallBackss callBackss ) {
     /*   instance.sendMessgeH(map, url, part, new HttpUtlis.MyCllBacks() {
            @Override
            public void sucesess(String json) {
                callBackss.sucecess(json);
            }
        });*/

    }

    @Override
    public void sendPicm(String url, List<String> list, final IcontClass.CallBackss callBackss) {
        instance.send_photo(url,list, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                callBackss.sucecess(json);
            }
        });
    }

}
