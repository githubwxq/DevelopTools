package com.wxq.commonlibrary.rxjavaimitate.imitate1;

/**
 * com.sxdsf.async.imitate1.Receiver
 *
 * @author SXDSF
 * @date 2017/7/3 上午12:49
 * @desc 接收信息的人  subscriber
 */

public abstract class Receiver<T> implements Callee<T>, Calling {

    private volatile boolean unCalled;

    @Override
    public void unCall() {
        unCalled = true;
    }

    @Override
    public boolean isUnCalled() {
        return unCalled;
    }
}
