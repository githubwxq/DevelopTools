package com.wxq.commonlibrary.http.download;


import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.http.common.CustomGsonConverterFactory;
import com.wxq.commonlibrary.http.common.HttpLoggingInterceptor;

import org.reactivestreams.Subscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 1. Description: 下载工具类
 * 2. Created by jia on 2017/11/30.
 * 3. 人之所以能，是相信能
 */
public class DownloadUtils {
    private Retrofit retrofit;
    private JsDownloadListener listener;
    private static final long DEFAULT_TIMEOUT = 30;

    public DownloadUtils(JsDownloadListener listener) {
        this.listener = listener;
        init();
    }

    /**
     * 开始下载
     *
     * @param url
     * @param filePath
     * @param subscriber
     */
    public void download(@NonNull String url, final String filePath, final String parentDirPath, Subscriber<File> subscriber, boolean isMainThread) {

        listener.onStartDownload();

        // subscribeOn()改变调用它之前代码的线程
        // observeOn()改变调用它之后代码的线程
        retrofit.create(DownloadAndUploadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        return writeFile(responseBody.byteStream(), filePath, parentDirPath);
                    }
                })
                .observeOn((isMainThread) ? AndroidSchedulers.mainThread() : Schedulers.io())
                .subscribe(subscriber);
    }

    /**
     * 开始下载  默认在主线程
     */
    public void download(@NonNull String url, final String filePath, final String parentDirPath, Subscriber<File> subscriber) {
        download(url, filePath, parentDirPath, subscriber, true);
    }


    /**
     * 创建目录，如果目录已经存在则直接返回
     *
     * @param dirPath
     * @return true -> 目录创建成功、目录已经存在;false -> 目录创建失败
     */
    public static boolean createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists())
            return file.mkdirs();
        return true;
    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private File writeFile(InputStream inputString, String filePath, String parentDirPath) {
        File file = new File(parentDirPath, filePath);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.isDirectory()) createDir(file.getParent());
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            byte[] b = new byte[1024];

            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (FileNotFoundException e) {
            listener.onFail("FileNotFoundException");
        } catch (IOException e) {
            listener.onFail("IOException");
        }

        return file;
    }

    private void init() {
        HttpLoggingInterceptor ipadloggingInterceptor = new HttpLoggingInterceptor("ipadTag");
        ipadloggingInterceptor.setColorLevel(Level.INFO);
        ipadloggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);

        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(ipadloggingInterceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalContent.BASE_URL)
                .client(client.build())
                //然后将下面的GsonConverterFactory.create()替换成我们自定义的ResponseConverterFactory.create()
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}