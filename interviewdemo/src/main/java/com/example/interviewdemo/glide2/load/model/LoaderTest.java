package com.example.interviewdemo.glide2.load.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.load.model.ResourceLoader;
import com.example.interviewdemo.glide2.load.model.data.DataFetcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */
public class LoaderTest {

    private static final String TAG = "LoaderTest";

    public static void testFindLoader(Context context) {


        ModelLoaderRegistry loaderRegistry = new ModelLoaderRegistry();
        loaderRegistry.add(String.class, InputStream.class, new StringModelLoader.StreamFactory());
        loaderRegistry.add(Uri.class, InputStream.class, new FileUriLoader.Factory(context.getContentResolver()));
        loaderRegistry.add(Uri.class, InputStream.class, new HttpUriLoader.Factory());
        loaderRegistry.add(File.class, InputStream.class, new FileLoader.Factory());

//        context.getAssets().open("/a/b.png");

        List<ModelLoader<String, ?>> modelLoaders = loaderRegistry.getModelLoaders(String.class);
        ModelLoader<String, ?> modelLoader = modelLoaders.get(0);
        //HttpUriFetcher
        final ModelLoader.LoadData<InputStream> loadData = (ModelLoader.LoadData<InputStream>) modelLoader.buildData("https://ss1.bdstatic" +
                ".com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2669567003," +
                "3609261574&fm=27&gp=0.jpg22222222asads");

        new Thread() {
            @Override
            public void run() {
                loadData.fetcher.loadData(new DataFetcher.DataFetcherCallback<InputStream>() {
                    @Override
                    public void onFetcherReady(InputStream o) {
                        try {
                            Log.e(TAG, "ready:" + o.available());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLoadFaled(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }.start();
//        modelLoader.buildData("/a/b.png");
    }


    public void test(Context context) {


        Uri uri = Uri.parse("http://www.xxx.xxx");
        HttpUriLoader httpUriLoader = new HttpUriLoader();
        ModelLoader.LoadData<InputStream> loadData = httpUriLoader.buildData(uri);
        //
        loadData.fetcher.loadData(new DataFetcher.DataFetcherCallback<InputStream>() {

            @Override
            public void onFetcherReady(InputStream is) {
                //
                BitmapFactory.decodeStream(is);
            }

            @Override
            public void onLoadFaled(Exception e) {
                e.printStackTrace();
            }
        });


//        FileUriLoader loader = new FileUriLoader(context.getContentResolver());
//        ModelLoader.LoadData<InputStream> loadData1 = loader.buildData(uri);
//        loadData1.fetcher.loadDta();
    }
}
