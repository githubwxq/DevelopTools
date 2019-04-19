package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Callee
 *
 * @author SXDSF
 * @date 2017/11/5 下午11:36
 * @desc 接电话的人
 */

public interface Callee<T> {

    void onCall(Release release);

    void onReceive(T t);

    void onCompleted();

    void onError(Throwable t);
}
