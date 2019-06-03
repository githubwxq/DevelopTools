package com.example.interviewdemo.okhttp.request;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class Request {

    //请求头
    private Map<String, String> headers;
    //请求方法
    private String method;
    //请求地址
    private HttpUrl url;
    //请求体
    private RequestBody body;


    public Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public String method() {
        return method;
    }

    public HttpUrl url() {
        return url;
    }

    public RequestBody body() {
        return body;
    }

    public Map<String, String> headers() {
        return headers;
    }

    /**
     * 构建者模式，不必了解数据细节
     */
    public final static class Builder {

        HttpUrl url;
        Map<String, String> headers = new HashMap<>();
        String method;

        RequestBody body;

        public Builder url(String url) {
            try {
                this.url = new HttpUrl(url);
                return this;
            } catch (MalformedURLException e) {
                throw new IllegalStateException("Failed Http Url", e);
            }
        }

        public Builder addHeader(String name, String value) {
            headers.put(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.remove(name);
            return this;
        }

        public Builder get() {
            method = "GET";
            return this;
        }


        public Builder post(RequestBody body) {
            this.body = body;
            method = "POST";
            return this;
        }

        public Request build() {
            if (url == null) {
                throw new IllegalStateException("url == null");
            }
            if (TextUtils.isEmpty(method)) {
                method = "GET";
            }
            return new Request(this);
        }
    }
    }
