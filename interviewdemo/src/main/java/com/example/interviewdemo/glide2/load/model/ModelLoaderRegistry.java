package com.example.interviewdemo.glide2.load.model;

import android.net.Uri;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class ModelLoaderRegistry {

    private List<Entry<?, ?>> entries = new ArrayList<>();

    /**
     * 注册 Loader
     *
     * @param modelClass 数据来源类型 String File
     * @param dataClass  数据转换后类型 加载后类型 String/File->InputStream
     * @param factory    创建ModelLoader的工厂
     * @param <Model>
     * @param <Data>
     */
    public synchronized <Model, Data> void add(Class<Model> modelClass, Class<Data> dataClass,
                                               ModelLoader.ModelLoaderFactory<Model, Data> factory) {
        entries.add(new Entry<>(modelClass, dataClass, factory));
    }

    /**
     * 获得 对应 model与data类型的 modelloader
     *
     * @param modelClass
     * @param dataClass
     * @param <Model>
     * @param <Data>
     * @return
     */
    public <Model, Data> ModelLoader<Model, Data> build(Class<Model> modelClass, Class<Data> dataClass) {
        List<ModelLoader<Model, Data>> loaders = new ArrayList<>();
        for (Entry<?, ?> entry : entries) {
            //是我们需要的Model与Data类型的Loader
            if (entry.handles(modelClass, dataClass)) {
                loaders.add((ModelLoader<Model, Data>) entry.factory.build(this));
            }
        }
        //找到多个匹配的loader
        if (loaders.size() > 1) {
            return new MultiModelLoader<>(loaders);
        } else if (loaders.size() == 1) {
            return loaders.get(0);
        }
        throw new RuntimeException("No Match:" + modelClass.getName() + " Data:" + dataClass.getName());
    }


    /**
     * 查找匹配的 Model类型的ModelLoader
     * @param modelClass
     * @param <Model>
     * @return
     */
    public <Model> List<ModelLoader<Model, ?>> getModelLoaders(Class<Model> modelClass) {
        List<ModelLoader<Model, ?>> loaders = new ArrayList<>();
        for (Entry<?, ?> entry : entries) {
            if (entry.handles(modelClass)) {
                loaders.add((ModelLoader<Model, ?>) entry.factory.build(this));
            }
        }
        return loaders;
    }


    private static class Entry<Model, Data> {
        Class<Model> modelClass;
        Class<Data> dataClass;
        ModelLoader.ModelLoaderFactory<Model, Data> factory;

        public Entry(Class<Model> modelClass, Class<Data> dataClass, ModelLoader.ModelLoaderFactory<Model, Data> factory) {
            this.modelClass = modelClass;
            this.dataClass = dataClass;
            this.factory = factory;
        }

        boolean handles(Class<?> modelClass, Class<?> dataClass) {
            // A.isAssignableFrom(B) B和A是同一个类型 或者 B是A的子类
            return this.modelClass.isAssignableFrom(modelClass) && this.dataClass.isAssignableFrom(dataClass);
        }

        boolean handles(Class<?> modelClass) {
            // A.isAssignableFrom(B) B和A是同一个类型 或者 B是A的子类
            return this.modelClass.isAssignableFrom(modelClass);
        }

    }
}
