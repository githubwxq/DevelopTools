package com.example.interviewdemo.glide2.cache.recycle;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/5/4.
 */

public interface BitmapPool {

    void put(Bitmap bitmap);

    /**
     * 获得一个可复用的Bitmap
     *  三个参数计算出 内存大小
     * @param width
     * @param height
     * @param config
     * @return
     */
    Bitmap get(int width, int height, Bitmap.Config config);


    void clearMemory();
    void trimMemory(int level);
}
