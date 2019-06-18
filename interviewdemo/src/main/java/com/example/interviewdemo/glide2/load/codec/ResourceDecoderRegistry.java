package com.example.interviewdemo.glide2.load.codec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lance
 * @date 2018/4/21
 */

public class ResourceDecoderRegistry {

    private final List<Entry<?>> entries = new ArrayList<>();

    public <Data> List<ResourceDecoder<Data>> getDecoders(Class<Data> dataClass) {
        List<ResourceDecoder<Data>> docoders = new ArrayList<>();
        for (Entry<?> entry : entries) {
            if (entry.handles(dataClass)) {
                docoders.add((ResourceDecoder<Data>) entry.decoder);
            }
        }
        return docoders;
    }

    private static class Entry<T> {
        private final Class<T> dataClass;
        final ResourceDecoder<T> decoder;

        public Entry(Class<T> dataClass, ResourceDecoder<T> decoder) {
            this.dataClass = dataClass;
            this.decoder = decoder;
        }

        public boolean handles(Class<?> dataClass) {
            //判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口
            return this.dataClass.isAssignableFrom(dataClass);
        }
    }

    public <T> void add(Class<T> dataClass, ResourceDecoder<T> decoder) {
        entries.add(new Entry<>(dataClass, decoder));
    }
}
