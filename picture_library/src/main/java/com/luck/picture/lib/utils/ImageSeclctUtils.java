package com.luck.picture.lib.utils;

import android.app.Activity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wxq.commonlibrary.constant.GlobalContent;

import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/1/6
 * @description 用于键盘上面的选择图片和拍照
 */
public class ImageSeclctUtils {
    /**
     * 单独拍照
     */
    public static void openCamera(Activity activity, List<LocalMedia> localMedias) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .setOutputCameraPath(GlobalContent.imgPath)
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(true)
                .compress(false)
                .isSetTheme(false)
                .selectionMedia(localMedias)
//                .setStyle(Global.loginType)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void openBulm(Activity activity, List<LocalMedia> localMedias) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .minSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .setOutputCameraPath(GlobalContent.imgPath)
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                .previewImage(true)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(false)
                .compress(false)
                .glideOverride(160, 160)
//                .setStyle(Global.loginType)
                .selectionMedia(localMedias)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 打开相机拍照和录像
     *
     * @param activity
     * @param localMedias
     */
    public static void openCameraForAll(Activity activity, List<LocalMedia> localMedias, long... videoParam) {
        long videoDuration = PictureConfig.DAFAULT_VIDEO_DURATION;
        if (videoParam != null && videoParam.length > 0) {
            videoDuration = videoParam[0];
        }
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofAll())
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .setOutputCameraPath(GlobalContent.imgPath)
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(true)
                .compress(false)
                .isSetTheme(false)
                .videoDuration(videoDuration)
                .selectionMedia(localMedias)
//                .setStyle(Global.loginType)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 空间能发30张照片
     *
     * @param activity
     * @param localMedias
     */
    public static void openBulmWithoutCamera(Activity activity, List<LocalMedia> localMedias, long... videoParam) {
        long videoDuration = PictureConfig.DAFAULT_VIDEO_DURATION;
        if (videoParam != null && videoParam.length > 0) {
            videoDuration = videoParam[0];
        }
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofAll())
                .maxSelectNum(30)
                .minSelectNum(1)
                //增加压缩
                .compress(true)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .setOutputCameraPath(GlobalContent.imgPath)
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                .previewImage(true)
                .compressGrade(Luban.THIRD_GEAR)
                .isCamera(false)
                .compress(false)
                .glideOverride(160, 160)
                .videoDuration(videoDuration)
//                .setStyle(Global.loginType)
                .selectionMedia(localMedias)
                .isJudeImageSize(true)
                .setmaxImageSize(PictureConfig.DEFAULT_IMAGE_SIZE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}
