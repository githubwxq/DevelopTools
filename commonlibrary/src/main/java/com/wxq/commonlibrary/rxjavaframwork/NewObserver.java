package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 养羊的人 通知了 铁匠 打铁
 * 铁匠
 * 观察者
 * T 铁匠
 */

public abstract class NewObserver<T>{
    /**
     * 铁匠 打铁的行为
     * @param t
     */
    public abstract void onNext(T t);
}
