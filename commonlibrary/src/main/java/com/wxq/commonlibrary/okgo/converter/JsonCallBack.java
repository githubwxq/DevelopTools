package com.wxq.commonlibrary.okgo.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.lzy.okgo.callback.AbsCallback;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/07/31
 * @description okgo的一种回调
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {

    public static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            return JSON.parseObject(body.string(), type, EMPTY_SERIALIZER_FEATURES);
        }
        return null;
    }
}
