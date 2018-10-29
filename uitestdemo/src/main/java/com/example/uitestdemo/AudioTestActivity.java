package com.example.uitestdemo;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.juziwl.uilibrary.multimedia.AudioPlayerUtil;
import com.juziwl.uilibrary.multimedia.AudioRecorderUtil;
import com.juziwl.uilibrary.multimedia.MediaUtils;
import com.juziwl.uilibrary.multimedia.NewRecordAudioLayout;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 语音录制语音合成demo
 */
public class AudioTestActivity extends BaseActivity {

    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_loading)
    TextView tvLoading;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.tv_play)
    TextView tvPlay;

    AudioRecorderUtil audioRecorderUtil;
    AudioPlayerUtil audioPlayerUtil;
    String audioFilePath;
    @BindView(R.id.rl_record_voice)
    NewRecordAudioLayout rlRecordVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_audio);
        ButterKnife.bind(this);
    }

    @Override
    protected void initViews() {
        //请求权限全部结果
        rxPermissions.request(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                            showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        } else {


                        }
                    }
                });

        audioRecorderUtil = new AudioRecorderUtil(GlobalContent.VOICEPATH, "700.mp4");
        audioRecorderUtil.setOnAudioStatusUpdateListener(new AudioRecorderUtil.OnAudioStatusUpdateListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onProgress(double db, long time) {
                //根据分贝值来设置录音时话筒图标的上下波动,同时设置录音时间
                tvLoading.setText(time / 1000 + "");
            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onStop(String filePath) {
                ToastUtils.showShort("audiopath" + filePath);
                // TODO 上传音频文件
                audioFilePath = filePath;
            }
        });


        rlRecordVoice.setListener(new NewRecordAudioLayout.RecordListener() {
            @Override
            public void getLastPath(String path) {
                ToastUtils.showShort("最终音频路径为"+path);
            }

            @Override
            public void clickFinish(String path) {
                ToastUtils.showShort("最终音频路径为"+path);
            }
        });


    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_audio;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.tv_start, R.id.tv_loading, R.id.tv_stop, R.id.tv_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start:

                audioRecorderUtil.start();
//                List<String> voces=new ArrayList<>();
//
//                MediaUtils.composeVoiceFile(this,)

                break;
            case R.id.tv_loading:

                List<String> voices = new ArrayList<>();
                voices.add("/storage/emulated/0/DevelopTools/audio/600.mp4");
                voices.add("/storage/emulated/0/DevelopTools/audio/700.mp4");

//
                audioFilePath = MediaUtils.composeVoiceFile(voices, "/storage/emulated/0/DevelopTools/audio/800.mp4");

//                audioFilePath="/storage/emulated/0/DevelopTools/audio/400.mp4";

                break;
            case R.id.tv_stop:

                audioRecorderUtil.stop();
                break;
            case R.id.tv_play:
                if (audioFilePath != null) {

                }

                if (audioPlayerUtil == null) {
                    audioPlayerUtil = new AudioPlayerUtil();
                } else {
                    audioPlayerUtil.stop();
                }
                audioPlayerUtil.start(audioFilePath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        ToastUtils.showShort("finish");
                    }
                });

                break;
        }
    }
}
