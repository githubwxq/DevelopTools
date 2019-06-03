package com.example.interviewdemo.okhttp.connection;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:连接池
 * version:1.0
 */
public class HttpConnectionPool {

    /**
     * 垃圾回收线程
     * 设置为守护线程(主线程退出后没有用户线程则会销毁)
     */
    private static ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread result = new Thread(runnable, "HttpClient HttpConnectionPool");
            result.setDaemon(true);
            return result;
        }
    };
    private static final Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
            Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), threadFactory);
    /**
     * 每个连接的最大存活时间
     */
    private final long keepAliveDuration;



    private final Runnable cleanupRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                long waitTimes = cleanup(System.currentTimeMillis());
                if (waitTimes == -1) {
                    return;
                }
                if (waitTimes > 0) {
                    synchronized (HttpConnectionPool.this) {
                        try {
                            HttpConnectionPool.this.wait(waitTimes);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        }
    };


    private final Deque<HttpConnection> connections = new ArrayDeque<>();
    private boolean cleanupRunning;

    public HttpConnectionPool() {
        this(1, TimeUnit.MINUTES);
    }

    public HttpConnectionPool(long keepAliveDuration, TimeUnit timeUnit) {
        //毫秒
        this.keepAliveDuration = timeUnit.toMillis(keepAliveDuration);
    }

    /**
     * 获取相同主机名和端口号的Http连接
     */
    public HttpConnection get(String host, int port) {
        Iterator<HttpConnection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            HttpConnection connection = iterator.next();
            //查连接是否复用( 同样的host )
            if (connection.isSameAddress(host, port)) {
                //正在使用的移出连接池
                iterator.remove();
                return connection;
            }
        }
        return null;
    }


    public void put(HttpConnection connection) {
        //执行检测清理
        if (!cleanupRunning) {
            cleanupRunning = true;
            executor.execute(cleanupRunnable);
        }
        connections.add(connection);
    }

    /**
     * 检查需要移除的连接返回下次检查时间
     */
    long cleanup(long now) {
        long longestIdleDuration = -1;
        synchronized (this) {
            for (Iterator<HttpConnection> i = connections.iterator(); i.hasNext(); ) {
                HttpConnection connection = i.next();
                //获得闲置时间 多长时间没使用这个了
                long idleDuration = now - connection.lastUsetime;
                //如果闲置时间超过允许
                if (idleDuration > keepAliveDuration) {
                    connection.closeQuietly();
                    i.remove();
                    Log.e("Pool", "移出连接池");
                    continue;
                }
                //获得最大闲置时间
                if (longestIdleDuration < idleDuration) {
                    longestIdleDuration = idleDuration;
                }
            }
            //下次检查时间
            if (longestIdleDuration >= 0) {
                return keepAliveDuration - longestIdleDuration;
            } else {
                //连接池没有连接 可以退出
                cleanupRunning = false;
                return longestIdleDuration;
            }
        }
    }

}
