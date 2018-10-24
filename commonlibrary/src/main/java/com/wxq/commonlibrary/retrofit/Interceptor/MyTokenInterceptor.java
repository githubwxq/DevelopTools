package com.wxq.commonlibrary.retrofit.Interceptor;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/23
 * desc: token拦截器
 * version:1.0
 */
public class MyTokenInterceptor implements Interceptor {

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
        Log.e("wxq","token_before_proceed");
        Response response= chain.proceed(request);
        Log.e("wxq","token_after_proceed");
        ResponseBody responseBody = response.body();
        //打印返回的值
        Log.e("wxq","mytoken_response.body"+ getResponseString(responseBody));
        String updateResult="{\"status\":200,\"errorMsg\":\"查询成功!\",\"content\":[{\"name\":\"wwww\"},{\"name\":\"小糖糖\"},{\"name\":\"王晓刚\"}],\"timeStamp\":1540271395854}";
        //中途修改返回的body
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody newResponseBody = clone.body();
        newResponseBody=ResponseBody.create(newResponseBody.contentType(),updateResult);
        Log.e("wxq","mytoken_newResponseBody.body"+ getResponseString(newResponseBody));
        return  response.newBuilder().body(newResponseBody).build();
    }

    private String getResponseString(ResponseBody responseBody) throws IOException {
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        charset = contentType.charset(UTF8);
        String resultBody=  buffer.clone().readString(charset);
        return resultBody;
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
