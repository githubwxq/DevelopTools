package com.juziwl.uilibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.juziwl.uilibrary.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.ImageSeclctUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

/**
 * 选择导航弹窗工具类
 */
public class ChooseNavtionHelp {

    private XXDialog xxDialog = null;

    private Activity mContext;

    public ChooseNavtionHelp(Activity context) {
        this.mContext = context;
    }

    public void getHeardPic(HeadPicCallBack callBack) {
        headPicCallBack = callBack;
        // 1 请求权限 。。。
        if (xxDialog == null) {
            xxDialog = new XXDialog(mContext, R.layout.dialog_head_pic_choose) {
                @Override
                public void convert(DialogViewHolder holder) {
                    holder.setText(R.id.rl_select,"打开百度地图");
                    holder.setText(R.id.rl_photo,"打开高德地图");

                    holder.getView(R.id.rl_photo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 拍照  先请求权限
                            requsePemisson(0);
                            xxDialog.dismiss();
                        }
                    });

                    holder.getView(R.id.rl_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            xxDialog.dismiss();
                        }
                    });

                    holder.getView(R.id.rl_select).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requsePemisson(1);
                            xxDialog.dismiss();
                        }
                    });
                }
            };
        }
        xxDialog.fromBottom().fullWidth().setCancelAble(true).setCanceledOnTouchOutside(true);
        xxDialog.showDialog();

    }

    private void requsePemisson(int i) {


    }


    private void requsetCameraPermisson() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        RxPermissionUtils.requestPhotoPermission(mContext, rxPermissions, o -> openCamera());
    }

    private void requsetExternalPemisson() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        RxPermissionUtils.requestExternalPermission(mContext, rxPermissions, o -> openBulm());
    }

    private void openBulm() {
        ImageSeclctUtils.chooseOneFromBulm(mContext);
    }

    private void openCamera() {
        ImageSeclctUtils.takeOnePhotoWithCut(mContext);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    String url = selectList.get(0).getCutPath();
                    if (!TextUtils.isEmpty(url)) {
                        headPicCallBack.getPath(url);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public HeadPicCallBack headPicCallBack;

    public interface HeadPicCallBack {
        public void getPath(String path);

    }

}
