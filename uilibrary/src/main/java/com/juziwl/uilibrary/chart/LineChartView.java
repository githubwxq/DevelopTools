package com.juziwl.uilibrary.chart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jeanboy on 2017/6/12.
 */

public class LineChartView extends View {

    private Paint linePaint;//曲线1画笔
    private Paint linePaintSecond;//曲线2画笔

    private Paint mPaintShader;//曲线2阴影画笔


    private Paint pointPaint;//曲线上1锚点画笔

    private Paint pointPaint2;//曲线上2锚点画笔


    private Paint tablePaint;//表格画笔

    private Paint textRulerPaint;//标尺文本画笔

    private Paint textPointPaint;//曲线上锚点文本画笔

    private Path linePath;//曲线路径

    private Path linePathSecond;//第二条曲线路径

    private Path shaderPath;//第二条曲线路径阴影


    private Path tablePath;//表格路径

    private int mWidth, mHeight;

    private List<Data> dataList = new ArrayList<>();  //第一条数据的集合


    private List<Data> dataListSecond = new ArrayList<>();  //第二条数据的集合


    private List<String> xList = new ArrayList<>();  //底部文字集合


    private Point[] linePoints; //第一条数据的点的集合


    private Point[] linePointsSecond; //第二条数据的点的集合


    private int stepStart;
    private int stepEnd;
    private int stepSpace;
    private int stepSpaceDefault = 40;
    private int stepSpaceDP = stepSpaceDefault;//item宽度默认dp
    private int topSpace, bottomSpace;
    private int tablePadding;
    private int tablePaddingDP = 10;//view四周padding默认dp

    private int maxValue, minValue;
    private int rulerValueDefault = 20;
    private int rulerValue = rulerValueDefault;//刻度单位跨度
    private int rulerValuePadding;//刻度单位与轴的间距
    private int rulerValuePaddingDP = 5;//刻度单位与轴的间距默认dp
    private float heightPercent = 0.72f;

    private int lineColorSecond = Color.parseColor("#ff9785");//曲线1颜色

    private int lineColor = Color.parseColor("#85B1ff");//曲线2颜色

    private float lineWidthDP = 1f;//曲线宽度dp

    private int pointColorSecond = Color.parseColor("#ff9785");//锚点1颜色

    private int pointColor = Color.parseColor("#85B1ff");//锚点2颜色


    private float pointWidthDefault = 8f;
    private float pointWidthDP = pointWidthDefault;//锚点宽度dp

    private int tableColor = Color.parseColor("#FFF0F0F0");//表格线颜色


    private float tableWidthDP = 0.5f;//表格线宽度dp

    private int rulerTextColor = Color.parseColor("#FF999999");//表格标尺文本颜色
    private float rulerTextSizeSP = 10f;//表格标尺文本大小

    //    private int pointTextColor = Color.parseColor("#009688");//锚点文本颜色
    private int pointTextColor = Color.parseColor("#00000000");//锚点文本颜色


    private int tableTextColor = Color.parseColor("#FF999999");//文字的颜色


    private float pointTextSizeSP = 10f;//锚点文本大小

    private boolean isShowTable = true;
    private boolean isBezierLine = false;  //是否设置贝塞尔
    private boolean isCubePoint = false;
    private boolean isInitialized = false;
    private boolean isPlayAnim = false;
    private boolean isShowShader = false;//是否显示阴影

    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimatorSecond;
    private boolean isPlayAnimSecond = false;


    private float currentValue = 0f;
    private boolean isAnimating = false;


