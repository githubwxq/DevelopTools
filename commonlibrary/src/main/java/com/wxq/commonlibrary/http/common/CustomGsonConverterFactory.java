package com.wxq.commonlibrary.http.common;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class CustomGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return new CustomGsonConverterFactory(gson);
    }

    private CustomGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        } else {
            this.gson = gson;
        }
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new Converter< ResponseBody,String>() {
                @Override
                public String convert(ResponseBody value) throws IOException {
                    return value.string();
                }
            };
        }else {
            TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
            return new GsonResponseBodyConverter(this.gson, adapter);
        }
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {

        if (type == JSONObject.class) {
            return new Converter<JSONObject, RequestBody>() {
                private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

                @Override
                public RequestBody convert(JSONObject jsonObject) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, jsonObject.toString());
                }
            };

        }else if(type == String.class){
            return new Converter<String, RequestBody>() {
                private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

                @Override
                public RequestBody convert(String s) throws IOException {
                    return RequestBody.create(MEDIA_TYPE, s);
                }
            };
        }else {
            TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
            return new GsonRequestBodyConverter(this.gson, adapter);
        }




    }


    static class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            JsonReader jsonReader = this.gson.newJsonReader(value.charStream());
            Object var3;
            try {
                var3 = this.adapter.read(jsonReader);
            } finally {
                value.close();
            }

            return (T) var3;
        }
    }

    static class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = this.gson.newJsonWriter(writer);
            this.adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }
}
