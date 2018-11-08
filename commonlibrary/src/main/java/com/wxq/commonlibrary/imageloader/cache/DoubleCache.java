package com.wxq.commonlibrary.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/08
 * desc:
 * version:1.0
 */
public class DoubleCache implements BitmapCache {

    //内存缓存
    private MemoryCache mMemoryCache = new MemoryCache();
    //硬盘缓存
    private DiskCache mDiskCache;

    public DoubleCache(Context context) {
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = mMemoryCache.get(request);
        if(bitmap == null){
            bitmap = mDiskCache.get(request);
            if(bitmap != null){
                //放入内存，方便再获取
                mMemoryCache.put(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }
}
