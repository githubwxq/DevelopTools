//package com.wxq.commonlibrary.http.converter;
//
//import com.juzi.common.config.GlobalContent;
//import com.juzi.common.http.Response.ResponseData;
//import com.juzi.common.model.ListData;
//
//import java.util.List;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Function;
//
///**
// * Created by Administrator on 2018/2/16.
// */
//
//public abstract class ListMapFunction<T  ,R> implements Function<ResponseData<ListData<T>>, R> {
//    public static final String NO_NETWORK = "-1000";
//    public static final String STATUS_SUCCESS = "200";
//    /**
//     * 服务器维护
//     */
//    public static final String STATUS_SERVER_MAINTAIN = "5050";
//
//    public String currentTimeStemp;
//
//    @Override
//    public R apply(@NonNull ResponseData<ListData<T>> tResponseData) throws Exception {
//        if (GlobalContent.STATUS_SUCCESS.equals(tResponseData.status)) {
//            if(tResponseData.content!=null&&tResponseData.content.list!=null){
//                return ListMapApply(tResponseData.content.list);
//            }else {
//                //数据获取异常的情况
//                throw new Exception(tResponseData.errorMsg);
//            }
//        } else {
//            if (GlobalContent.STATUS_SERVER_MAINTAIN.equals(tResponseData.status)) {
//                throw new Exception(tResponseData.status);
//            }
//            throw new Exception(tResponseData.errorMsg);
//        }
//    }
//
//    public abstract R ListMapApply( List<T> t) throws Exception;
//}
