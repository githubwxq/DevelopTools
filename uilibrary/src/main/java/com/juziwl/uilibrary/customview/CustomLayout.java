package com.juziwl.uilibrary.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by wxq on 2018/2/27 0027.
 */

public  class CustomLayout extends ViewGroup {
    public String TAG="CustomLayout";


    public CustomLayout(Context context) {
        this(context,null);
    }


    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG,"onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
                Log.d(TAG,"onLayout"+"left"+l+"top"+t+"right"+r+"bottom"+b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            int topPos = (int) (childH * i*1.5f);
            int leftPos = 0;
            childView.layout(leftPos, topPos, leftPos + childW, topPos + childH);
        }
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure"+"widthMeasureSpec"+widthMeasureSpec+"heightMeasureSpec"+heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureChild(widthMeasureSpec,heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    //    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.d(TAG,"onLayout"+"left"+l+"top"+t+"right"+r+"bottom"+b);
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = getChildAt(i);
//            int childW = childView.getMeasuredWidth();
//            int childH = childView.getMeasuredHeight();
//            int topPos = (int) (childH * i*0.5f);
//            int leftPos = 0;
//            childView.layout(leftPos, topPos, leftPos + childW, topPos + childH);
//        }
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG,"onSizeChanged"+"width"+w+"height"+h+"oldw"+oldw+"oldh"+oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
