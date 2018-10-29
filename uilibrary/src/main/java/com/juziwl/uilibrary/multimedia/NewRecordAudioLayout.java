package com.juziwl.uilibrary.multimedia;

/**
 * Created by 搬砖小能手 on 2017/4/24.
 *
 * @author wxq
 * @modify Neil
 * 介绍：可以断续录制音频的控件
 */

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.otherview.LineWaveVoiceView;
import com.juziwl.uilibrary.utils.ConvertUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewRecordAudioLayout extends RelativeLayout {
    private static final String TAG = NewRecordAudioLayout.class.getSimpleName();
    private onKybdsChangeListener mListener;
    NewRecordCircleProgressButton progress;
    TextView reset;
    RelativeLayout rlRecordVoice;
    RelativeLayout rl_try;
    RelativeLayout rl_finish;
    TextView sure;
    LineWaveVoiceView lineWaveVoice;
    TextView tv_center_tip;
    TextView tv_try;
    ImageView finish_bg,try_bg;


    long second;


    List<AudioObj> audioObjs = new ArrayList<>();


    AudioPlayerUtil audioPlayerUtil;

    String audioFilePath;

    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DevelopTools/video/";


    AudioRecorderUtil audioRecorderUtil;

    AudioObj audioObj = null;


    //是否正在试听
    boolean isTryPlay = false;

    public static final int STATE_TRYPAUSE = 5;
    public static final int STATE_TRYPLAY = 6;

    Handler timeHandler=new Handler();

    //一旦录制暂停就将
    Runnable updateTimeThread = new Runnable() {
        public void run() {

            if(totalSecond>=10){
                timeHandler.removeCallbacks(this);
                //达到最大然后走pause方法
                progress.setHasToLimit(true);
                setWidget(NewRecordCircleProgressButton.STATE_PAUSE);
                return;
            }
            totalSecond++;
            lineWaveVoice.setText(formatTime(totalSecond),true);
            timeHandler.postDelayed(updateTimeThread, 1000);
        }
    };


    long totalSecond=0;



    /**
     * 初始化通用控件
     *
     * @param context
     */
    private void init(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.layout_new_record_voice_panel, this);
        progress = view.findViewById(R.id.progress);
        lineWaveVoice = view.findViewById(R.id.line_wave_voice);
        sure = view.findViewById(R.id.sure);
        reset = view.findViewById(R.id.reset);
        rlRecordVoice = view.findViewById(R.id.rl_record_voice);
        tv_center_tip = view.findViewById(R.id.tv_center_tip);
        tv_try = view.findViewById(R.id.tv_try);
        rl_try = view.findViewById(R.id.rl_try);
        rl_finish = view.findViewById(R.id.rl_finish);
        finish_bg= view.findViewById(R.id.finish_bg);
        try_bg= view.findViewById(R.id.try_bg);


        //初始化
        progress.setInnerCircleColor(ContextCompat.getColor(context, R.color.color_ff6f26));
        progress.setDuratioin(8 * 60 * 1000L);
        progress.setShowSwipeProgress(true);
        lineWaveVoice.setText("00:00", false);
        lineWaveVoice.setLineWidth(ConvertUtils.dp2px(3, context));
        lineWaveVoice.setLineHeightRadio(0.8f);
        lineWaveVoice.setVisibility(View.VISIBLE);
        lineWaveVoice.setTextSize(16);

        progress.setOnOperationListener(new NewRecordCircleProgressButton.OnOperationListener() {
            @Override
            public void onStart() {
                Log.e("wxq", "onStart");
                startRecordAudio();
                setWidget(NewRecordCircleProgressButton.STATE_RECORDING);
                timeHandler.removeCallbacks(updateTimeThread);
                timeHandler.postDelayed(updateTimeThread, 1000);
            }

            @Override
            public void onPause() {
                Log.e("wxq", "onPause");
                stopRecord();
                setWidget(NewRecordCircleProgressButton.STATE_PAUSE);
            }

            @Override
            public void onProgress(long playtime) {
//                long currentSecond = playtime / 1000;
//                if (progress.isRecording()) {
//                    second = currentSecond;
//                    lineWaveVoice.setText(formatTime(currentSecond), true);
//                }

//                Log.e("wxq", "currentSecond" + currentSecond);

            }

            @Override
            public void onRecordEnd() {
                Log.e("wxq", "onRecordEnd");


            }

            @Override
            public void onPlayEnd() {

            }

            @Override
            public void toLimit() {
                Log.e("wxq", "已达时间上限");
            }


        });

        rl_try.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioPlayerUtil == null) {
                    audioPlayerUtil = new AudioPlayerUtil();
                }
                if (isTryPlay) {
                    //暂停试听
                    audioPlayerUtil.stop();
                } else {
                    //开始试听播放
                    audioPlayerUtil.start(audioFilePath, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            //播放结束
                            Log.e("wxq", "变为三角");
                            //暂停状态
                            setWidget(STATE_TRYPAUSE);
                        }
                    });
                    Log.e("wxq", "变为方形");
                }
                isTryPlay = !isTryPlay;
                if (isTryPlay) {
                    setWidget(STATE_TRYPLAY);
                } else {
                    setWidget(STATE_TRYPAUSE);
                }
            }
        });

        rl_finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordListener != null) {
                    recordListener.clickFinish(audioFilePath);
                }
                Log.e("wxq","finish"+audioFilePath);
            }
        });
        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               setWidget(NewRecordCircleProgressButton.STATE_RESET);
            }
        });
        //最初状态
        setWidget(NewRecordCircleProgressButton.STATE_RESET);
    }


    public void setRest(){
        setWidget(NewRecordCircleProgressButton.STATE_RESET);
    }

    /**
     * 各种状态对应控件状态
     *
     * @param state
     */
    public void setWidget(int state) {
        if (state == NewRecordCircleProgressButton.STATE_RESET) {
            //重录初始状态
            lineWaveVoice.setText("00:00", false);
            reset.setVisibility(GONE);
            tv_center_tip.setText("点击录音");
            rl_finish.setVisibility(GONE);
            rl_try.setVisibility(GONE);
            //如果在试听什么的都要停掉
            if (audioPlayerUtil!=null){
                audioPlayerUtil.stop();
            }
            progress.reset();
            for (AudioObj obj : audioObjs) {
                File file = new File(obj.audioPath);
                file.delete();
            }
            audioObjs.clear();
            totalSecond=0;
            timeHandler.removeCallbacks(updateTimeThread);
            //是否已达时间上线
            progress.setHasToLimit(false);
            lineWaveVoice.startRecord();
            lineWaveVoice.stopRollBack();
            lineWaveVoice.stopRecord();
        }

        if (state == NewRecordCircleProgressButton.STATE_RECORDING) {
            //正在录制初始状态
            tv_center_tip.setText("正在录音");
            reset.setVisibility(VISIBLE);
            rl_finish.setVisibility(VISIBLE);
            rl_try.setVisibility(VISIBLE);
            reset.setEnabled(false);
            rl_try.setEnabled(false);
            rl_finish.setEnabled(false);
            finish_bg.setBackgroundResource(R.mipmap.icon_wancheng_dis);
            try_bg.setBackgroundResource(R.mipmap.icon_dis);
            //修改字体颜色
            tv_center_tip.setEnabled(true);
            sure.setEnabled(false);
            tv_try.setEnabled(false);
            reset.setEnabled(false);
        }

        if (state == NewRecordCircleProgressButton.STATE_PAUSE) {
            //录音暂停状态
            reset.setVisibility(VISIBLE);
            rl_finish.setVisibility(VISIBLE);
            rl_try.setVisibility(VISIBLE);
            reset.setEnabled(true);
            rl_try.setEnabled(true);
            rl_finish.setEnabled(true);
            tv_center_tip.setText("录音已暂停");
            //修改字体颜色
            tv_center_tip.setEnabled(true);
            sure.setEnabled(true);
            tv_try.setEnabled(true);
            reset.setEnabled(true);
            finish_bg.setBackgroundResource(R.mipmap.icon_wancheng_nor);
            try_bg.setBackgroundResource(R.mipmap.icon_shiting_nor);

            progress.setInnerCircleColor(ContextCompat.getColor(this.getContext(), R.color.color_ff6f26));
            progress.setClickable(true);
        }

        if (state == STATE_TRYPAUSE) {
            //试听暂停状态
            tv_try.setText("试听");
            //修改字体颜色
            tv_center_tip.setEnabled(true);
            sure.setEnabled(true);
            tv_try.setEnabled(true);
            reset.setEnabled(true);
            rl_try.setEnabled(true);
            rl_finish.setEnabled(true);
            finish_bg.setBackgroundResource(R.mipmap.icon_wancheng_nor);
            try_bg.setBackgroundResource(R.mipmap.icon_shiting_nor);
            progress.setInnerCircleColor(ContextCompat.getColor(this.getContext(), R.color.color_ff6f26));
            progress.setClickable(true);
//            lineWaveVoice.stopRollBack();
            lineWaveVoice.stopRollBack();
        }
        if (state == STATE_TRYPLAY) {
            //正在试听状态
            tv_try.setText("暂停");
            //修改字体颜色
            tv_center_tip.setEnabled(false);
            sure.setEnabled(false);
            tv_try.setEnabled(true);
            reset.setEnabled(false);
            rl_try.setEnabled(true);
            rl_finish.setEnabled(false);
            finish_bg.setBackgroundResource(R.mipmap.icon_wancheng_dis);
            try_bg.setBackgroundResource(R.mipmap.icon_zanting_nor);
            //试听的时候无法录制
            progress.setInnerCircleColor(ContextCompat.getColor(this.getContext(), R.color.common_grey_cccccc));
            progress.setClickable(false);
            lineWaveVoice.startRollBack();
        }


    }


    /**
     * 开始录制文件
     */
    private void startRecordAudio() {
        // 试听完成重录不可点击
//        lineWaveVoice.startRecord();
        audioObj = new AudioObj();
        audioObj.audioName = getCurrentVoiceName();
        audioRecorderUtil = new AudioRecorderUtil(filePath, audioObj.audioName);
        audioRecorderUtil.setOnAudioStatusUpdateListener(new AudioRecorderUtil.OnAudioStatusUpdateListener() {
            @Override
            public void onStart() {
//                lineWaveVoice.startRecord();
            }

            @Override
            public void onProgress(double db, long time) {
                //根据分贝值来设置录音时话筒图标的上下波动,同时设置录音时间
//                tvLoading.setText(time/1000+"");
                audioObj.timeLength = time / 1000;
                lineWaveVoice.refreshElement((float)(db/100));
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onStop(String filePath) {
                audioObj.audioPath = filePath;
//                lineWaveVoice.stopRecord();
            }
        });
        audioRecorderUtil.start();

    }


    public void stopRecord() {
        audioRecorderUtil.stop();
        audioObjs.add(audioObj);
        //暂停的同时将文件合成
        List<String> voices = new ArrayList<>();
        for (AudioObj obj : audioObjs) {
            voices.add(obj.audioPath);
        }
        String fileName=getCurrentVoiceName();
        audioFilePath = MediaUtils.composeVoiceFile(voices, filePath + fileName);
        for (String voice : voices) {
            boolean delete = new File(voice).delete();
            Log.e("wxq","delete"+"================="+delete);
        }
        audioObjs.clear();
        audioObj = new AudioObj();
        audioObj.audioName = fileName;
        audioObj.audioPath = audioFilePath;
        audioObjs.add(audioObj);
        //时间停止
        timeHandler.removeCallbacks(updateTimeThread);
    }


    private DecimalFormat decimalFormat = new DecimalFormat("00");


    /**
     * @param currentSecond 单位是s
     */
    private String formatTime(long currentSecond) {
        long minute = currentSecond / 60;
        long second = currentSecond % 60;
        return decimalFormat.format(minute) + ":" + decimalFormat.format(second);
    }


    public NewRecordAudioLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public NewRecordAudioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewRecordAudioLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * 设置键盘状态监听器
     */
    public void setOnkbdStateListener(onKybdsChangeListener listener) {
        mListener = listener;
    }


    /**
     *
     */
    public interface onKybdsChangeListener {
        /**
         * 键盘状态改变事件
         *
         * @param state
         */
        public void onKeyBoardStateChange(int state);
    }

    /**
     * @return
     */
    public String getCurrentVoiceName() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        return time + ".mp3";
    }


    public void setListener(RecordListener listener) {
        this.recordListener = listener;
    }

    RecordListener recordListener;

    public interface RecordListener {
        /**
         * @param path
         * @return
         */
        void getLastPath(String path);

        /**
         * 点击完成
         *
         * @param path
         */
        void clickFinish(String path);
    }
}