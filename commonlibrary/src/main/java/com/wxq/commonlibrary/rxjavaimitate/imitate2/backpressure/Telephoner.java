package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

import com.sxdsf.async.imitate2.Function;
import com.sxdsf.async.imitate2.Switcher;

/**
 * com.sxdsf.async.imitate2.backpressure.Telephoner
 *
 * @author SXDSF
 * @date 2017/11/7 下午12:38
 * @desc 打电话的人
 */

public abstract class Telephoner<T> {

    public static <T> Telephoner<T> create(TelephonerOnCall<T> telephonerOnCall) {
        return new TelephonerCreate<>(telephonerOnCall);
    }

    public void call(Receiver<T> receiver) {
        callActual(receiver);
    }

    protected abstract void callActual(Receiver<T> receiver);

    public <R> Telephoner<R> map(Function<T, R> function) {
        return new TelephonerMap<>(this, function);
    }

    public <R> Telephoner<R> lift(TelephonerOperator<R, T> telephonerOperator) {
        return new TelephonerLift<>(this, telephonerOperator);
    }

    public Telephoner<T> callOn(Switcher switcher) {
        return new TelephonerCallOn<>(this, switcher);
    }

    public Telephoner<T> callbackOn(Switcher switcher) {
        return new TelephonerCallbackOn<>(this, switcher);
    }

    public <R> Telephoner<R> unify(TelephonerConverter<T, R> tTelephonerConverter) {
        return tTelephonerConverter.convert(this);
    }
}
