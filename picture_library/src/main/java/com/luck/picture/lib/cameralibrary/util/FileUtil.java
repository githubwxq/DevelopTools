package com.luck.picture.lib.cameralibrary.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * =====================================
 * 作    者: 陈嘉桐
 * 版    本：1.1.4
 * 创建日期：2017/4/25
 * 描    述：
 * =====================================
 */
public class FileUtil {

    public static String saveBitmap(String dir, Bitmap b) {
        File file = new File(dir);
        if (!file.exists() && file.isDirectory()) {
            file.mkdirs();
        }
        long dataTake = System.currentTimeMillis();
//        String jpegName = path + File.separator + "picture_" + dataTake + ".jpg";
        String jpegName = dir + "picture_" + dataTake + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String saveBitmap(String dir) {
        File file = new File(dir);
        if (!file.exists() && file.isDirectory()) {
            file.mkdirs();
        }
        long dataTake = System.currentTimeMillis();
//        String jpegName = path + File.separator + "picture_" + dataTake + ".jpg";
        String jpegName = dir + "picture_" + dataTake + ".jpg";
        Bitmap b = BitmapFactory.decodeFile(dir);
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean deleteFile(String url) {
        boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    //获取文件的大小
    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            return -1;
        }
        return file.length();
    }


}
