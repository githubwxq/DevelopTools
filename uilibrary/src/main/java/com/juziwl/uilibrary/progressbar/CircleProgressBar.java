package com.juziwl.uilibrary.progressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Army
 * @modify Neil
 * @version V_1.0.0
 * @date 2017/4/5
 * @description
 */
public class CircleProgressBar extends View {
    private int insideCircleRadius = 0;
    private int insideCircleRadiusPressed = 0;
    private int outsideCircleRadius = 0;
    private int outsideCircleRadiusPressed = 0;
    private int insideCircleColor = Color.WHITE;
    private int outsideCircleColor = Color.parseColor("#dddddd");
    private int arcColor = Color.parseColor("green");
    private Paint insidePaint, outsidePaint, arcPaint;
    private DrawCircle drawCircle;
    private int changedInsideRadius = 0, changedOutsideRadius = 0;
    private ObjectAnimator animator = new ObjectAnimator(), progressAnimator = new ObjectAnimator();
    private PropertyValuesHolder expandValuesHolder, reduceValuesHolder,
            arcValuesHolder = PropertyValuesHolder.ofInt("arcAngle", 0, 360);
    private RectF rectF;
    private int progressWidth = dip2px(5);
    private int maxDuration = 15 * 1000;

    public CircleProgressBar(Context context) {
        this(context, null, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        insidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insidePaint.setColor(insideCircleColor);
        outsidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outsidePaint.setColor(outsideCircleColor);
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(arcColor);
        arcPaint.setStrokeWidth(progressWidth);
        arcPaint.setStyle(Paint.Style.STROKE);
        setBackgroundColor(Color.TRANSPARENT);

        drawCircle = new DrawCommonCircle();

        expandValuesHolder = PropertyValuesHolder.ofInt("changedInsideRadius", 0, 1);
        reduceValuesHolder = PropertyValuesHolder.ofInt("changedOutsideRadius", 0, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width != height) {
            setMeasuredDimension(width, width);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (outsideCircleRadiusPressed == 0) {
            outsideCircleRadiusPressed = w / 2;
            outsideCircleRadius = (int) (outsideCircleRadiusPressed * 0.7);
            insideCircleRadius = (int) (outsideCircleRadiusPressed * 0.5);
            insideCircleRadiusPressed = (int) (outsideCircleRadiusPressed * 0.4);
            rectF = new RectF(0 + progressWidth / 2, 0 + progressWidth / 2, w - progressWidth / 2, w - progressWidth / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle.drawCircle(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(animator != null && (animator.isRunning() || animator.isStarted())){
                    animator.cancel();
                }
                if(progressAnimator != null && (progressAnimator.isRunning() || progressAnimator.isStarted())){
                    progressAnimator.cancel();
                }
                drawCircle = new DrawExpandCircle();
                drawCircle.onChange(insideCircleRadius, insideCircleRadiusPressed, outsideCircleRadius, outsideCircleRadiusPressed);
                return true;
            case MotionEvent.ACTION_UP:
                if(!(drawCircle instanceof DrawReduceCircle)){
                    if(animator != null && (animator.isRunning() || animator.isStarted())){
                        animator.cancel();
                    }
                    if(progressAnimator != null && (progressAnimator.isRunning() || progressAnimator.isStarted())){
                        progressAnimator.cancel();
                    }
                    drawCircle = new DrawReduceCircle();
                    drawCircle.onChange(changedInsideRadius, insideCircleRadius, changedOutsideRadius, outsideCircleRadius);
                    if(onProgressTouchEvent != null){
                        onProgressTouchEvent.onHandUp();
                    }
                    return true;
                }
                default:
                    break;
        }
        return super.onTouchEvent(event);
    }

    public void reset(){
        if(!(drawCircle instanceof DrawCommonCircle)){
            drawCircle = new DrawCommonCircle();
        }
        invalidate();
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    };

    class DrawReduceCircle extends DrawCircle {

        @Override
        void drawCircle(Canvas canvas) {
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, changedOutsideRadius, outsidePaint);
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, changedInsideRadius, insidePaint);
        }

        @Override
        void onChange(int insideFromRadius, int insideToRadius, int outsideFromRadius, int outsideToRadius) {
            //内圆扩大
            expandValuesHolder.setPropertyName("changedInsideRadius");
            expandValuesHolder.setIntValues(insideFromRadius, insideToRadius);
            //外圆缩小
            reduceValuesHolder.setPropertyName("changedOutsideRadius");
            reduceValuesHolder.setIntValues(outsideFromRadius, outsideToRadius);

            animator.cancel();
            animator.removeAllListeners();
            animator.removeAllUpdateListeners();
            animator.setTarget(CircleProgressBar.this);
            animator.setValues(reduceValuesHolder, expandValuesHolder);
            animator.setDuration(200);
            animator.addUpdateListener(updateListener);
            animator.start();
        }
    }

    class DrawExpandCircle extends DrawCircle {
        private int arcAngle = -90;

        public void setArcAngle(int arcAngle) {
            this.arcAngle = arcAngle;
        }

        @Override
        void drawCircle(Canvas canvas) {
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, changedOutsideRadius, outsidePaint);
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, changedInsideRadius, insidePaint);
            if (canDrawProgress) {
                canvas.drawArc(rectF, -90, arcAngle, false, arcPaint);
            }
        }

        @Override
        void onChange(int insideFromRadius, int insideToRadius, int outsideFromRadius, int outsideToRadius) {
            //内圆缩小
            reduceValuesHolder.setPropertyName("changedInsideRadius");
            reduceValuesHolder.setIntValues(insideFromRadius, insideToRadius);
            //外圆扩大
            expandValuesHolder.setPropertyName("changedOutsideRadius");
            expandValuesHolder.setIntValues(outsideFromRadius, outsideToRadius);

            animator.cancel();
            animator.setTarget(CircleProgressBar.this);
            animator.setValues(reduceValuesHolder, expandValuesHolder);
            animator.setDuration(200);
            animator.removeAllUpdateListeners();
            animator.addUpdateListener(updateListener);
            animator.removeAllListeners();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    canDrawProgress = true;
                    progressAnimator.setTarget(DrawExpandCircle.this);
                    progressAnimator.setValues(arcValuesHolder);
                    progressAnimator.setInterpolator(new LinearInterpolator());
                    progressAnimator.setDuration(maxDuration);
                    progressAnimator.removeAllUpdateListeners();
                    progressAnimator.addUpdateListener(updateListener);
                    progressAnimator.removeAllListeners();
                    progressAnimator.addListener(new AnimatorListenerAdapter() {
                        boolean isCancel = false;
                        @Override
                        public void onAnimationStart(Animator animation) {
                            if(onProgressTouchEvent != null){
                                onProgressTouchEvent.onHandTouch();
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            drawCircle = new DrawReduceCircle();
                            drawCircle.onChange(changedInsideRadius, insideCircleRadius, changedOutsideRadius, outsideCircleRadius);
                            if(onProgressTouchEvent != null && !isCancel){
                                onProgressTouchEvent.onHandUp();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isCancel = true;
                        }
                    });
                    progressAnimator.start();
                }
            });
            animator.start();
        }
    }

    class DrawCommonCircle extends DrawCircle {

        @Override
        void drawCircle(Canvas canvas) {
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, outsideCircleRadius, outsidePaint);
            canvas.drawCircle(outsideCircleRadiusPressed, outsideCircleRadiusPressed, insideCircleRadius, insidePaint);
        }

        @Override
        void onChange(int insideFromRadius, int insideToRadius, int outsideFromRadius, int outsideToRadius) {
            //不用进行任何操作
        }
    }

    abstract class DrawCircle {
        public boolean canDrawProgress = false;

        /**
         * 画圆
         * @param canvas
         */
        abstract void drawCircle(Canvas canvas);

        /**
         * 大小改变
         * @param insideFromRadius
         * @param insideToRadius
         * @param outsideFromRadius
         * @param outsideToRadius
         */
        abstract void onChange(int insideFromRadius, int insideToRadius, int outsideFromRadius, int outsideToRadius);
    }

    public void setChangedInsideRadius(int changedInsideRadius) {
        this.changedInsideRadius = changedInsideRadius;
    }

    public void setChangedOutsideRadius(int changedOutsideRadius) {
        this.changedOutsideRadius = changedOutsideRadius;
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private OnProgressTouchEvent onProgressTouchEvent;

    public void setOnProgressTouchEvent(OnProgressTouchEvent onProgressTouchEvent) {
        this.onProgressTouchEvent = onProgressTouchEvent;
    }

    public interface OnProgressTouchEvent{
        /**
         * 触碰
         */
        void onHandTouch();

        /**
         * 离开
         */
        void onHandUp();
    }
}
