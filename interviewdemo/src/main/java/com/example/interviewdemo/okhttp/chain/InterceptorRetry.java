package com.example.interviewdemo.okhttp.chain;

import android.util.Log;

import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.call.Call;

import java.io.IOException;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class InterceptorRetry implements Interceptor  {
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot", "重试拦截器....");
        Call call = chain.call; // 创建的时候对应的每个call强求 在子线程里面调用的自身也被传递 过来了
        IOException exception = null;
        for (int i = 0; i < chain.call.client().retrys(); i++) {
            if (call.isCanceled()) {
                throw new IOException("Canceled");
            }
            try {
                //调用下一个拦截器  proceed()
                Response response = chain.proceed();
                Log.e("interceprot", "重试拦截器...."+i);

                return response;
            } catch (IOException e) {
                exception = e;
            }

        }

        throw exception;
    }
}
