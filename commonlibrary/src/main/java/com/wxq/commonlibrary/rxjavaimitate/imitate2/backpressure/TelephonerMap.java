package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

import com.sxdsf.async.imitate2.Function;

/**
 * com.sxdsf.async.imitate2.TelephonerMap
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:36
 * @desc map操作符
 */

public class TelephonerMap<T, R> extends TelephonerWithUpstream<T, R> {

    private Function<T, R> mFunction;

    public TelephonerMap(Telephoner<T> source, Function<T, R> function) {
        super(source);
        mFunction = function;
    }

    @Override
    protected void callActual(Receiver<R> receiver) {
        source.call(new MapReceiver<>(receiver, mFunction));
    }

    static class MapReceiver<T, R> implements Receiver<T> {

        private final Receiver<R> mReceiver;

        private final Function<T, R> mFunction;

        public MapReceiver(Receiver<R> receiver, Function<T, R> function) {
            mReceiver = receiver;
            mFunction = function;
        }

        @Override
        public void onCall(Drop drop) {
            mReceiver.onCall(drop);
        }

        @Override
        public void onReceive(T t) {
            R tR = mFunction.call(t);
            mReceiver.onReceive(tR);
        }

        @Override
        public void onCompleted() {
            mReceiver.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            mReceiver.onError(t);
        }
    }
}
