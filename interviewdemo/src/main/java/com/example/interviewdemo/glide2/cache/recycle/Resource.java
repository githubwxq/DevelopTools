package com.example.interviewdemo.glide2.cache.recycle;

import android.graphics.Bitmap;

import com.example.interviewdemo.glide2.cache.Key;

/**
 * Created by Administrator on 2018/5/4.
 */

public class Resource {

    private Bitmap bitmap;

    //引用计数
    private int acquired;

    private ResourceListener listener;
    private Key key;


    public Resource(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 当acquired 为0的时候 回调 onResourceReleased
     */
    public interface ResourceListener {
        void onResourceReleased(Key key, Resource resource);
    }

    public void setResourceListener(Key key, ResourceListener listener) {
        this.key = key;
        this.listener = listener;
    }

    /**
     * 释放
     */
    public void recycle() {
        if (acquired > 0) {
            return;
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    /**
     * 引用计数-1
     */
    public void release() {
        if (--acquired == 0) {
            listener.onResourceReleased(key, this);
        }
    }

    /**
     * 引用计数+1
     */
    public void acquire() {
        if (bitmap.isRecycled()) {
            throw new IllegalStateException("Acquire a recycled resource");
        }
        ++acquired;
    }

}
