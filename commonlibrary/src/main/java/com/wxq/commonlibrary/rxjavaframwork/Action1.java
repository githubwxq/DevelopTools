package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * T 铁匠  打铁
 *目的  抽象 养羊的人
 */

public interface Action1<T> {
    //呼唤铁匠打铁的行为
    void call(T t);
}