    private float currentValueSecond = 0f;
    private boolean isAnimatingSecond = false;


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundDrawable(new BitmapDrawable());
        setupView();
    }

    private void setupView() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);//抗锯齿
        linePaint.setStyle(Paint.Style.STROKE);//STROKE描边FILL填充
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(dip2px(lineWidthDP));//边框宽度

        linePaintSecond = new Paint();
        linePaintSecond.setAntiAlias(true);//抗锯齿
        linePaintSecond.setStyle(Paint.Style.STROKE);//STROKE描边FILL填充
        linePaintSecond.setColor(lineColorSecond);
        linePaintSecond.setStrokeWidth(dip2px(lineWidthDP));//边框宽度


        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(pointColor);
        pointPaint.setStrokeWidth(dip2px(pointWidthDP));


        pointPaint2 = new Paint();
        pointPaint2.setAntiAlias(true);
        pointPaint2.setStyle(Paint.Style.FILL);
        pointPaint2.setColor(pointColorSecond);

        pointPaint2.setStrokeWidth(dip2px(pointWidthDP));


        tablePaint = new Paint();
        tablePaint.setAntiAlias(true);
        tablePaint.setStyle(Paint.Style.STROKE);
        tablePaint.setColor(tableColor);
        tablePaint.setStrokeWidth(dip2px(tableWidthDP));

        textRulerPaint = new Paint();
        textRulerPaint.setAntiAlias(true);
        textRulerPaint.setStyle(Paint.Style.FILL);
        textRulerPaint.setTextAlign(Paint.Align.CENTER);
        textRulerPaint.setColor(rulerTextColor);//文本颜色
        textRulerPaint.setTextSize(sp2px(rulerTextSizeSP));//字体大小

        textPointPaint = new Paint();
        textPointPaint.setAntiAlias(true);
        textPointPaint.setStyle(Paint.Style.FILL);
        textPointPaint.setTextAlign(Paint.Align.CENTER);
        textPointPaint.setColor(pointTextColor);//文本颜色
        textPointPaint.setTextSize(sp2px(pointTextSizeSP));//字体大小

        //  阴影画笔
        mPaintShader = new Paint();
        mPaintShader.setAntiAlias(true);
        mPaintShader.setStrokeWidth(2f);
        mPaintShader.setColor(Color.BLACK);


        linePath = new Path();
        linePathSecond = new Path();
        shaderPath = new Path();
        tablePath = new Path();

