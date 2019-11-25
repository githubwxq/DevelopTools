package com.example.interviewdemo.glide2.cache;

import android.content.ComponentCallbacks2;
import android.os.Build;
import androidx.collection.LruCache;

import com.example.interviewdemo.glide2.cache.recycle.Resource;

/**
 * Created by Administrator on 2018/5/4.
 */

public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCache {

    private ResourceRemoveListener listener;
    private boolean isRemoved;
    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(Key key, Resource value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //当在4.4以上手机复用的时候 需要通过此函数获得占用内存
            return value.getBitmap().getAllocationByteCount();
        }
        return value.getBitmap().getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, Key key, Resource oldValue, Resource newValue) {
        //给复用池使用
        if (null != listener && null != oldValue && !isRemoved) {
            listener.onResourceRemoved(oldValue);
        }
    }


    @Override
    public Resource remove2(Key key) {
        // 如果是主动移除的不会掉 listener.onResourceRemoved
        isRemoved = true;
        Resource remove = remove(key);
        isRemoved = false;
        return remove;
    }

    @Override
    public void clearMemory() {
        evictAll();
    }

    @Override
    public void trimMemory(int level) {
        //程序进入后台 进程加入lru列表 系统资源紧张时根据lru列表杀死进程
        if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearMemory();
            //程序所有ui被隐藏  应该释放一部分资源
        } else if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            trimToSize(maxSize() / 2);
        }
    }

    /**
     * 资源移除监听
     *
     * @param listener
     */
    @Override
    public void setResourceRemoveListener(ResourceRemoveListener listener) {
        this.listener = listener;
    }
}
