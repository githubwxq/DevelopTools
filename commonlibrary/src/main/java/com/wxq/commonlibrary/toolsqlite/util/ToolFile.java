package com.wxq.commonlibrary.toolsqlite.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 文件操作类
 */
public class ToolFile {
    //获取文件路径
    //Context.getCacheDir()：获取私有缓存路径；
    //Context.getFilesDir()：获取私有文件路径；
    //Context.getExternalCacheDir()：获取公有缓存路径；
    //Context.getExternalFilesDir()：获取公有文件路径；
    //Environment.getExternalStorageDirectory().getPath():获取公有存储路径;
    //Environment.isExternalStorageEmulated()；true代表公有存储是内置SD卡；false代表公有存储是外置SD卡

    /**
     * 根据路径判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 在SD卡中创建目录,并返回路径
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getSDDirPath(String filePath) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + filePath;
        path = getDirPath(path);
        return path;
    }

    /**
     * 创建目录并获取目录路径
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getDirPath(String filePath) {
        File file = getDir(filePath);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return filePath;
    }

    /**
     * 创建目录
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File getDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static File getFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }

        // 判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            file.getParentFile().mkdirs();
        }
        if (file.getParentFile().exists()) {
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成唯一文件名
     *
     * @param fileName 原文件名
     * @return 新文件名
     */
    public static String getNewFileName(String fileName) {
        String newFilename = null;
        if (fileName != null && !fileName.trim().equals("")) {
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                newFilename = fileName.substring(0, index) + "_";
                newFilename += Tool.getNowDate("yyyyMMddHHmmss");
                newFilename += fileName.substring(index);
            }
        }
        return newFilename;
    }

    /**
     * 复制文件
     *
     * @param is  输入流
     * @param fos 输出流
     */
    public static void copyFile(InputStream is, FileOutputStream fos) {
        try {
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
            }
            fos.flush();//刷新缓冲区
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param pathFrom 源路径
     * @param pathTo   目标路径
     */
    public static void copyFile(String pathFrom, String pathTo) {
        try {
            InputStream is = new FileInputStream(new File(pathFrom));
            FileOutputStream fos = new FileOutputStream(getFile(pathTo));
            copyFile(is, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制从assets目录中文件
     *
     * @param context   Context 使用CopyFiles类的Activity
     * @param assetPath String  asset文件路径,相对于assets目录的路径,开头不带"/"
     * @param newPath   String  复制后路径,绝对路径
     */
    public static void copyAssets(Context context, String assetPath, String newPath) {
        try {
            InputStream is = context.getAssets().open(assetPath);
            FileOutputStream fos = new FileOutputStream(getFile(newPath));
            copyFile(is, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取对象
     *
     * @param filePath 文件路径
     * @return 读取的对象
     * @throws Exception
     */
    public static Object readObject(String filePath) throws Exception {
        if (filePath == null || filePath.trim().equals("")) {
            return null;
        }
        File file = getFile(filePath);
        if (file == null) {
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * 写入对象
     *
     * @param filePath 文件路径
     * @param o        写入的对象
     * @return 是否写入成功
     * @throws Exception
     */
    public static boolean writeObject(String filePath, Object o) throws Exception {
        if (filePath == null || filePath.trim().equals("") || o == null) {
            return false;
        }
        File file = getFile(filePath);
        if (file == null) {
            return false;
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(o);
        oos.close();
        return true;
    }

    /**
     * 删除文件或目录及其子目录
     *
     * @param dir
     */
    public static void deleteDir(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }
}
