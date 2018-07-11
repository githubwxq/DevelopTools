package com.juziwl.uilibrary.chart;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;




/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/12/9
 * @description  进度半圆圈
 */
public class CIrcleProgress extends View {

    private Paint paint;
    private RectF rectF = new RectF();
    /**
     * 圆弧的宽度
     */
    private int arcWidth;
    /**
     * 当前View的宽度
     */
    private int width = 0;
    private String bottomText = "学生情况";
    private int bottomTextSize = dp2px(12);
    /**
     * 完成进度，传0-100
     */
    private int progress = 0;
    private int progressTextSize = dp2px(25);


    private String percent = "%";



    private int percentTextSize = dp2px(15);



    private  float maxProgress=100.0f;



    /**
     * 圆弧的底色
     */
    private int arcBgColor = Color.parseColor("#f2f2f2");
    /**
     * 百分比的字体颜色
     */
    private int percentTextColor = Color.parseColor("#333333");
    /**
     * bottomText的字体颜色
     */
    private int bottomTextColor = Color.parseColor("#999999");
    /**
     * 进度渐变的颜色
     */
    private int fromColor = Color.parseColor("#FFAA80"), toColor = Color.parseColor("#FF884D");

    public CIrcleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcWidth = dp2px(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(arcWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(arcBgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(arcWidth);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, -225, 270, false, paint);
        int needTotalAngel = (int) (progress / maxProgress * 270);  //该话的角度
        int fromA = Color.alpha(fromColor);
        int fromR = Color.red(fromColor);
        int fromG = Color.green(fromColor);
        int fromB = Color.blue(fromColor);
        int toA = Color.alpha(toColor);
        int toR = Color.red(toColor);
        int toG = Color.green(toColor);
        int toB = Color.blue(toColor);
        int deltaA = toA - fromA;
        int deltaR = toR - fromR;
        int deltaG = toG - fromG;
        int deltaB = toB - fromB;
        for (int i = 0; i < needTotalAngel; i++) {
            int addDeltaA = (int) (deltaA * i * 1.0 / needTotalAngel);
            int addDeltaR = (int) (deltaR * i * 1.0 / needTotalAngel);
            int addDeltaG = (int) (deltaG * i * 1.0 / needTotalAngel);
            int addDeltaB = (int) (deltaB * i * 1.0 / needTotalAngel);
            int color = Color.argb(fromA + addDeltaA, fromR + addDeltaR, fromG + addDeltaG, fromB + addDeltaB);
            paint.setColor(color);
            canvas.drawArc(rectF, -225 + i, 1, false, paint);
        }
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(bottomTextSize);
        paint.setColor(bottomTextColor);
        canvas.drawText(bottomText, width / 2, width * 3 / 4, paint);
        paint.setTextSize(progressTextSize);
        paint.setColor(percentTextColor);
        if (TextUtils.isEmpty(percent)) {
            if(TextUtils.isEmpty(bottomText)){
                canvas.drawText(progress + "", width / 2 , width*6/10, paint);
            }else {
                canvas.drawText(progress + "", width / 2, width / 2, paint);
            }
            paint.setTextSize(percentTextSize);
            canvas.drawText(percent, width / 2 + getDelta(), width / 2, paint);
        }else {
            if(percentTextSize==progressTextSize){
                canvas.drawText(progress + percent, width / 2  , width / 2, paint);
            }else {
                canvas.drawText(progress + "", width / 2 - dp2px(8), width / 2, paint);
                paint.setTextSize(percentTextSize);
                canvas.drawText(percent, width / 2 + getDelta(), width / 2, paint);
            }
        }
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }


    /**
     * 最后调用此方法
     */
    public void setProgress(@IntRange(from = 0, to = 100) int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setProgressTextSize(int progressTextSize) {
        this.progressTextSize = progressTextSize;
    }

    private int getDelta() {
        if (progressTextSize==percentTextSize){
            if (progress < 10) {
                return dp2px(0);
            } else if (progress > 99) {
                return dp2px(6); //3位
            } else {
                return dp2px(3);
            }

        }else {
            if (progress < 10) {
                return dp2px(6);
            } else if (progress > 99) {
                return dp2px(18);
            } else {
                return dp2px(14);
            }
        }
    }

    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (width == 0) {
            width = w;
            rectF.left = arcWidth / 2;
            rectF.top = arcWidth / 2;
            rectF.right = width - arcWidth / 2;
            rectF.bottom = width - arcWidth / 2;
        }
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }



    public void setPercentTextSize(int percentTextSize) {
        this.percentTextSize = percentTextSize;
    }


    public void setBottomTextSize(int bottomTextSize) {
        this.bottomTextSize = bottomTextSize;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }


}
