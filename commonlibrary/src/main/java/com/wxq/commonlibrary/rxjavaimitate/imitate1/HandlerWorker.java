package com.wxq.commonlibrary.rxjavaimitate.imitate1;

import android.os.Handler;
import android.os.Message;

/**
 * com.sxdsf.async.imitate1.HandlerWorker
 *
 * @author SXDSF
 * @date 2017/11/15 上午12:18
 * @desc 用于Android的Worker
 */

public class HandlerWorker extends Switcher.Worker {

    private final Handler mHandler;

    private volatile boolean mIsUnCalled;

    public HandlerWorker(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void unCall() {
        mIsUnCalled = true;
        mHandler.removeCallbacksAndMessages(this);
    }

    @Override
    public boolean isUnCalled() {
        return mIsUnCalled;
    }

    @Override
    public Calling switches(Action0 action0) {
        SwitcherAction tSwitcherAction = new SwitcherAction(action0, mHandler);
        Message tMessage = Message.obtain(mHandler, tSwitcherAction);
        tMessage.obj = this;
        mHandler.sendMessage(tMessage);
        return tSwitcherAction;
    }

    private static class SwitcherAction implements Runnable, Calling {

        private final Action0 mAction;
        private final Handler mHandler;
        private volatile boolean mIsUnCalled;

        public SwitcherAction(Action0 action, Handler handler) {
            mAction = action;
            mHandler = handler;
        }

        @Override
        public void unCall() {
            mIsUnCalled = true;
            mHandler.removeCallbacks(this);
        }

        @Override
        public boolean isUnCalled() {
            return mIsUnCalled;
        }

        @Override
        public void run() {
            mAction.call();
        }
    }
}
