package com.luck.picture.lib;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import com.bumptech.glide.Glide;
import com.juziwl.ijkplayerlib.media.IjkVideoView;
import com.luck.picture.lib.utils.PictureMd5;
import com.luck.picture.lib.widget.LoadingCircleView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.util.FileUtils;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/11/08
 * @description 视频播放页面  视频录制页面需要添加
 */
public class PictureVideoPlayActivity extends PictureBaseActivity implements IMediaPlayer.OnCompletionListener, View.OnClickListener {
    private static final String TAG = "TestAA===";
    public static final String EXTRA_VIDEO_URL = "video_path";
    public static final String EXTRA_VIDEO_IMG_URL = "videoImageUrl";
    public static final String EXTRA_VIDEO_ICAN_SAVE = "isVideoCanSave";
    public static final String EXTRA_VIDEO_SAVE_PATH = "videoSavePath";
    public static final String ACTION_FINISH_SELF = "com.luck.picture.lib.action_finish_self";
    public static String videoPath = "";
    private ImageView picture_left_back;
    private MediaController mMediaController;
    private IjkVideoView mVideoView;
    private ImageView iv_play;
    private int mPositionWhenPaused = -1;
    private boolean isNeedDownload = false;
    private LoadingCircleView loading;
    private ImageView defaultImg;
    private String defaultImgUrl = "";
    private boolean canSave = false;
    private String videoLocalPath = "";
    private String videoSavePath = "";
    private File file;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_FINISH_SELF.equals(intent.getAction())) {
                if (mVideoView != null) {
                    mVideoView.stopPlayback();
                    mVideoView = null;
                }
                OkGo.getInstance().cancelTag(PictureVideoPlayActivity.class);
                finish();
//                String userName = intent.getStringExtra("userName");
//                SingleDialog.getInstance().createDialog(PictureVideoPlayActivity.this, "提示",
//                        userName + "撤回了一条消息", "确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                SingleDialog.getInstance().dismiss();
//                                finish();
//                            }
//                        }).show();
            }
        }
    };

    /**
     * @param videoSavePath 传Global.VIDEOPATH
     */
    public static void navToVideoPlay(Context context, String videoPath,
                                      String defaultImgUrl, boolean canSave, String videoSavePath) {
        Intent a = new Intent(context, PictureVideoPlayActivity.class);
        a.putExtra(EXTRA_VIDEO_URL, videoPath);
        a.putExtra(EXTRA_VIDEO_IMG_URL, defaultImgUrl);
        a.putExtra(EXTRA_VIDEO_ICAN_SAVE, canSave);
        a.putExtra(EXTRA_VIDEO_SAVE_PATH, videoSavePath);
        context.startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.picture_activity_video_play);
        videoPath = getIntent().getStringExtra(EXTRA_VIDEO_URL);
        defaultImgUrl = getIntent().getStringExtra(EXTRA_VIDEO_IMG_URL);
        videoSavePath = getIntent().getStringExtra(EXTRA_VIDEO_SAVE_PATH);
        canSave = getIntent().getBooleanExtra(EXTRA_VIDEO_ICAN_SAVE, false);
        picture_left_back = (ImageView) findViewById(R.id.picture_left_back);
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        View videoMask = findViewById(R.id.video_mask);
        if (canSave) {
            videoMask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDialog();
                    return true;
                }
            });
        }
        videoMask.setOnClickListener(this);
        mVideoView.setBackgroundColor(Color.BLACK);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        loading = (LoadingCircleView) findViewById(R.id.loading);
        defaultImg = (ImageView) findViewById(R.id.default_img);
        mVideoView.setOnCompletionListener(this);
        picture_left_back.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(ACTION_FINISH_SELF));
        if (videoPath.startsWith("http")) {
            String suffix = videoPath.substring(videoPath.lastIndexOf("."));
            String fileName = PictureMd5.MD5(videoPath) + suffix;
            file = new File(videoSavePath, fileName);
            if (file.exists()) {
                isNeedDownload = false;
                videoLocalPath = file.getAbsolutePath();
                mVideoView.setVideoPath(file.getAbsolutePath());
                mVideoView.start();
            } else {
                isNeedDownload = true;
                defaultImg.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                Glide.with(this).load(defaultImgUrl).into(defaultImg);
                downloadVideo(videoPath, videoSavePath, fileName);
            }
        } else {
            videoLocalPath = videoPath;
            mVideoView.setVideoPath(videoPath);
            mVideoView.start();
        }
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("okGo");
        loggingInterceptor.setColorLevel(Level.INFO);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        //全局的读取超时时间
        builder.readTimeout(60_000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(60_000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(60_000, TimeUnit.MILLISECONDS);
        builder.proxy(Proxy.NO_PROXY);
        return builder.build();
    }

    private void downloadVideo(String videoPath, String videoDir, String fileName) {
        OkGo.<File>get(videoPath)
                .tag(PictureVideoPlayActivity.class)
                .client(getOkHttpClient())
                .execute(new FileCallback(videoDir, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        Logger.e(TAG + ";onSuccess==" + response.message());
                        String absolutePath = response.body().getAbsolutePath();
                        videoLocalPath = absolutePath;
                        if (mVideoView != null) {
                            mVideoView.setVideoPath(absolutePath);
                            mVideoView.start();
                            defaultImg.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        Logger.e(TAG + ";downloadProgress===fileSize==" + FileUtils.getFileSize(file));
                        loading.setProgerss((int) (progress.fraction * 100), true);
                        long totalSize = progress.totalSize;
                        long currentSize = progress.currentSize;
                        Logger.e(TAG + ";downloadProgress==="
                                + progress.fraction * 100 + "totalSize==" + totalSize + ";currentSize=" + currentSize);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        Logger.e(TAG + ";error==" + response.message());
                        super.onError(response);
                        isNeedDownload = true;

                    }

                    @Override
                    public void onCacheSuccess(Response<File> response) {
                        super.onCacheSuccess(response);
                        Logger.e(TAG + "onCacheSuccess" + response.message());
                    }
                });
    }

    private Dialog dialog;

    private void showDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.picture_popmenulongbtn, null);
            view.findViewById(R.id.btn_keep).setOnClickListener(this);
            view.findViewById(R.id.btn_shutdown).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog = new Dialog(this, R.style.picture_chooseDialog);
            dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        dialog.show();
        //在show调用之后设置宽度铺满
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);
    }

    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        if (!isNeedDownload) {
            if (mVideoView != null) {
                mVideoView.pause();
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoPath = "";
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        super.onDestroy();
        mMediaController = null;
        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        OkGo.getInstance().cancelTag(PictureVideoPlayActivity.class);
    }

    @Override
    public void onResume() {
        if (!isNeedDownload) {
            if (mVideoView != null) {
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
            }
        }
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.picture_left_back || id == R.id.video_mask) {
            finish();
        } else if (id == R.id.iv_play) {
            mVideoView.start();
            iv_play.setVisibility(View.INVISIBLE);
        } else if (id == R.id.btn_keep) {
            if (TextUtils.isEmpty(videoLocalPath)) {
                showToast("视频正在下载，请稍后保存");
                return;
            }

            MediaScannerConnection.scanFile(this, new String[]{videoLocalPath}, new String[]{"video/mp4"},
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            int first = videoLocalPath.indexOf("/orange/");
                            int end = videoLocalPath.lastIndexOf("/");
                            showToast("视频已保存至" + videoLocalPath.substring(first + 1, end) + "文件夹");
                        }
                    });
            dialog.dismiss();
        }
    }

    public ContentValues getVideoContentValues(File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", paramLong);
        localContentValues.put("date_modified", paramLong);
        localContentValues.put("date_added", paramLong);
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", paramFile.length());
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(paramFile.getPath());
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double duration = player.getDuration();
        player.release();
        // 时间插值
        localContentValues.put(MediaStore.Video.Media.DURATION, duration);
        return localContentValues;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name)) {
                    return getApplicationContext().getSystemService(name);
                }
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        iMediaPlayer.seekTo(0);
        iMediaPlayer.start();
    }
}
