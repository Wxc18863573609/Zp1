package com.zupu.zp.base.mvp;


import com.zupu.zp.base.mvp_no_dagger.BasePersenter;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:17
 * update: $date$
 */
public class Persenterimpl extends BasePersenter {
    @Override
    public void gethttp(String url, Map<String, Object> map, final Class cls) {
        getp(url,map,cls);
    }

    @Override
    public void posthttp(String url, Map<String, Object> map, Class cls) {
        postp(url,map,cls);
    }
    @Override
    public void deletehttp(String url, Map<String, Object> map, Class cls) {
        deletep(url,map,cls);
    }

    @Override
    public void puthttp(String url, File file, Class cls) {
        putp(url,file,cls);
    }

    @Override
    public void sendmessgehttp(String url, Map<String, Object> map, MultipartBody part, Class cls) {
        sendMessgeP(url,map,part,cls);
    }

    @Override
    public void postpublishs(String url, Map<String, Object> map, Class cls) {
        postpublishp(url,map,cls);
    }


    @Override
    public void sendPicp1(String url, List<String> list, Class cls) {
       sendPicp(url,list,cls);
    }
}
