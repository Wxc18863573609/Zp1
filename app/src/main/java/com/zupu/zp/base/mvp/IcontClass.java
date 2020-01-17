package com.zupu.zp.base.mvp;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:06
 * update: $date$
 */
public interface IcontClass {
    public interface Iview<T>{
        void sucecess(T t);
    }
    public interface Ipersenter{
        void onAtach(Iview view);
        void getp(String url, Map<String, Object> map, Class cls);
        void postp(String url, Map<String, Object> map, Class cls);
        void postpublishp(String url, Map<String, Object> map, Class cls);
        void deletep(String url, Map<String, Object> map, Class cls);
        void putp(String url, File file, Class cls);
        void sendMessgeP(String url, Map<String, Object> map, MultipartBody part, Class cls);
        void sendPicp(String url, List<String> list, Class cls);
        void onDtach();
    }
    public interface Imodel{
        void getm(String url, Map<String, Object> map, CallBackss callBackss);
        void postm(String url, Map<String, Object> map, CallBackss callBackss);
        void postpublishm(String url, Map<String, Object> map, CallBackss callBackss);
        void deletem(String url, Map<String, Object> map, CallBackss callBackss);
        void putm(String url,File file, CallBackss callBackss);
        void sendMessgeM(String url, Map<String, Object> map, MultipartBody part, CallBackss callBackss);
        void sendPicm(String url,List<String> list, CallBackss callBackss);
    }
    public interface CallBackss{
        void sucecess(String json);
    }

}
