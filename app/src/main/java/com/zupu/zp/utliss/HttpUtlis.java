package com.zupu.zp.utliss;

import android.content.SharedPreferences;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
 * date: 2019/7/30 15:18
 * update: $date$
 */
public class HttpUtlis {
    RxApi rxApi;
    private Cache mCache;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private HttpUtlis() {
      //  SharedPreferences loging = BaseApplication.Loging();
      //  sharedPreferences=loging;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //缓存
        File file = new File(Environment.getExternalStorageDirectory() + "/memeda");
        if (!file.exists()) {
            file.mkdir();
        }
        mCache = new Cache(file, 1024 * 1024 * 20);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
                           /*
                        addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //先用死的     做登录模块的换成这个
                        Request request = chain.request().newBuilder().addHeader("userId", sharedPreferences.getString("userId","0"))
                                .addHeader("sessionId", sharedPreferences.getString("sessionId","0"))
                                *//*.cacheControl(new CacheControl.Builder().maxAge(60*60*20,TimeUnit.SECONDS).build())*//*
                                .build();
//                                .addHeader("userId", "3406").addHeader("sessionId", "15644672160273406").build();
                        return chain.proceed(request);
                    }
                })*/
                .addInterceptor(new CacheInterceptor())//也就这里不同
                .addNetworkInterceptor(new CacheInterceptor())//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
                .cache(mCache)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlCount.Base_Head).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient). build();
        rxApi = retrofit.create(RxApi.class);


    }

    private static class HttpUtlisHolder {
        private static final HttpUtlis httpUtlis = new HttpUtlis();
    }

    public static HttpUtlis getInstance() {
        return HttpUtlisHolder.httpUtlis;
    }
    //GET
   public void  gethttps(String url, Map<String,Object> map, final MyCllBacks myCllBacks){
         rxApi.getinfo(url,map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
             @Override
             public void onCompleted() {

             }

             @Override
             public void onError(Throwable e) {

             }

             @Override
             public void onNext(ResponseBody responseBody) {
                 try {
                     myCllBacks.sucesess(responseBody.string());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });

   }


    //POST
    public void  posthttps(String url, Map<String,Object> map, final MyCllBacks myCllBacks){
        rxApi.postinfo(url,map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    myCllBacks.sucesess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void publish_sick(String url, Map<String,Object> map, final MyCllBacks myCllBacks){
        rxApi.publish_sick(url,map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    myCllBacks.sucesess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //UpImg
    public void  UpImg(String url, Map<String,Object> map, MultipartBody file, final MyCllBacks myCllBacks){
        rxApi.upimg(url,map,file).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    myCllBacks.sucesess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    //DELETE
    public void  Deletehttps(String url, Map<String,Object> map, final MyCllBacks myCllBacks){
        rxApi.deleteinfo(url,map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    myCllBacks.sucesess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    //PUT
    public void  Puthttps(String url, Map<String,Object> map, final MyCllBacks myCllBacks){
        rxApi.putinfo(url,map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    myCllBacks.sucesess(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void sendMessgeH(Map<String,Object> map,String url, MultipartBody part, final MyCllBacks myCllBacks){
        rxApi.upimg(url,map,part).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    myCllBacks.sucesess(responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface MyCllBacks{
        void sucesess(String json);
    }

    /**
     * 一、无论有无网路都添加缓存。
     * 目前的情况是我们这个要addNetworkInterceptor
     * 这样才有效。经过本人测试（chan）测试有效.
     * 60S后如果没有网络将获取不到数据，显示连接失败
     */
    static Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
          /*String cacheControl = request.header("Cache-Control");
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60";
            }*/
            int maxAge = 60;
            return response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    };
}

