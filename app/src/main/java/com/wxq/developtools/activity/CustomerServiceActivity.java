package com.wxq.developtools.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.gyf.immersionbar.ImmersionBar;
import com.juziwl.uilibrary.X5utils.OnFileChooserListener;
import com.juziwl.uilibrary.X5utils.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

import butterknife.BindView;

/**
 * 客服页面 七陌
 */
@Route(path = "/klook/customerService")
public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.webView)
    X5WebView mWebView;
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initViews() {
        ImmersionBar.with(this) .statusBarColor(com.wxq.commonlibrary.R.color.white)
                .statusBarDarkFont(true)
                .statusBarDarkFont(true).init();
        topHeard.setTitle("客服");
        initWebView();
    }
    /**
     * 给 WebView  填充内容
     */
    private void initWebView() {
        mWebView.setOnFileChooserListener(new OnFileChooserListener() {
            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                 selectImage();
            }

            @Override
            public boolean onShowFileChooser(ValueCallback<Uri[]> valueCallbacks, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = valueCallbacks;
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
        extras.put("生产日期","2019-8-27");
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

    @Override
    public void onBackPressed() {
        String lastPageUrl = mWebView.popLastPageUrl();
        if (lastPageUrl != null) {
            mWebView.loadUrl(lastPageUrl);
        }else {
            super.onBackPressed();
        }
    }
}
