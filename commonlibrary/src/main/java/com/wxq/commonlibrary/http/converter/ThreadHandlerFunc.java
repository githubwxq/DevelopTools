//package com.wxq.commonlibrary.http.converter;
//
//
//import com.juzi.common.config.GlobalContent;
//import com.juzi.common.http.Response.ResponseData;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Function;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2018/3/11
// * @description
// */
//public class ThreadHandlerFunc<T, R> implements Function<ResponseData<T>, R> {
//
//    private boolean isContentCanNull = false;
//
//    public ThreadHandlerFunc() {
//    }
//
//    public ThreadHandlerFunc(boolean isContentCanNull) {
//        this.isContentCanNull = isContentCanNull;
//    }
//
//    @Override
//    public R apply(@NonNull ResponseData<T> tResponseData) throws Exception {
//        //response中code码不为200 出现错误
//        if (GlobalContent.STATUS_SUCCESS.equals(tResponseData.status)) {
//            if (isContentCanNull) {
//                return onSuccess(tResponseData.content);
//            } else {
//                if (tResponseData.content != null) {
//                    return onSuccess(tResponseData.content);
//                } else {
//                    throw new Exception(tResponseData.errorMsg);
//                }
//            }
//        } else {
//            if (GlobalContent.STATUS_SERVER_MAINTAIN.equals(tResponseData.status)) {
//                throw new Exception(tResponseData.status);
//            }
//            throw new Exception(tResponseData.errorMsg);
//        }
//    }
//
//    public R onSuccess(T content) {
//        return (R) new Object();
//    }
//}
