package com.wxq.commonlibrary.rxjavaimitate.imitate1;

import android.os.Handler;
import android.os.Looper;

/**
 * com.sxdsf.async.imitate1.LooperSwitcher
 *
 * @author SXDSF
 * @date 2017/11/15 上午12:11
 * @desc 用于Android中Looper的Swticher
 */

public class LooperSwitcher extends Switcher {

    private Handler mHandler;

    public LooperSwitcher(Looper looper) {
        mHandler = new Handler(looper);
    }

    @Override
    public Worker createWorker() {
        return new HandlerWorker(mHandler);
    }
}
