package com.juziwl.uilibrary.multimedia;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
/**
 * 音频播放工具
 */
public class AudioPlayerUtil {
    private static final String TAG = "AudioRecordTest";
    private MediaPlayer mPlayer;

    public AudioPlayerUtil() {
    }

    public void start(String mFileName, OnCompletionListener listener) {
        if(this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        } else {
            this.mPlayer.reset();
        }

        try {
            this.mPlayer.setDataSource(mFileName);
            this.mPlayer.prepare();
            this.mPlayer.start();
            if(listener != null) {
                this.mPlayer.setOnCompletionListener(listener);
            }
        } catch (Exception var4) {
        }

    }

    public void stop() {
        if(this.mPlayer != null) {
            this.mPlayer.stop();
            this.mPlayer.release();
            this.mPlayer = null;
        }

    }
}