package com.wxq.commonlibrary.http.common;

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



    @POST
    Call<String> getPoiDetail(@Url String url);




}
