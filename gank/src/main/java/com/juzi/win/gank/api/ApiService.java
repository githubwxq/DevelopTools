package com.juzi.win.gank.api;

import com.juzi.win.gank.bean.GankBaseResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author 文庆
 * @date 2019/1/8.
 * @description
 */

public interface ApiService {


    /**
     * 获取gank数
     * @param page 页码
     * @return
     */
    @GET(value = "http://gank.io/api/data/{type}/10/{page}")
    Observable<GankBaseResponse> getGankData(@Path("type") String type, @Path("page") int page);


    //数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
    String[] GANK_ARR = {"Android", "iOS", "前端", "福利", "休息视频", "拓展资源"};
}
