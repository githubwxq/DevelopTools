package com.example.interviewdemo.okhttp.chain;

import com.example.interviewdemo.okhttp.Response;

import java.io.IOException;

/**
 * 拦截器接口
 */
public interface Interceptor {



    Response intercept(InterceptorChain chain) throws IOException;
}
