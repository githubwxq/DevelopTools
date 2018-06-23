package com.wxq.commonlibrary.glide;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/5/11
 * @description
 */
public interface OnLoadProgressListener {

    void onLoadStart();

    /**
     * @param progress 0-100
     */
    void onProgress(long total, int progress);

    void onLoadFinish();
}
