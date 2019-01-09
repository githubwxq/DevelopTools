package com.wxq.commonlibrary.http.common;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/24
 * desc:
 * version:1.0
 */
public class RetryIntercepter implements Interceptor {
    public int maxRetry;//最大重试次数
    private int retryNum = 3;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）
    public RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("retryNum=" + retryNum);
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            System.out.println("retryNum=" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}
