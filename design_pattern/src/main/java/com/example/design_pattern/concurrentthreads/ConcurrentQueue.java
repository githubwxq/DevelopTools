package com.example.design_pattern.concurrentthreads;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/22
 * desc:
 * version:1.0
 */
public class ConcurrentQueue {

    public static void main(String[] args) {

        ConcurrentLinkedQueue q = new ConcurrentLinkedQueue();
        q.offer("张三");
        q.offer("李四");
        q.offer("王五");
        q.offer("赵六");
        q.offer("大圣");
        //从头获取元素,删除该元素
        System.out.println(q.poll());
        //从头获取元素,不刪除该元素
        System.out.println(q.peek());
        //获取总长度
        System.out.println(q.size());
        ToyotaYQ yq = new ToyotaYQ();

        /**
         * 多个线程同时去消费 队列  并发队列
         */
        new Thread(yq, "ToyotaYQ_001").start();
        new Thread(yq, "ToyotaYQ_002").start();
        new Thread(yq, "ToyotaYQ_003").start();
        new Thread(yq, "ToyotaYQ_004").start();
        new Thread(yq, "ToyotaYQ_005").start();
        new Thread(yq, "ToyotaYQ_006").start();

    }


}
