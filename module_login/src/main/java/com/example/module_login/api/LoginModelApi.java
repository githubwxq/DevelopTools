package com.example.module_login.api;

import com.example.module_login.bean.HomePageData;
import com.wxq.commonlibrary.model.KlookResponseData;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginModelApi {


    @POST("app/login/sendsms/{mobile}")
    Flowable<KlookResponseData<Object>> sendsms(@Path("mobile") String mobile);


    @POST("/app/login/register")
    Flowable<KlookResponseData<Object>> register(@Body HashMap<String,String> parmer);



    @POST("/app/homePage/homepage")
    Flowable<KlookResponseData<HomePageData>> homepage();


    @POST("/app/login/login")
    Flowable<KlookResponseData<String>> login(@Body HashMap<String,String> data);






}
