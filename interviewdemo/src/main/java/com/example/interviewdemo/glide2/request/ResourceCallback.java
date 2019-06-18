package com.example.interviewdemo.glide2.request;


import com.example.interviewdemo.glide2.cache.recycle.Resource;

/**
 * @author Lance
 * @date 2018/4/21
 */

public interface ResourceCallback {
    void onResourceReady(Resource reference);
}
