package com.example.interviewdemo.thread;

import com.alibaba.fastjson.parser.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class TestThreadPool {

    public static void main(String[] args){

        ExecutorService pool= Executors.newFixedThreadPool(5);

//        List<Future<Integer>> list=new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Future<Integer> future=pool.submit(new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    int sum=0;
//                    for (int i = 0; i < 100; i++) {
//                        sum += i;
//                    }
//                    return sum;
//
//                }
//            });
//            list.add(future);
//
//        }
//
//        pool.shutdown();

//        for (Future<Integer> future : list) {
//            try {
//                System.out.println(future.get()+"结果");
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }



//        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
//
//        //2.为线程池的线程分配任务
//        for (int i = 0; i < 10; i++) {
//            pool.submit(threadPoolDemo);
//        }
//
//        //3.关闭线程池
//        pool.shutdown(); //平滑的关闭，等待现有任务执行完毕。shutdownNow()直接关闭。


        cache();


    }

    static class ThreadPoolDemo implements Runnable {

        private int i = 0;

        @Override
        public void run() {
            while (i < 100) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " : " + i++);
            }
        }
    }


  public static void cache(){
        ExecutorService pool=Executors.newCachedThreadPool();
      long start = System.currentTimeMillis();

      pool.execute(new Runnable() {
          @Override
          public void run() {
              int sum = 0;
              for (int i = 0; i < 10; i++) {
                  sum = sum+1;
                  System.out.println(Thread.currentThread().getName()+"======="+  sum);
              }
          }
      });

      System.out.println("cache: " + (System.currentTimeMillis() - start));
//      默认为60s未使用就被终止和移除
  }


    public static ExecutorService newSingleThreadExecutor() {
        return (new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>()));
    }







}



//
//五种线程池的适应场景
//
//        newCachedThreadPool：用来创建一个可以无限扩大的线程池，适用于服务器负载较轻，执行很多短期异步任务。
//        newFixedThreadPool：创建一个固定大小的线程池，因为采用无界的阻塞队列，所以实际线程数量永远不会变化，适用于可以预测线程数量的业务中，或者服务器负载较重，对当前线程数量进行限制。
//        newSingleThreadExecutor：创建一个单线程的线程池，适用于需要保证顺序执行各个任务，并且在任意时间点，不会有多个线程是活动的场景。
//        newScheduledThreadPool：可以延时启动，定时启动的线程池，适用于需要多个后台线程执行周期任务的场景。
//        newWorkStealingPool：创建一个拥有多个任务队列的线程池，可以减少连接数，创建当前可用cpu数量的线程来并行执行，适用于大耗时的操作，可以并行来执行
//
