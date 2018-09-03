package com.wxq.commonlibrary.retrofit;

import com.wxq.commonlibrary.okgo.response.ResponseData;
import com.wxq.commonlibrary.retrofit.upload.UpLoadData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载文件的url接口
 * Created by zhongjh on 2018/5/18.
 */
public interface DownloadService {
    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载


    //上传接口
    @Multipart
    @POST("cos/upload")
    Flowable<ResponseData<UpLoadData>> uploadFile(@Part("module") RequestBody description
            , @Part MultipartBody.Part file);

    //上传接口--多个
    @Multipart
    @POST("cos/upload/batch")
    Flowable<ResponseData<List<UpLoadData>>> uploadManyFile(@Part("module") RequestBody description,
                                                            @Part() List<MultipartBody.Part> parts);







}
