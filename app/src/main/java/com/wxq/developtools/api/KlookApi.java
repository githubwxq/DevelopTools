package com.wxq.developtools.api;

import com.wxq.commonlibrary.model.KlookResponseData;
import com.wxq.commonlibrary.model.UserInfo;
import com.wxq.developtools.model.AddShopCarParmer;
import com.wxq.developtools.model.AreaAndCountry;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.Certificate;
import com.wxq.developtools.model.CityVosBean;
import com.wxq.developtools.model.CollectionData;
import com.wxq.developtools.model.HomePageData;
import com.wxq.developtools.model.OrderBean;
import com.wxq.developtools.model.PackageBean;
import com.wxq.developtools.model.PayParmer;
import com.wxq.developtools.model.PayResultData;
import com.wxq.developtools.model.PersonInfo;
import com.wxq.developtools.model.ProductCommentData;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.Region;
import com.wxq.developtools.model.SaveCommentParmer;
import com.wxq.developtools.model.ShopCarBean;

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
     * 城市详情
     * @param id
     * @return
     */
    @POST("/app/location/findCityById/{id}")
    Flowable<KlookResponseData<CityVosBean>> findCityById(@Path("id")String id);




    /**
     * 根据商品获取评论数据
     * @param id
     * @return
     */

    /**
     * 分页查询商品列表
     * @param
     * @return
     */
    @POST("/app/comment/pageProductComment/{productId}/{page}/{rows}")
    Flowable<KlookResponseData<ProductCommentData>> pageProductComment(@Path("page")int page, @Path("rows")int rows, @Path("productId")String productId);




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
    Flowable<KlookResponseData<BaseListModeData<ProductDetailBean>>> pageProductByCondition(@Path("page")int page, @Path("rows")int rows,@Body HashMap<String,String> data);






    /**
     * 获取用户信息
     * @return
     */
    @POST("/app/user/findUserById")
    Flowable<KlookResponseData<UserInfo>> findUserById();

    /**
     * 获取用户信息
     * @return
     */
    @POST("/app/qiniu/getQiniuToken")
    Flowable<KlookResponseData<String>> getQiniuToken();



    /**
     * 更新头像   图片地址 以及用户id
     * @return
     */
    @POST("/app/user/updateHead")
    Flowable<KlookResponseData<Object>> updateHead(@Body HashMap<String, String> data);




    /**
     * 商品套餐详情
     * @return
     */
    @POST("/app/product/findProductPackageDetailById/{productPackageId}")
    Flowable<KlookResponseData<PackageBean>> findProductPackageDetailById(@Path("productPackageId") String id);



    /**
     * 设置用户账户
     * @return
     */
    @POST("/app/user/saveUserAccount")
    Flowable<KlookResponseData<PersonInfo>> saveUserAccount(@Body HashMap<String, String> data);


    /**
     * 获取用户列表
     * @return
     */
    @POST("/app/user/findUserAccountByUserId")
    Flowable<KlookResponseData<List<PersonInfo>>> findUserAccountByUserId();



    /**
     * 支付宝支付
     * @return
     */
    @POST("/app/buy/alipay")
    Flowable<KlookResponseData<PayResultData>> alipay(@Body PayParmer payParmer);


  /**
     * 支付宝支付
     * @return
     */
    @POST("/app/buy/wechatpay")
    Flowable<KlookResponseData<PayResultData>> wechatpay(@Body PayParmer payParmer);




    /**
     * 添加销售店
     * @return
     */
    @POST("/app/shopcart/insert")
    Flowable<KlookResponseData<PayResultData>> insertShopCart(@Body AddShopCarParmer payParmer);


     /**
     * 删除购物车
     * @return
     */
    @POST("/app/shopcart/delete/{id}")
    Flowable<KlookResponseData<Object>> deleteShopCart(@Path("id") String payParmer);



    /**
     * 分页查询购物车
     * @param
     * @return
     */
    @POST("/app/shopcart/pageShopCart/{page}/{rows}")
    Flowable<KlookResponseData<BaseListModeData<ShopCarBean>>> pageShopCart(@Path("page")int page, @Path("rows")int rows);




    /**
     * 分页查询我的订单
     * @param
     * @return
     */
    @POST("/app/order/pageOrder/{page}/{rows}")
    Flowable<KlookResponseData<BaseListModeData<OrderBean>>> pageOrder(@Path("page")int page, @Path("rows")int rows);



    @POST("/app/comment/save")
    Flowable<KlookResponseData<Object>> saveComment(@Body SaveCommentParmer parmer);




  //使用凭证列表页面

    @POST("/app/vouche/pageUseVouche/{page}/{rows}")
    Flowable<KlookResponseData<BaseListModeData<Certificate>>> pageUseVouche(@Path("page")int page, @Path("rows")int rows);








}
