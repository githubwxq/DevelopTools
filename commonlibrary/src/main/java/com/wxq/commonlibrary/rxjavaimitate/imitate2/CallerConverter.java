package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.CallerConverter
 *
 * @author SXDSF
 * @date 2017/11/20 上午8:01
 * @desc 用于整体变换
 */

public interface CallerConverter<T, R> {

    Caller<R> convert(Caller<T> caller);
}
