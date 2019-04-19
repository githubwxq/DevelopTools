package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 抽象的转换函数
 */

public interface Func1<T,R> {
    R call(T t);
}
