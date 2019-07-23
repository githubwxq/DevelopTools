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
 * 修改个人头像帮助类 比较通用的
 */
public class ChooseHeadPicHelp {

    private XXDialog xxDialog = null;

    private Activity mContext;

    public ChooseHeadPicHelp(Activity context) {
        this.mContext = context;
    }

    public void getHeardPic(HeadPicCallBack callBack) {
        headPicCallBack = callBack;
        // 1 请求权限 。。。
        if (xxDialog == null) {
            xxDialog = new XXDialog(mContext, R.layout.dialog_head_pic_choose) {
                @Override
                public void convert(DialogViewHolder holder) {
                    holder.getView(R.id.rl_photo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 拍照  先请求权限
                            requsetCameraPermisson();
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
                            // 相册中选择
                            requsetExternalPemisson();
                            xxDialog.dismiss();
                        }
                    });
                }
            };
        }
        xxDialog.fromBottom().fullWidth().setCancelAble(true).setCanceledOnTouchOutside(true);
        xxDialog.showDialog();

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
