package com.example.interviewdemo.okhttp.chain;

import android.util.Log;

import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.connection.HttpCodec;
import com.example.interviewdemo.okhttp.request.Request;

import java.io.IOException;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class InterceptorHeaders implements Interceptor {
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot", "Http头拦截器....");
        Request request = chain.call.request();  //一个call对应一个请求
        Map<String, String> headers = request.headers();
        headers.put(HttpCodec.HEAD_HOST, request.url().getHost());
        headers.put(HttpCodec.HEAD_CONNECTION, HttpCodec.HEAD_VALUE_KEEP_ALIVE);
        if (null != request.body()) {
//            、、post请求
            String contentType = request.body().contentType();

            if (contentType != null) {
                headers.put(HttpCodec.HEAD_CONTENT_TYPE, contentType);
            }
            long contentLength = request.body().contentLength();
            if (contentLength != -1) {
                headers.put(HttpCodec.HEAD_CONTENT_LENGTH, Long.toString(contentLength));
            }
        }

        //调用下一个拦截器
        return chain.proceed();
    }
}
