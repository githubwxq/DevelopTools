package com.juziwl.uilibrary.multimedia;

/**
 * Created by 搬砖小能手 on 2017/4/24.
 * @author wxq
 * @modify Neil
 * 介绍：可以断续录制音频的控件
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.otherview.LineWaveVoiceView;
import com.juziwl.uilibrary.progressbar.RecordCircleProgressButton;

public class NewRecordAudioLayout extends RelativeLayout {
    private static final String TAG = NewRecordAudioLayout.class.getSimpleName();
    /**
     * 软键盘弹起
     */
    public static final byte KEYBOARD_STATE_SHOW = -3;
    /**
     * 软键盘隐藏
     */
    public static final byte KEYBOARD_STATE_HIDE = -2;
    /**
     * 初始
     */
    public static final byte KEYBOARD_STATE_INIT = -1;
    private boolean mHasInit;
    private boolean mHasKeybord;
    private int mHeight;
    private onKybdsChangeListener mListener;

    RecordCircleProgressButton progress;
    TextView reset;
    RelativeLayout rlRecordVoice;
    TextView sure;
    LineWaveVoiceView lineWaveVoice;
    TextView   tv_center_tip;
    TextView   tv_try;


    /**
     * 初始化通用控件
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
    public void setOnkbdStateListener(onKybdsChangeListener listener){
        mListener = listener;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            //获取底部高度
            mHeight = b;
            //初始状态
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_INIT);
            }
        } else {
            mHeight = mHeight < b ? b : mHeight;
        }
        //大于则表示布局本遮挡或顶起
        if (mHasInit && mHeight > b) {
            mHasKeybord = true;
            //弹出
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
            }
            Log.w(TAG, "show keyboard.......");
        }
        //布局曾被遮挡或顶起，且回到了初始高度
        if (mHasInit && mHasKeybord && mHeight == b) {
            mHasKeybord = false;
            //收起
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
            }
            Log.w(TAG, "hide keyboard.......");
        }
    }

    public interface onKybdsChangeListener{
        /**
         * 键盘状态改变事件
         * @param state
         */
        public void onKeyBoardStateChange(int state);
    }
}