package com.juziwl.uilibrary.chatview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.chatview.audio.VoiceRecorderHelper;
import com.juziwl.uilibrary.emoji.ExpressionView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.KeyboardUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * 聊天界面输入控件
 */
public class ChatInput extends RelativeLayout implements TextWatcher, View.OnClickListener {

    private ImageButton btnAdd, btnVoice, btnKeyboard, btnEmotion;
    private Button btnSend;
    private EditText editText;
    private boolean isSendVisible, isHoldVoiceBtn, isEmoticonReady, isVoice;
    private InputMode inputMode = InputMode.NONE;
    private ChatView chatView;
    private LinearLayout morePanel, textPanel, ll_input;
    private TextView voicePanel;
    private ExpressionView emoticonPanel;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private Context cContext;
    private TextView view_hide;
    private Boolean stopMsg = false;
    private VoiceRecorderHelper voiceRecorderHelper;
    private boolean stopShowAtPage = false;


    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        cContext = context;
        LayoutInflater.from(context).inflate(R.layout.timmsg_chat_input, this);
        initView();
    }

    private void initView() {
        view_hide = (TextView) findViewById(R.id.view_hide);
        ll_input = (LinearLayout) findViewById(R.id.ll_input);
        textPanel = (LinearLayout) findViewById(R.id.text_panel);
        btnAdd = (ImageButton) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        btnVoice = (ImageButton) findViewById(R.id.btn_voice);
        btnVoice.setOnClickListener(this);
        btnEmotion = (ImageButton) findViewById(R.id.btnEmoticon);
        btnEmotion.setOnClickListener(this);
        morePanel = (LinearLayout) findViewById(R.id.morePanel);
        LinearLayout BtnPhoto = (LinearLayout) findViewById(R.id.btn_image);
        BtnPhoto.setOnClickListener(this);
        LinearLayout btnVideo = (LinearLayout) findViewById(R.id.btn_video);
        btnVideo.setOnClickListener(this);
        setSendBtn();
        btnKeyboard = (ImageButton) findViewById(R.id.btn_keyboard);
        btnKeyboard.setOnClickListener(this);
        voiceRecorderHelper = new VoiceRecorderHelper((Activity) cContext, new VoiceRecorderHelper.CallBack() {
            @Override
            public String setOutPutPath() {
                /** 设置录音结果路径，你的格式也在这里设置 */
                File file = new File(GlobalContent.VOICEPATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                return GlobalContent.VOICEPATH + System.currentTimeMillis();
            }

            @Override
            public void onDown(View v) {
                /** 纯粹的 down 事件回调 */
            }

            @Override
            public void onMove_in_limit(View v) {
                /** 手指移动的范围在限制内 */
            }

            @Override
            public void onMove_out_limit(View v) {
                /** 手指移动超过范围，内部做了显示取消的提示 */
            }

            @Override
            public void onUp_start(View v) {
                /** 纯粹的 Up 事件回调 */
            }

            @Override
            public void onUp_cancel(View v) {
                /** 这个时候已经因为手指移动超过范围取消了录音 */
            }

            @Override
            public void onFinishRecord() {
                /** 录音结束 */
            }

            @Override
            public void onRecordSuccess(float len, String savePath) {
                /** 录音解码并且保存成功 */
                Logger.d(String.format(Locale.getDefault(), "len = %f\tsavePath = %s", len, savePath));
                chatView.sendVoice((long) len, savePath);
            }

            @Override
            public void onRecordVolumeChange(int voiceValue) {
                /** 录音声音强度的变化，单位分贝 */
            }
        });
        voicePanel = (TextView) findViewById(R.id.voice_panel);
        voicePanel.setOnTouchListener((v, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    MediaUtil.getInstance(getContext()).stop();
                    updateVoiceView(true);
                    voiceRecorderHelper.Action_Down(v, motionEvent);
                    return false;
                case MotionEvent.ACTION_MOVE:
                    voiceRecorderHelper.Action_Move(v, motionEvent);
                    return false;
                case MotionEvent.ACTION_UP:
                    updateVoiceView(false);
                    voiceRecorderHelper.Action_Up(v, motionEvent);
                    return false;
                default:
                    break;
            }
            return false;
        });
        editText = (EditText) findViewById(R.id.input);
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                updateView(InputMode.TEXT);
            }
        });
        isSendVisible = editText.getText().length() != 0;
        emoticonPanel = (ExpressionView) findViewById(R.id.emoticonPanel);
        emoticonPanel.initEmojiView(cContext, editText, 1200);
    }

    private void updateView(InputMode mode) {
        if (mode == inputMode) {
            return;
        }
        leavingCurrentState();
        switch (inputMode = mode) {
            case MORE:
                isVoice = false;
                morePanel.setVisibility(VISIBLE);
                break;
            case TEXT:
                if (editText.requestFocus()) {
                    if (!stopMsg) {
                        KeyboardUtils.hideSoftInput(editText);
                        chatView.scrollBottom();
                    }
                }
                setSendBtn();
                break;
            case VOICE:
                voicePanel.setVisibility(VISIBLE);
                textPanel.setVisibility(GONE);
                btnVoice.setVisibility(GONE);
                btnKeyboard.setVisibility(VISIBLE);
                btnAdd.setVisibility(VISIBLE);
                btnSend.setVisibility(GONE);
                isVoice = true;
                break;
            case EMOTICON:
//                if (!isEmoticonReady) {
//                    prepareEmoticon();
//                }
                btnEmotion.setBackgroundResource(R.drawable.selector_keyboard_jianpan);
                emoticonPanel.setVisibility(VISIBLE);
                break;
            default:
                break;
        }
    }

    private void leavingCurrentState() {
        switch (inputMode) {
            case TEXT:
                 KeyboardUtils.hideSoftInput(editText);
                editText.clearFocus();
                break;
            case MORE:
                isVoice = false;
                morePanel.setVisibility(GONE);
                break;
            case VOICE:
                voicePanel.setVisibility(GONE);
                textPanel.setVisibility(VISIBLE);
                btnVoice.setVisibility(VISIBLE);
                btnKeyboard.setVisibility(GONE);
                isVoice = false;
                break;
            case EMOTICON:
                btnEmotion.setBackgroundResource(R.drawable.selector_keyboard_face);
                emoticonPanel.setVisibility(GONE);
                break;
            default:
                break;
        }
    }


    private void updateVoiceView(boolean isHoldVoiceBtn) {
        if (isHoldVoiceBtn) {
            voicePanel.setText(getResources().getString(R.string.chat_release_send));
            voicePanel.setBackgroundResource(R.drawable.btn_voice_pressed);
//            chatView.startSendVoice();
        } else {
            voicePanel.setText(getResources().getString(R.string.chat_press_talk));
            voicePanel.setBackgroundResource(R.drawable.btn_voice_normal);
//            chatView.endSendVoice();
        }
    }

    public boolean getVoicePanel() {
        return isVoice;
    }

    public TextView getVoiceBtn() {
        return voicePanel;
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (before == 0 && count == 1 && !stopShowAtPage) {
            if (GlobalContent.AT_WORD.equals(s.toString().substring(start, start + count))) {
                chatView.showAtPage();
            }
        }
        isSendVisible = s != null && s.toString().trim().length() > 0;
        setSendBtn();
        chatView.sending(isSendVisible);
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setSendBtn() {
        if (isSendVisible) {
            btnAdd.setVisibility(GONE);
            btnSend.setVisibility(VISIBLE);
        } else {
            btnAdd.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
        }
        if (stopMsg) {
            btnAdd.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
        }
    }

    private void prepareEmoticon() {
        if (emoticonPanel == null) {
            return;
        }
        for (int i = 0; i < 5; ++i) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
            for (int j = 0; j < 7; ++j) {

                try {
                    AssetManager am = getContext().getAssets();
                    final int index = 7 * i + j;
                    InputStream is = am.open(String.format("emoticon/%d.gif", index));
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Matrix matrix = new Matrix();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    matrix.postScale(3.5f, 3.5f);
                    final Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            width, height, matrix, true);
                    ImageView image = new ImageView(getContext());
                    image.setImageBitmap(resizedBitmap);
                    image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                    linearLayout.addView(image);
                    image.setOnClickListener(new OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.DONUT)
                        @Override
                        public void onClick(View v) {
                            String content = String.valueOf(index);
                            SpannableString str = new SpannableString(String.valueOf(index));
                            ImageSpan span = new ImageSpan(getContext(), resizedBitmap, ImageSpan.ALIGN_BASELINE);
                            str.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            editText.append(str);
                        }
                    });
                    is.close();
                } catch (IOException e) {

                }

            }
            emoticonPanel.addView(linearLayout);
        }
        isEmoticonReady = true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        BaseActivity activity = (BaseActivity) getContext();
        int id = v.getId();
        if (id == R.id.btn_send) {
            chatView.sendText(getEditText().getText().toString());
        }
        if (id == R.id.btn_add) {
            updateView(inputMode == InputMode.MORE ? InputMode.TEXT : InputMode.MORE);
        }
        if (id == R.id.btn_image) {
            if (activity != null && requestStorage(activity)) {
                //点击了选图片或者视频
                goToChoosePhotosOrVideo(activity);
            }
        }
        if (id == R.id.btn_voice) {
            if (activity != null && requestAudio(activity)) {
                updateView(InputMode.VOICE);
            }
        }
        if (id == R.id.btn_keyboard) {
            updateView(InputMode.TEXT);
            setSendBtn();
        }
        if (id == R.id.btn_video) {
            if (activity != null && requestStorage(activity)) {
                //点击了选图片或者视频
                goToChoosePhotosOrVideo(activity);
            }
        }
        if (id == R.id.btnEmoticon) {
            updateView(inputMode == InputMode.EMOTICON ? InputMode.TEXT : InputMode.EMOTICON);
        }
    }

    private void goToChoosePhotosOrVideo(BaseActivity activity) {
//        、、为什么rxpermission会报错
        RxPermissions rxPermissions=new RxPermissions(activity);
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(aBoolean -> {
//                    if (aBoolean) {
                        openAlbum(activity);
//                    } else {
//                        ToastUtils.showShort(R.string.open_external_storage);
//                    }
//                });
    }


    /**
     * 获取输入框文字
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text) {
        editText.setText(text);
    }

    /**
     * @param flag true是禁言
     */
    public void setInputType(String text, boolean flag) {
        if (flag) {
            stopMsg = true;
            updateView(InputMode.TEXT);
            setSendBtn();
            editText.setVisibility(INVISIBLE);
            KeyboardUtils.hideSoftInput(editText);
            view_hide.setVisibility(VISIBLE);
            view_hide.setClickable(true);
            view_hide.setText(text);
            morePanel.setVisibility(GONE);
        } else {
            stopMsg = false;
            editText.setVisibility(VISIBLE);
            view_hide.setVisibility(GONE);
            view_hide.setClickable(false);
            editText.setEnabled(true);
            morePanel.setVisibility(GONE);
        }
    }

    /**
     * 设置输入模式
     */
    public void setInputMode(InputMode mode) {
        updateView(mode);
    }


    public enum InputMode {
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        VIDEO,
        NONE,
    }

    private boolean requestVideo(Activity activity) {
        if (afterM()) {
            final List<String> permissionsList = new ArrayList<>();
            if ((activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.CAMERA);
            }
            if ((activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissionsList.size() != 0) {
                activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestCamera(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestAudio(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestStorage(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    public void stopShowAtPage(boolean stopShowAtPage) {
        this.stopShowAtPage = stopShowAtPage;
    }

    public EditText getEditText() {
        return editText;
    }

    public void destory() {
        voiceRecorderHelper.onDestroy();
    }


    /**
     * 前往选取图片和视频
     * @param activity
     */
    private void openAlbum(BaseActivity activity) {
        PictureSelector.create(activity)
                // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .openGallery(PictureMimeType.ofAll())
                // 最大图片选择数量
                .maxSelectNum(9)
                // 最小选择数量
                .minSelectNum(1)
                // 多选 or 单选
                .selectionMode(PictureConfig.MULTIPLE)
                // 是否可预览图片
                .previewImage(true)
                // 自定义拍照保存路径
                .setOutputCameraPath(GlobalContent.imgPath)
                // 自定义拍视频保存路径
                .setOutputVideoPath(GlobalContent.VIDEOPATH)
                // 是否显示拍照按钮
                .isCamera(true)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                // 是否显示gif图片
                .isGif(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
    /**
     * 处理拿到的图片和视频
     * @param
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //选图片
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            if (images.size() == 1 && images.get(0).getDuration() > 0) {
                  //当前选择的是视频 直接返回视频链接
                 chatView.sendVideo(images.get(0).getPath(),images.get(0).getDuration());
            } else {
                //当前选择的是图片集合
                List<String> list=new ArrayList<>();
                for (LocalMedia image : images) {
                    String path = image.getPath();
                    list.add(path);
                }
                chatView.sendImage(list);
            }
            return;
        }
    }



}
