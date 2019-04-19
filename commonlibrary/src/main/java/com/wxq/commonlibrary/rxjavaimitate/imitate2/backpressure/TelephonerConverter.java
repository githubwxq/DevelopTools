package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerConverter
 *
 * @author SXDSF
 * @date 2017/11/20 上午8:06
 * @desc 用于整体变换
 */

public interface TelephonerConverter<T, R> {

    Telephoner<R> convert(Telephoner<T> telephoner);
}
