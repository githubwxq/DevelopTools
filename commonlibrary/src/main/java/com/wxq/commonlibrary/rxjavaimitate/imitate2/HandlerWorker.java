package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import android.os.Handler;
import android.os.Message;

/**
 * com.sxdsf.async.imitate2.HandlerWorker
 *
 * @author SXDSF
 * @date 2017/11/15 上午1:02
 * @desc 用于Android的Worker
 */

public class HandlerWorker extends Switcher.Worker {

    private final Handler mHandler;

    private volatile boolean mIsReleased;

    public HandlerWorker(Handler handler) {
        mHandler = handler;
    }

    @Override
    public boolean isReleased() {
        return mIsReleased;
    }

    @Override
    public void release() {
        mIsReleased = true;
        mHandler.removeCallbacksAndMessages(this);
    }

    @Override
    public Release switches(Runnable runnable) {
        LooperSwitcher.SwitcherRunnable tSwitcherRunnable = new LooperSwitcher.SwitcherRunnable(runnable, mHandler);
        Message tMessage = Message.obtain(mHandler, tSwitcherRunnable);
        tMessage.obj = this;
        mHandler.sendMessage(tMessage);
        return tSwitcherRunnable;
    }
}
