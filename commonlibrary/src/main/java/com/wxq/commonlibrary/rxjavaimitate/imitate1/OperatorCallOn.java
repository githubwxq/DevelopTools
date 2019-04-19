package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.OperatorCallOn
 *
 * @author SXDSF
 * @date 2017/11/15 下午3:43
 * @desc 用于callOn的OnCall
 */

public class OperatorCallOn<T> implements Caller.OnCall<T> {

    private final Switcher mSwitcher;
    private final Caller<T> mCaller;

    public OperatorCallOn(Switcher switcher, Caller<T> caller) {
        mSwitcher = switcher;
        mCaller = caller;
    }

    @Override
    public void call(final Receiver<T> tReceiver) {
        Switcher.Worker tWorker = mSwitcher.createWorker();
        tWorker.switches(new Action0() {
            @Override
            public void call() {
                Receiver<T> tReceiver1 = new Receiver<T>() {
                    @Override
                    public void onCompleted() {
                        tReceiver.onCompleted();
                    }

                    @Override
                    public void onError(Throwable t) {
                        tReceiver.onError(t);
                    }

                    @Override
                    public void onReceive(T t) {
                        tReceiver.onReceive(t);
                    }
                };
                mCaller.call(tReceiver1);
            }
        });
    }
}
