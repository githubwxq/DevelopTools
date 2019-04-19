package com.wxq.commonlibrary.rxjavaframwork;


import android.os.Handler;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class OnSubscrbleMain<T> implements OnSubscrible<T> {
    private Handler handler;
    private   Observable<T> source;  //原先的被观察者
    public OnSubscrbleMain(Handler handler, Observable<T> source) {
        this.handler = handler;
        this.source=source;
    }


    @Override
    public void call(final NewObserver<? super T> newObserver) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                source.subscrible(newObserver);
            }
        });
    }
}
