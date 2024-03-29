package com.luck.picture.lib.model;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import android.text.TextUtils;

import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * author：luck
 * project：LocalMediaLoader
 * package：com.luck.picture.ui
 * email：893855882@qq.com
 * data：16/12/31
 */

public class LocalMediaLoader {
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String DURATION = "duration";
    private static final int AUDIO_DURATION = 500;// 过滤掉小于500毫秒的录音
    private int type = PictureConfig.TYPE_IMAGE;
    private FragmentActivity activity;
    private boolean isGif;
    private long videoMaxS = 0;
    private long videoMinS = 0;

    private final static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
    };

    private final static String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.WIDTH,
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DURATION,
    };


    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            DURATION,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
    };

    private final static String[] AUDIO_PROJECTION = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.DURATION,
    };

    /**
     * 查询全部图片和视频，并且过滤掉已损坏图片和视频
     */
    private static final String SELECTION_ALL =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + MediaStore.MediaColumns.WIDTH + ">0";


    private static final String[] SELECTION_ALL_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };

    /**
     * 只查询图片条件
     */
    private final static String CONDITION_GIF =
            "(" + MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?)" + " AND "
                    + MediaStore.MediaColumns.WIDTH + ">0";

    private final static String[] SELECT_GIF = {
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    };


    /**
     * 获取全部图片，但过滤掉gif
     */
    private final static String CONDITION =
            "(" + MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?" + " or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?)" + " AND "
                    + MediaStore.MediaColumns.WIDTH + ">0";

    private final static String[] SELECT = {
            "image/jpeg",
            "image/png",
            "image/webp"
    };

    /**
     * 获取全部图片和视频，但过滤掉gif图片
     */
    private static final String SELECTION_NOT_GIF =
            "(" + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"

                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + MediaStore.MediaColumns.WIDTH + ">0";

    private static final String[] SELECTION_NOT_GIF_ARGS = {
            "image/jpeg",
            "image/png",
            "image/webp",
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };


    private static final String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";


    //   构造方法初始化变量
    public LocalMediaLoader(FragmentActivity activity, int type, boolean isGif, long videoMaxS, long videoMinS) {
        this.activity = activity;
        this.type = type;
        this.isGif = isGif;
        this.videoMaxS = videoMaxS;
        this.videoMinS = videoMinS;
    }

    // 回调关心的数据
    public void loadAllMedia(final LocalMediaLoadListener imageLoadListener) {
        activity.getSupportLoaderManager().initLoader(type, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {  //type  获取的类型
                        CursorLoader cursorLoader = null;
                        switch (id) {
                            case PictureConfig.TYPE_ALL:
                                String condition = getDurationCondition(0, 0);
                                String selectionAll =
                                        "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                                                + " OR "
                                                + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?" +
                                                " and " + condition + ")"
                                                + " AND " + MediaStore.MediaColumns.SIZE + ">0";
                                //根据变量选择不同的条件获取对应的资源
                                cursorLoader = new CursorLoader(activity, QUERY_URI,
                                        PROJECTION, selectionAll, SELECTION_ALL_ARGS, ORDER_BY);
                                break;
                            case PictureConfig.TYPE_IMAGE:
                                cursorLoader = new CursorLoader(
                                        activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        IMAGE_PROJECTION, isGif ? CONDITION_GIF : CONDITION,
                                        isGif ? SELECT_GIF : SELECT, IMAGE_PROJECTION[0] + " DESC");
                                break;
                            case PictureConfig.TYPE_VIDEO:
                                cursorLoader = new CursorLoader(
                                        activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                                        getDurationCondition(0, 0), null, VIDEO_PROJECTION[0] + " DESC");
                                break;
                            case PictureConfig.TYPE_AUDIO:
                                cursorLoader = new CursorLoader(
                                        activity, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AUDIO_PROJECTION,
                                        getDurationCondition(0, AUDIO_DURATION),
                                        null, AUDIO_PROJECTION[0] + " DESC");
                                break;
                            default:
                        }

                        return cursorLoader;
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {  // 加载器和数据
                        List<LocalMediaFolder> imageFolders = new ArrayList<>();
                        LocalMediaFolder allImageFolder = new LocalMediaFolder();
                        List<LocalMedia> latelyImages = new ArrayList<>();
                        if (data != null) {
                            int count = data.getCount();
                            if (count > 0) {
                                data.moveToFirst();
                                do {
                                    try {
                                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                                        File file = new File(path);
                                        if (!file.exists() || file.isHidden()) {
                                            continue;
                                        }
                                        String pictureType = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[6]));
                                        if (TextUtils.isEmpty(pictureType)) {
                                            continue;
                                        }
                                        if (!isGif && SELECT_GIF[2].equals(pictureType)) {
                                            continue;
                                        }
                                        boolean eqImg = pictureType.startsWith(PictureConfig.IMAGE);
                                        int duration = eqImg ? 0 : data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[7]));
                                        int w = eqImg ? data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]))
                                                : data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4]));
                                        int h = eqImg ? data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]))
                                                : data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4]));
                                        LocalMedia image = new LocalMedia(path, duration, type, pictureType, w, h);

                                        long id = data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                                        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);


                                        image.setUri(uri);

                                        LocalMediaFolder folder = getImageFolder(path, imageFolders);
                                        List<LocalMedia> images = folder.getImages();
                                        images.add(image);
                                        folder.setImageNum(folder.getImageNum() + 1);
                                        latelyImages.add(image);
                                        int imageNum = allImageFolder.getImageNum();
                                        allImageFolder.setImageNum(imageNum + 1);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } while (data.moveToNext());
                                try {
                                    if (latelyImages.size() > 0) {
                                        sortFolder(imageFolders);
                                        // 第一个文件夹为全部文件夹其他的获取图片的父
                                        imageFolders.add(0, allImageFolder);
                                        allImageFolder.setFirstImagePath(latelyImages.get(0).getPath());
                                        String title = type == PictureMimeType.ofAudio() ?
                                                activity.getString(R.string.picture_all_audio)
                                                : activity.getString(R.string.picture_camera_roll);
                                        allImageFolder.setName(title);
                                        // 全部胶卷的文件夹天加了全部的文件
                                        allImageFolder.setImages(latelyImages);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                imageLoadListener.loadComplete(imageFolders);
                            } else {
                                // 如果没有相册
                                imageLoadListener.loadComplete(imageFolders);
                            }
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                    }
                });
    }

    private void sortFolder(List<LocalMediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<LocalMediaFolder>() {
            @Override
            public int compare(LocalMediaFolder lhs, LocalMediaFolder rhs) {
                if (lhs.getImages() == null || rhs.getImages() == null) {
                    return 0;
                }
                int lsize = lhs.getImageNum();
                int rsize = rhs.getImageNum();
                return lsize == rsize ? 0 : (lsize < rsize ? 1 : -1);
            }
        });
    }

    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        for (LocalMediaFolder folder : imageFolders) {
            // 同一个文件夹下，返回自己，否则创建新文件夹
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setPath(folderFile.getAbsolutePath());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }

    public interface LocalMediaLoadListener {
        void loadComplete(List<LocalMediaFolder> folders);
    }

    private String getDurationCondition(long exMaxLimit, long exMinLimit) {
        long maxS = videoMaxS == 0 ? Long.MAX_VALUE : videoMaxS;
        if (exMaxLimit != 0) {
            maxS = Math.min(maxS, exMaxLimit);
        }

        return String.format(Locale.CHINA, "%d <%s duration and duration <= %d",
                Math.max(exMinLimit, videoMinS),
                Math.max(exMinLimit, videoMinS) == 0 ? "" : "=",
                maxS);
    }
}
