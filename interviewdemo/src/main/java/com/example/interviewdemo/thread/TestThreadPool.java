package com.example.interviewdemo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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



//    这里是7个参数(我们在开发中用的更多的是5个参数的构造方法)，OK，那我们来看看这里七个参数的含义：
//
//        corePoolSize 线程池中核心线程的数量
//
//        maximumPoolSize 线程池中最大线程数量
//
//        keepAliveTime 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
//
//        unit 第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
//
//        workQueue 线程池中的任务队列，该队列主要用来存储已经被提交但是尚未执行的任务。存储在这里的任务是由ThreadPoolExecutor的execute方法提交来的。
//
//        threadFactory 为线程池提供创建新线程的功能，这个我们一般使用默认即可
//
//        handler 拒绝策略，当线程无法执行新任务时（一般是由于线程池中的线程数量已经达到最大数或者线程池关闭导致的），默认情况下，当线程池无法处理新线程时，会抛出一个RejectedExecutionException。
//
//        通过构造方法可以看出：
//
//        maximumPoolSize < corePoolSize的时候会抛出异常；
//
//        maximumPoolSize(最大线程数) = corePoolSize(核心线程数) + noCorePoolSize(非核心线程数)；
//
//        当currentSize<corePoolSize时，没什么好说的，直接启动一个核心线程并执行任务。
//
//        当currentSize>=corePoolSize、并且workQueue未满时，添加进来的任务会被安排到workQueue中等待执行。
//
//        当workQueue已满，但是currentSize<maximumPoolSize时，会立即开
//
//        启一个非核心线程来执行任务。
//
//        当currentSize>=corePoolSize、workQueue已满、并且currentSize>maximumPoolSize时，调用handler默认抛出RejectExecutionExpection异常。



//1．凡事得靠ThreadPoolExecutor（铺垫环节，懂的直接跳过）
//
//        Executor作为一个接口，它的具体实现就是ThreadPoolExecutor。
//
//        Android中的线程池都是直接或间接通过配置ThreadPoolExecutor来实现不同特性的线程池。
//
//        先介绍ThreadPoolExecutor的一个常用的构造方法。

       /*
 2 *@ ThreadPoolExecutor构造参数介绍
 3 *@author SEU_Calvin
 4 * @date 2016/09/03
 5 */
