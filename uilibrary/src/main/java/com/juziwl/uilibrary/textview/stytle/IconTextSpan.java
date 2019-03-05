package com.juziwl.uilibrary.textview.stytle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ConvertUtils;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/05
 * desc:自定义
 * version:1.0
 */
public class IconTextSpan extends ReplacementSpan {

    public Context context;
    private String mText;  //Icon内文字
    private float mTextSize; //文字大小
    private  Paint mPaint;
    private int mWidth;
    private float mRightMargin;
    private TextPaint textPaint;

    public IconTextSpan(Context context, String mText) {
        this.context = context;
        this.mText = mText;
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(context.getResources().getColor(R.color.green_400));
        mPaint.setAntiAlias(true);
        textPaint=new TextPaint();
        textPaint.setColor(context.getResources().getColor(R.color.pink_400));
        mTextSize= ConvertUtils.sp2px(15,context);//15 sp
        textPaint.setTextSize(mTextSize);
        textPaint.setAntiAlias(true);
    }

//    参数有 paint画笔 文字信息charSequence 开始和结束位置start,end  Paint.FontMetricsInt包含文字的属性值 top,ascent等
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        Rect textRect = new Rect();
        Paint apaint = new Paint();
        apaint.setTextSize(mTextSize);
        apaint.getTextBounds(mText, 0, mText.length(), textRect);

        return mWidth = (int)(textRect.width());
    }

//    参数有 画布，文字，span开始结束位置，上下左位置，y是文字基准线位置，画笔
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float textHeight = metrics.descent - metrics.ascent; // baseline  y ==0    metrics.ascent为负值
        RectF bgRect = new RectF(x, top, x + mWidth, bottom);
        canvas.drawRoundRect(bgRect, 0, 0, mPaint);
        canvas.drawText(mText, x, (y-(bottom-top-textHeight)/2), textPaint);

    }
}
