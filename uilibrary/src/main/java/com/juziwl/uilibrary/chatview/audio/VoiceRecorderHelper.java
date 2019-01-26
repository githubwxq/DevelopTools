package com.juziwl.uilibrary.chatview.audio;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;


/**
 * 作者：林冠宏
 * <p>
 * author: LinGuanHong,lzq is my dear wife.
 * <p>
 * My GitHub : https://github.com/af913337456/
 * <p>
 * My Blog   : http://www.cnblogs.com/linguanh/
 * <p>
 * on 2017/7/26.
 * <p>
 * function：高度封装的 语音 录制工具库
 */

@SuppressWarnings("dialog 已经在里面被封装好了")
public class VoiceRecorderHelper {

    private final static int RECORD_MOVE_DIS_LIMIT = 180;
    /**
     * 单位秒
     */
    public static final float DEFAULT_SOUND_RECORD_TIME = 60.0f;
    private float maxRecordTime = DEFAULT_SOUND_RECORD_TIME;

    private float y1 = 0, y2 = 0;
    private String audioSavePath;
    private CallBack callBack;
    private AudioRecordHandler audioRecorderInstance = null;
    private boolean isShowDialog = true;
    private Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.HANDLER_RECORD_FINISHED:
                    onRecordVoiceEnd((Float) msg.obj);
                    break;
                // 录音结束
                case HandlerConstant.HANDLER_STOP_PLAY:

