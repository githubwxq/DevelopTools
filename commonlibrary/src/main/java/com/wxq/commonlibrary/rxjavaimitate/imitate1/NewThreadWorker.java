package com.wxq.commonlibrary.rxjavaimitate.imitate1;


import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * com.sxdsf.async.imitate1.NewThreadWorker
 *
 * @author SXDSF
 * @date 2017/11/14 下午11:05
 * @desc 新线程的工作类
 */

public class NewThreadWorker extends Switcher.Worker {

    private volatile boolean mIsUnCalled;

    private final ExecutorService mExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "Async");
        }
    });

    @Override
    public void unCall() {
        mIsUnCalled = true;
    }

    @Override
    public boolean isUnCalled() {
        return mIsUnCalled;
    }

    @Override
    public Calling switches(Action0 action0) {
        SwitcherAction tSwitcherAction = new SwitcherAction(action0);
        mExecutor.submit(tSwitcherAction);
        return tSwitcherAction;
    }

    private static class SwitcherAction implements Runnable, Calling {

        private final Action0 mAction0;

        private volatile boolean mIsUnCalled;

        public SwitcherAction(Action0 action0) {
            mAction0 = action0;
        }

        @Override
        public void unCall() {
            mIsUnCalled = true;
        }

        @Override
        public boolean isUnCalled() {
            return mIsUnCalled;
        }

        @Override
        public void run() {
            mAction0.call();
        }
    }
}
