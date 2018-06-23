package com.juziwl.uilibrary.progressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/11/29
 * @description 录音的控制显示和进度显示，是正方形
 */
public class RecordCircleProgressButton extends View implements View.OnClickListener {

    private int width = 0;
    private Paint paint;
    private int outCircleColor = Color.parseColor("#f2f2f2");
    private int outCircleWidth;
    private int middleCircleColor = Color.WHITE;
    private int middleCircleWidth;
    private int innerCircleColor = Color.parseColor("#0093e8");
    private int innerCircleWidth;
    private float sweepAngle = 0;
    private RectF progressRect = new RectF(), squareRect = new RectF();
    private Path path = new Path();
    private int squareCornerRadius;
    public static final int STATE_RESET = 0;
    public static final int STATE_RECORDING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_PLAY = 3;
    private int currentState = STATE_RESET;
    private ObjectAnimator animator = null;
    private OnOperationListener onOperationListener = null;
    private long duratioin = 60_000L;
    /**
     * 是否显示进度
     */
    private boolean isShowSwipeProgress = true;

    public RecordCircleProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outCircleWidth = dp2Px(4);
        middleCircleWidth = outCircleWidth * 2;
        squareCornerRadius = dp2Px(4);
        setOnClickListener(this);
    }

    public void reset() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        currentState = STATE_RESET;
        sweepAngle = 0;
        invalidate();
    }

    public boolean isRecording() {
        return currentState == STATE_RECORDING;
    }

    public boolean isPlaying() {
        return currentState == STATE_PLAY;
    }

    public boolean isResetting() {
        return currentState == STATE_RESET;
    }

    public void stopRecordOrPlay() {
        performClick();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                currentState = STATE_RECORDING;
//                if (animator != null) {
//                    animator.cancel();
//                    animator.removeAllListeners();
//                    animator.removeAllUpdateListeners();
//                } else {
//                    animator = new ObjectAnimator();
//                    animator.setTarget(this);
//                    animator.setPropertyName("sweepAngle");
//                    animator.setDuration(60 * 1000);
//                    animator.setInterpolator(new LinearInterpolator());
//                    animator.setFloatValues(0, 360);
//                }
//                animator.addUpdateListener(animation -> {
//                    if (onOperationListener != null) {
//                        onOperationListener.onProgress(animation.getCurrentPlayTime());
//                    }
//                });
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        onAnimEnd();
//                    }
//                });
//                if (onOperationListener != null) {
//                    onOperationListener.onStart();
//                }
//                animator.start();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float x = event.getX();
//                float y = event.getY();
//                if (!(0 <= x && x <= width && 0 <= y && y <= width)) {
//                    onAnimEnd();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_OUTSIDE:
//            case MotionEvent.ACTION_CANCEL:
//                onAnimEnd();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    private void onAnimEnd() {
        if (onOperationListener != null) {
            onOperationListener.onRecordEnd();
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        currentState = STATE_PAUSE;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOutCircle(canvas);
        drawMiddleCircle(canvas);
        if (currentState == STATE_RESET) {
            drawInnerCircle(canvas);
        } else if (currentState == STATE_RECORDING || currentState == STATE_PLAY) {
            drawInnerSquare(canvas);
        } else {
            drawInnerTriangle(canvas);
        }
    }

    @Override
    public void onClick(View v) {
        if (currentState == STATE_RESET) {
            currentState = STATE_RECORDING;
            initAnimator();
            animator.removeAllUpdateListeners();
            animator.addUpdateListener(animation -> {
                if (onOperationListener != null) {
                    onOperationListener.onProgress(animation.getCurrentPlayTime());
                }
            });
            animator.removeAllListeners();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onAnimEnd();
                }
            });
            if (onOperationListener != null) {
                onOperationListener.onStart();
            }
            animator.start();
        } else if (currentState == STATE_RECORDING) {
            onAnimEnd();
        } else if (currentState == STATE_PAUSE) {
            initAnimator();
            animator.removeAllUpdateListeners();
            animator.addUpdateListener(animation -> {
                if (onOperationListener != null) {
                    onOperationListener.onProgress(animation.getCurrentPlayTime());
                }
            });
            animator.removeAllListeners();
            currentState = STATE_PLAY;
            if (onOperationListener != null) {
                onOperationListener.onStart();
            }
            animator.start();
        } else if (currentState == STATE_PLAY) {
            animator.cancel();
            currentState = STATE_PAUSE;
            if (onOperationListener != null) {
                onOperationListener.onPlayEnd();
            }
            invalidate();
        }
    }

    private void initAnimator() {
        if (animator == null) {
            animator = new ObjectAnimator();
            animator.setTarget(this);
            animator.setPropertyName("sweepAngle");
            animator.setDuration(duratioin);
            animator.setInterpolator(new LinearInterpolator());
            animator.setFloatValues(0, 360);
        }
    }

    private void drawOutCircle(Canvas canvas) {
        int radius = width / 2 - outCircleWidth / 2;
        paint.setColor(outCircleColor);
        paint.setStrokeWidth(outCircleWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, width / 2, radius, paint);
        if (isShowSwipeProgress) {
            paint.setColor(innerCircleColor);
            canvas.drawArc(progressRect, -90, sweepAngle, false, paint);
        }
    }

    private void drawMiddleCircle(Canvas canvas) {
        paint.setColor(middleCircleColor);
        paint.setStrokeWidth(middleCircleWidth);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, width / 2, width / 2 - outCircleWidth - middleCircleWidth / 2, paint);
    }

    private void drawInnerCircle(Canvas canvas) {
        paint.setColor(innerCircleColor);
        paint.setStrokeWidth(innerCircleWidth);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, width / 2, innerCircleWidth, paint);
    }

    private void drawInnerSquare(Canvas canvas) {
        paint.setColor(innerCircleColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(squareRect, squareCornerRadius, squareCornerRadius, paint);
    }

    private void drawInnerTriangle(Canvas canvas) {
        paint.setColor(innerCircleColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(squareCornerRadius));
        canvas.drawPath(path, paint);
        paint.setPathEffect(null);
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (width == 0) {
            width = w;
            innerCircleWidth = width / 2 - outCircleWidth - middleCircleWidth;
            progressRect.left = outCircleWidth / 2;
            progressRect.top = outCircleWidth / 2;
            progressRect.right = w - outCircleWidth / 2;
            progressRect.bottom = h - outCircleWidth / 2;

            squareRect.left = (float) (width / 3.0);
            squareRect.top = (float) (width / 3.0);
            squareRect.right = (float) (width * 2.0 / 3);
            squareRect.bottom = (float) (width * 2.0 / 3);

            setTrianglePath();
        }
    }

    private void setTrianglePath() {
        float leftBorderX = width * 7f / 18;
        float halfLength = (float) (width / 3 / Math.tan(Math.PI / 3));
        float halfWidth = width / 2f;
        path.moveTo(leftBorderX, halfWidth);
        path.lineTo(leftBorderX, halfWidth - halfLength);
        path.lineTo(leftBorderX + width / 3f, width / 2);
        path.lineTo(leftBorderX, halfWidth + halfLength);
        path.close();
    }

    public void setInnerCircleColor(int innerCircleColor) {
        this.innerCircleColor = innerCircleColor;
    }

    private int dp2Px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.onOperationListener = onOperationListener;
    }

    public long getDuratioin() {
        return duratioin;
    }

    public void setDuratioin(long duratioin) {
        this.duratioin = duratioin;
    }

    public void setShowSwipeProgress(boolean showSwipeProgress) {
        isShowSwipeProgress = showSwipeProgress;
    }

    public interface OnOperationListener {
        void onStart();

        /**
         * @param playtime 执行了的时间
         */
        void onProgress(long playtime);

        void onRecordEnd();

        void onPlayEnd();
    }
}
