package com.juziwl.uilibrary.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.juziwl.uilibrary.utils.ConvertUtils;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/05
 * desc:树状图
 * version:1.0
 */
public class VolumneView extends CustomView {
    private Paint mPaint;
    private int mCount;
    private int mWidth;
    private int mRectHeight;
    private int mRectWidth;
    private LinearGradient mLinearGradient;
    private double mRandom;
    private float mcurrentHeight;

    public static final int OFFSET = 5;

    public VolumneView(Context context) {
        super(context);
    }

    public VolumneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VolumneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        super.init();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mCount =6;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mRectHeight = getMeasuredHeight();
        mRectWidth = (int) (mWidth * 0.8 / mCount);
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight,
                Color.GREEN, Color.YELLOW, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCount; i++) {
            mRandom = Math.random();
            mcurrentHeight = (float) (mRectHeight * mRandom);
            float width = ConvertUtils.dp2px(10,getContext());
            canvas.drawRect(  i * (mRectWidth+width), mcurrentHeight,
                    width*i+(i + 1) * (mRectWidth), mRectHeight, mPaint);
        }
        //重绘制每隔1000s
        postInvalidateDelayed(1000);

    }
}
