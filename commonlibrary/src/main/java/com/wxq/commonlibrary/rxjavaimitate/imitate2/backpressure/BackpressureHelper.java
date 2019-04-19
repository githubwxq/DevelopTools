package com.wxq.commonlibrary.rxjavaimitate.imitate2.backpressure;

import java.util.concurrent.atomic.AtomicLong;

/**
 * com.sxdsf.async.imitate2.backpressure.BackpressureHelper
 *
 * @author SXDSF
 * @date 2017/11/7 下午4:28
 * @desc 处理背压的帮助类
 */

public final class BackpressureHelper {

    public static void add(AtomicLong requested, long n) {
        long r = requested.get();
        if (r == Long.MAX_VALUE) {
            return;
        }
        long u = r + n;
        if (u < 0L) {
            u = Long.MAX_VALUE;
        }
        requested.compareAndSet(r, u);
    }

    public static void produced(AtomicLong requested, long n) {
        long current = requested.get();
        if (current == Long.MAX_VALUE) {
            return;
        }
        long update = current - n;
        if (update < 0L) {
            update = 0L;
        }
        requested.compareAndSet(current, update);
    }
}
