package com.juziwl.uilibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.juziwl.uilibrary.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.ImageSeclctUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.BitmapUtils;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.functions.Consumer;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {


    public  ZXingView shareScanViewfinderView;
   ImageView tv_show_code;
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_scan;
    }

    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, ScanActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("扫一扫").setRightText("打开相册").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //前往选择相片
                ImageSeclctUtils.chooseOneFromBulmWithoutCut(ScanActivity.this);
            }
        });
        shareScanViewfinderView=findViewById(R.id.share_scan_viewfinder_view);
        shareScanViewfinderView.setDelegate(this);
        tv_show_code=findViewById(R.id.tv_show_code);
        tv_show_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBitmap();
            }
        });
    }

    private void createBitmap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("wxq1234", ConvertUtils.dp2px(200));
                String path = BitmapUtils.saveBitmap(bitmap);
                ToastUtils.showShort("图片保存地址"+path);
                UIHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        tv_show_code.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    String url = selectList.get(0).getPath();
                    if (!TextUtils.isEmpty(url)) {
                        ToastUtils.showShort(url + "选中的东西");
                    }
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            return QRCodeDecoder.syncDecodeQRCode(url);
                        }

                        @Override
                        protected void onPostExecute(String result) {
                            doScanQRCodeResult(result);
                        }
                    }.execute();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理结果
     * @param result
     */
    private void doScanQRCodeResult(String result) {
        if (StringUtils.isEmpty(result)) {
            ToastUtils.showShort("识别失败");
        }else {
            ToastUtils.showShort(result);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                shareScanViewfinderView.startCamera();//打开后置摄像头开始预览，但是并未开始识别
                shareScanViewfinderView.showScanRect();//显示扫描框
                shareScanViewfinderView.startSpot();
            }
        });
    }

    @Override
    protected void onStop() {
        shareScanViewfinderView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();

    }
    @Override
    protected void onPause() {
        super.onPause();
        shareScanViewfinderView.stopSpot();
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        doScanQRCodeResult(result);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

}


////使用方式 https://github.com/bingoogolapple/BGAQRCode-Android
//qrcv_topOffset	扫描框距离 toolbar 底部的距离	90dp
//        qrcv_cornerSize	扫描框边角线的宽度	3dp
//        qrcv_cornerLength	扫描框边角线的长度	20dp
//        qrcv_cornerColor	扫描框边角线的颜色	@android:color/white
//        qrcv_cornerDisplayType	扫描框边角线显示位置(相对于边框)，默认值为中间	center
//        qrcv_rectWidth	扫描框的宽度	200dp
//        qrcv_barcodeRectHeight	条码扫样式描框的高度	140dp
//        qrcv_maskColor	除去扫描框，其余部分阴影颜色	#33FFFFFF
//        qrcv_scanLineSize	扫描线的宽度	1dp
//        qrcv_scanLineColor	扫描线的颜色「扫描线和默认的扫描线图片的颜色」	@android:color/white
//        qrcv_scanLineMargin	扫描线距离上下或者左右边框的间距	0dp
//        qrcv_isShowDefaultScanLineDrawable	是否显示默认的图片扫描线「设置该属性后 qrcv_scanLineSize 将失效，可以通过 qrcv_scanLineColor 设置扫描线的颜色，避免让你公司的UI单独给你出特定颜色的扫描线图片」	false
//        qrcv_customScanLineDrawable	扫描线的图片资源「默认的扫描线图片样式不能满足你的需求时使用，设置该属性后 qrcv_isShowDefaultScanLineDrawable、qrcv_scanLineSize、qrcv_scanLineColor 将失效」	null
//        qrcv_borderSize	扫描边框的宽度	1dp
//        qrcv_borderColor	扫描边框的颜色	@android:color/white
//        qrcv_animTime	扫描线从顶部移动到底部的动画时间「单位为毫秒」	1000
//        qrcv_isCenterVertical（已废弃，如果要垂直居中用 qrcv_verticalBias="0.5"来代替）	扫描框是否垂直居中，该属性为true时会忽略 qrcv_topOffset 属性	false
//        qrcv_verticalBias	扫描框中心点在屏幕垂直方向的比例，当设置此值时，会忽略 qrcv_topOffset 属性	-1
//        qrcv_toolbarHeight	Toolbar 的高度，通过该属性来修正由 Toolbar 导致扫描框在垂直方向上的偏差	0dp
//        qrcv_isBarcode	扫描框的样式是否为扫条形码样式	false
//        qrcv_tipText	提示文案	null
//        qrcv_tipTextSize	提示文案字体大小	14sp
//        qrcv_tipTextColor	提示文案颜色	@android:color/white
//        qrcv_isTipTextBelowRect	提示文案是否在扫描框的底部	false
//        qrcv_tipTextMargin	提示文案与扫描框之间的间距	20dp
//        qrcv_isShowTipTextAsSingleLine	是否把提示文案作为单行显示	false
//        qrcv_isShowTipBackground	是否显示提示文案的背景	false
//        qrcv_tipBackgroundColor	提示文案的背景色	#22000000
//        qrcv_isScanLineReverse	扫描线是否来回移动	true
//        qrcv_isShowDefaultGridScanLineDrawable	是否显示默认的网格图片扫描线	false
//        qrcv_customGridScanLineDrawable	扫描线的网格图片资源	nulll
//        qrcv_isOnlyDecodeScanBoxArea	是否只识别扫描框中的码	false
//        qrcv_isShowLocationPoint	是否显示定位点	false
//        qrcv_isAutoZoom	码太小时是否自动缩放	false