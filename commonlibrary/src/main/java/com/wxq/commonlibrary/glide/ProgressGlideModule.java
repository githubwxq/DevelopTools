//package com.wxq.commonlibrary.glide;
//
//import android.content.Context;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.LibraryGlideModule;
//
//import java.io.InputStream;
//
//import okhttp3.OkHttpClient;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2018/5/11
// * @description
// */
//@GlideModule
//public final class ProgressGlideModule extends LibraryGlideModule {
//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new ProgressInterceptor());
//        OkHttpClient okHttpClient = builder.build();
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
//    }
//}
