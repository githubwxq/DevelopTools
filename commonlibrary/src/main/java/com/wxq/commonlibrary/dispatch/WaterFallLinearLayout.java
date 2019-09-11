package com.wxq.commonlibrary.dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/20
 * desc:
 * version:1.0
 */
public class WaterFallLinearLayout extends LinearLayout {
    private static String TAG = "WaterFallLinearLayout";

    public WaterFallLinearLayout(Context context) {
        super(context);
    }

    public WaterFallLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterFallLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 时间都给最外层拿走了 他想如何处理就看如何处理
        return true;
    }

    /**
     * 事件传递机制：
     *  1. view执行dispatchTouchEvent方法,开始分发事件 ;
     *  2. 执行onInterceptTouchEvent 判断是否是中断事件 ;
     *  3. 执行onTouchEvent方法，去处理事件
     */

    /**
     * 31      * 分发事件的方法,最早执行
     * 32
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int width = getWidth() / getChildCount(); //

        int height = getHeight();

        int count = getChildCount();

        float eventX = event.getX();

        if (eventX < width) {
            event.setLocation(width / 2, event.getY());

            float eventY = event.getY();

            if (eventY < height / 2) {
                event.setLocation(width / 2, event.getY());
                getChildAt(0).dispatchTouchEvent(event);
                getChildAt(2).dispatchTouchEvent(event);
                System.out.println("左边的listview的上半部分：" + eventY);
                return true;
            } else {
                event.setLocation(width / 2, event.getY());
                getChildAt(0).dispatchTouchEvent(event);
                return true;
            }
        } else if (eventX > width && eventX < 2 * width) {
            event.setLocation(width / 2, event.getY());
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                try {
                    child.dispatchTouchEvent(event);
                } catch (Exception e) {
                }
            }
            System.out.println("中间listview的上半部分：" + event.getY());
            return true;
        }else{
            getChildAt(2).dispatchTouchEvent(event);
        }
        return true;
    }
}
