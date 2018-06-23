package com.wxq.commonlibrary.glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/5/11
 * @description
 */
public class ProgressInterceptor implements Interceptor {
    static final Map<String, OnLoadProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url, OnLoadProgressListener listener) {
        LISTENER_MAP.put(url, listener);
    }

    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }

    public static void clear() {
        LISTENER_MAP.clear();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        return response.newBuilder().body(new ProgressResponseBody(url, body)).build();
    }
}
