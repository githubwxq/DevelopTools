package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.Drop
 *
 * @author SXDSF
 * @date 2017/11/7 下午12:44
 * @desc 丢弃
 */

public interface Drop {

    void request(long n);

    void drop();
}
