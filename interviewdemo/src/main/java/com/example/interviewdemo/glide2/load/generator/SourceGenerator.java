package com.example.interviewdemo.glide2.load.generator;

import android.util.Log;

import com.example.interviewdemo.glide2.GlideContext;
import com.example.interviewdemo.glide2.load.model.ModelLoader;
import com.example.interviewdemo.glide2.load.model.data.DataFetcher;

import java.util.List;

/**
 * 专门负责从图片源地址加载数据的生成器
 * @author Lance
 * @date 2018/4/22
 */
public class SourceGenerator implements DataGenerator, DataFetcher.DataFetcherCallback<Object> {
    private static final String TAG = "SourceGenerator";

    private final DataGeneratorCallback cb;
    private GlideContext context;
    private int loadDataListIndex;
    private List<ModelLoader.LoadData<?>> loadDatas;
    private ModelLoader.LoadData<?> loadData;

    public SourceGenerator(GlideContext context, Object model, DataGeneratorCallback cb) {
        this.context = context;
        this.cb = cb;
        loadDatas = context.getRegistry().getLoadDatas(model);
    }

    @Override
    public boolean startNext() {
        Log.e(TAG, "源加载器开始加载");
        boolean started = false;
        while (!started && hasNextModelLoader()) {
            loadData = loadDatas.get(loadDataListIndex++);
            Log.e(TAG, "获得加载设置数据");
            // hasLoadPath : 是否有个完整的加载路径 从将Model转换为Data之后 有没有一个对应的将Data
            // 转换为图片的解码器
            if (loadData != null && context.getRegistry().hasLoadPath(loadData.fetcher.getDataClass
                    ())) {
                Log.e(TAG, "加载设置数据输出数据对应能够查找有效的解码器路径,开始加载数据");
                started = true;
                // 将Model转换为Data
                loadData.fetcher.loadData(this);
            }
        }
        return started;
    }

    /**
     * 是否有下一个modelloader支持加载
     * @return
     */
    private boolean hasNextModelLoader() {
        return loadDataListIndex < loadDatas.size();
    }

    @Override
    public void cancel() {
        if (loadData != null) {
            loadData.fetcher.cancel();
        }
    }

    /**
     * 成功由Model获得一个Data
     * @param data
     */
    @Override
    public void onFetcherReady(Object data) {
        Log.e(TAG, "加载器加载数据成功回调");
        cb.onDataReady(loadData.key, data, DataGeneratorCallback.DataSource.REMOTE);
    }

    @Override
    public void onLoadFaled(Exception e) {
        Log.e(TAG, "加载器加载数据失败回调");
        cb.onDataFetcherFailed(loadData.key, e);
    }

}
