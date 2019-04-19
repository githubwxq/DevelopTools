package com.wxq.commonlibrary.rxjavaframwork;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/2/20 0020.
 * 线程切换中间类
 */

public class OnSubscribleOnIO<T> implements  OnSubscrible<T> {
    private static ExecutorService executorService= Executors.newCachedThreadPool();
    Observable<T> source;

    public OnSubscribleOnIO(Observable<T> source) {
        this.source = source;
    }
    @Override
    public void call(final NewObserver<? super T> newObserver) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                source.subscrible(newObserver);
            }
        };
        executorService.submit(runnable);
    }
}
