package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

/**
 * com.sxdsf.async.imitate2.TelephonerLift
 *
 * @author SXDSF
 * @date 2017/11/12 下午11:52
 * @desc lift操作符
 */

public class TelephonerLift<R, T> extends TelephonerWithUpstream<T, R> {

    private final TelephonerOperator<R, T> mOperator;

    public TelephonerLift(Telephoner<T> source, TelephonerOperator<R, T> operator) {
        super(source);
        mOperator = operator;
    }

    @Override
    protected void callActual(Receiver<R> receiver) {
        Receiver<T> tReceiver = mOperator.call(receiver);
        source.call(tReceiver);
    }
}
