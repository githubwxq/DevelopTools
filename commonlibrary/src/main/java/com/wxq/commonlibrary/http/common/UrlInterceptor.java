package com.wxq.commonlibrary.http.common;


import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UrlInterceptor implements Interceptor {
    private String TAG = "UrlInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Log.e(TAG, "----------Request Start----------------");
        Log.e(TAG, "| OldUrl=" + request.url().toString());
        List<String> mark = request.headers("url_mark");

        HttpUrl newUrl = null;
        if (mark != null && mark.size() > 0) {
            Log.e(TAG, "| Head=" + mark.get(0));
            if (mark.get(0).equals("1")) {
                newUrl = HttpUrl.parse("http://www.baidu.com/");
            } else if (mark.get(0).equals("2")) {
                newUrl = HttpUrl.parse("http://www.github.com/");
            } else {
                newUrl = request.url();
            }
            request = request.newBuilder().url(newUrl).build();
        }


        Log.e(TAG, "| NewUrl=" + request.url().toString());

        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();


        Log.e(TAG, "| " + request.toString());
        Log.e(TAG, "| Response:" + content);
        Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
//通过添加注解heard 判断url  @Headers({"url_mark:1"}) 添加heard标识  拦截器获取heard