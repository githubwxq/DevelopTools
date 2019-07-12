package com.wxq.developtools.api;

import com.wxq.commonlibrary.model.KlookResponseData;
import com.wxq.developtools.model.AreaAndCountry;
import com.wxq.developtools.model.CollectionBean;
import com.wxq.developtools.model.HomePageData;
import com.wxq.developtools.model.Region;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KlookApi {


    @POST("/app/homePage/homepage")
    Flowable<KlookResponseData<HomePageData>> homepage();


    @POST("/app/location/findAllRegion")
    Flowable<KlookResponseData<List<Region>>> findAllRegion();


    @POST("/app/location/findCountryAndCity")
    Flowable<KlookResponseData<List<AreaAndCountry>>> findCountryAndCity(@Body HashMap<String,String> data);


    @POST("/app/collect/pageCollectProduct/{page}/{rows}")
    Flowable<KlookResponseData<List<CollectionBean>>> pageCollectProduct(@Path("page")int page, @Path("rows")int rows);



    @POST("/app/collect/delete/{productId}")
    Flowable<KlookResponseData<Object>> deleteProduct(@Path("productId")String productId);


    @POST("/app/collect/save/{productId}")
    Flowable<KlookResponseData<Object>> saveProduct(@Path("productId")String productId);








}
