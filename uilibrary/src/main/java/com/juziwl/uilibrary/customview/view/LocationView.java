package com.juziwl.uilibrary.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/05
 * desc:控件位子操作
 * version:1.0
 */
public class LocationView extends CustomView {
    public LocationView(Context context) {
        super(context);
    }

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(currentX, currentY, 50, mDeafultPaint);//绘制圆的过程





    }

    private int currentX = 60;
    private int currentY = 60;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();
        float eventX = event.getX();
        currentX = (int) eventX;
        currentY = (int) event.getY();
        invalidate();
        Log.e("location", "rawX" + rawX + "event" + eventX);
        return true;


    }
}
