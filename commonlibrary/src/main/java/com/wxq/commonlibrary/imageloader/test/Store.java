package com.wxq.commonlibrary.imageloader.test;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/29
 * desc:
 * version:1.0
 */
public class Store {
    private final int MAXSIZE = 2;

    private int count = 0;

    public synchronized void add() throws InterruptedException {
        while (count > MAXSIZE) {
            System.out.print("仓库满了====");
            System.out.println(Thread.currentThread().getName() + "are waiting。。。。。。。。");
            this.wait();
        }
        count++;
        System.out.println(Thread.currentThread().getName() + "存入仓库，当前货物数：" + count);
        this.notify(); //  消费线程唤醒它


    }

    public synchronized void remove() throws InterruptedException {

        if (count <= 0) {
            System.out.print("仓库 is empty");
            System.out.println(Thread.currentThread().getName() + "等待中。。。。");
            this.wait();

        }
        count--;
        System.out.println(Thread.currentThread().getName() + "取出货物，当前货物数：" + count);
        this.notify();//  消费线程唤醒它

    }

    class Producer extends Thread {
        private Store store;

        Producer(Store s) {
            store = s;
        }

        @Override
        public void run() {
            while (true) {

                try {
                    store.add();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }


    }

    public class Consume extends Thread {
        private Store store;

        public Consume(Store store) {
            this.store = store;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    store.remove();
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    public static void main(String[] args) {
        Store s = new Store();
        Thread producer1 = s.new Producer(s);//成员内部类需通过对象访问
        Thread producer2 = s.new Producer(s);//成员内部类需通过对象访问
        Thread consume1 = s.new Consume(s);
        Thread consume2 = s.new Consume(s);
        producer1.setName("producer1");
        producer2.setName("producer2");
        consume1.setName("consume1");
        consume2.setName("consume2");
        producer1.start();
        producer2.start();
        consume1.start();
        consume2.start();

    }


}
