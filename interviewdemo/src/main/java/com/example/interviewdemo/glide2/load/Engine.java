package com.example.interviewdemo.glide2.load;

import android.util.Log;

import com.example.interviewdemo.glide2.GlideContext;
import com.example.interviewdemo.glide2.cache.ActiveResources;
import com.example.interviewdemo.glide2.cache.DiskCache;
import com.example.interviewdemo.glide2.cache.Key;
import com.example.interviewdemo.glide2.cache.MemoryCache;
import com.example.interviewdemo.glide2.cache.recycle.BitmapPool;
import com.example.interviewdemo.glide2.cache.recycle.Resource;
import com.example.interviewdemo.glide2.request.ResourceCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class Engine implements MemoryCache.ResourceRemoveListener, Resource.ResourceListener,
        EngineJob.EngineJobListener {
    private static final String TAG = "Engine";


    public static class LoadStatus {
        private final EngineJob engineJob;
        private final ResourceCallback cb;

        LoadStatus(ResourceCallback cb, EngineJob engineJob) {
            this.cb = cb;
            this.engineJob = engineJob;
        }

        public void cancel() {
            engineJob.removeCallback(cb);
        }
    }

    private final DiskCache diskCache;
    private final BitmapPool bitmapPool;
    private final MemoryCache memoryCache;
    private final ThreadPoolExecutor threadPool;
    ActiveResources activeResources;
    Map<Key, EngineJob> jobs = new HashMap<>();

    public Engine(MemoryCache memoryCache, DiskCache diskCache, BitmapPool
            bitmapPool, ThreadPoolExecutor threadPool) {
        this.memoryCache = memoryCache;
        this.diskCache = diskCache;
        this.bitmapPool = bitmapPool;
        this.threadPool = threadPool;
        activeResources = new ActiveResources(this);
    }

    public void shutdown() {
        long shutdownSeconds = 5;
        threadPool.shutdown();
        try {
            //5s 需要停掉线程池
            if (!threadPool.awaitTermination(shutdownSeconds, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(shutdownSeconds, TimeUnit.SECONDS)) {
                    throw new RuntimeException("Failed to shutdown");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        diskCache.clear();
        activeResources.shutdown();
    }

    public LoadStatus load(GlideContext glideContext, Object model, int width, int height,
                           ResourceCallback cb) {
        EngineKey engineKey = new EngineKey(model, width, height);
        //1、先从活动资源当中查找对应的图片
        Resource resource = activeResources.get(engineKey);
        if (null != resource) {
            Log.e(TAG, "使用活跃缓存数据:" + resource);
            //引用数+1
            resource.acquire();
            cb.onResourceReady(resource);
            return null;
        }
        //2、从内存缓存当中找图片
        //从缓存移除 将它加入到活跃缓冲中
        resource = memoryCache.remove2(engineKey);
        if (null != resource) {
            Log.e(TAG, "使用内存缓存数据");
            // 加入正在使用集合 引用数+1
            activeResources.activate(engineKey, resource);
            resource.acquire();
            resource.setResourceListener(engineKey, this);
            cb.onResourceReady(resource);
            return null;
        }

        //3、文件缓存 或者 图片的源地址加载  IO操作

        // 从集合从检查是否有同样图片的加载工作
        // 如果存在 本次加载只需要等待上一次加载工作完成
        // 重复的请求 获得上一次的工作 并添加监听器
        // 请求完成 回调所有监听器
        EngineJob engineJob = jobs.get(engineKey);
        if (engineJob != null) {
            Log.e(TAG, "数据正在加载,添加数据加载状态监听");
            engineJob.addCallback(cb);
            return new LoadStatus(cb, engineJob);
        }
        // 创建一个新的加载任务
        engineJob = new EngineJob(threadPool, engineKey, this);
        engineJob.addCallback(cb);
        //加载任务
        DecodeJob decodeJob = new DecodeJob(glideContext, diskCache, model, width, height,
                engineJob);
        //启动加载任务
        engineJob.start(decodeJob);
        jobs.put(engineKey, engineJob);
        return new LoadStatus(cb, engineJob);
    }

    /**
     * 从内存缓存中移除回调
     * 将其加入复用池
     *
     * @param resource
     */
    @Override
    public void onResourceRemoved(Resource resource) {
        Log.e(TAG, "内存缓存移除，加入复用池");
        bitmapPool.put(resource.getBitmap());
    }

    /**
     * 引用计数为0回调
     * 将其从正在使用集合移除 并加入内存缓存
     *
     * @param key
     * @param resource
     */
    @Override
    public void onResourceReleased(Key key, Resource resource) {
        Log.e(TAG, "引用计数为0,移除活跃缓存，加入内存缓存:" + key);
        activeResources.deactivate(key);
        memoryCache.put(key, resource);
    }

    @Override
    public void onEngineJobComplete(EngineJob engineJob, Key key, Resource resource) {
        if (resource != null) {
            //设置引用计数为0(没有在使用了)的回调
            resource.setResourceListener(key, this);
            //加入活动缓存
            activeResources.activate(key, resource);
        }
        jobs.remove(key);
    }

    @Override
    public void onEngineJobCancelled(EngineJob engineJob, Key key) {
        jobs.remove(key);
    }
}
