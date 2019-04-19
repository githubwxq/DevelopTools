package com.wxq.commonlibrary.rxjavaimitate.imitate1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * com.sxdsf.async.imitate1.OperatorCallbackOn
 *
 * @author SXDSF
 * @date 2017/11/19 上午10:58
 * @desc 用于callbackOn
 */

public class OperatorCallbackOn<T> implements Caller.Operator<T, T> {

    private final Switcher mSwitcher;

    public OperatorCallbackOn(Switcher switcher) {
        mSwitcher = switcher;
    }

    @Override
    public Receiver<T> call(Receiver<T> tReceiver) {
        return new CallbackOnReceiver<>(tReceiver, mSwitcher);
    }

    private static final class CallbackOnReceiver<T> extends Receiver<T> implements Action0 {

        private final Receiver<T> mReceiver;

        private final Switcher.Worker mWorker;

        private final Queue<T> mQueue = new LinkedList<>();

        public CallbackOnReceiver(Receiver<T> receiver, Switcher switcher) {
            mReceiver = receiver;
            mWorker = switcher.createWorker();
        }

        @Override
        public void call() {
            T t = mQueue.poll();
            mReceiver.onReceive(t);
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onReceive(T t) {
            mQueue.offer(t);
            switches();
        }

        private void switches() {
            mWorker.switches(this);
        }
    }
}
