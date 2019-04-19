package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.Caller
 *
 * @author SXDSF
 * @date 2017/7/3 上午12:47
 * @desc 打电话的人
 */

public class Caller<T> {

    final OnCall<T> onCall;

    public Caller(OnCall<T> onCall) {
        this.onCall = onCall;
    }

    public static <T> Caller<T> create(OnCall<T> onCall) {
        return new Caller<>(onCall);
    }

    public Calling call(Receiver<T> receiver) {
        this.onCall.call(receiver);
        return receiver;
    }

    public interface OnCall<T> extends Action1<Receiver<T>> {

    }

    public interface Operator<R, T> extends Func1<Receiver<R>, Receiver<T>> {

    }

    public interface Converter<T, R> extends Func1<Caller<T>, Caller<R>> {

    }

    public final <R> Caller<R> lift(final Operator<R, T> operator) {
        return create(new OnCallLift<>(onCall, operator));
    }

    public final <R> Caller<R> map(Func1<T, R> func) {
        return lift(new MapOperator<>(func));
    }

    public final Caller<T> callOn(Switcher switcher) {
        return create(new OperatorCallOn<>(switcher, this));
    }

    public final Caller<T> callbackOn(Switcher switcher) {
        return lift(new OperatorCallbackOn<T>(switcher));
    }

    public final <R> Caller<R> unify(Converter<T, R> converter) {
        return converter.call(this);
    }
}
