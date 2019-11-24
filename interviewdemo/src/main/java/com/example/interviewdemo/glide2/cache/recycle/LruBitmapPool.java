package com.example.interviewdemo.glide2.cache.recycle;

import android.content.ComponentCallbacks2;
import android.graphics.Bitmap;
import androidx.collection.LruCache;


import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/5/4.
 */

public class LruBitmapPool extends LruCache<Integer, Bitmap> implements BitmapPool {
    private boolean isRemoved;

    // 负责筛选
    NavigableMap<Integer, Integer> map = new TreeMap<>();

    private final static int MAX_OVER_SIZE_MULTIPLE = 2;

    public LruBitmapPool(int maxSize) {
        super(maxSize);
    }

    /**
     * 将Bitmap放入复用池
     *
     * @param bitmap
     */
    @Override
    public void put(Bitmap bitmap) {
        //isMutable 必须是true
        if (!bitmap.isMutable()) {
            bitmap.recycle();
            return;
        }
        int size = bitmap.getAllocationByteCount();
        if (size >= maxSize()) {
            bitmap.recycle();
            return;
        }
        put(size, bitmap);
        map.put(size, 0);
    }

    /**
     * 获得一个可复用的Bitmap
     */
    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        //新Bitmap需要的内存大小  (只关心 argb8888和RGB65)
        int size = width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
        //获得等于 size或者大于size的key
        Integer key = map.ceilingKey(size);
        //从key集合从找到一个>=size并且 <= size*MAX_OVER_SIZE_MULTIPLE
        if (null != key && key <= size * MAX_OVER_SIZE_MULTIPLE) {
            isRemoved = true;
            Bitmap remove = remove(key);
            isRemoved = false;
            return remove;
        }
        return null;
    }


    @Override
    protected int sizeOf(Integer key, Bitmap value) {
        return value.getAllocationByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, Integer key, Bitmap oldValue, Bitmap newValue) {
        map.remove(key);
        if (!isRemoved)
            oldValue.recycle();
    }

    @Override
    public void clearMemory() {
        evictAll();
    }

    @Override
    public void trimMemory(int level) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
            clearMemory();
        } else if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            trimToSize(maxSize() / 2);
        }
    }
}
