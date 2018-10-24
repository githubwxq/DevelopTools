package com.wxq.commonlibrary.retrofit.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/10/24
 * desc://模拟的错误的返回值
 * version:1.0
 */
public class TestNetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String url = request.url().toString();
        System.out.println("url=" + url);
        String responseString="{\"status\":200,\"errorMsg\":\"查询成功!\",\"content\":[{\"name\":\"error\"},{\"name\":\"error\"},{\"name\":\"王晓刚\"}],\"timeStamp\":1540271395854}";
        Response response = null;
        response=new Response.Builder().code(200).request(request).protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"),responseString)).build();
        return response;
    }
}