//        resetParam();
    }

    private void initAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(dataListSecond.size() * 150);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                currentValue = 0f;
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentValue = 1f;
                isAnimating = false;
                isPlayAnim = false;
            }
        });
        valueAnimator.setStartDelay(500);

        //第二条线的动画

        valueAnimatorSecond = ValueAnimator.ofFloat(0f, 1f).setDuration(dataListSecond.size() * 150);
        valueAnimatorSecond.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorSecond.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValueSecond = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimatorSecond.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                currentValueSecond = 0f;
                isPlayAnimSecond = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentValueSecond = 1f;
                isAnimatingSecond = false;
                isPlayAnimSecond = false;
            }
        });
        valueAnimatorSecond.setStartDelay(500);

    }

    private void resetParam() {
        if (linePath != null) {

            linePath.reset();
        }
        linePathSecond.reset();
        shaderPath.reset();
        tablePath.reset();
//        stepSpace = dip2px(stepSpaceDP);
        if (dataListSecond.size() != 0 && mWidth != 0) {
            stepSpace = mWidth / (dataListSecond.size());
        } else {
            stepSpace = dip2px(stepSpaceDP);
        }

        tablePadding = dip2px(tablePaddingDP);
        rulerValuePadding = dip2px(rulerValuePaddingDP);
        stepStart = tablePadding * (isShowTable ? 2 : 1);   //2倍的间距作为起始点
        stepEnd = stepStart + stepSpace * (dataListSecond.size() - 1);
        topSpace = bottomSpace = tablePadding;
        if (!dataList.isEmpty()) {
            linePoints = new Point[dataList.size()];
        }
        if (!dataListSecond.isEmpty()) {
            linePointsSecond = new Point[dataListSecond.size()];
        }

        initAnim();
        isInitialized = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();//父类期望的高度
//        int width = tablePadding + getTableEnd() + getPaddingLeft() + getPaddingRight();//计算自己的宽度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);//父类期望的高度
        if (MeasureSpec.EXACTLY == heightMode) {
            height = getPaddingTop() + getPaddingBottom() + height;
        }
        mWidth = width;
        mHeight=height;
        setMeasuredDimension(width, height);//设置自己的宽度和高度
        resetParam();
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mWidth = w;
//        mHeight = h;
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);//绘制背景颜色
        canvas.translate(0f, mHeight / 2f + (getViewDrawHeight() + topSpace + bottomSpace) / 2f);//设置画布中心点垂直居中

        if (!isInitialized) {
            setupLine();
        }

        if (isShowTable) {
            drawTable(canvas);//绘制表格
        }
        drawLine(canvas);//绘制曲线
        drawLinePoints(canvas);//绘制曲线上的点


    }

    private void drawText(Canvas canvas, Paint textPaint, String text, float x, float y) {
        canvas.drawText(text, x, y, textPaint);
    }

    /**
     * 绘制标尺y轴文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawRulerYText(Canvas canvas, String text, float x, float y) {
        textRulerPaint.setTextAlign(Paint.Align.RIGHT);
        Paint.FontMetrics fontMetrics = textRulerPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offsetY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = y + offsetY;
        float newX = x - rulerValuePadding;
        drawText(canvas, textRulerPaint, text, newX, newY);
    }

    /**
     * 绘制标尺x轴文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawRulerXText(Canvas canvas, String text, float x, float y) {
        textRulerPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textRulerPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offsetY = fontTotalHeight / 2 - fontMetrics.bottom;
//        float newY = y + offsetY + rulerValuePadding;
        float newY = rulerValuePadding;
        drawText(canvas, textRulerPaint, text, x, newY);
    }

    /**
     * 绘制曲线上锚点文本
     *
     * @param canvas
     * @param text
     * @param x
     * @param y
     */
    private void drawLinePointText(Canvas canvas, String text, float x, float y) {
        textPointPaint.setTextAlign(Paint.Align.CENTER);
        float newY = y - rulerValuePadding;
        drawText(canvas, textPointPaint, text, x, newY);
    }

    private int getTableStart() {
        return isShowTable ? stepStart + tablePadding : stepStart;
    }

    private int getTableEnd() {
        return isShowTable ? stepEnd + tablePadding : stepEnd;
    }

    /**
     * 绘制背景表格
     *
     * @param canvas
     */
    private void drawTable(Canvas canvas) {
        int tableEnd = getTableEnd() + stepSpace / 2;

        int rulerCount = maxValue / rulerValue;
        int rulerMaxCount = maxValue % rulerValue > 0 ? rulerCount + 1 : rulerCount;
        int rulerMax = rulerValue * rulerMaxCount + rulerValueDefault;

        tablePath.moveTo(stepStart, -getValueHeight(rulerMax));//加上顶部的间隔
//        tablePath.lineTo(stepStart, 0);//标尺y轴
//        tablePath.lineTo(tableEnd, 0);//标尺x轴

        int startValue = minValue - (minValue > 0 ? 0 : minValue % rulerValue);
        int endValue = (maxValue + rulerValue);

        //标尺y轴连接线
        do {
            int startHeight = -getValueHeight(startValue);
            tablePath.moveTo(stepStart, startHeight);
            tablePath.lineTo(tableEnd, startHeight);
            //绘制y轴刻度单位
            drawRulerYText(canvas, String.valueOf(startValue), stepStart, startHeight);
            startValue += rulerValue;
        } while (startValue < endValue);

        canvas.drawPath(tablePath, tablePaint);


        //绘制x轴刻度单位
        drawRulerXValue(canvas);
    }

    /**
     * 绘制标尺x轴上所有文本
     *
     * @param canvas
     */
    private void drawRulerXValue(Canvas canvas) {
        if (linePointsSecond != null) {
            for (int i = 0; i < linePointsSecond.length; i++) {
                Point point = linePointsSecond[i];
                if (point == null) break;

                drawRulerXText(canvas, xList.get(i), linePointsSecond[i].x, 0);  //底部的时间点到时候换一个集和存放下面的文字
            }
        }

    }

    /**
     * 绘制曲线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int[] shadeColors = new int[]{
                lineColorSecond, Color.WHITE};

        //  渐变阴影
        LinearGradient mShader = new LinearGradient(0, getValueHeight(minValue), 0, getHeight(), shadeColors, null, Shader.TileMode.REPEAT);
        mPaintShader.setShader(mShader);


        if (!dataList.isEmpty()) {
            if (isPlayAnim) {
                Path dst = new Path();
                PathMeasure measure = new PathMeasure(linePath, false);
                measure.getSegment(0, currentValue * measure.getLength(), dst, true);
                canvas.drawPath(dst, linePaint);
            } else {
                canvas.drawPath(linePath, linePaint);
            }
            if (isShowShader) {
                canvas.drawPath(linePath, mPaintShader);
            }
        }


        //第二条先处理
        if (!dataListSecond.isEmpty()) {
            if (isPlayAnimSecond) {
                Path dst = new Path();
                PathMeasure measure = new PathMeasure(linePathSecond, false);
                measure.getSegment(0, currentValueSecond * measure.getLength(), dst, true);
                canvas.drawPath(dst, linePaintSecond);
                // TODO: 2018/6/7  阴影暂时不能动画  有时间在修改！！！
            } else {
                canvas.drawPath(linePathSecond, linePaintSecond);
            }
            if (isShowShader) {
                canvas.drawPath(shaderPath, mPaintShader);
            }

        }


    }

    private float mProgress;

    //  计算动画进度
    public void setPercentage(float percentage) {
        if (percentage < 0.0f || percentage > 1.0f) {
            throw new IllegalArgumentException(
                    "setPercentage not between 0.0f and 1.0f");
        }
        mProgress = percentage;
        invalidate();
    }

    /**
     * @param lineChartView
     * @param duration      动画持续时间
     */
    public void startAnim(LineChartView lineChartView, long duration) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(lineChartView, "percentage", 0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }

    /**
     * 是否显示阴影---在set数据之前调用
     *
     * @param isShowShader
     */
    public void setShowShader(boolean isShowShader) {
        this.isShowShader = isShowShader;

    }


    /**
     * 绘制曲线上的锚点
     *
     * @param canvas
     */
    private void drawLinePoints(Canvas canvas) {
        if (linePoints != null) {
            float pointWidth = dip2px(pointWidthDP) / 2;
            int pointCount = linePoints.length;
            if (isPlayAnim) {
                pointCount = Math.round(currentValue * linePoints.length);
            }
            for (int i = 0; i < pointCount; i++) {
                Point point = linePoints[i];
                if (point == null) break;
                if (isCubePoint) {
                    canvas.drawPoint(point.x, point.y, pointPaint);
                } else {
                    canvas.drawCircle(point.x, point.y, pointWidth, pointPaint);
                }
                //绘制点的文本
                drawLinePointText(canvas, String.valueOf(dataList.get(i).getValue()), point.x, point.y);
            }
        }


        if (linePointsSecond != null) {
            float pointWidth = dip2px(pointWidthDP) / 2;
            int pointCount = linePointsSecond.length;
            if (isPlayAnimSecond) {
                pointCount = Math.round(currentValueSecond * linePointsSecond.length);
            }
            for (int i = 0; i < pointCount; i++) {
                Point point2 = linePointsSecond[i];
                if (point2 == null) break;
                if (isCubePoint) {
                    canvas.drawPoint(point2.x, point2.y, pointPaint2);
                } else {
                    canvas.drawCircle(point2.x, point2.y, pointWidth, pointPaint2);
                }
                //绘制点的文本
                drawLinePointText(canvas, String.valueOf(dataListSecond.get(i).getValue()), point2.x, point2.y);

            }
        }


    }

    /**
     * 获取value值所占的view高度
     *
     * @param value
     * @return
     */
    private int getValueHeight(int value) {
        float valuePercent = Math.abs(value - minValue) * 100f / (Math.abs(maxValue - minValue) * 100f);//计算value所占百分比
        return (int) (getViewDrawHeight() * valuePercent + bottomSpace + 0.5f);//底部加上间隔
    }

    /**
     * 获取绘制区域高度
     *
     * @return
     */
    private float getViewDrawHeight() {
        return getMeasuredHeight() * heightPercent;
    }

    /**
     * 初始化曲线数据
     */
    private void setupLine() {
        int stepTemp = getTableStart();
        if (!dataList.isEmpty()) {


            Point pre = new Point();

            //初始化线条数据
            pre.set(stepTemp, -getValueHeight(dataList.get(0).getValue()));//坐标系从0,0默认在第四象限绘制
            linePoints[0] = pre;
            linePath.moveTo(pre.x, pre.y);

            if (dataList.size() == 1) {
                isInitialized = true;
                return;
            }

            for (int i = 1; i < dataList.size(); i++) {
                Data data = dataList.get(i);
                Point next = new Point();
                next.set(stepTemp += stepSpace, -getValueHeight(data.getValue()));

                if (isBezierLine) {
                    int cW = pre.x + stepSpace / 2;

                    Point p1 = new Point();//控制点1
                    p1.set(cW, pre.y);

                    Point p2 = new Point();//控制点2
                    p2.set(cW, next.y);

                    linePath.cubicTo(p1.x, p1.y, p2.x, p2.y, next.x, next.y);//创建三阶贝塞尔曲线
                } else {
                    linePath.lineTo(next.x, next.y);
                }

                pre = next;
                linePoints[i] = next;
            }
        }


        if (!dataListSecond.isEmpty()) {
            //创建第二条线条的数据
            stepTemp = getTableStart();
            Point pre2 = new Point();

            //初始化线条数据
            pre2.set(stepTemp, -getValueHeight(dataListSecond.get(0).getValue()));//坐标系从0,0默认在第四象限绘制

            int minValue = 0;
            List<Integer> valueList = new ArrayList<>();
            for (Data data : dataListSecond) {
                valueList.add(data.getValue());
            }


            minValue = Collections.min(valueList);


            linePointsSecond[0] = pre2;
            linePathSecond.moveTo(pre2.x, pre2.y);

            shaderPath.moveTo(pre2.x, -getValueHeight(minValue));
            shaderPath.lineTo(pre2.x, pre2.y);


            if (dataListSecond.size() == 1) {
                isInitialized = true;
                return;
            }

            for (int i = 1; i < dataListSecond.size(); i++) {
                Data data = dataListSecond.get(i);
                Point next = new Point();
                next.set(stepTemp += stepSpace, -getValueHeight(data.getValue()));

                if (isBezierLine) {
                    int cW = pre2.x + stepSpace / 2;

                    Point p1 = new Point();//控制点1
                    p1.set(cW, pre2.y);

                    Point p2 = new Point();//控制点2
                    p2.set(cW, next.y);


                    linePathSecond.cubicTo(p1.x, p1.y, p2.x, p2.y, next.x, next.y);//创建三阶贝塞尔曲线
                    shaderPath.cubicTo(p1.x, p1.y, p2.x, p2.y, next.x, next.y);//创建三阶贝塞尔曲线
                    if (i == dataListSecond.size() - 1) {

                        shaderPath.lineTo(next.x, -getValueHeight(minValue));//创建三阶贝塞尔曲线
                        shaderPath.close();
//                    shaderPath.cubicTo(p1.x, minValue, p2.x, minValue, next.x, minValue);//创建三阶贝塞尔曲线
                    }
                } else {
                    linePathSecond.lineTo(next.x, next.y);
                    shaderPath.lineTo(next.x, -getValueHeight(minValue));//创建三阶贝塞尔曲线
                    shaderPath.close();
                }

                pre2 = next;
                linePointsSecond[i] = next;
            }
        }
        ;


        isInitialized = true;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private void refreshLayout() {


        resetParam();

        requestLayout();

        postInvalidate();

    }

    /*-------------可操作方法---------------*/


    public void setData(List<Data> dataListSecond, List<String> datas3, int maxY) {


        this.dataListSecond.clear();
        this.dataListSecond.addAll(dataListSecond);

        xList.clear();
        xList.addAll(datas3);

        int maxValue2 = Collections.max(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int minValue2 = Collections.min(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();

        maxValue = maxY;

        minValue = 0;

//        if (maxValue / 10 > 0) {
//            if (maxValue % 10 > 0) {
//                maxValue = 10 * (maxValue / 10 + 1);
//            } else {
//                maxValue = 10 * (maxValue / 10);
//            }
//        } else {
//            maxValue = 10;
//        }


        rulerValue = (maxValue - minValue) / 5;

//        if (rulerValue <= 0) {
//            rulerValue = 10;
//        }
        refreshLayout();
    }


    /**
     * 设置数据
     *
     * @param dataList
     * @param datas3
     */
    public void setData(List<Data> dataList, List<Data> dataListSecond, List<String> datas3) {
        if (dataList == null || dataListSecond == null) {
            throw new RuntimeException("dataList cannot is null!");
        }
        if (dataList.isEmpty() || dataListSecond.isEmpty()) return;

        this.dataList.clear();
        this.dataList.addAll(dataList);

        this.dataListSecond.clear();
        this.dataListSecond.addAll(dataListSecond);

        xList.clear();
        xList.addAll(datas3);


        int maxValue1 = Collections.max(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int minValue1 = Collections.min(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int maxValue2 = Collections.max(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int minValue2 = Collections.min(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        maxValue = maxValue1 > maxValue2 ? maxValue1 : maxValue2;

        minValue = minValue1 < minValue2 ? minValue1 : minValue2;


        if (maxValue / 10 > 0) {
            if (maxValue % 10 > 0) {
                maxValue = 10 * (maxValue / 10 + 1);
            } else {
                maxValue = 10 * (maxValue / 10);
            }
        } else {
            maxValue = 10;
        }


        rulerValue = (maxValue - minValue) / 5;
//        if (rulerValue <= 0) {
//            rulerValue = 10;
//        }
        refreshLayout();
    }


    public void setData(List<Data> dataList, List<Data> dataListSecond, List<String> datas3, int maxY) {
        if (dataList == null || dataListSecond == null) {
            throw new RuntimeException("dataList cannot is null!");
        }
        if (dataList.isEmpty() || dataListSecond.isEmpty()) return;

        this.dataList.clear();
        this.dataList.addAll(dataList);

        this.dataListSecond.clear();
        this.dataListSecond.addAll(dataListSecond);

        xList.clear();
        xList.addAll(datas3);


        int maxValue1 = Collections.max(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int minValue1 = Collections.min(this.dataList, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int maxValue2 = Collections.max(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        int minValue2 = Collections.min(this.dataListSecond, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getValue() - o2.getValue();
            }
        }).getValue();


        maxValue = maxY;
        if (maxY == 100) {
            minValue = 0;
        } else {

            minValue = minValue1 < minValue2 ? minValue1 : minValue2;
        }


//        if (maxValue / 10 > 0) {
//            if (maxValue % 10 > 0) {
//                maxValue = 10 * (maxValue / 10 + 1);
//            } else {
//                maxValue = 10 * (maxValue / 10);
//            }
//        } else {
//            maxValue = 10;
//        }


        rulerValue = (maxValue - minValue) / 5;
//        if (rulerValue <= 0) {
//            rulerValue = 10;
//        }
        refreshLayout();
    }


    /**
     * 设置是否显示标尺表格
     *
     * @param showTable
     */
    public void setShowTable(boolean showTable) {
        isShowTable = showTable;
        refreshLayout();
    }

    /**
     * 设置是否是贝塞尔曲线
     *
     * @param isBezier
     */
    public void setBezierLine(boolean isBezier) {
        isBezierLine = isBezier;
        refreshLayout();
    }

    /**
     * 设置锚点形状
     *
     * @param isCube
     */
    public void setCubePoint(boolean isCube) {
        isCubePoint = isCube;
        refreshLayout();
    }

    /**
     * 设置标尺y轴间距
     *
     * @param space
     */
    public void setRulerYSpace(int space) {
        if (space <= 0) {
            space = rulerValueDefault;
        }
        this.rulerValue = space;
        refreshLayout();
    }

    /**
     * 设置曲线点的间距，标尺x轴间距
     *
     * @param dp
     */
    public void setStepSpace(int dp) {
        if (dp < stepSpaceDefault) {
            dp = stepSpaceDefault;
        }
        this.stepSpaceDP = dp;
        refreshLayout();
    }

    /**
     * 设置锚点尺寸
     *
     * @param dp
     */
    public void setPointWidth(float dp) {
        if (dp <= 0) {
            dp = pointWidthDefault;
        }
        this.pointWidthDP = dp;
        refreshLayout();
    }

    /**
     * 播放动画
     */
    public void playAnim() {
        this.isPlayAnim = true;
        this.isPlayAnimSecond = true;
        if (isAnimating || isAnimatingSecond) return;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        if (valueAnimatorSecond != null) {
            valueAnimatorSecond.start();
        }
    }

    public static class Data {

        public int value;
        public String time;


        public Data(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
}

//、、使用方法
//        List<LineChartView.Data> datas2 = new ArrayList<>();
//        for (int value : dataArr2) {
//        LineChartView.Data data = new LineChartView.Data(value);
//        datas2.add(data);
//        }
//
//        // x轴数据
//        List<String> datas3 = new ArrayList<>();
//        for (String value : dataArr3) {
//        datas3.add(value);
//        }
//        lineChartView.setData(datas,datas2,datas3);
//
//    public void pointModelToggle(View view) {
//        if (lineChartView != null) {
//            isCube = !isCube;
//            lineChartView.setCubePoint(isCube);
//        }
//    }
//   lineChartView.setStepSpace(progress);
//    public void doAnimation(View view) {
//        if (lineChartView != null) {
//            lineChartView.playAnim();
//        }
//    }
//private boolean isShowTable = false;
//
//    public void tableToggle(View view) {
//        if (lineChartView != null) {
//            isShowTable = !isShowTable;
//            lineChartView.setShowTable(isShowTable);
//        }
//    }
