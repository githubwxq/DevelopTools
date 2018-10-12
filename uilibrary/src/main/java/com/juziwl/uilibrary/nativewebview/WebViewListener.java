package com.juziwl.uilibrary.nativewebview;

import android.net.Uri;
import android.webkit.ValueCallback;


/**
 * webview回调监听
 */
public interface WebViewListener {

    /**
     * 5.0及以上走这个回调
     */
    boolean onShowFileChooser(ValueCallback<Uri[]> valueCallback);


    void onPageFinish(String url);


    void onProgressChanged(int newProgress);
}
