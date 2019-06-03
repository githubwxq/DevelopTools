package com.example.interviewdemo.okhttp.chain;

import android.util.Log;

import com.example.interviewdemo.okhttp.Response;
import com.example.interviewdemo.okhttp.connection.HttpCodec;
import com.example.interviewdemo.okhttp.connection.HttpConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class InterceptorCallService  implements Interceptor{
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot", "通信拦截器....");
        HttpCodec httpCodec = chain.httpCodec;
        HttpConnection connection = chain.connection; //链接不是null
        InputStream is = connection.call(httpCodec);
        //HTTP/1.1 200 OK 空格隔开的响应状态
        String statusLine = httpCodec.readLine(is);
        //读取响应头
        Map<String, String> headers = httpCodec.readHeaders(is);
        //是否保持连接
        boolean isKeepAlive = false;
        if (headers.containsKey(HttpCodec.HEAD_CONNECTION)) {
            isKeepAlive = headers.get(HttpCodec.HEAD_CONNECTION).equalsIgnoreCase(HttpCodec
                    .HEAD_VALUE_KEEP_ALIVE);
        }
        int contentLength = -1;
        if (headers.containsKey(HttpCodec.HEAD_CONTENT_LENGTH)) {
            contentLength = Integer.valueOf(headers.get(HttpCodec.HEAD_CONTENT_LENGTH));
        }

        //分块编码数据
        boolean isChunked = false;
        if (headers.containsKey(HttpCodec.HEAD_TRANSFER_ENCODING)) {
            isChunked = headers.get(HttpCodec.HEAD_TRANSFER_ENCODING).equalsIgnoreCase(HttpCodec
                    .HEAD_VALUE_CHUNKED);
        }
        String body = null;
        if (contentLength > 0) {
            byte[] bytes = httpCodec.readBytes(is, contentLength);
            body = new String(bytes);
        } else if (isChunked) {
            body = httpCodec.readChunked(is);
        }
        String[] status = statusLine.split(" ");
        connection.updateLastUseTime();

        //返回最后该响应的结果响应结果

        return new Response(Integer.valueOf(status[1]), contentLength, headers, body, isKeepAlive);
    }
}
