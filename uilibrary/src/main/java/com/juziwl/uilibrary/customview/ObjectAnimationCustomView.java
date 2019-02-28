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

package com.juziwl.uilibrary.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ObjectAnimationCustomView extends View {

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

    public void setInnerRadius(float innerRadius) {
        this.innerRadius = innerRadius;

    }

    public float innerRadius = 80; //默认当为wrapcontent的时候给的默认值




    public ObjectAnimationCustomView setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
        return this;
    }

    public int innerMaxRadius = 80;
    public int innerMinRadius = 40;


    public ObjectAnimationCustomView(Context context) {
        this(context, null);
    }

    public ObjectAnimationCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObjectAnimationCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentContext = context;
        initView(context);
    }

    public void initView(Context context) {
        defaultSize = dp2px(context, defaultSize);
        innerRadius = dp2px(context, innerRadius);
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setColor(Color.YELLOW);
        initAnimatior();
    }


    private ObjectAnimator animator = null;

    private ObjectAnimator animator2 = null;


    private void initAnimatior() {
        animator = new ObjectAnimator();
        animator.setTarget(this);
        animator.setPropertyName("innerRadius");
        animator.setFloatValues(dp2px(mCurrentContext,innerMaxRadius) ,dp2px(mCurrentContext,innerMinRadius) );
        animator.setDuration(totalTime/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getCurrentPlayTime();
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (currentState==1) {
                    currentState=2;
                    animator.setFloatValues(dp2px(mCurrentContext,innerMinRadius) ,dp2px(mCurrentContext,innerMaxRadius) );
                }else {
                    currentState=1;
                    animator.setFloatValues(dp2px(mCurrentContext,innerMaxRadius) ,dp2px(mCurrentContext,innerMinRadius) );
                }
                animator.setDuration(totalTime/2);
                animator.start();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });


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


//    <color name="pink_200">#ff96b9</color>
//
//    <color name="pink_500">#ff4080</color>
//
//    <color name="teal_200">#7dcfb4</color>
//
//    <color name="teal_500">#13a876</color>

    public static final String LIGHTPINK = "#ff96b9";
    public static final String DARKPINK = "#ff4080";
    public static final String LIGHTGREEN = "#7dcfb4";
    public static final String DARKGREEN = "#13a876";
    /**
     * 1 收紧状态  3收紧结束
     * <p>
     * 2放松状态  4 放松结束
     */
    public int currentState = 1;


    public int currentRadius = 50;


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw++++++++++++++++++++");
        super.onDraw(canvas);
//        mDeafultPaint.
//                canvas.d

        //外圆
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        if (currentState == 0 || currentState == 1) {
            paint.setColor(Color.parseColor(DARKPINK));
        } else {
            paint.setColor(Color.parseColor(DARKGREEN));
        }
        canvas.drawCircle(defaultSize/2, defaultSize/2, defaultSize/2 , paint);

        //内圆
        Paint innerPaint = new Paint();
        innerPaint.setStyle(Paint.Style.FILL);
        if (currentState == 0 || currentState == 1) {
            innerPaint.setColor(Color.parseColor(LIGHTPINK));
        } else {
            innerPaint.setColor(Color.parseColor(LIGHTGREEN));
        }
        canvas.drawCircle(defaultSize/2 , defaultSize/2 , innerRadius, innerPaint);

        //中间字

    }


    public ObjectAnimationCustomView setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        return this;
    }

    /**
     * 总时长
     */
    public int totalTime = 10000;

    boolean hasStart=false;

    public void start() {
        if (!hasStart) {
            animator.start();
            hasStart=true;
            return;
        }
        if (animator.isPaused()) {
            animator.resume();
        }else {
            animator.pause();
        }
    }

    public void pause() {

    }


    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
//                 1背景
//                 2主体（onDraw()）
//                 3子 View（dispatchDraw()）
//                 4滑动边缘渐变和滑动条
//                 5前景