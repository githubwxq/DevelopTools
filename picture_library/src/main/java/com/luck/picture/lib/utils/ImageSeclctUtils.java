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


    /**
     * 打开相册只显示图片 最多9张
     * @param activity
     * @param localMedias
     */
    public static void openImageBulm(Activity activity, List<LocalMedia> localMedias) {
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


    /**
     * 打开相册显示图片和视频
     * @param activity
     * @param localMedias
     */
    public static void openAlbum(Activity activity, List<LocalMedia> localMedias) {
        PictureSelector.create(activity)
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .openGallery(PictureMimeType.ofAll())
                .openGallery(PictureMimeType.ofImage())
                // 最大图片选择数量
                .maxSelectNum(9)
                // 最小选择数量
                .minSelectNum(1)
                // 多选 or 单选
                .selectionMode(PictureConfig.MULTIPLE)
                // 是否可预览图片
                .previewImage(true)
                // 自定义拍照保存路径
                .setOutputCameraPath(GlobalContent.imgPath)
                // 自定义拍视频保存路径
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                // 是否显示拍照按钮
                .isCamera(true)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                // 是否显示gif图片
                .isGif(false)
                .compress(false)
                .selectionMedia(localMedias)
//                .setStyle(GlobalContent.loginType)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    /**
     * 更改头像选择一张图片
     * @param activity
     */
    public static void takeOnePhotoWithCut(Activity activity) {
        // 单独拍照
        PictureSelector.create(activity)
                // 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .openCamera(PictureMimeType.ofImage())
                // 主题样式设置 具体参考 values/styles
//                        .theme(themeId)
                // 最小选择数量
                .minSelectNum(1)
                // 多选 or 单选
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片
                .previewImage(true)
                // luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressGrade(Luban.THIRD_GEAR)
                // 是否显示拍照按钮
                .isCamera(false)
                // 自定义拍照保存路径
                .setOutputCameraPath(GlobalContent.imgPath)
                // 自定义拍视频保存路径
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                // 是否裁剪
                .enableCrop(true)
                // 是否压缩
                .compress(false)
                //系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .withAspectRatio(1, 1)
                // 裁剪框是否可拖拽
                .freeStyleCropEnabled(true)
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropFrame(false)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .showCropGrid(false)
                //预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .previewEggs(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    public static void chooseOneFromBulmWithoutCut(Activity activity) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(PictureMimeType.ofImage())
                // 最大图片选择数量
                .maxSelectNum(1)
                // 最小选择数量
                .minSelectNum(1)
                // 每行显示个数
                .imageSpanCount(4)
                // 多选 or 单选
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片
                .previewImage(true)
                // luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressGrade(Luban.THIRD_GEAR)
                // 是否显示拍照按钮
                .isCamera(false)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // 自定义拍照保存路径
                .setOutputCameraPath(GlobalContent.imgPath)
                // 自定义拍视频保存路径
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                // 是否裁剪
                .enableCrop(false)
                // 是否压缩
                .compress(false)
                //系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.sizeMultiplier(0.5f)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                //int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .withAspectRatio(1, 1)
                // 是否显示gif图片
                .isGif(false)
                // 裁剪框是否可拖拽
                .freeStyleCropEnabled(true)
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropFrame(false)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);


    }
    /**
     * 更换头像从相册选择单张图片
     * @param activity
     */
    public static void chooseOneFromBulm(Activity activity) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(activity)
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(PictureMimeType.ofImage())
                // 最大图片选择数量
                .maxSelectNum(1)
                // 最小选择数量
                .minSelectNum(1)
                // 每行显示个数
                .imageSpanCount(4)
                // 多选 or 单选
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片
                .previewImage(true)
                // luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .compressGrade(Luban.THIRD_GEAR)
                // 是否显示拍照按钮
                .isCamera(false)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // 自定义拍照保存路径
                .setOutputCameraPath(GlobalContent.imgPath)
                // 自定义拍视频保存路径
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                // 是否裁剪
                .enableCrop(true)
                // 是否压缩
                .compress(false)
                //系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                // glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.sizeMultiplier(0.5f)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                //int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .withAspectRatio(1, 1)
                // 是否显示gif图片
                .isGif(false)
                // 裁剪框是否可拖拽
                .freeStyleCropEnabled(true)
                // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropFrame(false)
                // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

}
