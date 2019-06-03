package com.example.interviewdemo.okhttp.chain;

import android.util.Log;

import com.example.interviewdemo.okhttp.HttpClient;
import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.connection.HttpConnection;
import com.example.interviewdemo.okhttp.request.HttpUrl;
import com.example.interviewdemo.okhttp.request.Request;

import java.io.IOException;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class InterceptorConnection implements Interceptor{
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot","连接拦截器....");
        Request request = chain.call.request();
        HttpClient client = chain.call.client();
        HttpUrl url = request.url();
        String host = url.getHost();
        int port = url.getPort();

        HttpConnection connection = client.connectionPool().get(host, port);
        if (null == connection) {
            connection = new HttpConnection();
        } else {
            Log.e("call", "使用连接池......");
        }
        connection.setRequest(request);
        try {
            //调用下一个拦截器   此刻有connection 了 原先都是null
            Response response = chain.proceed(connection);
            if (response.isKeepAlive()) {
                client.connectionPool().put(connection);
            }
            return response;
        } catch (IOException e) {
            throw e;
        }
    }
}
