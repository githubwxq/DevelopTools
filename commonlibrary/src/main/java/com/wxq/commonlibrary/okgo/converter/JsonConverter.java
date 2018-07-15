package com.wxq.commonlibrary.okgo.converter;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/08/01
 * @description okgo配合RxJava需要的转换器
 */
public class JsonConverter<T> implements Converter<T> {
    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            return JSON.parseObject(body.string(), type, JsonCallBack.EMPTY_SERIALIZER_FEATURES);
        }
        return null;
    }
}
