package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerWithUpstream
 *
 * @author SXDSF
 * @date 2017/11/13 上午12:57
 * @desc
 */

public abstract class TelephonerWithUpstream<T, R> extends Telephoner<R> implements TelephonerSource<T> {

    protected final Telephoner<T> source;

    public TelephonerWithUpstream(Telephoner<T> source) {
        this.source = source;
    }

    @Override
    public Telephoner<T> source() {
        return source;
    }
}
