package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * com.sxdsf.async.imitate2.CallerCallbackOn
 *
 * @author SXDSF
 * @date 2017/11/19 下午12:03
 * @desc 用于callbackOn
 */

public class CallerCallbackOn<T> extends CallerWithUpstream<T, T> {

    private final Switcher mSwitcher;

    public CallerCallbackOn(Caller<T> source, Switcher switcher) {
        super(source);
        mSwitcher = switcher;
    }

    @Override
    protected void callActual(Callee<T> callee) {
        source.call(new CallbackOnCallee<>(callee, mSwitcher));
    }

    private static final class CallbackOnCallee<T> implements Callee<T>, Runnable {

        private final Callee<T> mCallee;

        private final Switcher.Worker mWorker;

        private final Queue<T> mQueue = new LinkedList<>();

        public CallbackOnCallee(Callee<T> callee, Switcher switcher) {
            mCallee = callee;
            mWorker = switcher.createWorker();
        }

        @Override
        public void onCall(Release release) {

        }

        @Override
        public void onReceive(T t) {
            mQueue.offer(t);
            switches();
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void run() {
            T t = mQueue.poll();
            mCallee.onReceive(t);
        }

        private void switches() {
            mWorker.switches(this);
        }
    }
}
