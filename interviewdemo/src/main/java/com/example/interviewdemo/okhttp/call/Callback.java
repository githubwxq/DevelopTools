package com.example.interviewdemo.okhttp.call;


import com.example.interviewdemo.okhttp.Response;

/**
 * Http回调接口
 */
public interface Callback {
    void onFailure(Call call, Throwable throwable);

    void onResponse(Call call, Response response);
}
