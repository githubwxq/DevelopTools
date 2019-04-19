package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.CallerWithUpstream
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:22
 * @desc Caller用于操作符的类
 */

public abstract class CallerWithUpstream<T, R> extends Caller<R> implements CallerSource<T> {

    protected final Caller<T> source;

    public CallerWithUpstream(Caller<T> source) {
        this.source = source;
    }

    @Override
    public Caller<T> source() {
        return source;
    }
}
