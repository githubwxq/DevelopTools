package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author lijia
 * @version V_5.0.0
 * @modify Neil
 * @date 2016/12/15 0015
 * @description 判断输入框弹起或者收起的相对布局
 */
public class ResizeRelativeLayout extends RelativeLayout {
    private OnResizeListener mListener;

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mChangeSize = 200;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh);
        }
        if (oldw == 0 || oldh == 0) {
            return;
        }
        if (boardListener != null) {
            if (h - oldh < -mChangeSize) {
                boardListener.keyBoardVisable(Math.abs(h - oldh));
            }
            if (h - oldh > mChangeSize) {
                boardListener.keyBoardInvisable(Math.abs(h - oldh));
            }
        }
    }

    public interface SoftkeyBoardListener {

        public void keyBoardVisable(int move);

        public void keyBoardInvisable(int move);
    }

    SoftkeyBoardListener boardListener;

    public void setSoftKeyBoardListener(SoftkeyBoardListener boardListener) {
        this.boardListener = boardListener;
    }

    public interface OnResizeListener {
        /**
         * 重新测量大小
         *
         * @param w
         * @param h
         * @param oldw
         * @param oldh
         */
        void OnResize(int w, int h, int oldw, int oldh);
    }

    public void setOnResizeListener(OnResizeListener l) {
        mListener = l;
    }
}
