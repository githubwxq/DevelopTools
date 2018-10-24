package com.wxq.commonlibrary.retrofit.Interceptor;

import android.util.Log;

import com.wxq.commonlibrary.util.TimeUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/23
 * desc: 处理打印请求日志
 * version:1.0
 */
public class MyLogInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        //日志拦截
        Request request = chain.request();
        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                Log.e("wxq","\t" + name + ": " + headers.value(i));
            }
        }
        Log.e("wxq","mylog_before_proceed");
        Response response= chain.proceed(request);

        Log.e("wxq","mylog_after_proceed");
        Log.e("wxq","mylog_response.body"+response.body().toString());
        return response;
    }

    public static String bodyToString(Request request) {
        try {
            RequestBody body = request.body();
            if (body == null) {
                return "";
            }
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            return buffer.readString(charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) {
            charset = UTF8;
        }
        return charset;
    }
}
