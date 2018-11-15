package com.example.commonservice;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/15
 * desc:  先创建一个ServiceCallBack接口作为数据回调的载体监听，并加上泛型。
 * version:1.0
 */
public interface ServiceCallBack<T> {

    void callBack(T t);
}
