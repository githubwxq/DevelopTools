package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.TelephonerLift
 *
 * @author SXDSF
 * @date 2017/11/12 下午11:52
 * @desc lift操作符
 */

public class CallerLift<R, T> extends CallerWithUpstream<T, R> {

    private final CallerOperator<R, T> mOperator;

    public CallerLift(Caller<T> source, CallerOperator<R, T> operator) {
        super(source);
        mOperator = operator;
    }

    @Override
    protected void callActual(Callee<R> callee) {
        Callee<T> tCallee = mOperator.call(callee);
        source.call(tCallee);
    }
}
