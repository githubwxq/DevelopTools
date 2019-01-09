package com.wxq.commonlibrary.http.download;

import com.wxq.commonlibrary.okgo.response.ResponseData;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Description:
 * Created by jia on 2017/11/30.
 * 人之所以能，是相信能
 */
public interface DownloadAndUploadService {

    @Streaming
    @GET
    Flowable<ResponseBody> download(@Url String url);


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