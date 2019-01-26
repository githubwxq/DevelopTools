
package com.juziwl.uilibrary.chatview.audio;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class AudioRecordHandler implements Runnable {
    public static final String TAG = AudioRecordHandler.class.getSimpleName();
    private float maxRecordTime;

    private volatile boolean isRecording;
    private final Object mutex = new Object();
    private String fileName = null;
    private float recordTime = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long maxVolumeStart = 0;
    private long maxVolumeEnd = 0;
    private MediaRecorder mRecorder = null;

    public interface UiHandleCallback {
        Handler getUiHandle();
    }

    private UiHandleCallback handleCallback;

    public AudioRecordHandler(String fileName) {
        this.fileName = fileName;
    }

    public void setHandleCallback(UiHandleCallback handleCallback) {
        this.handleCallback = handleCallback;
    }

    @Override
    public void run() {
        try {
            synchronized (mutex) {
                while (!this.isRecording) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        throw new IllegalStateException("speexlib Wait() interrupted!", e);
                    }
                }
            }
            android.os.Process
                    .setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mRecorder.setOutputFile(fileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            try {
                mRecorder.prepare();
                mRecorder.start();
                recordTime = 0;
            } catch (Exception e) {
                Log.e(TAG, "prepare() failed");
            }
            startTime = System.currentTimeMillis();
            maxVolumeStart = System.currentTimeMillis();
            while (this.isRecording) {
                endTime = System.currentTimeMillis();
                recordTime = (endTime - startTime) / 1000.0f;
                if (recordTime >= maxRecordTime) {
                    if (handleCallback != null) {
                        handleCallback.getUiHandle().sendEmptyMessage(HandlerConstant.RECORD_AUDIO_TOO_LONG);
                    }
                    break;
                }
                maxVolumeEnd = System.currentTimeMillis();
                setMaxVolume();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (mRecorder != null) {
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setMaxVolume() {
        try {
            if (maxVolumeEnd - maxVolumeStart < 100) {
                return;
            }
            maxVolumeStart = maxVolumeEnd;
            int max;
            if (mRecorder != null) {
                max = (int) (mRecorder.getMaxAmplitude() / 2700.0);
            } else {
                max = 0;
            }
            Message Msg = handleCallback.getUiHandle().obtainMessage(HandlerConstant.RECEIVE_MAX_VOLUME, max);
            if (handleCallback != null) {
                handleCallback.getUiHandle().sendMessage(Msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(float len) {
        recordTime = len;
    }

    public void setRecording(boolean isRec) {
        synchronized (mutex) {
            this.isRecording = isRec;
            if (this.isRecording) {
                mutex.notify();
            }
        }
    }

    public boolean isRecording() {
        synchronized (mutex) {
            return isRecording;
        }
    }

    /**
     * @param maxRecordTime 单位是s
     */
    public void setMaxRecordTime(float maxRecordTime) {
        this.maxRecordTime = maxRecordTime;
    }
}
