package com.example.design_pattern.concurrentthreads;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/22
 * desc:
 * version:1.0
 */
public class ThreadTest {

    public static void main(String[] args) {
        int corePoolSize = 1;
        int maximumPoolSize = 100;
        long keepAliveTime = 30;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
        ThreadFactory threadFactory = new NameThreadFacotry();
        RejectedExecutionHandler handler = new MyIgnorePolicy();

        ThreadPoolExecutor executor=
                new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,new SynchronousQueue<Runnable>(),threadFactory);
        for (int i = 1; i <= 20; i++) {
            MyTask task = new MyTask(String.valueOf(i));
            executor.execute(task);
        }
        try {
            System.in.read(); //阻塞主线程
//            Thread.sleep(600000); //让任务执行慢点
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 任务工厂
     */













}
