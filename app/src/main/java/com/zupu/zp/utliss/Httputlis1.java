package com.zupu.zp.utliss;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.zupu.zp.entity.ZegoApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/9/27 20:21
 * update: $date$
 */
public class Httputlis1 {
    RxApi rxapi;
    private Cache mCache;
    OkHttpClient okHttpClient;
    public Httputlis1() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //缓存
        File file = new File(Environment.getExternalStorageDirectory() + "/zpcach");
        if (!file.exists()) {
            file.mkdir();
        }
    //    mCache = new Cache(file, 1024 * 1024 * 20);
         okHttpClient = new OkHttpClient.Builder()
              /*   .addInterceptor(interceptor).addInterceptor(new Interceptor() {
                     @Override
                     public Response intercept(Chain chain) throws IOException {
                         //先用死的     做登录模块的换成这个
                         Request request = chain.request().newBuilder()
                                 .addHeader("Content-lenth", "5000")
                                 *//*.cacheControl(new CacheControl.Builder().maxAge(60*60*20,TimeUnit.SECONDS).build())*//*
                                 .build();
                         return chain.proceed(request);
                     }
                 })*/
                 .connectTimeout(30, TimeUnit.SECONDS)
                 .readTimeout(30, TimeUnit.SECONDS)
                 .addInterceptor(interceptor)
                /* .addInterceptor(new CacheInterceptor())//也就这里不同
                 .addNetworkInterceptor(new CacheInterceptor())//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
                 .cache(mCache)*/
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlCount.Base_Head).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
        rxapi = retrofit.create(RxApi.class);



    }

    private static class HttputlisHouder {
        private static final Httputlis1 httputlis = new Httputlis1();
    }

    public static Httputlis1 getInstance() {
        return HttputlisHouder.httputlis;
    }

    public void gethttp(String url, Map<String, Object> map, final Mycallbacks mycallbacks) {
        rxapi.getinfo(url, map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError:微信 "+e.getMessage() );
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    mycallbacks.sucess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    //POST
    public void  posthttps(String url, Map<String,Object> map, final Mycallbacks mycallbacks){
        rxapi.postinfo(url, map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "posthttps: "+e.getMessage() );
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    mycallbacks.sucess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void uploadPic(String url,File file, final Mycallbacks mycallbacks){

    final MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        RequestBody body=RequestBody.create(mediaType,file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(),body);
        Log.e("TAG", "uploadPic: "+file.toString() );
        rxapi.uploadPic(url,part)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: "+e.getMessage() );
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    mycallbacks.sucess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void send_photo(String url , List<String> list, final Mycallbacks mycallbacks){


    /*    final MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        RequestBody body=RequestBody.create(mediaType,file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(),body);
        Log.e("TAG", "uploadPic: "+file.toString() );
    */

        Map<String,RequestBody> params = new HashMap<>();

         HashMap<String, MultipartBody.Part> maps = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            File file=new File(list.get(i));
            RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part part=MultipartBody.Part.createFormData("picture"+i,file.getName(),body);
            params.put("picture\";filename=\"" + file.getName(), body);
        }

        rxapi.send_pircture(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                            Log.e("TAG", "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            mycallbacks.sucess(responseBody.string());
                        } catch (Exception e) {
                        }
                    }
                });

    }



    /*public void send_photo(String url , List<String> list, final Mycallbacks mycallbacks){
        *//*
        *   final MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");
        RequestBody body=RequestBody.create(mediaType,file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(),body);
        Log.e("TAG", "uploadPic: "+file.toString() );
        * *//*

        Map<String,RequestBody> params = new HashMap<>();

        HashMap<String, MultipartBody.Part> maps = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            File file=new File(list.get(i));
            RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part part=MultipartBody.Part.createFormData("picture"+i,file.getName(),body);
            params.put("picture\";filename=\"" + file.getName(), body);
        }

        rxapi.send_pircture(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("TAG", "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            mycallbacks.sucess(responseBody.string());
                        } catch (Exception e) {
                        }
                    }
                });

    }*/

    public interface Mycallbacks {
        void sucess(String json);
    }




}
