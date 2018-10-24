
package com.wxq.commonlibrary.retrofit;

import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.okgo.converter.CustomGsonConverterFactory;
import com.wxq.commonlibrary.retrofit.Interceptor.MyHeardInterceptor;
import com.wxq.commonlibrary.retrofit.Interceptor.MyLogInterceptor;
import com.wxq.commonlibrary.retrofit.Interceptor.MyTokenInterceptor;
import com.wxq.commonlibrary.retrofit.Interceptor.RetryIntercepter;
import com.wxq.commonlibrary.retrofit.Interceptor.TestNetInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class Api {

    private static final long DEFAULT_TIMEOUT = 10;
    private volatile static Api api;
    private Retrofit retrofit;
    private  Retrofit retrofit2;

    private Api() {
        //token拦截器
//        TokenInterceptor tokenInterceptor=new TokenInterceptor();

        //日志拦截器
//        com.lzy.okgo.interceptor.HttpLoggingInterceptor ipadloggingInterceptor = new com.lzy.okgo.interceptor.HttpLoggingInterceptor("teacher_pad");
//        ipadloggingInterceptor.setColorLevel(Level.WARNING);
//        ipadloggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .addNetworkInterceptor(new NetworkInterceptor())
                .addInterceptor(new RetryIntercepter(3))
                .addInterceptor(new MyHeardInterceptor())
                .addInterceptor(new MyLogInterceptor())
                .addInterceptor(new TestNetInterceptor())
//                .addInterceptor(new MyTokenInterceptor())
//                .addInterceptor(tokenInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalContent.BASE_URL)
                .client(client)
                //然后将下面的GsonConverterFactory.create()替换成我们自定义的ResponseConverterFactory.create()
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    public static Api getInstance() {
        if (api == null) {
            synchronized (Api.class) {
                if (api == null) {
                    api = new Api();
                }
            }
        }
        return api;
    }

    //   更具类型获取不同的service
    public <T> T getApiService(Class<T> tClass) {
        return retrofit.create(tClass);

    }

}
