package com.wxq.developtools.api;

import com.wxq.developtools.model.HomePageData;
import com.wxq.commonlibrary.model.KlookResponseData;

import io.reactivex.Flowable;
import retrofit2.http.POST;

public interface KlookApi {


    @POST("/app/homePage/homepage")
    Flowable<KlookResponseData<HomePageData>> homepage();

}
