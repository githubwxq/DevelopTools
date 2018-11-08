package com.wxq.commonlibrary.imageloader.config;

import com.wxq.commonlibrary.imageloader.cache.BitmapCache;
import com.wxq.commonlibrary.imageloader.cache.MemoryCache;
import com.wxq.commonlibrary.imageloader.policy.LoadPolicy;
import com.wxq.commonlibrary.imageloader.policy.ReversePolicy;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:建造者模式
 * version:1.0
 */
public class ImageLoaderConfig {
    /**
     * 缓存策略
     */
    private BitmapCache bitmapCache= new MemoryCache();

    /**
     * 加载策略
     */
    private LoadPolicy loadPolicy=new ReversePolicy();

    /**
     * 默认线程数
     */
    private int threadCount = Runtime.getRuntime().availableProcessors();

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return loadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    /**
     * 加载策略
     */
    private DisplayConfig displayConfig=new DisplayConfig();

    private ImageLoaderConfig() {


    }


    public static class Builder {

        private ImageLoaderConfig config;


        public Builder() {

            config = new ImageLoaderConfig();
        }


        /**
         * 设置缓存策略
         *
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache) {
            config.bitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         *
         * @param loadPolicy
         * @return
         */
        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            config.loadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置设置线程个数
         *
         * @param count
         * @return
         */
        public Builder setThreadCount(int count) {
            config.threadCount = count;
            return this;
        }

        /**
         * 设置加载显示的图片
         *
         * @param loadingImage
         * @return
         */
        public Builder setLoadingImage(int loadingImage) {
            config.displayConfig.loadingImage = loadingImage;
            return this;
        }


        /**
         * 设置加载失败显示的图片
         *
         * @param loadingFailImage
         * @return
         */
        public Builder setFaildImage(int loadingFailImage) {
            config.displayConfig.faildImage = loadingFailImage;
            return this;
        }


        public ImageLoaderConfig build() {
            return config;
        }
    }


}
