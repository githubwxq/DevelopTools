package com.wxq.commonlibrary.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wxq.commonlibrary.imageloader.cache.BitmapCache;
import com.wxq.commonlibrary.imageloader.config.DisplayConfig;
import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc: 处理加载中公共的部分
 * version:1.0
 */
public abstract class AbstractLoader implements Loader {

    /**
     * 缓存大家都有      单列可以到处使用它的配置 比较方便
     */
    private BitmapCache bitmapCache = SimpleImageLoader.getInstance().getConfig().getBitmapCache();

    /**
     * 拿到显示配置
     */

    private DisplayConfig displayConfig = SimpleImageLoader.getInstance().getConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {  //  加载图片loadImage  之前   之后 相同操作采用抽象类封装一层
        //调用成不管你是如何获取缓存 反正我只要结果
        Bitmap bitmap = bitmapCache.get(request);
        if (bitmap == null) {
            showLoadingImage(request);
            //开始加载图片  之前之后
            bitmap = onLoad(request);
            //开始缓存图片
            cacheBitmap(request, bitmap);
        }

        //不为null的时候直接显示
        deliveryToUIThread(request, bitmap);
    }

    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();

        if (imageView != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }
            });
        }


    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();

        //防止图片错位
        if (bitmap != null && imageView.getTag().equals(request.getImageUrl())) {
            imageView.setImageBitmap(bitmap);
        }
        if (bitmap == null && displayConfig != null && displayConfig.faildImage != -1) {
            imageView.setImageResource(displayConfig.faildImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展  自行扩展 通过接口的形式
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, request.getImageUrl());
        }

    }


    /**
     * 缓存图片
     *
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {

        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class) {
                bitmapCache.put(request, bitmap);
            }
        }
    }

    //抽象加载策略应为加载本地图片和加载网络图片有差异 所以抽象出来
    protected abstract Bitmap onLoad(BitmapRequest request);

    private void showLoadingImage(BitmapRequest request) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImage);
                    }
                });
            }
        }


    }

    private boolean hasLoadingPlaceHolder() {
        return (displayConfig != null && displayConfig.loadingImage > 0);
    }


    public boolean hasFailedPlaceHolder() {

        return (displayConfig != null && displayConfig.faildImage > 0);
    }

}
