package com.example.interviewdemo.glide2.cache;

import com.example.interviewdemo.glide2.cache.recycle.Resource;

/**
 * Created by Administrator on 2018/5/4.
 */

public interface MemoryCache {

    void clearMemory();

    void trimMemory(int level);

    interface ResourceRemoveListener{
        void onResourceRemoved(Resource resource);
    }

    Resource put(Key key, Resource resource);

    void setResourceRemoveListener(ResourceRemoveListener listener);

    Resource remove2(Key key);

}
