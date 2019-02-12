package com.wxq.commonlibrary.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

/**
 * Created by 王晓清.
 * data 2019/1/28.
 * describe .
 */
public class VideoUtils {

    /**
     * 获取视频第一帧的图片
     */
    public static String getVideoFirstImage(String path) {
        String picTempPath = "";
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(path);
                Bitmap bitmap = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC );;
                picTempPath = BitmapUtils.saveBitmap(bitmap);
            }
        }
        if (picTempPath.isEmpty()) {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(path);
                Bitmap bitmap = retriever.getFrameAtTime();;
                picTempPath = BitmapUtils.saveBitmap(bitmap);
            }
        }


        return picTempPath;
    }

    /**
     * 获取视频的宽高和旋转角度
     */
    public static ArrayMap<String, String> getVideoInfo(String path) {
        ArrayMap<String, String> map = new ArrayMap<>(10);
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            // 视频宽度
            String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            map.put("width", width);
            // 视频高度
            String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            map.put("height", height);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // 视频旋转方向
                String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                map.put("rotation", rotation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据旋转角度判断
     * @return
     */
    public static ArrayMap<String, String> getVideoRealSize(String path) {
        ArrayMap<String, String> videoInfo = VideoUtils.getVideoInfo(path);
        String widthStr = videoInfo.get("width");
        String heightStr = videoInfo.get("height");
        String rotationStr = videoInfo.get("rotation");
        int width, height, rotation;
        if (rotationStr == null) {
            if (widthStr != null && heightStr != null) {
                width = Integer.parseInt(widthStr);
                height = Integer.parseInt(heightStr);
                if (width > height) {
                    videoInfo.put("height", widthStr);
                    videoInfo.put("width", heightStr);
                } else {
                    videoInfo.put("height", heightStr);
                    videoInfo.put("width", widthStr);
                }
            }
        } else {
            rotation = Integer.parseInt(rotationStr);
            if (widthStr != null && heightStr != null) {
                width = Integer.parseInt(widthStr);
                height = Integer.parseInt(heightStr);
                //苹果发的视频
                if (width < height) {
                    //横着拍
                    if (rotation == 90 || rotation == 270) {
                        videoInfo.put("height", widthStr);
                        videoInfo.put("width", heightStr);
                    } else {
                        videoInfo.put("height", heightStr);
                        videoInfo.put("width", widthStr);
                    }
                } else {
                    //横着拍
                    if (rotation == 0 || rotation == 180) {
                        videoInfo.put("height", heightStr);
                        videoInfo.put("width", widthStr);
                    } else {
                        videoInfo.put("height", widthStr);
                        videoInfo.put("width", heightStr);
                    }
                }
            }
        }
        return videoInfo;
    }


    /**
     * 获取视频大小
     * @param source
     * @return
     */
    private String ReadVideoSize(File source) {
        FileChannel fc= null;
        String size = "";
        try {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(source);
            fc= fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=fc){
                try{
                    fc.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

}
