package com.example.interviewdemo.glide2;

import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.example.interviewdemo.glide2.cache.ArrayPool;
import com.example.interviewdemo.glide2.cache.MemoryCache;
import com.example.interviewdemo.glide2.cache.recycle.BitmapPool;
import com.example.interviewdemo.glide2.load.Engine;
import com.example.interviewdemo.glide2.load.codec.StreamBitmapDecoder;
import com.example.interviewdemo.glide2.load.model.FileLoader;
import com.example.interviewdemo.glide2.load.model.FileUriLoader;
import com.example.interviewdemo.glide2.load.model.HttpUriLoader;
import com.example.interviewdemo.glide2.load.model.StringModelLoader;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/4.
 */

public class Glide implements ComponentCallbacks2 {

    private final MemoryCache memoryCache;
    private final BitmapPool bitmapPool;
    private final ArrayPool arrayPool;
    //
    private final RequestManagerRetriever requestManagerRetriever;

    private static Glide glide;


    private final Engine engine;
    private final GlideContext glideContext;

    protected Glide(Context context, GlideBuilder builder) {

        memoryCache = builder.memoryCache;
        bitmapPool = builder.bitmapPool;
        arrayPool = builder.arrayPool;
        //注册机
        Registry registry = new Registry();
        ContentResolver contentResolver = context.getContentResolver();
        registry.add(String.class, InputStream.class, new StringModelLoader.StreamFactory())
                .add(Uri.class, InputStream.class, new HttpUriLoader.Factory())
                .add(Uri.class, InputStream.class, new FileUriLoader.Factory(contentResolver))
                .add(File.class, InputStream.class, new FileLoader.Factory())
                .register(InputStream.class, new StreamBitmapDecoder(bitmapPool, arrayPool));

        engine = builder.engine;
        glideContext = new GlideContext(context, builder.defaultRequestOptions,
                engine, registry);

        requestManagerRetriever = new RequestManagerRetriever(glideContext);
    }

    /**
     * 默认实现
     *
     * @param context
     * @return
     */
    private static Glide get(Context context) {
        if (null == glide) {
            synchronized (Glide.class) {
                if (null == glide) {
                    init(context, new GlideBuilder());
                }
            }
        }
        return glide;
    }


    /**
     * 使用者可以定制自己的 GlideBuilder
     *
     * @param context
     * @param builder
     */
    public static void init(Context context, GlideBuilder builder) {
        if (Glide.glide != null) {
            tearDown();
        }
        Context applicationContext = context.getApplicationContext();
        Glide glide = builder.build(applicationContext);
        applicationContext.registerComponentCallbacks(glide);
        Glide.glide = glide;
    }

    public static synchronized void tearDown() {
        if (glide != null) {
            glide.glideContext
                    .getApplicationContext()
                    .unregisterComponentCallbacks(glide);
            glide.engine.shutdown();
        }
        glide = null;
    }

    public static RequestManager with(FragmentActivity activity) {
        return Glide.get(activity).requestManagerRetriever.get(activity);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    /**
     * 内存紧张
     */
    @Override
    public void onLowMemory() {
        //memory和bitmappool顺序不能变
        //因为memory移除后会加入复用池
        memoryCache.clearMemory();
        bitmapPool.clearMemory();
        arrayPool.clearMemory();
    }

    /**
     * 需要释放内存
     *
     * @param level 内存状态
     */
    @Override
    public void onTrimMemory(int level) {
        memoryCache.trimMemory(level);
        bitmapPool.trimMemory(level);
        arrayPool.trimMemory(level);
    }


}
