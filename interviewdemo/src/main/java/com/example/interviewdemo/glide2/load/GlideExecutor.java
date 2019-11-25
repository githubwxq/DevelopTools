package com.example.interviewdemo.glide2.load;

import androidx.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class GlideExecutor {

    private static int bestThreadCount;

    public static int calculateBestThreadCount() {
        if (bestThreadCount == 0) {
            bestThreadCount = Math.min(4, Runtime.getRuntime().availableProcessors());
        }
        return bestThreadCount;
    }


    private static final class DefaultThreadFactory implements ThreadFactory {
        private int threadNum;


        @Override
        public synchronized Thread newThread(@NonNull Runnable runnable) {
            final Thread result = new Thread(runnable, "glide-thread-" + threadNum);
            threadNum++;
            return result;
        }
    }

    public static ThreadPoolExecutor newExecutor() {
        int threadCount = calculateBestThreadCount();
        return new ThreadPoolExecutor(
                threadCount /* corePoolSize */,
                threadCount /* maximumPoolSize */,
                0 /* keepAliveTime */,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory());
    }
}
