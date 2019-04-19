package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.OnCallLift
 *
 * @author SXDSF
 * @date 2017/9/11 上午10:30
 * @desc 做变换用的OnCall
 */

public class OnCallLift<T, R> implements Caller.OnCall<R> {

    private final Caller.OnCall<T> parent;

    private final Caller.Operator<R, T> operator;

    public OnCallLift(Caller.OnCall<T> parent, Caller.Operator<R, T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override
    public void call(Receiver<R> receiver) {
        Receiver<T> tReceiver =  this.operator.call(receiver);
        this.parent.call(tReceiver);
    }
}
