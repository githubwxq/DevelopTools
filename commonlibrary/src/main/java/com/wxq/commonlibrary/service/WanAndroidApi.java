package com.wxq.commonlibrary.service;

import com.wxq.commonlibrary.model.LoginResponse;
import com.wxq.commonlibrary.model.ResponseData;
import com.wxq.commonlibrary.model.User;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/23
 * desc: 玩android自定义api接口返回数据
 * version:1.0
 */
public interface WanAndroidApi {

    @GET("login")
    Flowable<ResponseData<LoginResponse>> getLoginDate();


    @GET("nameList")
    Flowable<ResponseData<List<User>>> getNameList();

}
