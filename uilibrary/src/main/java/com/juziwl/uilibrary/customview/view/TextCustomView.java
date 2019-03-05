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

public class TextCustomView extends View {

    public String TAG = "CustomView";


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

    public int defaultSize = 200; //默认当为wrapcontent的时候给的默认值


    public TextCustomView(Context context) {
        this(context, null);
    }

    public TextCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentContext = context;
        initView();
    }

    public void initView() {
        mDeafultPaint.setStyle(Paint.Style.FILL);
        mDeafultPaint.setColor(Color.YELLOW);

    }

    @Override
    protected void onAttachedToWindow() {
        //手动的线初始化view 让viewgroup 添加 我们自定义的view
        Log.d(TAG, "dispatchDraw");
        super.onAttachedToWindow();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "onSizeChanged" + "width" + w + "height" + h + "oldw" + oldw + "oldh" + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
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
        Log.d(TAG, "dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw++++++++++++++++++++");
        super.onDraw(canvas);
        mDeafultPaint.setTextSize(50);

        Paint.FontMetrics fontMetrics = mDeafultPaint.getFontMetrics();
        Paint.FontMetricsInt fm = mDeafultPaint.getFontMetricsInt();

//        top：可绘制的最高高度所在线
//        bottom：可绘制的最低高度所在线
//        ascent ：系统建议的，绘制单个字符时，字符应当的最高高度所在线
//        descent：系统建议的，绘制单个字符时，字符应当的最低高度所在线
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        float leading = fontMetrics.leading;

        //y 坐标确认
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getHeight() / 2 + dy; //高度的一半 向下偏移dy才到基本线
        canvas.drawText("aagggffkk", 0, baseLine, mDeafultPaint);
        //知道中线求基本线


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

//                         自定义文本总结




//float ascent = fontMetrics.ascent;
//float descent = fontMetrics.descent;
//float top = fontMetrics.top;
//float bottom = fontMetrics.bottom;
//float leading = fontMetrics.leading;
//
//
//ascent=ascent线的y坐标-baseline线的y坐标；//负数
//
//descent=descent线的y坐标-baseline线的y坐标；//正数
//
//top=top线的y坐标-baseline线的y坐标；//负数
//
//bottom=bottom线的y坐标-baseline线的y坐标；//正数
//
//leading=top线的y坐标-ascent线的y坐标；//负数
////y 坐标确认
//int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
//int baseLine = getHeight() / 2 + dy; //高度的一半 向下偏移dy才到基本线
//













