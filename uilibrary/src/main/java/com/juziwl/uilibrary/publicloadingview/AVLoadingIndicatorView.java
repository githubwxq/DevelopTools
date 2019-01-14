//package com.juziwl.uilibrary.publicloadingview;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// * @author null
// * @modify Neil
// */
//public class AVLoadingIndicatorView extends View {
//    public @interface Indicator {
//    }
//
//    /**
//     * Sizes (with defaults in DP)
//     */
//    public static final int DEFAULT_SIZE = 45;
//
//    //attrs
//
//    Paint mPaint;
//
//    BaseIndicatorController mIndicatorController;
//
//    private boolean mHasAnimation;
//
//    public AVLoadingIndicatorView(Context context) {
//        super(context);
//        init(null, 0);
//    }
//
//    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(attrs, 0);
//    }
//
//    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(attrs, defStyleAttr);
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs, defStyleAttr);
//    }
//
//    private void init(AttributeSet attrs, int defStyle) {
//        mPaint = new Paint();
//        mPaint.setColor(Color.WHITE);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAntiAlias(true);
//        applyIndicator();
//    }
//
//    private void applyIndicator() {
//        mIndicatorController = new BallSpinFadeLoaderIndicator();
//        mIndicatorController.setTarget(this);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
//        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }
//
//    private int measureDimension(int defaultSize, int measureSpec) {
//        int result = defaultSize;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else if (specMode == MeasureSpec.AT_MOST) {
//            result = Math.min(defaultSize, specSize);
//        } else {
//            result = defaultSize;
//        }
//        return result;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        drawIndicator(canvas);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (!mHasAnimation) {
//            mHasAnimation = true;
//            applyAnimation();
//        }
//    }
//
//    void drawIndicator(Canvas canvas) {
//        mIndicatorController.draw(canvas, mPaint);
//    }
//
//    void applyAnimation() {
//        mIndicatorController.createAnimation();
//    }
//
//    private int dp2px(int dpValue) {
//        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        mIndicatorController.destory();
//        super.onDetachedFromWindow();
//    }
//}
