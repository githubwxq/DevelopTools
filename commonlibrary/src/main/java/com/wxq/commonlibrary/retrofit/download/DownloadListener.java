package com.wxq.commonlibrary.retrofit.download;

import java.io.File;

/**
 * 下载进度回调
 * Created by zhongjh on 2018/5/18.
 */
public interface DownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload(File file);

    void onFail(Throwable ex);

}