//        6 public ThreadPoolExecutor(
//        7 //核心线程数，除非allowCoreThreadTimeOut被设置为true，否则它闲着也不会死
//        8 int corePoolSize,
//        9 //最大线程数，活动线程数量超过它，后续任务就会排队
//        10 int maximumPoolSize,
//        11 //超时时长，作用于非核心线程（allowCoreThreadTimeOut被设置为true时也会同时作用于核心线程），闲置超时便被回收
//        12 long keepAliveTime,
//        13 //枚举类型，设置keepAliveTime的单位，有TimeUnit.MILLISECONDS（ms）、TimeUnit. SECONDS（s）等
//        14 TimeUnit unit,
//        15 //缓冲任务队列，线程池的execute方法会将Runnable对象存储起来
//        16 BlockingQueue<Runnable> workQueue,
//        17 //线程工厂接口，只有一个new Thread(Runnable r)方法，可为线程池创建新线程
//        18 ThreadFactory threadFactory)
//        ThreadPoolExecutor的各个参数所代表的特性注释中已经写的很清楚了，那么ThreadPoolExecutor执行任务时的心路历程是什么样的呢？（以下用currentSize表示线程池中当前线程数量）
//
//        （1）当currentSize<corePoolSize时，没什么好说的，直接启动一个核心线程并执行任务。
//
//        （2）当currentSize>=corePoolSize、并且workQueue未满时，添加进来的任务会被安排到workQueue中等待执行。
//
//        （3）当workQueue已满，但是currentSize<maximumPoolSize时，会立即开启一个非核心线程来执行任务。
//
//        （4）当currentSize>=corePoolSize、workQueue已满、并且currentSize>maximumPoolSize时，调用handler默认抛出RejectExecutionExpection异常。
//
//
//
//        2． Android中的四类线程池
//
//        Android中最常见的四类具有不同特性的线程池分别为FixThreadPool、CachedThreadPool、ScheduleThreadPool以及SingleThreadExecutor。
//
//
//
//        2.1     FixThreadPool（一堆人排队上公厕）
//
//        1 /*
// 2 *@FixThreadPool介绍
// 3 *@author SEU_Calvin
// 4 * @date 2016/09/03
// 5 */
//        6 public static ExecutorService newFixThreadPool(int nThreads){
//        7     return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//        8 }
//        9 //使用
//        10 Executors.newFixThreadPool(5).execute(r);
//        （1）从配置参数来看，FixThreadPool只有核心线程，并且数量固定的，也不会被回收，所有线程都活动时，因为队列没有限制大小，新任务会等待执行。
//
//        （2）【前方高能，笔者脑洞】FixThreadPool其实就像一堆人排队上公厕一样，可以无数多人排队，但是厕所位置就那么多，而且没人上时，厕所也不会被拆迁，哈哈o(∩_∩)o ，很形象吧。
//
//        （3）由于线程不会回收，FixThreadPool会更快地响应外界请求，这也很容易理解，就好像有人突然想上厕所，公厕不是现用现建的。
//
//
//
//        2.2     SingleThreadPool（公厕里只有一个坑位）
//
//        1 /*
// 2 *@SingleThreadPool介绍
// 3 *@author SEU_Calvin
// 4 * @date 2016/09/03
// 5 */
//        6 public static ExecutorService newSingleThreadPool (int nThreads){
//        7     return new FinalizableDelegatedExecutorService ( new ThreadPoolExecutor (1, 1, 0, TimeUnit. MILLISECONDS, new LinkedBlockingQueue<Runnable>()) );
//        8 }
//        9 //使用
//        10 Executors.newSingleThreadPool ().execute(r);
//        （1）从配置参数可以看出，SingleThreadPool只有一个核心线程，确保所有任务都在同一线程中按顺序完成。因此不需要处理线程同步的问题。
//
//        （2）【前方高能，笔者脑洞】可以把SingleThreadPool简单的理解为FixThreadPool的参数被手动设置为1的情况，即Executors.newFixThreadPool(1).execute(r)。所以SingleThreadPool可以理解为公厕里只有一个坑位，先来先上。为什么只有一个坑位呢，因为这个公厕是收费的，收费的大爷上年纪了，只能管理一个坑位，多了就管不过来了（线程同步问题）。
//
//
//
//        2.3     CachedThreadPool（一堆人去一家很大的咖啡馆喝咖啡）
//
//        1 /*
// 2 *@CachedThreadPool介绍
// 3 *@author SEU_Calvin
// 4 * @date 2016/09/03
// 5 */
//        6 public static ExecutorService newCachedThreadPool(int nThreads){
//        7     return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit. SECONDS, new SynchronousQueue<Runnable>());
//        8 }
//        9 //使用
//        10 Executors.newCachedThreadPool().execute(r);
//        （1）CachedThreadPool只有非核心线程，最大线程数非常大，所有线程都活动时，会为新任务创建新线程，否则利用空闲线程（60s空闲时间，过了就会被回收，所以线程池中有0个线程的可能）处理任务。
//
//        （2）任务队列SynchronousQueue相当于一个空集合，导致任何任务都会被立即执行。
//
//        （3）【前方高能，笔者脑洞】CachedThreadPool就像是一堆人去一个很大的咖啡馆喝咖啡，里面服务员也很多，随时去，随时都可以喝到咖啡。但是为了响应国家的“光盘行动”，一个人喝剩下的咖啡会被保留60秒，供新来的客人使用，哈哈哈哈哈，好恶心啊。如果你运气好，没有剩下的咖啡，你会得到一杯新咖啡。但是以前客人剩下的咖啡超过60秒，就变质了，会被服务员回收掉。
//
//        （4）比较适合执行大量的耗时较少的任务。喝咖啡人挺多的，喝的时间也不长。
//
//
//
//
//
//        2.4    ScheduledThreadPool（4个里面唯一一个有延迟执行和周期重复执行的线程池）
//
//        1 /*
// 2 *@ScheduledThreadPool介绍
// 3 *@author SEU_Calvin
// 4 * @date 2016/09/03
// 5 */
//        6 public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize){
//        7 return new ScheduledThreadPoolExecutor(corePoolSize);
//        8 }
//        9 public ScheduledThreadPoolExecutor(int corePoolSize){
//        10 super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedQueue ());
//        11 }
//        12 //使用，延迟1秒执行，每隔2秒执行一次Runnable r
//        13 Executors. newScheduledThreadPool (5).scheduleAtFixedRate(r, 1000, 2000, TimeUnit.MILLISECONDS);
//        （1）核心线程数固定，非核心线程（闲着没活干会被立即回收）数没有限制。
//
//        （2）从上面代码也可以看出，ScheduledThreadPool主要用于执行定时任务以及有固定周期的重复任务。
//
//
//
//        至此Android中最常见的四类不同特性的线程池内容总结完毕。

