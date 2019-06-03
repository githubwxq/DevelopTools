package com.example.interviewdemo.okhttp.chain;

import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.call.Call;
import com.example.interviewdemo.okhttp.connection.HttpCodec;
import com.example.interviewdemo.okhttp.connection.HttpConnection;

import java.io.IOException;
import java.util.List;




/**
 * 拦截链。
 * 责任链会调用拦截器，
 * 而每个拦截器都会调用下一个拦截器获取Response，
 * 调用之前做请求初始化操作，
 * 调用之后做响应处理操作。
 * 类似递归。
 */
public class InterceptorChain {

    final List<Interceptor> interceptors;
    final int index;
    final Call call;
    final HttpConnection connection;

    final HttpCodec httpCodec = new HttpCodec();

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call, HttpConnection connection) {
        this.interceptors = interceptors;
        this.index = index;
        this.call = call;
        this.connection = connection;
    }


    /**
     * 每个拦截器都条用给下面这个方法每次
     * @return
     * @throws IOException
     */
    public Response proceed() throws IOException {
        return proceed(connection);
    }

    /**
     * 调用拦截器，获取Response
     */
    public Response proceed(HttpConnection connection) throws IOException {
        Interceptor interceptor = interceptors.get(index); // 获取不同的拦截 每次获取另一个拦截器 next
        // 下一个链对象
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call, connection);
        Response response = interceptor.intercept(next); // next 另一个责任连对象
        return response;
    }

}
