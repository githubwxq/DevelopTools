//package com.wxq.commonlibrary.http;
//
//import android.provider.Settings;
//
//import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
//import com.wxq.commonlibrary.http.converter.CustomGsonConverterFactory;
//
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//
//public class Api {
//
//    private static final long DEFAULT_TIMEOUT = 10;
//    private volatile static Api api;
//    private Retrofit retrofit;
//    private  Retrofit retrofit2;
//
//    private Api() {
//        //token拦截器
////        TokenInterceptor tokenInterceptor=new TokenInterceptor();
//
//        //日志拦截器
//        com.lzy.okgo.interceptor.HttpLoggingInterceptor ipadloggingInterceptor = new com.lzy.okgo.interceptor.HttpLoggingInterceptor("teacher_pad");
//        ipadloggingInterceptor.setColorLevel(Level.WARNING);
//        ipadloggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
//        //缓存
////        int size = 1024 * 1024 * 100;
////        File cacheFile = new File(BaseApp.getContext().getCacheDir(), "OkHttpCache");
////        Cache cache = new Cache(cacheFile, size);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
////                .addNetworkInterceptor(new NetworkInterceptor())
//                .addInterceptor(ipadloggingInterceptor)
////                .addInterceptor(tokenInterceptor)
//                .build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Global.BASE_URL)
//                .client(client)
//                //然后将下面的GsonConverterFactory.create()替换成我们自定义的ResponseConverterFactory.create()
//                .addConverterFactory(CustomGsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//
//    }
//
//    public static Api getInstance() {
//        if (api == null) {
//            synchronized (Api.class) {
//                if (api == null) {
//                    api = new Api();
//                }
//            }
//        }
//        return api;
//    }
//
//    //   更具类型获取不同的service
//    public <T> T getApiService(Class<T> tClass) {
//        return retrofit.create(tClass);
//
//    }
//
//}