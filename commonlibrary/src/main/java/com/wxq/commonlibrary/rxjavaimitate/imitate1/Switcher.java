package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.Switcher
 *
 * @author SXDSF
 * @date 2017/11/14 下午5:52
 * @desc 用于线程切换
 */

public abstract class Switcher {

    public abstract Worker createWorker();

    public static abstract class Worker implements Calling {

        public abstract Calling switches(Action0 action0);
    }
}
