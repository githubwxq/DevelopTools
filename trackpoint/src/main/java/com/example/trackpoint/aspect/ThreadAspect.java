package com.example.trackpoint.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/16
 * desc:线程 切换 配合注解
 * version:1.0
 */
@Aspect
public class ThreadAspect {

    @Around("execution(@com.example.trackpoint.annotation.Background * *(..))")
    public void doBackground(final ProceedingJoinPoint joinPoint) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }


    @Around("execution(@com.example.trackpoint.annotation.UiThread * *(..))")
    public void doUiThread(final ProceedingJoinPoint joinPoint) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }

}
