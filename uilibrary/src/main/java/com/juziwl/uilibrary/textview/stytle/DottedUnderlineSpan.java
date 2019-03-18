package com.juziwl.uilibrary.textview.stytle;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.style.ReplacementSpan;

/**
 * textview 指定文字 下划线 虚线
 */
public class DottedUnderlineSpan extends ReplacementSpan {
    private float mOffsetY=8;
    private Paint p;
    private int mWidth;
    private String mSpan;

    private float mSpanLength;
    private boolean mLengthIsCached = false;

    public DottedUnderlineSpan(int _color, String _spannedText){
        initPaint(_color);
        mSpan = _spannedText;
    }

    private void initPaint(int _color){
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(_color);
        p.setStrokeWidth(3);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(new DashPathEffect(new float[] {5, 5}, 0));
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text, start, end);
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.drawText(text, start, end, x, y, paint);
        if(!mLengthIsCached)
            mSpanLength = paint.measureText(mSpan);

        // https://code.google.com/p/android/issues/detail?id=29944
        // canvas.drawLine can't draw dashes when hardware acceleration is enabled,
        // but canvas.drawPath can    从哪里到哪里 按照字长度决定
        Path path = new Path();
        path.moveTo(x, y + mOffsetY);
        path.lineTo(x + mSpanLength, y + mOffsetY);
        canvas.drawPath(path, this.p);
    }
}
