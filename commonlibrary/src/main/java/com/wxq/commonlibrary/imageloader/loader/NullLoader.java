package com.wxq.commonlibrary.imageloader.loader;

import android.graphics.Bitmap;

import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/08
 * desc:什么都不处理
 * version:1.0
 */
public class NullLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
