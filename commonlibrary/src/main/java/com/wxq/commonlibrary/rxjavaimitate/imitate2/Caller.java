package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Caller
 *
 * @author SXDSF
 * @date 2017/11/5 下午11:24
 * @desc 打电话的人
 */

public abstract class Caller<T> {

    public static <T> Caller<T> create(CallerOnCall<T> callerOnCall) {
        return new CallerCreate<>(callerOnCall);
    }

    public void call(Callee<T> callee) {
        callActual(callee);
    }

    protected abstract void callActual(Callee<T> callee);

    public <R> Caller<R> lift(CallerOperator<R, T> operator) {
        return new CallerLift<>(this, operator);
    }

    public <R> Caller<R> map(Function<T, R> function) {
        return new CallerMap<>(this, function);
    }

    public Caller<T> callOn(Switcher switcher) {
        return new CallerCallOn<>(this, switcher);
    }

    public Caller<T> callbackOn(Switcher switcher) {
        return new CallerCallbackOn<>(this, switcher);
    }

    public <R> Caller<R> unify(CallerConverter<T, R> callerConverter) {
        return callerConverter.convert(this);
    }
}
