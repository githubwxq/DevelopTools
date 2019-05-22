package com.example.design_pattern.concurrentthreads;

import java.util.Queue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/22
 * desc:
 * version:1.0
 */
public class ToyotaYQ implements Runnable {
    // zhix
    private static final Object lock = new Object();
    private static Queue<String> queueYQ = MQ.initQueue();
    @Override
    public void run() {

        while (true){
            synchronized (lock){
                String thisVIN = queueYQ.poll();

                if (thisVIN==null) {
                    break;
                }

                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "成功制单：" + thisVIN + "。剩余：" + queueYQ.size() + "个任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
    }
}
