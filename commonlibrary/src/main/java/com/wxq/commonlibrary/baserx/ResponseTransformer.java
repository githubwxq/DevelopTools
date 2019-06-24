package com.wxq.commonlibrary.baserx;//package com.juzi.common.baserx;

import com.wxq.commonlibrary.http.ApiException;
import com.wxq.commonlibrary.model.KlookResponseData;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {

    public static <T> FlowableTransformer<KlookResponseData<T>, T> handleResult() {
        return upstream -> upstream
//                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, Flowable<? extends KlookResponseData<T>>> {
        @Override
        public Flowable<? extends KlookResponseData<T>> apply(Throwable throwable) throws Exception {
            return Flowable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<KlookResponseData<T>, Flowable<T>> {
        @Override
        public Flowable<T> apply(KlookResponseData<T> tResponse) throws Exception {
            int code = tResponse.code;
            String message = tResponse.msg;
            if (code == 200) {
                return Flowable.just(tResponse.data==null?(T)(new Object()):tResponse.data);
            } else {
                return Flowable.error(new ApiException(code, message));
            }
        }
    }
}
