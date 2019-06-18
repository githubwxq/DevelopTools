package com.example.interviewdemo.glide2.cache;

import com.example.interviewdemo.glide2.cache.recycle.BitmapPool;
import com.example.interviewdemo.glide2.cache.recycle.LruBitmapPool;
import com.example.interviewdemo.glide2.cache.recycle.Resource;

/**
 * Created by Administrator on 2018/5/4.
 */

public class CacheTest implements Resource.ResourceListener, MemoryCache.ResourceRemoveListener {

    LruMemoryCache lruMemoryCache;
    ActiveResources activeResource;
    BitmapPool bitmapPool;

    public Resource test(Key key) {
        bitmapPool = new LruBitmapPool(10);
        //内存缓存
        lruMemoryCache = new LruMemoryCache(10);
        lruMemoryCache.setResourceRemoveListener(this);
        //活动资源缓存
        activeResource = new ActiveResources(this);

        /**
         * 第一步 从活动资源中查找是否有正在使用的图片
         */
        Resource resource = activeResource.get(key);
        if (null != resource) {
            //当不使用的时候 release
            resource.acquire();
            return resource;
        }
        /**
         * 第二步 从内存缓存中查找
         */
        resource = lruMemoryCache.get(key);
        if (null != resource) {
            //1.为什么从内存缓存移除？
            // 因为lru可能移除此图片 我们也可能recycle掉此图片
            // 如果不移除，则下次使用此图片从活动资源中能找到，但是这个图片可能被recycle掉了
            lruMemoryCache.remove2(key);
            resource.acquire();
            activeResource.activate(key, resource);
            return resource;
        }
        return null;
    }

    /**
     * 这个资源没有正在使用了
     * 将其从活动资源移除
     * 重新加入到内存缓存中
     *
     * @param key
     * @param resource
     */
    @Override
    public void onResourceReleased(Key key, Resource resource) {
        activeResource.deactivate(key);
        lruMemoryCache.put(key, resource);
    }

    /**
     * 从内存缓存被动移除 回调
     * 放入 复用池
     *
     * @param resource
     */
    @Override
    public void onResourceRemoved(Resource resource) {
        bitmapPool.put(resource.getBitmap());
    }
}
