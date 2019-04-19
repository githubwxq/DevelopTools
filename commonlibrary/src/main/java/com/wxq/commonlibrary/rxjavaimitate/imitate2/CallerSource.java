package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.CallerSource
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:38
 * @desc 返回源Caller
 */

public interface CallerSource<T> {

    Caller<T> source();
}
