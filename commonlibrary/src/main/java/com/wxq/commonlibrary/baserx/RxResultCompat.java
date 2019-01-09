package com.wxq.commonlibrary.baserx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class RxResultCompat {
    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResult<T>> upstream) {
                return upstream.flatMap(new Function<BaseResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResult<T> tBaseResult) throws Exception {
                        int status = tBaseResult.getStatus();
                        tBaseResult.getStatus();
                        if (tBaseResult.isSuccess()) {
                            return Observable.just(tBaseResult.getData());
                        } else if (status == BaseResult.ERROR) {
                            return Observable.error(new BaseException(tBaseResult.getStatus() + "", tBaseResult.getInfo()));
                        }
                      //这里可以增加一些 错误码的处理
                        return Observable.empty();
                    }
                });
            }
        };
    }
}


