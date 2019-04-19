package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * com.sxdsf.async.imitate2.NewThreadWorker
 *
 * @author SXDSF
 * @date 2017/11/15 上午12:44
 * @desc 文件描述
 */

public class NewThreadWorker extends Switcher.Worker {

    private volatile boolean mIsReleased;

    private final ExecutorService mExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "Async");
        }
    });

    @Override
    public boolean isReleased() {
        return mIsReleased;
    }

    @Override
    public void release() {
        mIsReleased = true;
    }

    @Override
    public Release switches(Runnable runnable) {
        SwitcherAction tSwitcherAction = new SwitcherAction(runnable);
        mExecutor.submit((Callable<Object>) tSwitcherAction);
        return tSwitcherAction;
    }

    private static class SwitcherAction implements Runnable, Callable<Object>, Release {

        private final Runnable mRunnable;

        private volatile boolean mIsReleased;

        public SwitcherAction(Runnable runnable) {
            mRunnable = runnable;
        }

        @Override
        public boolean isReleased() {
            return mIsReleased;
        }

        @Override
        public void release() {
            mIsReleased = true;
        }

        @Override
        public void run() {
            mRunnable.run();
        }

        @Override
        public Object call() throws Exception {
            run();
            return null;
        }
    }
}
