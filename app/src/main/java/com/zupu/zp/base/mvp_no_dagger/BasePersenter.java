package com.zupu.zp.base.mvp_no_dagger;


import com.google.gson.Gson;
import com.zupu.zp.base.mvp.IcontClass;
import com.zupu.zp.base.mvp.ModelImpl;
import com.zupu.zp.bean.ZpBean;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:10
 * update: $date$
 */
public abstract class BasePersenter implements IcontClass.Ipersenter {
    private IcontClass.Iview iview;
    private IcontClass.Imodel imodel;
    public abstract void gethttp(String url, Map<String, Object> map, final Class cls);
    public abstract void posthttp(String url, Map<String, Object> map, final Class cls);
    public abstract void deletehttp(String url, Map<String, Object> map, final Class cls);

    public abstract void postpublishs(String url, Map<String, Object> map, final Class cls);

    public abstract void puthttp(String url,File file, final Class cls);
    public abstract void sendmessgehttp(String url, Map<String, Object> map, MultipartBody part, final Class cls);
    public abstract void sendPicp1(String url,List<String> list, final Class cls);
    @Override
    public void onAtach(IcontClass.Iview view) {
        iview=view;
        imodel=new ModelImpl();
    }



    @Override
    public void getp(String url, Map<String, Object> map, final Class cls) {
        imodel.getm(url, map, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                iview.sucecess(o);
            }
        });
    }

    @Override
    public void postp(String url, Map<String, Object> map, final Class cls) {
        imodel.postm(url, map, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                if (o instanceof ZpBean){
                    EventBus.getDefault().post(json);
                }
                iview.sucecess(o);
            }
        });
    }

    @Override
    public void postpublishp(String url, Map<String, Object> map, final Class cls) {
        imodel.postpublishm(url, map, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json,cls);
                iview.sucecess(o);
            }
        });
    }

    @Override
    public void deletep(String url, Map<String, Object> map, final Class cls) {
        imodel.deletem(url, map, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                iview.sucecess(o);
            }
        });
    }
    @Override
    public void putp(String url, File file, final Class cls) { imodel.putm(url,file, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                iview.sucecess(o);
            }
        });
    }

    @Override
    public void sendMessgeP(String url, Map<String,Object> map, MultipartBody part, final Class cls) {
        imodel.sendMessgeM(url, map,part, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                iview.sucecess(o);
            }
        });
    }

    @Override
    public void sendPicp(String url, List<String> list, final Class cls) {
        imodel.sendPicm(url,list, new IcontClass.CallBackss() {
            @Override
            public void sucecess(String json) {
                Gson gson = new Gson();
                Object o = gson.fromJson(json, cls);
                iview.sucecess(o);
            }
        });
    }
    @Override
    public void onDtach() {
        iview=null;
        imodel=null;
    }
}
