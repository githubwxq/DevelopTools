package com.example.interviewdemo.lock;
 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class SynchronizedTest {
    private static int i=0;
 
    public int getI()throws Exception{
        i++;
        Thread.sleep(100);
        return i;
    }
    /**
     * @Author Loujitao
     * @Date 2018/6/29
     * @Time  14:16
     * @Description:
     * 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁
     */
    public synchronized int getI1()throws Exception{
        i++;
        Thread.sleep(100);
        return i;
    }
    /*
    修饰静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
    * */
    public synchronized static int getI2()throws Exception{
        i++;
        Thread.sleep(100);
        return i;
    }
    /*
    修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
    * */
    public int getI3(Object o)throws Exception{
        synchronized (o){
            i++;
            Thread.sleep(100);
            return i;
        }
    }
 
    public static void main(String[] args) {
        //实例化本类实例，调用普通方法是用到。
        final SynchronizedTest syt=new SynchronizedTest();
        //创建了一个最大容量为5的线程池
        ExecutorService es= Executors.newFixedThreadPool(5);
        //这里的lock对象，是用作锁代码块是用的，做为所对象  别用字符串常量做锁；锁对象引用改变，会引发change锁事件，即立即释放锁
        final Object lock=new Object();
 
        for (int i=1;i<10;i++){
            es.execute(new Runnable() {
                @Override
                public void run() {
                   try {
//                     System.out.println(syt.getI());//调用非安全的方法
//                     System.out.println(syt.getI1());//调用加锁了的对象方法
//                     System.out.println(SynchronizedTest.getI2());//调用加锁的静态方法
                       System.out.println(syt.getI3(lock));//调用调用锁代码块的方法
                  } catch (Exception e) {
                      e.printStackTrace();
                 }
             }
            });
        }
        es.shutdown();
    }
}

//
//
//在java编程中，经常需要用到同步，而用得最多的也许是synchronized关键字了，下面看看这个关键字的用法。
//
//        因为synchronized关键字涉及到锁的概念，所以先来了解一些相关的锁知识。
//
//         
//
//        java的内置锁：每个java对象都可以用做一个实现同步的锁，这些锁成为内置锁。线程进入同步代码块或方法的时候会自动获得该锁，在退出同步代码块或方法时会释放该锁。获得内置锁的唯一途径就是进入这个锁的保护的同步代码块或方法。
//
//         
//
//        java内置锁是一个互斥锁，这就是意味着最多只有一个线程能够获得该锁，当线程A尝试去获得线程B持有的内置锁时，线程A必须等待或者阻塞，知道线程B释放这个锁，如果B线程不释放这个锁，那么A线程将永远等待下去。
//
//         
//
//        java的对象锁和类锁：java的对象锁和类锁在锁的概念上基本上和内置锁是一致的，但是，两个锁实际是有很大的区别的，对象锁是用于对象实例方法，或者一个对象实例上的，类锁是用于类的静态方法或者一个类的class对象上的。我们知道，类的对象实例可以有很多个，但是每个类只有一个class对象，所以不同对象实例的对象锁是互不干扰的，但是每个类只有一个类锁。但是有一点必须注意的是，其实类锁只是一个概念上的东西，并不是真实存在的，它只是用来帮助我们理解锁定实例方法和静态方法的区别的
//
//
//synchronized 
//        在修饰代码块的时候需要一个reference对象作为锁的对象. 
//        在修饰方法的时候默认是当前对象作为锁的对象. 
//        在修饰类时候默认是当前类的Class对象作为锁的对象.
//
//        线程同步的方法：sychronized、lock、reentrantLock分析 
//
//        上面已经对锁的一些概念有了一点了解，下面探讨synchronized关键字的用法。
//
//         synchronized的用法：synchronized修饰方法和synchronized修饰代码块。
//
//
//
//        一、对象锁（方法锁）实例与分析
//
//          类中非静态方法上的锁；用this做锁；
//
//
//
//        二、类锁实例与分析
//
//            类中静态方法上的锁；用XXX.class做锁；
//
//
//
//        三、引用对象作为锁，代码块实例与分析
//
//             用类中的成员变量引用做锁；
//        ---------------------

