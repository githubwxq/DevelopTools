package com.wxq.commonlibrary.http.common;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * okhttp 拦截器 统一打印request和response
 */
public class HttpLogInterceptor implements Interceptor {
    private static final String TAG = HttpLogInterceptor.class.getSimpleName();
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        String rBody;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);
        String reqlog="发送请求: method：" + request.method()
                + "\nurl：" + request.url()
                + "\n请求头：" + request.headers()
                + "\n请求参数: " +  JsonUtil.formatJson(body);
        String resultlog="收到响应: code:" + response.code()
                + "\n请求url：" + response.request().url()
                + "\n请求body：" + body
                + "\nResponse: " +     JsonUtil.formatJson(rBody)         ;

        LogUtil.d("retrofit======log"+reqlog+"\n"+resultlog);
        return response;
    }
}
