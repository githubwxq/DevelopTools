//package com.juziwl.uilibrary.multimedia;
//
//import android.content.Intent;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.jakewharton.rxbinding2.widget.RxTextView;
//import com.juziwl.commonlibrary.config.Global;
//import com.juziwl.commonlibrary.utils.CommonTools;
//import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.juziwl.commonlibrary.utils.StringUtils;
//import com.juziwl.commonlibrary.utils.ToastUtils;
//import com.juziwl.commonlibrary.utils.UIHandler;
//import com.juziwl.modellibrary.BaseAppDelegate;
//import com.juziwl.modellibrary.ui.activity.WatchImagesActivity;
//import com.juziwl.uilibrary.edittext.MyEditTextView;
//import com.juziwl.uilibrary.emoji.ExpressionView;
//import com.juziwl.uilibrary.emoji.MTextView;
//import com.juziwl.uilibrary.layout.ListenTouchRelativeLayout;
//import com.juziwl.uilibrary.ninegridview.NewNineGridlayout;
//import com.juziwl.uilibrary.otherview.LineWaveVoiceView;
//import com.juziwl.uilibrary.progressbar.RecordCircleProgressButton;
//import com.juziwl.uilibrary.utils.CommonDialog;
//import com.juziwl.uilibrary.utils.RxPermissionUtils;
//import com.juziwl.xiaoxin.R;
//import com.juziwl.xiaoxin.model.ParOutCourseHomeworkDetail;
//import com.juziwl.xiaoxin.ui.homework.activity.ParUncommitOutCourseHomeworkDescActivity;
//import com.juziwl.xiaoxin.ui.homework.activity.PublishHomeworkActivity;
//import com.juziwl.xiaoxin.utils.VoiceUtils;
//import com.luck.picture.lib.PictureSelectorView;
//import com.luck.picture.lib.config.PictureMimeType;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.tencent.qcloud.utils.MediaUtil;
//
//import java.text.DecimalFormat;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.juziwl.xiaoxin.ui.homework.activity.ParUncommitOutCourseHomeworkDescActivity.ACTION_PLAY_VOICE;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/12/1
// * @description
// */
//public class ParUncommitOutCourseHomeworkDescDelegate extends BaseAppDelegate {
//
//    @BindView(R.id.ll_homework_require)
//    LinearLayout llHomeworkRequire;
//    @BindView(R.id.content)
//    MTextView content;
//    @BindView(R.id.voice_play)
//    ImageView voicePlay;
//    @BindView(R.id.duration_par)
//    TextView durationPar;
//    @BindView(R.id.ninegridLayout)
//    NewNineGridlayout ninegridLayout;
//    @BindView(R.id.input)
//    MyEditTextView input;
//    @BindView(R.id.action_voice)
//    ImageView actionVoice;
//    @BindView(R.id.voice_play_stu)
//    ImageView voicePlayStu;
//    @BindView(R.id.duration_par_stu)
//    TextView durationParStu;
//    @BindView(R.id.delete_voice)
//    ImageView deleteVoice;
//    @BindView(R.id.ll_stu_recorded_voice)
//    LinearLayout llStuRecordedVoice;
//    @BindView(R.id.pic_select_view)
//    PictureSelectorView picSelectView;
//    @BindView(R.id.reset)
//    TextView reset;
//    @BindView(R.id.progress)
//    RecordCircleProgressButton progress;
//    @BindView(R.id.sure)
//    TextView sure;
//    @BindView(R.id.time)
//    TextView time;
//    @BindView(R.id.rl_record_voice)
//    RelativeLayout rlRecordVoice;
//    @BindView(R.id.rl_content)
//    RelativeLayout rlContent;
//    @BindView(R.id.rl_voice_item)
//    View rlVoiceItem;
//    @BindView(R.id.rl_stu_voice)
//    RelativeLayout rlStuVoice;
//    @BindView(R.id.iv_pic)
//    ImageView ivPic;
//    @BindView(R.id.iv_camera)
//    ImageView ivCamera;
//    @BindView(R.id.iv_face)
//    ImageView ivFace;
//    @BindView(R.id.emoji_relative)
//    ExpressionView emojiRelative;
//    @BindView(R.id.rl_bottom)
//    RelativeLayout rlBottom;
//    @BindView(R.id.scrollView)
//    ScrollView scrollView;
//    @BindView(R.id.ll_operate_container)
//    LinearLayout llOperateContainer;
//    @BindView(R.id.content_delete)
//    View contentDelete;
//    @BindView(R.id.line_wave_voice)
//    LineWaveVoiceView lineWaveVoice;
//    @BindView(R.id.tv_record_time)
//    TextView tvRecordTime;
//
//    private long second = 0;
//    private int[] position = new int[2];
//    /**
//     * 10s提示
//     */
//    public static final int REMAIN_TIME = 10;
//
//    @Override
//    public void initWidget() {
//        ButterKnife.bind(this, getRootView());
//        input.setHint("请输入作业答案");
//        rlVoiceItem.setVisibility(View.GONE);
//        tvRecordTime.setVisibility(View.VISIBLE);
//        time.setVisibility(View.GONE);
//        lineWaveVoice.setLineWidth(DisplayUtils.dip2px(3));
//        lineWaveVoice.setLineHeightRadio(0.8f);
//        lineWaveVoice.setText(R.string.click_to_record, false);
//        lineWaveVoice.setVisibility(View.VISIBLE);
//        picSelectView.setOutputCameraPath(Global.imgPath);
//        picSelectView.setOutputVideoPath(Global.VIDEOPATH);
//        picSelectView.setLoginType(Global.loginType);
//        picSelectView.setChooseMode(PictureMimeType.ofImage());
//        picSelectView.initData(getActivity(), 3, 9, DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(30),
//                () -> RxPermissionUtils.requestExternalPhotoAudioPermission(getActivity(), getActivity().rxPermissions,
//                        o -> picSelectView.getOnAddPicClickListener().onAddPicClick()));
//        picSelectView.setOnImageDeleteListener(position -> interactiveListener.onInteractive(ParUncommitOutCourseHomeworkDescActivity.ACTION_IMAGE_DELETE, null));
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) progress.getLayoutParams();
//        params.topMargin = DisplayUtils.dip2px(80);
//        progress.setLayoutParams(params);
//        progress.setInnerCircleColor(ContextCompat.getColor(getActivity(), R.color.color_ff6f26));
//        progress.setDuratioin(8 * 60 * 1000L);
//        progress.setShowSwipeProgress(false);
//        click(actionVoice, o -> {
//            emojiRelative.setVisibility(View.GONE);
//            CommonTools.hideInput(input);
//            RxPermissionUtils.requestAudioPermission(getActivity(), getActivity().rxPermissions, o1 -> {
//                MediaUtil.getInstance().stop();
//                rlRecordVoice.setVisibility(View.VISIBLE);
//            });
//        });
//        progress.setOnOperationListener(new RecordCircleProgressButton.OnOperationListener() {
//            @Override
//            public void onStart() {
//                if (progress.isRecording()) {
//                    reset.setVisibility(View.GONE);
//                    sure.setVisibility(View.GONE);
//                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RECORD_START, null);
//                    lineWaveVoice.startRecord();
//                    return;
//                }
//                if (progress.isPlaying()) {
//                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_PLAY_RECORD, null);
//                    lineWaveVoice.startRollBack();
//                }
//            }
//
//            @Override
//            public void onProgress(long playtime) {
//                long currentSecond = playtime / 1000;
//                if (progress.isRecording()) {
//                    second = currentSecond;
//                    lineWaveVoice.setText(formatTime(currentSecond), true);
////                time.setText(formatTime(currentSecond));
//                }
//            }
//
//            @Override
//            public void onRecordEnd() {
//                reset.setVisibility(View.VISIBLE);
//                sure.setVisibility(View.VISIBLE);
//                lineWaveVoice.setText(formatTime(second), false);
////                lineWaveVoice.stopRecord();
//                interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RECORD_STOP, null);
//            }
//
//            @Override
//            public void onPlayEnd() {
//                interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_STOP_PLAYRECORD, null);
//                lineWaveVoice.stopRollBack();
//            }
//        });
//        click(reset, o -> {
////            time.setText(R.string.click_to_record);
//            lineWaveVoice.setText(R.string.click_to_record, false);
//            lineWaveVoice.stopRollBack();
//            progress.reset();
//            reset.setVisibility(View.GONE);
//            sure.setVisibility(View.GONE);
//            interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//        });
//        click(sure, o -> {
//            MediaUtil.getInstance().stop();
//            if (second < 2) {
//                ToastUtils.showToast("录制时间太短，请重新录制");
////                time.setText(R.string.click_to_record);
//                lineWaveVoice.setText(R.string.click_to_record, false);
//                progress.reset();
//                reset.setVisibility(View.GONE);
//                sure.setVisibility(View.GONE);
//                interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//                return;
//            }
//            actionVoice.setVisibility(View.INVISIBLE);
//            actionVoice.setClickable(false);
//            rlRecordVoice.setVisibility(View.GONE);
////            time.setText(R.string.click_to_record);
//            lineWaveVoice.setText(R.string.click_to_record, false);
//            progress.reset();
//            reset.setVisibility(View.GONE);
//            sure.setVisibility(View.GONE);
//            llStuRecordedVoice.setVisibility(View.VISIBLE);
//            durationParStu.setText(StringUtils.formatVoiceTime(second));
//            interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RECORD_FINISH, null);
//            interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_CAN_COMMIT_CLICK, null);
//        });
//        click(deleteVoice, o -> CommonDialog.getInstance().createDialog(getActivity(),
//                getActivity().getString(R.string.common_kindly_reminder), "确定删除这条录音？",
//                getActivity().getString(R.string.common_cancel), null,
//                getActivity().getString(R.string.common_makesure), v -> {
//                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//                    llStuRecordedVoice.setVisibility(View.GONE);
//                    actionVoice.setVisibility(View.VISIBLE);
//                    actionVoice.setClickable(true);
//                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_CAN_COMMIT_CLICK, null);
//                }).show());
//        click(rlStuVoice, o -> interactiveListener.onInteractive(ACTION_PLAY_VOICE, null));
//        RxTextView.textChanges(input)
//                .subscribe(charSequence -> {
//                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_CAN_COMMIT_CLICK, null);
//                });
//        emojiRelative.initEmojiView(getActivity(), input, 500);
//        click(ivCamera, o -> interactiveListener.onInteractive(ParUncommitOutCourseHomeworkDescActivity.ACTION_OPEN_CAMERA, null));
//        click(ivPic, o -> interactiveListener.onInteractive(ParUncommitOutCourseHomeworkDescActivity.ACTION_SELECT_IMAGE, null));
//        click(ivFace, o -> {
//            scrollView.scrollTo(0, DisplayUtils.getScreenHeight());
//            if (emojiRelative.getVisibility() != View.GONE) {
//                ivFace.setImageResource(R.drawable.selector_keyboard_face);
//                emojiRelative.setVisibility(View.GONE);
//                CommonTools.showInput(input);
//            } else {
//                ivFace.setImageResource(R.drawable.selector_keyboard_jianpan);
//                CommonTools.hideInput(input);
//                emojiRelative.setVisibility(View.VISIBLE);
//            }
//        });
//        llOperateContainer.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
//            if (oldBottom > 0 && bottom < oldBottom) {
//                scrollToBottom();
//            }
////            if (isStartInput) {
////                scrollToBottom();
////            }
//        });
//    }
//
//    private boolean isDialogShow = false;
//
//    public void initView() {
//        ListenTouchRelativeLayout touchRelativeLayout = (ListenTouchRelativeLayout) getActivity().findViewById(R.id.base_activity_rootview);
//        touchRelativeLayout.setOnTouchListener(event -> {
//            if (rlRecordVoice.getVisibility() == View.VISIBLE) {
//                rlRecordVoice.getLocationInWindow(position);
//                if (event.getY() < position[1]) {
//                    if (!progress.isResetting()) {
//                        if (isDialogShow) {
//                            return false;
//                        }
//                        isDialogShow = true;
//                        if (progress.isPlaying() || progress.isRecording()) {
//                            progress.stopRecordOrPlay();
//                        }
//                        CommonDialog.getInstance().createDialog(getActivity(), getActivity().getString(R.string.common_kindly_reminder),
//                                "录音已完成,是否上传?", "删除", view -> {
//                                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//                                    llStuRecordedVoice.setVisibility(View.GONE);
//                                    actionVoice.setVisibility(View.VISIBLE);
//                                    actionVoice.setClickable(true);
//                                    interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_CAN_COMMIT_CLICK, null);
//                                    reset.performClick();
//                                    rlRecordVoice.setVisibility(View.GONE);
//                                    isDialogShow = false;
//                                }, "上传", view -> {
//                                    sure.performClick();
//                                    isDialogShow = false;
//                                }).show();
//                        return true;
//                    } else {
//                        rlRecordVoice.setVisibility(View.GONE);
//                    }
//                }
//            }
//            if (emojiRelative.getVisibility() == View.VISIBLE) {
//                llOperateContainer.getLocationInWindow(position);
//                if (event.getY() < position[1]) {
//                    ivFace.setImageResource(R.drawable.selector_keyboard_face);
//                    emojiRelative.setVisibility(View.GONE);
//                }
//            }
////            input.getLocationInWindow(position);
////            if (position[1] <= event.getY() && event.getY() <= position[1] + input.getHeight()
////                    && position[0] <= event.getX() + input.getWidth()) {
////                scrollToBottom();
////            }
//            return false;
//        });
//    }
//
//    public boolean onBackPress() {
//        if (rlRecordVoice.getVisibility() == View.VISIBLE) {
//            if (!progress.isResetting()) {
//                if (isDialogShow) {
//                    return false;
//                }
//                isDialogShow = true;
//                if (progress.isPlaying() || progress.isRecording()) {
//                    progress.stopRecordOrPlay();
//                }
//                CommonDialog.getInstance().createDialog(getActivity(), getActivity().getString(R.string.common_kindly_reminder),
//                        "录音已完成,是否上传?", "删除", view -> {
//                            interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//                            llStuRecordedVoice.setVisibility(View.GONE);
//                            actionVoice.setVisibility(View.VISIBLE);
//                            actionVoice.setClickable(true);
//                            interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_CAN_COMMIT_CLICK, null);
//                            reset.performClick();
//                            rlRecordVoice.setVisibility(View.GONE);
//                            isDialogShow = false;
//                        }, "上传", view -> {
//                            sure.performClick();
//                            isDialogShow = false;
//                        }).show();
//                return true;
//            } else {
//                rlRecordVoice.setVisibility(View.GONE);
//            }
//        }
//        return false;
//    }
//
//    public void showContentDelete() {
//        contentDelete.setVisibility(View.VISIBLE);
//    }
//
//    public void stopPlayRecord() {
//        if (progress.isRecording() || progress.isPlaying()) {
//            progress.stopRecordOrPlay();
//        }
//    }
//
//    public void resetVoice() {
////        time.setText(R.string.click_to_record);
//        lineWaveVoice.setText(R.string.click_to_record, false);
//        progress.reset();
//        reset.setVisibility(View.GONE);
//        sure.setVisibility(View.GONE);
//        rlRecordVoice.setVisibility(View.GONE);
//        interactiveListener.onInteractive(PublishHomeworkActivity.ACTION_RESET, null);
//    }
//
//    public void onRecordVolumeChange(float voice) {
//        lineWaveVoice.refreshElement(voice);
//    }
//
//    public void setHomeworkData(ParOutCourseHomeworkDetail homeworkData) {
//        scrollView.setVisibility(View.VISIBLE);
//        llOperateContainer.setVisibility(View.VISIBLE);
//        if (!StringUtils.isEmpty(homeworkData.voice)) {
//            rlVoiceItem.setVisibility(View.VISIBLE);
//            click(rlVoiceItem, o -> VoiceUtils.playVoice(voicePlay, homeworkData.voice, homeworkData.pId));
//            durationPar.setText(StringUtils.formatVoiceTime(Long.parseLong(homeworkData.voiceLength)));
//        }
//        if (StringUtils.isEmpty(homeworkData.content)) {
//            content.setVisibility(View.GONE);
//        } else {
//            content.setMText(homeworkData.content);
//        }
//        if (StringUtils.isEmpty(homeworkData.sImg)) {
//            ninegridLayout.setVisibility(View.GONE);
//        } else {
//            ninegridLayout.showPic(DisplayUtils.getScreenWidth() - DisplayUtils.dip2px(60),
//                    homeworkData.sImg, position1 -> WatchImagesActivity.navToWatchImages(getActivity(), homeworkData.sImg, position1));
//        }
//    }
//
//    private void scrollToBottom() {
//        UIHandler.getInstance().postDelayed(() -> scrollView.scrollTo(0, DisplayUtils.getScreenHeight()), 20);
//    }
//
//    public List<LocalMedia> setMultiPickResultViewData(int requestCode, int resultCode, Intent data) {
//        picSelectView.onActivityResult(requestCode, resultCode, data);
//        return picSelectView.getSelectList();
//    }
//
//    public boolean isRecording() {
//        return progress.isRecording();
//    }
//
//    public long getSecond() {
//        return second;
//    }
//
//    public String getInputText() {
//        return input.getText().toString().trim();
//    }
//
//    public ImageView getVoicePlayStu() {
//        return voicePlayStu;
//    }
//
//    private DecimalFormat decimalFormat = new DecimalFormat("00");
//
//    /**
//     * @param currentSecond 单位是s
//     */
//    private String formatTime(long currentSecond) {
//        long minute = currentSecond / 60;
//        long second = currentSecond % 60;
//        return decimalFormat.format(minute) + ":" + decimalFormat.format(second);
//    }
//
//    @Override
//    public int getRootLayoutId() {
//        return R.layout.activity_par_uncommit_outcourse_homework_desc;
//    }
//}
