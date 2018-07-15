package com.wxq.commonlibrary.okgo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxq.commonlibrary.okgo.converter.CustomGsonConverterFactory;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

//通过反射获取baseurl  String BASE_URL = "http://www.gank.io/api/";  service借口中需要申明
public class ServiceFactory {

    private final Gson mGsonDateFormat;

    private ServiceFactory() {
        mGsonDateFormat = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * create a service
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private final static long DEFAULT_TIMEOUT = 10;

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置缓存
//        File httpCacheDirectory = new File(FileUtils.getCacheDir(SolidApplication.getInstance()), "OkHttpCache");
//        httpClientBuilder.cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024));
        return httpClientBuilder.build();
    }
}
