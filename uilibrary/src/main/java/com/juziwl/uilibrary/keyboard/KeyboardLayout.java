package com.juziwl.uilibrary.keyboard;

/**
 * Created by 搬砖小能手 on 2017/4/24.
 * @author null
 * @modify Neil
 * 介绍：这个是自定义的布局，自定义布局可以继承各种常见布局。自定义布局有键盘状态改变监听器，可以通过注册监听器来监听软键盘状态。
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class KeyboardLayout extends RelativeLayout {
    private static final String TAG = KeyboardLayout.class.getSimpleName();
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

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardLayout(Context context) {
        super(context);
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