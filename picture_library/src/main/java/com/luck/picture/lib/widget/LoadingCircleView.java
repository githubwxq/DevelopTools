package com.luck.picture.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/11/7
 * @description 视频加载的进度条
 */
public class LoadingCircleView extends View {

    private Paint paintBgCircle;

    private Paint paintCircle;

    private Paint paintProgressCircle;
    //开始角度
    private float startAngle = -90f;
    //结束
    private float sweepAngle = 0;
    //进度圆与背景圆的间距
    private int progressCirclePadding = 0;
    //进度圆是否填充
    private boolean fillIn = false;

    private int animDuration = 2000;
    //动画效果
    private LodingCircleViewAnim mLodingCircleViewAnim;

    private int width = 0;
    private RectF f = null;

    public LoadingCircleView(Context context) {
        this(context, null);
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init() {

        mLodingCircleViewAnim = new LodingCircleViewAnim();
        mLodingCircleViewAnim.setDuration(animDuration);
        progressCirclePadding = dip2px(getContext(), 3);

        paintBgCircle = new Paint();
        paintBgCircle.setAntiAlias(true);
        paintBgCircle.setStyle(Paint.Style.FILL);
        paintBgCircle.setColor(Color.WHITE);

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.BLACK);

        paintProgressCircle = new Paint();
        paintProgressCircle.setAntiAlias(true);
        paintProgressCircle.setStyle(Paint.Style.FILL);
        paintProgressCircle.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (f == null) {
            return;
        }
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2, paintBgCircle);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2 - progressCirclePadding / 2, paintCircle);
        canvas.drawArc(f, startAngle, sweepAngle, true, paintProgressCircle);
        if (!fillIn) {
            canvas.drawCircle(width / 2, width / 2, width / 2 - progressCirclePadding * 2, paintCircle);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (width == 0) {
            width = w;
            f = new RectF(progressCirclePadding, progressCirclePadding,
                    width - progressCirclePadding, width - progressCirclePadding);
        }
    }

    public void startAnimAutomatic(boolean fillIn) {
        this.fillIn = fillIn;
        if (mLodingCircleViewAnim != null) {
            clearAnimation();
        }
        startAnimation(mLodingCircleViewAnim);
    }

    public void stopAnimAutomatic() {
        if (mLodingCircleViewAnim != null) {
            clearAnimation();
        }
    }

    /**
     * @param progerss 0-100
     */
    public void setProgerss(int progerss, boolean fillIn) {
        this.fillIn = fillIn;
        sweepAngle = (float) (360 / 100.0 * progerss);
        invalidate();
    }

    private class LodingCircleViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                sweepAngle = 360 * interpolatedTime;
                invalidate();
            } else {
                startAnimAutomatic(fillIn);
            }

        }
    }
}