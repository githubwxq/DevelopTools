package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.X5utils.OnFileChooserListener;
import com.juziwl.uilibrary.X5utils.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

/**
 * 通用的webview页面
 */
public class CommonWebActivity extends BaseActivity {

    private static final int MAX_COUNT = 100;
    ProgressBar progressBar1;

    X5WebView webview;

    TextView tvMessage;

    LinearLayout llNodata;

     String url;
    String title;

    public static void navToActivity(Activity activity,String url,String title) {
        Intent intent = new Intent(activity, CommonWebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        activity.startActivity(intent);
    }

    //全局声明，用于记录选择图片返回的地址值
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    @Override
    protected void initViews() {
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        topHeard.setTitle(title);
        webview=findViewById(R.id.webview);
        tvMessage=findViewById(R.id.tv_message);
        llNodata=findViewById(R.id.ll_nodata);
        progressBar1=findViewById(R.id.progressBar1);
        progressBar1.setMax(MAX_COUNT);
        progressBar1.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.webview_progressbar_bg));
        webview.setVerticalScrollBarEnabled(false);
        webview.setOnProgressChangeListener(newProgress -> {
            progressBar1.setProgress(newProgress);
            if (progressBar1 != null && newProgress != MAX_COUNT) {
                progressBar1.setVisibility(View.VISIBLE);
            } else if (progressBar1 != null) {
                progressBar1.setVisibility(View.GONE);
            }
        });
        webview.setOnPageFinishListenter(url -> {


        });

        webview.setOnFileChooserListener(new OnFileChooserListener() {
            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadFile = valueCallback;
                openFileChooseProcess();
            }
            @Override
            public boolean onShowFileChooser(ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadFiles = valueCallback;
                openFileChooseProcess();
                return true;
            }
        });
        webview.loadUrl(url);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_common_web;
    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        String lastPageUrl = webview.popLastPageUrl();
        if (lastPageUrl != null) {
            webview.loadUrl(lastPageUrl);
        }else {
            super.onBackPressed();
        }
    }
    /**
     * 确保注销配置能够被释放
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (this.webview != null) {
            webview.destroy();
        }
        super.onDestroy();
    }
    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
