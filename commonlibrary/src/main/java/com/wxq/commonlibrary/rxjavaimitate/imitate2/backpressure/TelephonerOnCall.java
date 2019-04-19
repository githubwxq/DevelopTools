package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerOnCall
 *
 * @author SXDSF
 * @date 2017/11/7 下午12:42
 * @desc 当打电话的时候
 */

public interface TelephonerOnCall<T> {

    void call(TelephonerEmitter<T> telephonerEmitter);
}
