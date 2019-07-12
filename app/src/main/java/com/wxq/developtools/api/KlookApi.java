package com.wxq.developtools.api;

import com.wxq.commonlibrary.model.KlookResponseData;
import com.wxq.developtools.model.AreaAndCountry;
import com.wxq.developtools.model.CollectionData;
import com.wxq.developtools.model.HomePageData;
import com.wxq.developtools.model.ProductDetailBean;
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
    Flowable<KlookResponseData<CollectionData>> pageCollectProduct(@Path("page")int page, @Path("rows")int rows);



    @POST("/app/collect/delete/{productId}")
    Flowable<KlookResponseData<Object>> deleteProduct(@Path("productId")String productId);


    @POST("/app/collect/save/{productId}")
    Flowable<KlookResponseData<Object>> saveProduct(@Path("productId")String productId);


    /**
     * s商品详情页面
     * @param id
     * @return
     */
    @POST("/app/product/findProductById/{id}")
    Flowable<KlookResponseData<ProductDetailBean>> findProductById(@Path("id")String id);




    /**
     * 商品详情信息页面 推荐商品
     * @param
     * @return
     */
    @POST("/app/product/listProductInfoRecommend/{productId}")
    Flowable<KlookResponseData<Object>> listProductInfoRecommend(@Path("productId")String productId);




    /**
     * 分页查询商品列表
     * @param
     * @return
     */
    @POST("/app/product/pageProductByCondition/{page}/{rows}")
    Flowable<KlookResponseData<CollectionData>> pageProductByCondition(@Path("page")int page, @Path("rows")int rows,@Body HashMap<String,String> data);



}
