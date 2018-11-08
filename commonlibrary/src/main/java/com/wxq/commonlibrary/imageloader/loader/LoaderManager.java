package com.wxq.commonlibrary.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/08
 * desc:加载器管理类
 * version:1.0
 */
public class LoaderManager {
    private Map<String, Loader> mLoaderMap = new HashMap<>();


    private static LoaderManager mInstance = new LoaderManager();

    public static LoaderManager getmInstance() {

        return mInstance;
    }


    private LoaderManager() {

        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());

    }

    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);

    }


    Loader loader;


    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return new NullLoader();
    }
}
