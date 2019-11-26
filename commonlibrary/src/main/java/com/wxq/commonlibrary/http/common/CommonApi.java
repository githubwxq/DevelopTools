package com.wxq.commonlibrary.http.common;

import com.wxq.commonlibrary.model.BaiduResule;
import com.wxq.commonlibrary.model.KlookResponseData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface CommonApi {

    @POST("/app/login/login")
    Call<KlookResponseData<String>> login(@Body HashMap<String,String> data);


    /**
     * 根据经纬度获取百度poi相关信息
     * @param url
     * @return
     */
    @POST
    Call<BaiduResule> getPoiDetail(@Url String url);




}
