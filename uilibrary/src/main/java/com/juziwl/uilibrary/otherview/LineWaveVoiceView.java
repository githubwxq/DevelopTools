package com.juziwl.uilibrary.otherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;

import com.wxq.commonlibrary.util.ConvertUtils;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * 语音录制的动画效果
 * Created by chenxf on 17-6-12.
 */
public class LineWaveVoiceView extends View {

    private Paint paint;
    /**
     * 矩形波纹颜色
     */
    private int lineColor = Color.parseColor("#ff6f26");
    /**
     * 矩形波纹宽度
     */
    private float lineWidth = ConvertUtils.dp2px(4f);
    private float textSize = ConvertUtils.dp2px(16);
    private static final String DEFAULT_TEXT = "00:00";
    private String text = DEFAULT_TEXT;
    private int textColor = Color.parseColor("#999999");
    /**
     * //最小的矩形线高，是线宽的2倍，线宽从lineWidth获得
     */
    private final int MIN_WAVE_H = 2;
    /**
     * 最高波峰，是线宽的4倍
     */
    private final int MAX_WAVE_H = 7;

    /**
     * 默认矩形波纹的高度，总共10个矩形，左右各有10个
     */
    private int[] DEFAULT_WAVE_HEIGHT = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    private LinkedList<Integer> mWaveList = new LinkedList<>();

    /**
     * 右边波纹矩形的数据，10个矩形复用一个rectF
     */
    private RectF rectRight = new RectF();
    /**
     * 左边波纹矩形的数据
     */
    private RectF rectLeft = new RectF();

    private LinkedList<Integer> list = new LinkedList<>();
    public static final int MAX_RECT_NUM = 10;
    public static final int SPACE_WIDTH = ConvertUtils.dp2px(4);
    private boolean isDrawWave = true;
    private float lineWidthRadio = 0.8f;
    private float lineHeightRadio = 0.6f;
    private long startTime = 0;
    private int curPosition = 0;

    public LineWaveVoiceView(Context context) {
        super(context);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resetList(list, DEFAULT_WAVE_HEIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int widthCentre = getWidth() / 2;
        int heightCentre = getHeight() / 2;

        //更新时间
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        float textWidth = paint.measureText(text) + 2 * SPACE_WIDTH;
        canvas.drawText(text, widthCentre, heightCentre - (paint.ascent() + paint.descent()) / 2, paint);

        if (!isDrawWave) {
            return;
        }
        //更新左右两边的波纹矩形
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(lineWidth);
        paint.setAntiAlias(true);
        for (int i = 0; i < MAX_RECT_NUM; i++) {
            //右边矩形
            rectRight.left = widthCentre + 1.5f * i * lineWidth + textWidth / 2 + lineWidth;
            rectRight.top = heightCentre - list.get(i) * lineWidth * lineHeightRadio / 2;
            rectRight.right = rectRight.left + lineWidth * lineWidthRadio;
            rectRight.bottom = rectRight.top + list.get(i) * lineWidth * lineHeightRadio;

            //左边矩形
            rectLeft.left = widthCentre - (1.5f * i * lineWidth + textWidth / 2 + 2 * lineWidth);
            rectLeft.top = heightCentre - list.get(i) * lineWidth * lineHeightRadio / 2;
            rectLeft.right = rectLeft.left + lineWidth * lineWidthRadio;
            rectLeft.bottom = rectLeft.top + list.get(i) * lineWidth * lineHeightRadio;

            canvas.drawRect(rectRight, paint);
            canvas.drawRect(rectLeft, paint);
        }
    }

    /**
     * @param percent 0 - 1
     */
    public synchronized void refreshElement(float percent) {
        //wave 在 2 ~ 7 之间
        int waveH = MIN_WAVE_H + Math.round(percent * (MAX_WAVE_H - MIN_WAVE_H));
        list.add(0, waveH);
        list.removeLast();
        mWaveList.add(waveH);
        invalidate();
    }

    public synchronized void setText(String text, boolean isDrawWave) {
        this.text = text;
        this.isDrawWave = isDrawWave;
        if (!isDrawWave) {
            resetList(list, DEFAULT_WAVE_HEIGHT);
        }
        invalidate();
    }

    public synchronized void setText(@StringRes int resId, boolean isDrawWave) {
        this.text = getContext().getString(resId);
        this.isDrawWave = isDrawWave;
        if (!isDrawWave) {
            resetList(list, DEFAULT_WAVE_HEIGHT);
        }
        invalidate();
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setLineWidthRadio(float lineWidthRadio) {
        this.lineWidthRadio = lineWidthRadio;
    }

    public void setLineHeightRadio(float lineHeightRadio) {
        this.lineHeightRadio = lineHeightRadio;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (curPosition < mWaveList.size()) {
                Integer integer = mWaveList.get(curPosition);
                list.add(0, integer);
                list.removeLast();
                text = formatTime((System.currentTimeMillis() - startTime) / 1000);
                invalidate();
                curPosition++;
                postDelayed(this, 100);
            } else {
                isDrawWave = false;
                invalidate();
            }
        }
    };

    public void startRecord() {
        mWaveList.clear();
    }

    public void startRollBack() {
        startTime = System.currentTimeMillis();
        curPosition = 0;
        isDrawWave = true;
        removeCallbacks(runnable);
        postDelayed(runnable, 100);
    }

    public void stopRollBack() {
        removeCallbacks(runnable);
    }

    public synchronized void stopRecord() {
        resetList(list, DEFAULT_WAVE_HEIGHT);
        text = DEFAULT_TEXT;
        invalidate();
    }

    private void resetList(List<Integer> list, int[] array) {
        list.clear();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
    }

    private DecimalFormat decimalFormat = new DecimalFormat("00");

    /**
     * @param currentSecond 单位是s
     */
    private String formatTime(long currentSecond) {
        long minute = currentSecond / 60;
        long second = currentSecond % 60;
        return decimalFormat.format(minute) + ":" + decimalFormat.format(second);
    }

}