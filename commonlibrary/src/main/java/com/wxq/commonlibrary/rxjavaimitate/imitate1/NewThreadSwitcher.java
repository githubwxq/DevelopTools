package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.NewThreadSwitcher
 *
 * @author SXDSF
 * @date 2017/11/14 下午11:02
 * @desc 新线程的switcher
 */

public class NewThreadSwitcher extends Switcher {

    @Override
    public Worker createWorker() {
        return new NewThreadWorker();
    }
}
