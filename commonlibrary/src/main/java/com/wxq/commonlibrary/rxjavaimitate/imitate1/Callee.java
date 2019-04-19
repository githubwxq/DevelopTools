package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.Callee
 *
 * @author SXDSF
 * @date 2017/7/3 上午12:48
 * @desc 接电话的人 观察者
 */

public interface Callee<T> {

    void onCompleted();

    void onError(Throwable t);

    void onReceive(T t);
}
