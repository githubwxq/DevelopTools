package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.Release
 *
 * @author SXDSF
 * @date 2017/11/5 下午11:39
 * @desc 挂断电话  disposable 用后即丢弃的; 一次性的; 可动用的; 可自由支配的;
 */

public interface Release {

    boolean isReleased();

    void release();
}
