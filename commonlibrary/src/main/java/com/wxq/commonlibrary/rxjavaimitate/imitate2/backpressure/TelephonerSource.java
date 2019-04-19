package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerSource
 *
 * @author SXDSF
 * @date 2017/11/13 上午12:58
 * @desc 返回源Telephoner
 */

public interface TelephonerSource<T> {

    Telephoner<T> source();
}
