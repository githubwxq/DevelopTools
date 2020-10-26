package com.example.uitestdemo.fragment.components.qrcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.PathUtils;
import androidx.fragment.app.Fragment;

import com.example.qrlibrary.XQRCode;
import com.example.qrlibrary.util.QRCodeAnalyzeUtils;
import com.example.uitestdemo.R;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class QrDetailFragment extends BaseFragment {

    @BindView(R.id.tv_activity)
    TextView tvActivity;

    public static QrDetailFragment newInstance() {
        QrDetailFragment fragment = new QrDetailFragment();

        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_qr_detail;
    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.tv_activity)
    public void onViewClicked() {
        startScan(ScanType.DEFAULT);

    }







    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    /**
     * 定制化扫描界面Request Code
     */
    public static final int REQUEST_CUSTOM_SCAN = 113;

    private void startScan(ScanType scanType) {
        switch (scanType) {
            case CUSTOM:


                break;
            case DEFAULT:
                XQRCode.startScan(this, REQUEST_CODE);
                break;
            case DEFAULT_Custom:
                CustomCaptureActivity.start(this, REQUEST_CODE, R.style.XQRCodeTheme_Custom);
                break;
            case REMOTE:
                Intent intent = new Intent(XQRCode.ACTION_DEFAULT_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理二维码扫描结果
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
            handleScanResult(data);
        }

        //选择系统图片并解析
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                getAnalyzeQRCodeResult(uri);
            }
        }
    }

    /**
     * 处理二维码扫描结果
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    showToast("解析结果:" + result);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {

                    showToast("解析二维码失败:");
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getAnalyzeQRCodeResult(Uri uri) {
//        XQRCode.analyzeQRCode(PathUtils.getFilePathByUri(getContext(), uri), new QRCodeAnalyzeUtils.AnalyzeCallback() {
//            @Override
//            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                XToastUtils.toast("解析结果:" + result, Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onAnalyzeFailed() {
//                XToastUtils.toast("解析二维码失败", Toast.LENGTH_LONG);
//            }
//        });
    }




    /**
     * 二维码扫描类型
     */
    public enum ScanType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 默认(修改主题）
         */
        DEFAULT_Custom,
        /**
         * 远程
         */
        REMOTE,
        /**
         * 自定义
         */
        CUSTOM,
    }


}