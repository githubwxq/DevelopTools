package com.example.uitestdemo;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.TextView;

import com.juziwl.uilibrary.nativewebview.NativeWebView;
import com.juziwl.uilibrary.nativewebview.WebViewListener;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.KeyEvent.KEYCODE_BACK;

public class TestNativeWebViewActivity extends BaseActivity {


    @BindView(R.id.webview)
    NativeWebView mWebView;
    @BindView(R.id.tv_click)
    TextView tvClick;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initViews() {
//        mWebView.loadUrl("https://www.baidu.com");
        mWebView.loadUrl("file:///android_asset/webpage/javascript.html");

        mWebView.setWebviewListener(new WebViewListener() {

            @Override
            public boolean onShowFileChooser(ValueCallback<Uri[]> valueCallback) {
                return false;
            }

            @Override
            public void onPageFinish(String url) {

            }

            @Override
            public void onProgressChanged(int newProgress) {

            }
        });
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mWebView.loadUrl("javascript:callJS()");

                mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        ToastUtils.showShort(value);
                    }
                });

            }
        });


        mWebView.addJavascriptInterface(this, "android");//AndroidtoJS类对象映射到js的test对象


    }


    @JavascriptInterface
    public void hello(String msg) {
        System.out.println("JS调用了Android的hello方法" + msg);
        ToastUtils.showShort(msg);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_native;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
