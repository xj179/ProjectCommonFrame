package com.common.projectcommonframe.api;

import com.common.projectcommonframe.base.BaseResponse;
import com.common.projectcommonframe.entity.Login;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * api service
 * 所有网络请求在这个类当中
 */
public interface ApiService {

    @POST("query")
//    @POST("province-count")
    Observable<BaseResponse<List<Login>>> login(@QueryMap Map<String, Object> map);

    @POST("query")
    Observable<BaseResponse<List<Login>>> logout(@QueryMap Map<String, Object> map);

//    // 登录的请求
//    @POST("loginManage/login")
//    Observable<BaseResponse<Login>> login(@QueryMap Map<String, String> map);

//    //上传图片
//    @POST("file/up")
//    @Multipart
//    Observable<BaseResponse<List<UploadFile>>> upload(@Part List<MultipartBody.Part> parts);

    //请求的接口地址：http://api.avatardata.cn/Weather/Query?key=75bfe88f27a34311a41591291b7191ce&cityname=%E9%95%BF%E6%B2%99
    @GET("Weather/Query?")
    Observable<BaseResponse<List<Login>>> loadWeatherInfo(@Query("key") String key, @Query("cityname") String cityname);


}
