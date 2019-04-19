package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import android.os.Handler;
import android.os.Looper;

/**
 * com.sxdsf.async.imitate2.LooperSwitcher
 *
 * @author SXDSF
 * @date 2017/11/15 上午1:01
 * @desc 用于Android的Switcher
 */

public class LooperSwitcher extends Switcher {

    private Handler mHandler;

    public LooperSwitcher(Looper looper) {
        mHandler = new Handler(looper);
    }

    public Release switches(final Runnable runnable) {
        SwitcherRunnable tSwitcherRunnable = new SwitcherRunnable(runnable, mHandler);
        mHandler.post(tSwitcherRunnable);
        return tSwitcherRunnable;
    }

    @Override
    public Worker createWorker() {
        return new HandlerWorker(mHandler);
    }

    public static class SwitcherRunnable implements Runnable, Release {

        private final Runnable mRunnable;
        private final Handler mHandler;
        private volatile boolean mIsReleased;

        public SwitcherRunnable(Runnable runnable, Handler handler) {
            mRunnable = runnable;
            mHandler = handler;
        }

        @Override
        public void run() {
            mRunnable.run();
        }

        @Override
        public boolean isReleased() {
            return mIsReleased;
        }

        @Override
        public void release() {
            mIsReleased = true;
            mHandler.removeCallbacks(this);
        }
    }
}
