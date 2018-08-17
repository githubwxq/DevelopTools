package com.wxq.commonlibrary.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.wxq.commonlibrary.constant.GlobalContent;

/**
 * Created by 王晓清.
 * data 2018/8/14.
 * describe .glide緩存
 */

public class GlideCache extends AppGlideModule {
    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath();
    private String appRootPath = null;
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        //手机app路径
        appRootPath = context.getCacheDir().getPath();
        builder.setDiskCache(
                new DiskLruCacheFactory(GlobalContent.imgPath, diskCacheSizeBytes )
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }
}
