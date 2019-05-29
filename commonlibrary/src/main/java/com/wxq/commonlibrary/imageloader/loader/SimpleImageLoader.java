package com.wxq.commonlibrary.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wxq.commonlibrary.imageloader.config.DisplayConfig;
import com.wxq.commonlibrary.imageloader.config.ImageLoaderConfig;
import com.wxq.commonlibrary.imageloader.request.BitmapRequest;
import com.wxq.commonlibrary.imageloader.request.RequestQueue;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:使用单列
 * version:1.0
 */
public class SimpleImageLoader {


    private ImageLoaderConfig config;


    private RequestQueue mRequestQueue;


    private static SimpleImageLoader mInstance;

    private SimpleImageLoader() {


    }


    public SimpleImageLoader(ImageLoaderConfig imageLoaderConfig) {
        config = imageLoaderConfig;
//        初始化请求队列
        mRequestQueue=new RequestQueue(imageLoaderConfig.getThreadCount());
        //开启请求队列
        mRequestQueue.start();
    }


    //  dcl单列
    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
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
        displayImage(imageView,uri,null,null);

    }


    public void displayImage(ImageView imageView, String uri, DisplayConfig displayConfig,ImageListener imageListener) {
//         实力化封装请求  请求对应图片 url 以及 配置 回调等等 请求添加到队列
        BitmapRequest bitmapRequest=new BitmapRequest(imageView,uri,displayConfig,imageListener);
        mRequestQueue.addRequest(bitmapRequest);

    }




    public static interface ImageListener {

        /**
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);

    }



    public ImageLoaderConfig getConfig() {
        return config;
    }






    //配置
//  ImageLoaderConfig.Builder build = new ImageLoaderConfig.Builder();
//        build.setThreadCount(3) //线程数量
//                .setLoadPolicy(new ReversePolicy()) //加载策略
//            .setCachePolicy(new DoubleCache(this)) //缓存策略
//            .setLoadingImage(R.drawable.loading)
//                .setFaildImage(R.drawable.not_found);
//
//    ImageLoaderConfig config = build.build();
//    //初始化
//    imageLoader = SimpleImageLoader.getInstance(config);



}
