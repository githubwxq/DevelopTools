package com.juziwl.uilibrary.chatview.audio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/31
 * @description
 */
public class ThreadToolUtils {

    private ExecutorService singleThreadPool = null;

    private ThreadToolUtils() {
        singleThreadPool = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), new DefaultThreadFactory());
    }

    private static class SingletonHolder {
        private static ThreadToolUtils instance = new ThreadToolUtils();
    }

    public static ThreadToolUtils getInstance() {
        return SingletonHolder.instance;
    }

    public void runInThreadPool(Runnable runnable) {
        if (singleThreadPool != null && !singleThreadPool.isShutdown()) {
            singleThreadPool.execute(runnable);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-speexlib-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

}
