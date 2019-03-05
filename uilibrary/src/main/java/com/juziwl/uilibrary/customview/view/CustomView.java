/*
 * Copyright 2016 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2016-12-03 22:55:55
 *
 */

package com.juziwl.uilibrary.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

    public String TAG="CustomView";


    /**
     * the context of current view
     */
    protected Context mCurrentContext;

    /**
     * the width of current view.
     */
    protected int mViewWidth;

    /**
     * the height of current view.
     */
    protected int mViewHeight;

    /**
     * default Paint.
     */
    protected Paint mDeafultPaint = new Paint();

    /**
     * default TextPaint
     */
    protected TextPaint mDefaultTextPaint = new TextPaint();


    public void setnDesired(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int defaultSize = 250; //默认当为wrapcontent的时候给的默认值


    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentContext = context;
        init();
    }

    public void init() {
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onAttachedToWindow() {
       //手动的线初始化view 让viewgroup 添加 我们自定义的view
        Log.d(TAG,"dispatchDraw");
        super.onAttachedToWindow();
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG,"onSizeChanged"+"width"+w+"height"+h+"oldw"+oldw+"oldh"+oldh);
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG,"onMeasure");
        setMeasuredDimension(resolveMeasured(widthMeasureSpec, defaultSize), resolveMeasured(heightMeasureSpec, defaultSize));
    }

    /**
     * @param measureSpec
     * @param desired
     * @return
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED: //
                result = desired;
                break;
            case MeasureSpec.AT_MOST:  //wrap
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:  //match
            default:
                result = specSize;
        }
        return result;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d(TAG,"dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG,"onDraw++++++++++++++++++++");
        super.onDraw(canvas);
//        mDeafultPaint.
//                canvas.d
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(10);
//        canvas.drawCircle(200, 200, 200, paint);
    }

    /**
     * 处理用户客户交互的
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下
                Log.e("TAG", "手指按下");
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动
                Log.e("TAG", "手指移动");
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起
                Log.e("TAG", "手指抬起");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }




}
//                 1背景
//                 2主体（onDraw()）
//                 3子 View（dispatchDraw()）
//                 4滑动边缘渐变和滑动条
//                 5前景