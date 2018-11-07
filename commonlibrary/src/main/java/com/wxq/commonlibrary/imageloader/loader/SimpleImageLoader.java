package com.wxq.commonlibrary.imageloader.loader;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.wxq.commonlibrary.imageloader.config.DisplayConfig;
import com.wxq.commonlibrary.imageloader.config.ImageLoadConfig;
import com.wxq.commonlibrary.imageloader.request.BitmapRequest;
import com.wxq.commonlibrary.imageloader.request.RequestQueue;

import dalvik.system.DexClassLoader;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:使用单列
 * version:1.0
 */
public class SimpleImageLoader {

    public ImageLoadConfig getConfig() {
        return config;
    }

    private ImageLoadConfig config;


    private RequestQueue requestQueue;


    private static SimpleImageLoader mInstance;

    private SimpleImageLoader() {


    }


    public SimpleImageLoader(ImageLoadConfig imageLoadConfig) {
        config = imageLoadConfig;
    }


    //  dcl单列
    public static SimpleImageLoader getInstance(ImageLoadConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }
        return mInstance;
    }

    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("没有初始化");
        }
        return mInstance;
    }


    public void displayImage(ImageView imageView, String uri) {


    }


    public void displayImage(ImageView imageView, String uri, DisplayConfig displayConfig,ImageListener imageListener) {

        BitmapRequest bitmapRequest=new BitmapRequest();







    }




    public static interface ImageListener {

        /**
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);

    }






}
