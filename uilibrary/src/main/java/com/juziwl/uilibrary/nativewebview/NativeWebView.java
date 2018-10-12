package com.juziwl.uilibrary.nativewebview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Stack;

/**
 * 原生webview基础配置
 */
public class NativeWebView extends WebView {
    private int flag;
    private WebViewListener webviewListener;

    public static final String JOIN_QQ_GROUP_PREFIX = "//qm.qq.com/cgi-bin/qm/qr?k=";
    private Stack<String> mUrls = new Stack<>();

    private WebViewClient client = new WebViewClient() {

        private boolean isLoading = false;
        private String beforeUrl = null;

        /**
         * 防止加载网页时调起系统浏览器 通过 WebViewClient 的方法shouldOverrideUrlLoading ()回调拦截 url
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                view.getContext().startActivity(intent);
            } else if (url.contains(JOIN_QQ_GROUP_PREFIX)) {
                int start = url.indexOf(JOIN_QQ_GROUP_PREFIX) + JOIN_QQ_GROUP_PREFIX.length();
                String key;
                int end = url.indexOf("&", start);
                if (end > -1) {
                    key = url.substring(start, end);
                } else {
                    key = url.substring(start);
                }
             joinQQGroup(view.getContext(), key);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);

            if (isLoading && !mUrls.empty()) {
                beforeUrl = mUrls.pop();
            }
            recordUrl(url);
            isLoading = true;
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);

            if (webviewListener != null) {
                webviewListener.onPageFinish(url);
            }
            isLoading = false;
        }

        /**
         * 记录非重定向链接, 避免刷新页面造成的重复入栈
         */
        private void recordUrl(String url) {
            if (!TextUtils.isEmpty(url) && !url.equalsIgnoreCase(getLastPageUrl())) {
                mUrls.push(url);
            } else if (!TextUtils.isEmpty(beforeUrl)) {
                mUrls.push(beforeUrl);
                beforeUrl = null;
            }
        }

        /**
         * 获取上一页的链接
         */
        private synchronized String getLastPageUrl() {
            return mUrls.empty() ? "" : mUrls.peek();
        }


        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (sslError.getCertificate().getIssuedTo().getCName().contains("imexue.com")
                    || sslError.getCertificate().getIssuedTo().getCName().contains("juziwl.com")
                    || sslError.getCertificate().getIssuedTo().getCName().contains("jzexueyun.com")
                    ) {
                //忽略SSL证书错误，继续加载页面
                sslErrorHandler.proceed();
            } else {
                sslErrorHandler.cancel();
            }
        }
    };

    /**
     * 推出上一页链接
     */
    public String popLastPageUrl() {
        if (mUrls.size() >= 2) {
            //当前url
            mUrls.pop();
            //上一个url
            return mUrls.pop();
        }
        return null;
    }

    public NativeWebView(Context context, AttributeSet arg1) {
        super(context, arg1);
        setWebViewClient(client);
        setWebChromeClient(chromeClient);
        initWebViewSettings(context);
        setClickable(true);
        setOverScrollMode(View.OVER_SCROLL_ALWAYS);
    }

    private void initWebViewSettings(final Context context) {
        WebSettings webSetting = this.getSettings();
        webSetting.setDefaultTextEncodingName("UTF-8");
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSetting.setAppCachePath(context.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(context.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(context.getDir("geolocation", 0).getPath());
        // 关于是否缩放
        webSetting.setDisplayZoomControls(false);
        webSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= 21) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

//        if (getX5WebViewExtension() != null) {
//            Bundle data = new Bundle();
//            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，
//            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，
//            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
//            getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
//        }
        CookieSyncManager.createInstance(context);
        CookieSyncManager.getInstance().sync();
    }



    public void setWebviewListener(WebViewListener webviewListener) {
        this.webviewListener = webviewListener;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public NativeWebView(Context arg0) {
        this(arg0, null);
    }


    public void setFlag(int i) {
        flag = i;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (flag == 1) {
            return super.onKeyDown(keyCode, event);
        } else {
//			if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {  //表示按返回键时的操作
                goBack();
                return true;    //已处理
            }
//			}
            return super.onKeyDown(keyCode, event);
        }
    }

    private WebChromeClient chromeClient = new WebChromeClient() {


        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
            b.setTitle("Alert");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();
            return true;
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (webviewListener != null) {
                webviewListener.onProgressChanged(i);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }


        //        @Override
//        public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
//            if (webviewListener != null) {
//                webviewListener.openFileChooser(valueCallback);
//            } else {
//                super.openFileChooser(valueCallback, s, s1);
//            }
//        }

   //接收文件代码
  //      valueCallbacks.onReceiveValue(new Uri[]{uri});

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            if (webviewListener != null) {
                return webviewListener.onShowFileChooser(valueCallback);
            } else {
                return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }
        }
    };

    /****************
     *
     * 发起添加群流程。群号：Android接班人(188683852) 的 key 为： BeGM-2AoLqFuLAnCxzfhKUgdi3sr1jgY
     * 调用 joinQQGroup(BeGM-2AoLqFuLAnCxzfhKUgdi3sr1jgY) 即可发起手Q客户端申请加群 Android接班人(188683852)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
//            ToastUtils.showLong("请先安装QQ客户端");
            //自定义一个toast！！！"请先安装QQ客户端"

            return false;
        }
    }
    public boolean canBack() {
        if (canGoBack()) {
              goBack();
            return false;
        }
        return true;
    }

}


// Android版本变量
//    final int version = Build.VERSION.SDK_INT;
// 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
//if (version < 18) {
//        mWebView.loadUrl("javascript:callJS()");
//        } else {
//        mWebView.evaluateJavascript（"javascript:callJS()", new ValueCallback<String>() {
//@Override
//public void onReceiveValue(String value) {
//        //此处为 js 返回的结果
//        }
//        });
//        }