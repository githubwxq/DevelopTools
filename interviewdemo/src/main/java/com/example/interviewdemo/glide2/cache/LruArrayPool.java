package com.example.interviewdemo.glide2.cache;

import android.support.v4.util.LruCache;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Lance
 * @date 2018/4/22
 */

public class LruArrayPool implements ArrayPool {

    public static final int ARRAY_POOL_SIZE_BYTES = 4 * 1024 * 1024;

    private final int maxSize;
    private LruCache<Integer, byte[]> cache;
    private final NavigableMap<Integer, Integer> sortedSizes = new TreeMap<>();
    //单个资源的与maxsize 最大比例
    private static final int SINGLE_ARRAY_MAX_SIZE_DIVISOR = 2;
    //溢出大小
    private final static int MAX_OVER_SIZE_MULTIPLE = 8;

    public LruArrayPool() {
        this(ARRAY_POOL_SIZE_BYTES);
    }

    public LruArrayPool(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LruCache<Integer, byte[]>(maxSize) {
            @Override
            protected int sizeOf(Integer key, byte[] value) {
                return value.length;
            }


            @Override
            protected void entryRemoved(boolean evicted, Integer key, byte[] oldValue, byte[]
                    newValue) {
                sortedSizes.remove(oldValue.length);
            }
        };
    }


    @Override
    public byte[] get(int len) {
        //获得等于或大于比len大的key
        Integer key = sortedSizes.ceilingKey(len);
        if (null != key) {
            //缓存中的大小只能比需要的大小溢出8倍
            if (key <= (MAX_OVER_SIZE_MULTIPLE * len)) {
                byte[] bytes = cache.remove(key);
                sortedSizes.remove(key);
                return bytes == null ? new byte[len] : bytes;
            }
        }
        return new byte[len];
    }

    @Override
    public void put(byte[] data) {
        int length = data.length;
        //太大了 不缓存
        if (!isSmallEnoughForReuse(length)) {
            return;
        }
        sortedSizes.put(length, 1);
        cache.put(length, data);
    }


    private boolean isSmallEnoughForReuse(int byteSize) {
        return byteSize <= maxSize / SINGLE_ARRAY_MAX_SIZE_DIVISOR;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void clearMemory() {
        cache.evictAll();
    }

    @Override
    public void trimMemory(int level) {
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearMemory();
        } else if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
                || level == android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL) {
            cache.trimToSize(maxSize / 2);
        }
    }
}
