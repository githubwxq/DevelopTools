package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.Receiver
 *
 * @author SXDSF
 * @date 2017/11/7 下午12:41
 * @desc 接电话的人
 */

public interface Receiver<T> {

    void onCall(Drop d);

    void onReceive(T t);

    void onError(Throwable t);

    void onCompleted();
}
