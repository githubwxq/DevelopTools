package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

import java.util.concurrent.atomic.AtomicLong;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerCreate
 *
 * @author SXDSF
 * @date 2017/11/7 下午1:26
 * @desc 实际的Telephoner
 */

public final class TelephonerCreate<T> extends Telephoner<T> {

    private TelephonerOnCall<T> mTelephonerOnCall;

    public TelephonerCreate(TelephonerOnCall<T> telephonerOnCall) {
        mTelephonerOnCall = telephonerOnCall;
    }

    @Override
    protected void callActual(Receiver<T> receiver) {
        DropEmitter<T> tDropEmitter = new DropEmitter<>(receiver);
        receiver.onCall(tDropEmitter);
        mTelephonerOnCall.call(tDropEmitter);
    }

    private static class DropEmitter<T>
            extends AtomicLong
            implements TelephonerEmitter<T>, Drop {

        private Receiver<T> mReceiver;

        private volatile boolean mIsDropped;

        private DropEmitter(Receiver<T> receiver) {
            mReceiver = receiver;
        }

        @Override
        public void onReceive(T t) {
            if (get() != 0) {
                mReceiver.onReceive(t);
                BackpressureHelper.produced(this, 1);
            }
        }

        @Override
        public void request(long n) {
            BackpressureHelper.add(this, n);
        }

        @Override
        public void onCompleted() {
            mReceiver.onCompleted();
        }

        @Override
        public void drop() {
            mIsDropped = true;
        }

        @Override
        public void onError(Throwable t) {
            mReceiver.onError(t);
        }
    }
}
