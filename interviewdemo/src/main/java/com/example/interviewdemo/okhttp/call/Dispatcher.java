package com.example.interviewdemo.okhttp.call;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:调度器，管理所有的请求任务
 * version:1.0
 */
public class Dispatcher {
    //最多同时请求
    private int maxRequests;

    //同一个host同时最多请求
    private int maxRequestsPerHost;

    public Dispatcher() {
        this(15, 2);
    }

    public Dispatcher(int maxRequests, int maxRequestsPerHost) {
        this.maxRequests = maxRequests;
        this.maxRequestsPerHost = maxRequestsPerHost;
    }


    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 等待执行队列
     */
    private final Deque<Call.AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    /**
     * 正在执行队列
     */
    private final Deque<Call.AsyncCall> runningAsyncCalls = new ArrayDeque<>();


    public synchronized ExecutorService executorService() {
        if (executorService==null) {
            ThreadFactory threadFactory = new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread result = new Thread(runnable, "Http Client");
                    return result;
                }
            };
            executorService=new ThreadPoolExecutor(0,Integer.MAX_VALUE,60,
                    TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),threadFactory);

        }


return executorService;

    }







    public void enqueue(Call.AsyncCall call) {
        Log.e("Dispatcher", "同时有:" + runningAsyncCalls.size());
        Log.e("Dispatcher", "host同时有:" + runningCallsForHost(call));
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            Log.e("Dispatcher", "提交执行");
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            Log.e("Dispatcher", "等待执行");
            readyAsyncCalls.add(call);
        }
    }


    /**
     * 同一host 的 同时请求数
     *
     * @param call
     * @return
     */
    private int runningCallsForHost(Call.AsyncCall call) {
        int result = 0;
        //如果执行这个请求，则相同的host数量是result
        for (Call.AsyncCall c : runningAsyncCalls) {
            if (c.host().equals(call.host())) {
                result++;
            }
        }
        return result;
    }
    /**
     * 请求结束 移出正在运行队列
     * 并判断是否执行等待队列中的请求
     *
     * @param asyncCall
     */
    public void finished(Call.AsyncCall asyncCall) {

        synchronized (this) {
            runningAsyncCalls.remove(asyncCall);
            //判断是否执行等待队列中的请求
            promoteCalls();
        }


    }

    private void promoteCalls() {
        //同时请求达到上限
        if (runningAsyncCalls.size() >= maxRequests) {
            return;
        }

        //没有等待执行请求 请求是空的 没必要处理等待中的队列了
        if (readyAsyncCalls.isEmpty()) {
            return;
        }

        for (Iterator<Call.AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            Call.AsyncCall call = i.next();
            //同一host同时请求为达上限
            if (runningCallsForHost(call) < maxRequestsPerHost) {
                i.remove();
                runningAsyncCalls.add(call);
                executorService().execute(call);
            }
            if (runningAsyncCalls.size()>=maxRequests) {
                return;
            }

        }



    }
}
