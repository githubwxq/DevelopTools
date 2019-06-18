package com.example.interviewdemo.glide2.load.model;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/7.
 */

public class StringModelLoader implements ModelLoader<String, InputStream> {

    private final ModelLoader<Uri, InputStream> loader;

    public StringModelLoader(ModelLoader<Uri, InputStream> loader) {
        this.loader = loader;
    }

    @Override
    public boolean handles(String s) {
        return true;
    }

    @Override
    public LoadData<InputStream> buildData(String model) {
        Uri uri;
        if (model.startsWith("/")) {
            uri = Uri.fromFile(new File(model));
        } else {
            uri = Uri.parse(model);
        }
        return loader.buildData(uri);
    }


    public static class StreamFactory implements ModelLoaderFactory<String, InputStream> {

        @Override
        public ModelLoader<String, InputStream> build(ModelLoaderRegistry registry) {
            return new StringModelLoader(registry.build(Uri.class, InputStream.class));
        }
    }

}
