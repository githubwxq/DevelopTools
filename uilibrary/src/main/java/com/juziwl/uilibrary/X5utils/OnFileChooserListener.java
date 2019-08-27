package com.juziwl.uilibrary.X5utils;

import android.net.Uri;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/5/9
 * @description webview选择文件需要的回调
 */
public interface OnFileChooserListener {
    /**
     * 5.0以下走这个回调
     */
    void openFileChooser(ValueCallback<Uri> valueCallback);

    /**
     * 5.0及以上走这个回调
     */
    boolean onShowFileChooser(ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams);
}
