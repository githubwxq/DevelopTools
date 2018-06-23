package com.juziwl.uilibrary.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018年1月9日
 * @description 监听触碰事件的layout
 */
public class ListenTouchRelativeLayout extends RelativeLayout {

    private OnTouchListener onTouchListener;

    public ListenTouchRelativeLayout(Context context) {
        this(context, null);
    }

    public ListenTouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (onTouchListener != null) {
            if (onTouchListener.onTouch(ev)) {
                return false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public interface OnTouchListener {
        boolean onTouch(MotionEvent event);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
}
