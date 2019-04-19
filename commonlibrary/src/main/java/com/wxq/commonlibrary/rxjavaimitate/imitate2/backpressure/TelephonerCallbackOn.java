package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

import com.sxdsf.async.imitate2.Switcher;

import java.util.LinkedList;
import java.util.Queue;

/**
 * com.sxdsf.async.imitate2.backpressure.TelephonerCallbackOn
 *
 * @author SXDSF
 * @date 2017/11/19 下午6:50
 * @desc 用于callbackOn
 */

public class TelephonerCallbackOn<T> extends TelephonerWithUpstream<T, T> {

    private final Switcher mSwitcher;

    public TelephonerCallbackOn(Telephoner<T> source, Switcher switcher) {
        super(source);
        mSwitcher = switcher;
    }

    @Override
    protected void callActual(Receiver<T> receiver) {
        source.call(new CallbackOnReceiver<>(receiver, mSwitcher));
    }

    private static final class CallbackOnReceiver<T> implements Receiver<T>, Runnable {

        private final Receiver<T> mReceiver;

        private final Switcher.Worker mWorker;

        private final Queue<T> mQueue = new LinkedList<>();

        public CallbackOnReceiver(Receiver<T> receiver, Switcher switcher) {
            mReceiver = receiver;
            mWorker = switcher.createWorker();
        }

        @Override
        public void onCall(Drop d) {
            mReceiver.onCall(d);
        }

        @Override
        public void onReceive(T t) {
            mQueue.offer(t);
            switches();
        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void run() {
            T t = mQueue.poll();
            mReceiver.onReceive(t);
        }

        private void switches() {
            mWorker.switches(this);
        }
    }
}
