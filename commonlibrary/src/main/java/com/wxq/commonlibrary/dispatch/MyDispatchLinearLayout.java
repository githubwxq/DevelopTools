package com.wxq.commonlibrary.dispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/20
 * desc:事件分发viewgroup
 * version:1.0
 */
public class MyDispatchLinearLayout extends LinearLayout {
    private static String TAG = "MyDispatchLinearLayout";
    public MyDispatchLinearLayout(Context context) {
        super(context);
    }

    public MyDispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG,"onTouchEvent====MyDispatchLinearLayout=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG,"onTouchEvent====MyDispatchLinearLayout=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG,"onTouchEvent====MyDispatchLinearLayout=====ACTION_UP");
        }

        Log.e(TAG,"onTouchEvent====MyDispatchLinearLayout是否处理"+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG,"dispatchTouchEvent====MyDispatchLinearLayout=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG,"dispatchTouchEvent====MyDispatchLinearLayout=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG,"dispatchTouchEvent====MyDispatchLinearLayout=====ACTION_UP");
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int type = event.getAction();
        if (MotionEvent.ACTION_DOWN == type) {
            Log.e(TAG,"onInterceptTouchEvent====MyDispatchLinearLayout=====ACTION_DOWN");
        }
        if (MotionEvent.ACTION_MOVE == type) {
            Log.e(TAG,"onInterceptTouchEvent====MyDispatchLinearLayout=====ACTION_MOVE");
        }
        if (MotionEvent.ACTION_UP == type) {
            Log.e(TAG,"onInterceptTouchEvent====MyDispatchLinearLayout=====ACTION_UP");
        }
        Log.e(TAG,"onInterceptTouchEvent是否拦截"+  super.onInterceptTouchEvent(event));
        return super.onInterceptTouchEvent(event);
    }
}




//View里，有两个回调函数 ：
//
//1 public boolean dispatchTouchEvent(MotionEvent ev)；
//2 public boolean onTouchEvent(MotionEvent ev);
//ViewGroup里，有三个回调函数 ：
//
//1 public boolean dispatchTouchEvent(MotionEvent ev)；
//2 public boolean onInterceptTouchEvent(MotionEvent ev);
//3 public boolean onTouchEvent(MotionEvent ev);
//在Activity里，有两个回调函数 ：
//
//1 public boolean dispatchTouchEvent(MotionEvent ev)；
//2 public boolean onTouchEvent(MotionEvent ev);
//Android中默认情况下事件传递是由最终的view的接收到，传递过程是从父布局到子布局，也就是从Activity到ViewGroup到View的过程，默认情况，ViewGroup起到的是透传作用。
//Android中事件传递过程（按箭头方向）如下图,图片来自[qiushuiqifei]，谢谢[qiushuiqifei]整理。