                    break;
                case HandlerConstant.RECEIVE_MAX_VOLUME:
                    onReceiveMaxVolume((Integer) msg.obj);
                    break;
                case HandlerConstant.RECORD_AUDIO_TOO_LONG:
                    doFinishRecordAudio();
                    break;
                default:
                    break;
            }
        }
    };
    private Activity activity;

    private ImageView soundVolumeImg = null;
    private Dialog soundVolumeDialog = null;
    private LinearLayout soundVolumeLayout = null;

    @SuppressWarnings("捞底回收")
    public void onDestroy() {
        audioRecorderInstance = null;
        audioSavePath = null;
        activity = null;
        if (uiHandler != null) {
            uiHandler.removeCallbacksAndMessages(null);
            uiHandler = null;
        }
    }

    public void onPause() {

    }

    public VoiceRecorderHelper(Activity activity, CallBack callBack) {
        this.callBack = callBack;
        this.activity = activity;
        initSoundVolumeDialog();
    }

    private void initSoundVolumeDialog() {
        try {
            soundVolumeDialog = new Dialog(activity, R.style.SoundVolumeStyle);
            soundVolumeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            soundVolumeDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            soundVolumeDialog.setContentView(R.layout.sound_volume_dialog);
            soundVolumeDialog.setCanceledOnTouchOutside(true);
            soundVolumeImg = (ImageView) soundVolumeDialog.findViewById(R.id.sound_volume_img);
            soundVolumeLayout = (LinearLayout) soundVolumeDialog.findViewById(R.id.sound_volume_bg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CallBack {

        String setOutPutPath();

        /**
         * 设置输出路径
         */

        void onDown(View v);

        void onMove_in_limit(View v);

        void onMove_out_limit(View v);

        void onUp_start(View v);

        void onUp_cancel(View v);

        void onFinishRecord();

        void onRecordSuccess(float len, String savePath);

        void onRecordVolumeChange(int voiceValue);
    }

    private void doFinishRecordAudio() {
        try {
            if (audioRecorderInstance.isRecording()) {
                audioRecorderInstance.setRecording(false);
            }
            callBack.onFinishRecord();

            if (soundVolumeDialog.isShowing()) {
                soundVolumeDialog.dismiss();
            }
            audioRecorderInstance.setRecordTime(DEFAULT_SOUND_RECORD_TIME);
            onRecordVoiceEnd(DEFAULT_SOUND_RECORD_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onRecordVoiceEnd(float audioLen) {
        callBack.onRecordSuccess(audioLen, audioSavePath);
    }

    /**
     * 根据分贝值设置录音时的音量动画
     */
    private void onReceiveMaxVolume(int voiceValue) {
        callBack.onRecordVolumeChange(voiceValue);
        switch (voiceValue) {
            case 0:
            case 1:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_01);
                break;
            case 2:
            case 3:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_02);
                break;
            case 4:
            case 5:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_03);
                break;
            case 6:
            case 7:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_04);
                break;
            case 8:
            case 9:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_05);
                break;
            case 10:
            case 11:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_06);
                break;
            default:
                soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_07);
                break;
        }
    }

    public void Action_Down(View v, MotionEvent event) {
        if (event != null) {
            y1 = event.getY();
        }
        if (isShowDialog) {
            callBack.onDown(v);
            soundVolumeImg.setImageResource(R.drawable.tt_sound_volume_01);
            soundVolumeImg.setVisibility(View.VISIBLE);
            soundVolumeLayout.setBackgroundResource(R.drawable.tt_sound_volume_default_bk);
            soundVolumeDialog.show();
        }
        audioSavePath = callBack.setOutPutPath();

        audioRecorderInstance = new AudioRecordHandler(audioSavePath);
        audioRecorderInstance.setMaxRecordTime(maxRecordTime);
        audioRecorderInstance.setHandleCallback(new AudioRecordHandler.UiHandleCallback() {
            @Override
            public Handler getUiHandle() {
                return uiHandler;
            }
        });
        audioRecorderInstance.setRecording(true);
        ThreadToolUtils.getInstance().runInThreadPool(audioRecorderInstance);
    }

    public void Action_Move(View v, MotionEvent event) {
        if (event != null) {
            y2 = event.getY();
        }
        if (isShowDialog) {
            if (y1 - y2 > RECORD_MOVE_DIS_LIMIT) {
                callBack.onMove_in_limit(v);
                soundVolumeImg.setVisibility(View.GONE);
                soundVolumeLayout.setBackgroundResource(R.drawable.tt_sound_volume_cancel_bk);
            } else {
                callBack.onMove_out_limit(v);
                soundVolumeImg.setVisibility(View.VISIBLE);
                soundVolumeLayout.setBackgroundResource(R.drawable.tt_sound_volume_default_bk);
            }
        }
    }

    public void Action_Up(View v, MotionEvent event) {
        if (event != null) {
            y2 = event.getY();
        }
        if (audioRecorderInstance == null) {
            return;
        }
        if (audioRecorderInstance.isRecording()) {
            audioRecorderInstance.setRecording(false);
        }
        if (soundVolumeDialog.isShowing()) {
            soundVolumeDialog.dismiss();
        }
        callBack.onUp_start(v);
        if (y1 - y2 <= RECORD_MOVE_DIS_LIMIT) {
            if (audioRecorderInstance.getRecordTime() >= 1) {
                if (audioRecorderInstance.getRecordTime() < DEFAULT_SOUND_RECORD_TIME) {
                    Message msg = uiHandler.obtainMessage();
                    msg.what = HandlerConstant.HANDLER_RECORD_FINISHED;
                    msg.obj = audioRecorderInstance.getRecordTime();
                    uiHandler.sendMessage(msg);
                }
            } else {
                /** 在取消的范围 */
                callBack.onUp_cancel(v);
                if (isShowDialog) {
                    soundVolumeImg.setVisibility(View.GONE);
                    soundVolumeLayout.setBackgroundResource(R.drawable.tt_sound_volume_short_tip_bk);
                    soundVolumeDialog.show();
                }
                uiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (soundVolumeDialog.isShowing()) {
                            soundVolumeDialog.dismiss();
                        }
                    }
                }, 700);
            }
        }
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    /**
     * @param maxRecordTime 单位是s
     */
    public void setMaxRecordTime(float maxRecordTime) {
        this.maxRecordTime = maxRecordTime;
    }
}
