package com.example.interviewdemo.glide2;

import android.content.Context;

import com.example.interviewdemo.glide2.load.Engine;
import com.example.interviewdemo.glide2.request.RequestOptions;


/**
 * @author Lance
 * @date 2018/5/9
 */

public class GlideContext {

    Context context;
    RequestOptions defaultRequestOptions;
    Engine engine;
    Registry registry;

    public GlideContext(Context context, RequestOptions defaultRequestOptions, Engine engine,
                        Registry registry) {
        this.context = context;
        this.defaultRequestOptions = defaultRequestOptions;
        this.engine = engine;
        this.registry = registry;
    }

    public Context getContext() {
        return context;
    }


    public RequestOptions getDefaultRequestOptions() {
        return defaultRequestOptions;
    }

    public Engine getEngine() {
        return engine;
    }

    public Registry getRegistry() {
        return registry;
    }

    public Context getApplicationContext() {
        return context;
    }
}
