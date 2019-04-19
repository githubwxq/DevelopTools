package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Switcher
 *
 * @author SXDSF
 * @date 2017/11/15 上午12:38
 * @desc 用于线程切换的抽象类
 */

public abstract class Switcher {

    public abstract Worker createWorker();

    public Release switches(final Runnable runnable) {
        Worker tWorker = createWorker();
        tWorker.switches(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        });
        return tWorker;
    }

    public static abstract class Worker implements Release {

        public abstract Release switches(Runnable runnable);
    }
}
