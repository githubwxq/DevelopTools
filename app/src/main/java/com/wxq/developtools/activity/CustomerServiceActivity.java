package com.wxq.developtools.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 客服页面
 */
@Route(path = "/klook/customerService")
public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView mWebView;
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initViews() {
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.white));
        topHeard.setTitle("客服");
        initWebView();
    }
    /**
     * 给 WebView  填充内容
     */
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        //settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 默认使用缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);            // 不使用缓存  （注！使用缓存页面会有问题，有时登录不上）
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//允许js弹出窗口
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {


            // Android  4.1以上
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                selectImage();
            }

            // Android 5.0以上
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                selectImage(fileChooserParams);
                return true;
            }
        });


        JSONObject left = new JSONObject();
        left.put("url", "http://fs-im-kefu.7moor.com/im/219b1c10-97bc-11e9-a2c7-034767220935/2019-07-24%2016:42:30/1563957750037/73738147.jpg?imageView2/2/w/10000");

        JSONObject right1 = new JSONObject();
        right1.put("text", "紧凑型SUV家庭首选旅行必备家用汽车紧凑型SUV家庭首选旅行必备家用汽车");
        right1.put("color", "#595959");
        right1.put("fontSize", 12);

        JSONObject right2 = new JSONObject();
        right2.put("text", "不错啊");
        right2.put("color", "#595959");
        right2.put("fontSize", 12);

        JSONObject right3 = new JSONObject();
        right3.put("text", "￥ 100");
        right3.put("color", "#ff0000");
        right3.put("fontSize", 14);

        JSONObject productInfo = new JSONObject();
        productInfo.put("left", left);
        productInfo.put("url", "https://www.7moor.com/");
        productInfo.put("right1", right1);
        productInfo.put("right2", right2);
        productInfo.put("right3", right3);


        JSONObject extras = new JSONObject();
        extras.put("生产日期","2019-1-1");
        extras.put("质保期","180天");


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickName", "文庆昵称");
        jsonObject.put("cardInfo", productInfo);


        String deviceName = "安卓";
        String userId = "user1234";
        String fromDevice = "Android";
        String otherParams = jsonObject.toJSONString();
        String customField = extras.toJSONString();

        Log.e("aaa", productInfo.toString());

        String url = String.format("https://ykf-webchat.7moor.com/wapchat.html?accessId=219b1c10-97bc-11e9-a2c7-034767220935" +
                        "&urlTitle=%s&clientId=%s&fromUrl=%s&otherParams=%s&customField=%s",
                deviceName, userId, fromDevice, otherParams,customField);

        mWebView.loadUrl(url);


        Log.e("aaa", url);

    }

    private void selectImage() {
        selectImage(null);
    }

    private void selectImage(WebChromeClient.FileChooserParams fileChooserParams) {
        Intent intent = new Intent();
        String action = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            action = Intent.ACTION_OPEN_DOCUMENT;
        } else {
            action = Intent.ACTION_GET_CONTENT;
        }
        intent.setAction(action);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                && fileChooserParams.getAcceptTypes().length > 0 && !"".equals(fileChooserParams.getAcceptTypes()[0])) {
            intent.setType("image/*");
        } else {
            intent.setType("*/*");
        }
        startActivityForResult(Intent.createChooser(intent, "Image Chooser"), 15);
    }

    //全局声明，用于记录选择图片返回的地址值
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 15 || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }



}
