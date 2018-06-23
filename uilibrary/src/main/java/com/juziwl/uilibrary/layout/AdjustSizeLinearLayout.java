package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class AdjustSizeLinearLayout extends LinearLayout {

    public AdjustSizeLinearLayout(Context context) {
        super(context);
    }

    public AdjustSizeLinearLayout(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    public AdjustSizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mChangeSize = 200;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.e("###xiamin", "change" + w + " " + h + " " + oldw + " " + oldh);
        if (oldw == 0 || oldh == 0)
            return;

        if (boardListener != null) {
            if (oldw != 0 && h - oldh < -mChangeSize) {
                Log.e("###xiamin","键盘弹出"+  "change" + w + " " + h + " " + oldw + " " + oldh);

                boardListener.keyBoardVisable(Math.abs(h - oldh));
            }

            if (oldw != 0 && h - oldh > mChangeSize) {

                Log.e("###xiamin","键盘结束"+  "change" + w + " " + h + " " + oldw + " " + oldh);
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
}
