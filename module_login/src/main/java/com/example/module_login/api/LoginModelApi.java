package com.example.module_login.api;

import com.wxq.commonlibrary.model.KlookResponseData;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginModelApi {


    @POST("app/login/sendsms/{mobile}")
    Flowable<KlookResponseData<Object>> sendsms(@Path("mobile") String mobile);




}
