package com.example.interviewdemo.glide2.load.model;

import android.net.Uri;

import com.example.interviewdemo.glide2.load.ObjectKey;
import com.example.interviewdemo.glide2.load.model.data.HttpUriFetcher;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/7.
 */

public class HttpUriLoader implements ModelLoader<Uri, InputStream> {

    /**
     * http类型的uri此loader才支持
     *
     * @param uri
     * @return
     */
    @Override
    public boolean handles(Uri uri) {
        String scheme = uri.getScheme();
        return scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https");
    }

    @Override
    public LoadData<InputStream> buildData(Uri uri) {
        return new LoadData<InputStream>(new ObjectKey(uri), new HttpUriFetcher(uri));
    }


    public static class Factory implements ModelLoaderFactory<Uri, InputStream> {

        @Override
        public ModelLoader<Uri, InputStream> build(ModelLoaderRegistry registry) {
            return new HttpUriLoader();
        }
    }
}
