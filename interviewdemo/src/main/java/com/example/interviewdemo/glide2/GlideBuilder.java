package com.example.interviewdemo.glide2;

import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;

import com.example.interviewdemo.glide2.cache.ArrayPool;
import com.example.interviewdemo.glide2.cache.DiskCache;
import com.example.interviewdemo.glide2.cache.DiskLruCacheWrapper;
import com.example.interviewdemo.glide2.cache.LruArrayPool;
import com.example.interviewdemo.glide2.cache.LruMemoryCache;
import com.example.interviewdemo.glide2.cache.MemoryCache;
import com.example.interviewdemo.glide2.cache.recycle.BitmapPool;
import com.example.interviewdemo.glide2.cache.recycle.LruBitmapPool;
import com.example.interviewdemo.glide2.load.Engine;
import com.example.interviewdemo.glide2.load.GlideExecutor;
import com.example.interviewdemo.glide2.request.RequestOptions;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2018/5/9.
 */

public class GlideBuilder {

    MemoryCache memoryCache;
    DiskCache diskCache;
    BitmapPool bitmapPool;
    //进行数组的缓存
    ArrayPool arrayPool;
    RequestOptions defaultRequestOptions = new RequestOptions();
    ThreadPoolExecutor executor;
    Engine engine;

    public void setMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public void setDiskCache(DiskCache diskCache) {
        this.diskCache = diskCache;
    }

    public void setBitmapPool(BitmapPool bitmapPool) {
        this.bitmapPool = bitmapPool;
    }

    public void setArrayPool(ArrayPool arrayPool) {
        this.arrayPool = arrayPool;
    }

    private static int getMaxSize(ActivityManager activityManager) {
        //使用最大可用内存的0.4作为缓存使用  64M
        final int memoryClassBytes = activityManager.getMemoryClass() * 1024 * 1024;
        return Math.round(memoryClassBytes * 0.4f);
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public void setDefaultRequestOptions(RequestOptions defaultRequestOptions) {
        this.defaultRequestOptions = defaultRequestOptions;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }


    public Glide build(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        //Glide缓存最大可用内存大小
        int maxSize = getMaxSize(activityManager);
        if (null == arrayPool){
            arrayPool = new LruArrayPool();
        }
        //减去数组缓存后的可用内存大小
        int availableSize = maxSize - arrayPool.getMaxSize();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        // 获得一个屏幕大小的argb所占的内存大小
        int screenSize = widthPixels * heightPixels * 4;

        //bitmap复用占 4份
        float bitmapPoolSize = screenSize * 4.0f;
        //内存缓存占 2份
        float memoryCacheSize = screenSize * 2.0f;

        if (bitmapPoolSize + memoryCacheSize <= availableSize) {
            bitmapPoolSize = Math.round(bitmapPoolSize);
            memoryCacheSize = Math.round(memoryCacheSize);
        } else {
            //把总内存分成 6分
            float part = availableSize / 6.0f;
            bitmapPoolSize = Math.round(part * 4);
            memoryCacheSize = Math.round(part * 2);
        }
        if (null == bitmapPool) {
            bitmapPool = new LruBitmapPool((int) bitmapPoolSize);
        }
        if (null == memoryCache) {
            memoryCache = new LruMemoryCache((int) memoryCacheSize);
        }
        if (null == diskCache) {
            diskCache = new DiskLruCacheWrapper(context);
        }
        if (executor == null) {
            executor = GlideExecutor.newExecutor();
        }
        engine = new Engine(memoryCache, diskCache, bitmapPool, executor);
        memoryCache.setResourceRemoveListener(engine);
        return new Glide(context, this);
    }

}
