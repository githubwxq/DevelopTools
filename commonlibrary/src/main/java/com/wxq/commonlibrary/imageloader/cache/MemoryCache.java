package com.wxq.commonlibrary.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:内存缓存  需要外面包裹累处理  LruCache
 * version:1.0
 */
public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;


    public MemoryCache() {

        int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);

        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //Bitmap 占用的控件
                return value.getRowBytes() * value.getHeight();
            }
        };


    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mLruCache.put(request.getImageUriMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getImageUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getImageUriMD5());
    }
}
