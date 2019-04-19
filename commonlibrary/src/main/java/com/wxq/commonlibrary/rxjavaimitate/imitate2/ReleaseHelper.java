package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import java.util.concurrent.atomic.AtomicReference;

/**
 * com.sxdsf.async.imitate2.ReleaseHelper
 *
 * @author SXDSF
 * @date 2017/11/7 上午7:43
 * @desc 挂电话帮助类
 */

public enum ReleaseHelper implements Release {

    RELEASED;

    public static boolean isReleased(Release release) {
        return release == RELEASED;
    }

    public static boolean release(AtomicReference<Release> releaseAtomicReference) {
        Release current = releaseAtomicReference.get();
        Release d = RELEASED;
        if (current != d) {
            current = releaseAtomicReference.getAndSet(d);
            if (current != d) {
                if (current != null) {
                    current.release();
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReleased() {
        return true;
    }

    @Override
    public void release() {

    }
}
