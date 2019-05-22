package com.example.design_pattern.concurrentthreads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/22
 * desc:
 * version:1.0
 */
public class BlockQueueDemo {

    public static void main(String[] args) {


        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(1); //定长为2的阻塞队列
        ExecutorService service = Executors.newCachedThreadPool();  //缓存线程池

//        创建3个生产者：
        ProducterDemo p1 = new ProducterDemo("车鉴定web端",queue);
        ProducterDemo p2 = new ProducterDemo("车鉴定APP端",queue);
        ProducterDemo p3 = new ProducterDemo("车鉴定接口端",queue);
        ProducterDemo p4 = new ProducterDemo("车鉴定M栈",queue);

        //创建三个消费者：
        ConsumerDemo c1 = new ConsumerDemo("消费者1111111",queue);
        ConsumerDemo c2 = new ConsumerDemo("消费者2222222",queue);
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
//        service.execute(c2);
//        try {
//            System.out.print("begin");
//            queue.put(1);
//            queue.put(2);
//            queue.put(3); // 主线程被线程阻塞了
////            queue.put(3);
////            queue.put(4);
////            queue.put(5);
//            System.out.print("stop");
//        } catch (InterruptedException e) {
//            System.out.print(e.getMessage());
//            e.printStackTrace();
//        }
////        System.out.print(queue.poll()+"取出一个");
//        for (Integer integer : queue) {
//            System.out.print(integer+"=========");
//        }

    }






}

/*
 *
什么是线程阻塞？

        在某一时刻某一个线程在运行一段代码的时候，这时候另一个线程也需要运行，但是在运行过程中的那个线程执行完成之前，另一个线程是无法获取到CPU执行权的（调用sleep方法是进入到睡眠暂停状态，但是CPU执行权并没有交出去，而调用wait方法则是将CPU执行权交给另一个线程），这个时候就会造成线程阻塞。


为什么会出现线程阻塞？

        1.睡眠状态：当一个线程执行代码的时候调用了sleep方法后，线程处于睡眠状态，需要设置一个睡眠时间，
        此时有其他线程需要执行时就会造成线程阻塞，而且sleep方法被调用之后，线程不会释放锁对象，也就是说锁还在该线程手里，CPU执行权还在自己手里，等睡眠时间一过，该线程就会进入就绪状态，典型的“占着茅坑不拉屎”；

        2.等待状态：当一个线程正在运行时，调用了wait方法，此时该线程需要交出CPU执行权，也就是将锁释放出去，交给另一个线程，该线程进入等待状态，但与睡眠状态不一样的是，进入等待状态的线程不需要设置睡眠时间，但是需要执行notify方法或者notifyall方法来对其唤醒，自己是不会主动醒来的，等被唤醒之后，
        该线程也会进入就绪状态，但是进入仅需状态的该线程手里是没有执行权的，也就是没有锁，而睡眠状态的线程一旦苏醒，进入就绪状态时是自己还拿着锁的。等待状态的线程苏醒后，就是典型的“物是人非，大权旁落“；

        3.礼让状态：当一个线程正在运行时，调用了yield方法之后，该线程会将执行权礼让给同等级的线程或者比它高一级的线程优先执行，
        此时该线程有可能只执行了一部分而此时把执行权礼让给了其他线程，这个时候也会进入阻塞状态，但是该线程会随时可能又被分配到执行权，这就很”中国化的线程“了，比较讲究谦让；

        4.自闭状态：当一个线程正在运行时，调用了一个join方法，此时该线程会进入阻塞状态，另一个线程会运行，
        直到运行结束后，原线程才会进入就绪状态。这个比较像是”走后门“，本来该先把你的事情解决完了再解决后边的人的事情，
        但是这时候有走后门的人，那就会停止给你解决，而优先把走后门的人事情解决了；

        5.suspend() 和 resume() ：这两个方法是配套使用的，suspend() 是让线程进入阻塞状态，它的解药就是resume()，
        没有resume()它自己是不会恢复的，由于这种比较容易出现死锁现象，所以jdk1.5之后就已经被废除了，
        这对就是相爱相杀的一对。
        ---------------------*/


