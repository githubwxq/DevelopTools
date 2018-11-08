package com.wxq.commonlibrary.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.wxq.commonlibrary.imageloader.disk.DiskLruCache;
import com.wxq.commonlibrary.imageloader.disk.IOUtil;
import com.wxq.commonlibrary.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/07
 * desc:
 * version:1.0
 */
public class DiskCache implements BitmapCache {


    public static DiskCache mDiskCache;


    private String mCacheDir = "ImageLoader";

    private static final int MB = 1024 * 1024;

    private DiskLruCache mDiskLruCache;


    public static DiskCache getInstance(Context context) {
        if(mDiskCache==null)
        {
            synchronized (DiskCache.class)
            {
                if(mDiskCache==null)
                {
                    mDiskCache=new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }


    public DiskCache(Context context) {

        initDiskCache(context);

    }

    private void initDiskCache(Context context) {

        File directory = getDiskCache(mCacheDir, context);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            mDiskLruCache = mDiskLruCache.open(directory, 1, 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private File getDiskCache(String mCacheDir, Context context) {
        return new File(Environment.getExternalStorageDirectory(), mCacheDir);
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {

        DiskLruCache.Editor editor = null;
        OutputStream os = null;
        try {
            //路径非法字符
            editor = mDiskLruCache.edit(request.getImageUriMD5());
            //返回流然后写文件
            os = editor.newOutputStream(0);
            if (persistBitmap2Disk(bitmap, os)) {
                editor.commit();
            } else {
                editor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean persistBitmapDisk(Bitmap bitmap, OutputStream os) {
        //bitMap 写入到本地
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
        try {
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(bufferedOutputStream);
        }
        //保存到本地
        return true;
    }


    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos=new BufferedOutputStream(os);

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            IOUtil.closeQuietly(bos);
        }
        return true;

    }


    @Override
    public Bitmap get(BitmapRequest request) {
        try {
            DiskLruCache.Snapshot snapshot=mDiskLruCache.get(request.getImageUriMD5());
            if(snapshot!=null)
            {
                InputStream inputStream=snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
