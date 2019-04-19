package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Function
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:31
 * @desc 文件描述
 */

public interface Function<T, R> {

    R call(T t);
}
