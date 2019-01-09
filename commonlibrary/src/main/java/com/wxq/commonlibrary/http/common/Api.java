
package com.wxq.commonlibrary.http.common;

import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.wxq.commonlibrary.constant.GlobalContent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * api请求的基类
 * <p>
 * 以后可以优化为多baseurl模式--https://github.com/JessYanCoding/RetrofitUrlManager
 */

public class Api {

    private static final long DEFAULT_TIMEOUT = 10;
    private volatile static Api api;
    private Retrofit retrofit;

    public Api() {
        //token拦截器
//        TokenInterceptor tokenInterceptor=new TokenInterceptor();
        //日志拦截器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor("okgo");
        logInterceptor.setColorLevel(Level.WARNING);
        logInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new NetworkInterceptor())
                .addInterceptor(logInterceptor)
                .addInterceptor(new RetryIntercepter(3))
//                .addInterceptor(new TestNetInterceptor())
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


    // 根据类型获取不同的service
    public <T> T getApiService(Class<T> tClass) {
        return retrofit.create(tClass);
    }

}
