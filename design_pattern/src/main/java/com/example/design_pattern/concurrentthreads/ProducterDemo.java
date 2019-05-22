package com.example.design_pattern.concurrentthreads;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/22
 * desc:
 * version:1.0
 */
public class ProducterDemo implements Runnable {

    private String producerName;
    private BlockingQueue queue;//阻塞队列
    private Random r = new Random();


    public ProducterDemo(String producerName, BlockingQueue queue) {
        this.producerName = producerName;
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true) {
            try {
                int task = r.nextInt(100);  //产生随机数
                queue.put(task);  //生产者向队列中放入一个随机数
                System.out.println(producerName + "开始生产任务：" + task);
                Thread.sleep(1000);  //减缓生产者生产的速度，如果队列为空，消费者就会阻塞不会进行消费直到有数据被生产出来
            } catch (InterruptedException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
