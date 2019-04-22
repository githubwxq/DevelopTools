package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Emitter
 *
 * @author SXDSF
 * @date 2017/11/5 下午11:41
 * @desc 用于发射数据  emitter
 */

public interface Emitter<T> {

    void onReceive(T t);

    void onCompleted();

    void onError(Throwable t);
}
