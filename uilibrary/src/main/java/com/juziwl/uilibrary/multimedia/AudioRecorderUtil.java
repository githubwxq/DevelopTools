package com.juziwl.uilibrary.multimedia;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Handler;

import java.io.File;
import java.io.IOException;

/**
 * 音频录制工具
 */
public class AudioRecorderUtil {
    private final String TAG = AudioRecorderUtil.class.getName();
    public static final int MAX_LENGTH = 60000;
    private String filePath;
    private String folderPath;
    private String fileName;
    private MediaRecorder mMediaRecorder;
    private int maxLength;
    private long startTime;
    private long endTime;
    private AudioRecorderUtil.OnAudioStatusUpdateListener audioStatusUpdateListener;
    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            AudioRecorderUtil.this.updateMicStatus();
        }
    };
    private int BASE = 1;
    private int SPACE = 100;

    public AudioRecorderUtil(String folderPath,String fileName) {
        File path = new File(folderPath);
        if(!path.exists()) {
            path.mkdirs();
        }

        this.folderPath = folderPath;
        this.fileName = fileName;
        this.maxLength = '\uea60';
    }

    public void start() {
        if(this.mMediaRecorder == null) {
            this.mMediaRecorder = new MediaRecorder();
        } else {
            try {
                this.mMediaRecorder.stop();
                this.mMediaRecorder.reset();
                this.mMediaRecorder.release();
            } catch (Exception var2) {
                this.mMediaRecorder.reset();
            }
        }

        this.mHandler.removeCallbacks(this.mUpdateMicStatusTimer);
        try {
            this.mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            this.mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.filePath = this.folderPath + fileName;
            this.mMediaRecorder.setOutputFile(this.filePath);
            this.mMediaRecorder.setMaxDuration(this.maxLength);
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.setOnInfoListener(new OnInfoListener() {
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    //达到最大录制时间
                    if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        AudioRecorderUtil.this.stop();
                    }

                }
            });
            this.mMediaRecorder.start();
            this.startTime = System.currentTimeMillis();
            this.updateMicStatus();
            if(this.audioStatusUpdateListener != null) {
                this.audioStatusUpdateListener.onStart();
            }
        } catch (IllegalStateException var3) {
            if(this.audioStatusUpdateListener != null) {
                this.audioStatusUpdateListener.onError(var3);
            }

            this.cancel();
        } catch (IOException var4) {
            if(this.audioStatusUpdateListener != null) {
                this.audioStatusUpdateListener.onError(var4);
            }

            this.cancel();
        }

    }

    public long getSumTime() {
        return this.startTime == 0L?0L:System.currentTimeMillis() - this.startTime;
    }

    public long stop() {
        if(this.mMediaRecorder == null) {
            return 0L;
        } else {
            this.endTime = System.currentTimeMillis();

            try {
                this.mMediaRecorder.stop();
                this.mMediaRecorder.reset();
                this.mMediaRecorder.release();
                this.mMediaRecorder = null;
                if(this.audioStatusUpdateListener != null) {
                    this.audioStatusUpdateListener.onStop(this.filePath);
                }
            } catch (RuntimeException var3) {
                this.mMediaRecorder.reset();
                this.mMediaRecorder.release();
                this.mMediaRecorder = null;
                File file = new File(this.filePath);
                if(file.exists()) {
                    file.delete();
                }

                if(this.audioStatusUpdateListener != null) {
                    this.audioStatusUpdateListener.onError(var3);
                }
            }

            this.filePath = "";
            return this.endTime - this.startTime;
        }
    }

    public void cancel() {
        try {
            this.mMediaRecorder.stop();
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
        } catch (RuntimeException var2) {
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
        }

        File file = new File(this.filePath);
        if(file.exists()) {
            file.delete();
        }

        this.filePath = "";
        if(this.audioStatusUpdateListener != null) {
            this.audioStatusUpdateListener.onCancel();
        }

    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * postDelay100毫秒计时 获取声音变化
     */
    private void updateMicStatus() {
        if(this.mMediaRecorder != null) {
            double ratio = (double)this.mMediaRecorder.getMaxAmplitude() / (double)this.BASE;
            double db = 0.0D;
            if(ratio > 1.0D) {
                db = 20.0D * Math.log10(ratio);
                if(null != this.audioStatusUpdateListener) {
                    this.audioStatusUpdateListener.onProgress(db, System.currentTimeMillis() - this.startTime);
                }
            }

            this.mHandler.postDelayed(this.mUpdateMicStatusTimer, (long)this.SPACE);
        }

    }

    public void setOnAudioStatusUpdateListener(AudioRecorderUtil.OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    public interface OnAudioStatusUpdateListener {
        void onStart();

        void onProgress(double var1, long var3);

        void onError(Exception var1);

        void onCancel();

        void onStop(String var1);
    }
}