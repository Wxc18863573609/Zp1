package com.zupu.zp.utliss;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:20
 * update: $date$
 */
public interface RxApi {

    //上传
    @Multipart
    @POST
    Observable<ResponseBody> uploadPic(@Url String url, @Part MultipartBody.Part part);
    @GET
    Observable<ResponseBody> getinfo(@Url String url, @QueryMap Map<String, Object> map);
    @POST
    Observable<ResponseBody> postinfo(@Url String url, @QueryMap Map<String, Object> map);
    @POST
    Observable<ResponseBody>upimg(@Url String url, @QueryMap Map<String, Object> map, @Body MultipartBody body);
    @PUT
    Observable<ResponseBody> putinfo(@Url String url, @QueryMap Map<String, Object> map);
    @DELETE
    Observable<ResponseBody> deleteinfo(@Url String url, @QueryMap Map<String, Object> map);

    @Headers({"Content-Type:application/json","Accept:application/json"})
    @POST
    Observable<ResponseBody> publish_sick(@Url String uri, @Body Map<String, Object> map);
   /* @Multipart
    @POST
    Observable<ResponseBody> send_pircture(@Url String url, @PartMap Map<String, RequestBody> params);*/

    @POST
    Observable<ResponseBody> send_pircture(@Url String url, @PartMap Map<String,RequestBody> params);

/*    @Multipart
    @POST
    Observable<ResponseBody> send_pircture(@Url String url, @Query("sickCircleId") int sickCircleId, @PartMap Map<String,RequestBody> params);*/

}
