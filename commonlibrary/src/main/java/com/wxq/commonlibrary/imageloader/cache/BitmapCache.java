package com.wxq.commonlibrary.imageloader.cache;

import android.graphics.Bitmap;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:
 * version:1.0
 */
public interface BitmapCache {

    /**
     * 缓存bitmap
     *
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 获取bitmap
     *
     * @param request
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除对象
     * @param request
     */
    void remove(BitmapRequest request);


}
