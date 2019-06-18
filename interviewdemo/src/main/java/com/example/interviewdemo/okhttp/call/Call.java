package com.example.interviewdemo.okhttp.call;

import com.example.interviewdemo.okhttp.HttpClient;
import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.chain.Interceptor;
import com.example.interviewdemo.okhttp.chain.InterceptorCallService;
import com.example.interviewdemo.okhttp.chain.InterceptorChain;
import com.example.interviewdemo.okhttp.chain.InterceptorConnection;
import com.example.interviewdemo.okhttp.chain.InterceptorHeaders;
import com.example.interviewdemo.okhttp.chain.InterceptorRetry;
import com.example.interviewdemo.okhttp.request.Request;

import java.io.IOException;
import java.util.ArrayList;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc: 请求任务
 * version:1.0
 */
public class Call {
    Request request;
    HttpClient client;

    //是否执行过
    boolean executed;
    //是否退出了  对整个请求来说的
    boolean canceled;



    public Call(HttpClient httpClient, Request request) {
     this.client=httpClient;
     this.request=request;
    }

    /**
     * 异步调用
     */
    public Call enqueue(Callback callback){
        synchronized (this){
            if(executed){
                throw new IllegalStateException("Already Executed");
            }else {
                executed = true;
            }
        }
        client.dispatcher().enqueue(new AsyncCall(callback));
        return this;
    }
    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Request request() {
        return request;
    }

    public HttpClient client() {
        return client;
    }


    /**
     * 异步请求任务
     */
    final class AsyncCall implements Runnable {
        private final Callback callback;  //每一个对应一个 异步结果
        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            //是否已经通知过callback
            boolean signalledCallback = false;
            try {
                Response response = getResponse();
                if (canceled) {
                    signalledCallback = true;
                    callback.onFailure(Call.this, new IOException("Canceled"));
                }else {
                    signalledCallback = true;
                    callback.onResponse(Call.this, response);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (!signalledCallback) {
                    callback.onFailure(Call.this, e);
                }

            }finally {
                //移除这一次的请求 因为已经完成了
                client.dispatcher().finished(this);
            }


        }

        public String host() {
            return request.url().getHost();
        }

    }

    /**
     * 获取返回值
     * @return
     */
    private Response getResponse() throws IOException {
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(client.interceptors);
        interceptors.add(new InterceptorRetry());
        interceptors.add(new InterceptorHeaders());
        interceptors.add(new InterceptorConnection());
        interceptors.add(new InterceptorCallService());
//       责任链 启动
        InterceptorChain interceptorChain=new InterceptorChain(interceptors,0,this,null);
        return interceptorChain.proceed();
    }



}
