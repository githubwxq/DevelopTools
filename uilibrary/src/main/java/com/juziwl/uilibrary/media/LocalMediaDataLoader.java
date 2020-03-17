package com.juziwl.uilibrary.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;

import com.luck.picture.lib.entity.LocalMediaFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取相册相关数据的加载器
 */
public class LocalMediaDataLoader implements Handler.Callback {
    private static final int MSG_QUERY_MEDIA_SUCCESS = 0;
    private static final int MSG_QUERY_MEDIA_ERROR = -1;

    private static String[] TYPE_IMAGE_STATIC = new String[]{"image/jpg", "image/jpeg", "image/png", "image/heic"};
    private static String[] TYPE_IMAGE_GIF = new String[]{"image/gif"};
    private static String[] TYPE_IMAGE_ALL = new String[]{"image/jpg", "image/jpeg", "image/png", "image/heic", "image/gif"};


    private Handler mHandler;

    private Context mContext;

    public LocalMediaDataLoader(Context mContext) {
        this.mContext = mContext;
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }


    public void loadAllImageData() {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = mContext.getContentResolver();
                Cursor cursor = null;
                String[] queryType = null;
                queryType = TYPE_IMAGE_ALL;
                cursor = cr.query(mImgUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        queryType,
                        MediaStore.Images.Media.DATE_TAKEN+ " DESC");


                List<LocalMediaEntity> localMediaEntities = new ArrayList<>();

                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    long dateTaken = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                    LocalMediaEntity localMediaEntity = new LocalMediaEntity();
                    localMediaEntity.setPath(path);
                    localMediaEntities.add(localMediaEntity);

                }

                mHandler.sendMessage(mHandler.obtainMessage(MSG_QUERY_MEDIA_SUCCESS, localMediaEntities));
            }
        });
    }


    @Override
    public boolean handleMessage(Message msg) {
        if (mCompleteListener == null) return false;
        switch (msg.what) {
            case MSG_QUERY_MEDIA_SUCCESS:
                mCompleteListener.loadComplete((List<LocalMediaEntity>) msg.obj);
                break;
            case MSG_QUERY_MEDIA_ERROR:
                mCompleteListener.loadMediaDataError();
                break;
        }

        return false;
    }

    private LocalMediaLoadListener mCompleteListener;

    public void setCompleteListener(LocalMediaLoadListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    public interface LocalMediaLoadListener {

        void loadComplete(List<LocalMediaEntity> folders);

        void loadMediaDataError();
    }
}
