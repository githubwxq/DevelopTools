package com.example.interviewdemo.okhttp;


import com.example.interviewdemo.okhttp.call.Call;
import com.example.interviewdemo.okhttp.call.Dispatcher;
import com.example.interviewdemo.okhttp.chain.Interceptor;
import com.example.interviewdemo.okhttp.connection.HttpConnectionPool;
import com.example.interviewdemo.okhttp.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class HttpClient {
    //调度器，用来分发请求任务
    private Dispatcher dispatcher;

    //连接池，用来管理Socket连接
    private HttpConnectionPool httpConnectionPool;
    //请求重试次数，默认重试3次
    private int retrys;

    //拦截器
    public List<Interceptor> interceptors = new ArrayList<>();


    public int retrys() {
        return retrys;
    }


    public HttpClient(Builder builder){
        dispatcher= builder.dispatcher;
        httpConnectionPool = builder.httpConnectionPool;
        retrys = builder.retrys;
        interceptors = builder.interceptors;
    }
    public Call newCall(Request request) {
        return new Call(this, request);
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }


    public static  final class Builder{
        //调度器，用来分发请求任务
        Dispatcher dispatcher;
        //连接池，用来管理Socket连接
        HttpConnectionPool httpConnectionPool;
        //请求重试次数，默认重试3次
        int retrys = 1;
        //拦截器
        List<Interceptor> interceptors = new ArrayList<>();

        public Builder dispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder connectionPool(HttpConnectionPool httpConnectionPool) {
            this.httpConnectionPool = httpConnectionPool;
            return this;
        }

        public Builder retrys(int retrys) {
            this.retrys = retrys;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        /**
         * 创建HttpClient
         */
        public HttpClient build() {
            if (null == dispatcher) {
                dispatcher = new Dispatcher();
            }
            if (null == httpConnectionPool) {
                httpConnectionPool = new HttpConnectionPool();
            }
            return new HttpClient(this);
        }




    }





    public HttpConnectionPool connectionPool() {
        return httpConnectionPool;
    }


}


//
//OkHttpClient
//
//        Factory for calls, which can be used to send HTTP requests and read their responses.
//        工厂生产者，负责生产calls
//
//        Request
//
//        OkHttp的请求，通过Request.Builder().build创建, Request.Builder()配置请求信息，如请求方式get/post等、请求参数RequestBody、请求头header
//
//        Call
//
//        调度者，Call作顶级接口，具体实现由RealCall负责。负责Request和Response的桥梁作用，将Request以execute()同步的方式执行输出Response, 或将Request以enqueue(callback)异步的方式加入调度。
//
//        Dispatcher(ThreadPoolExecutor)
//
//        调度线程池Disptcher实现了高并发，低阻塞的实现。采用Deque作为缓存，先进先出的顺序执行
//        任务在try/finally中调用了finished函数，控制任务队列的执行顺序，而不是采用锁，减少了编码复杂性提高性能
//        只有当Call以异步请求的方式，才触发Dispatcher的调度工作。
//
//        Response



