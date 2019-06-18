package com.example.interviewdemo.glide2;

import android.widget.ImageView;

import com.example.interviewdemo.glide2.request.Request;
import com.example.interviewdemo.glide2.request.RequestOptions;

import java.io.File;

/**
 * Created by Administrator on 2018/5/9.
 */

public class RequestBuilder {

    private final GlideContext glideContext;
    private RequestOptions requestOptions;
    private RequestManager requestManager;
    private Object model;

    public RequestBuilder(GlideContext glideContext, RequestManager requestManager) {
        this.glideContext = glideContext;
        this.requestManager = requestManager;
        this.requestOptions = glideContext.defaultRequestOptions;
    }

    public RequestBuilder apply(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
        return this;
    }

    public RequestBuilder load(String string) {
        model = string;
        return this;
    }

    public RequestBuilder load(File file) {
        model = file;
        return this;
    }

    /**
     * 加载图片并设置到ImageView当中
     *
     * @param view
     */
    public void into(ImageView view) {
        //将View交给Target
        Target target = new Target(view);
        //图片加载与设置
        Request request = new Request(glideContext,requestOptions, model, target);
        //Request交给 RequestManager 管理
        requestManager.track(request);
    }
}
