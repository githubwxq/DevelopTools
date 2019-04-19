package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.MapOperator
 *
 * @author SXDSF
 * @date 2017/9/11 上午10:56
 * @desc map操作符
 */

public class MapOperator<T, R> implements Caller.Operator<R, T> {

    private final Func1<T, R> mapper;

    public MapOperator(Func1<T, R> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Receiver<T> call(Receiver<R> rReceiver) {
        return new MapReceiver<>(rReceiver, this.mapper);
    }
}
