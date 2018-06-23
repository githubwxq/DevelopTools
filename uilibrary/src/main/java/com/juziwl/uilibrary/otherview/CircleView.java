//package com.juziwl.uilibrary.otherview;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.AnimatorListenerAdapter;
//import com.nineoldandroids.animation.ValueAnimator;
//
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/05/25
// * @description
// */
//public class CircleView extends View {
//
//    private int width = 0, height = 0;
//    private Path path;
//    private Paint paint;
//    private float radio = 0.77f;
//
//    /**
//     * 圆圈半径
//     */
//    private int radius;
//    /**
//     * 画笔宽度
//     */
//    private int paintWidth;
//
//
//    public int centx;
//    public int centy;
//
//    private int maxradius;
//
//
//    public CircleView(Context context) {
//        this(context, null);
//    }
//
//    public CircleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private void init() {
////        path = new Path();
////        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
////        paint.setColor(getResources().getColor(com.juziwl.commonlibrary.R.color.color_00af5b));
////        paint.setStyle(Paint.Style.FILL);
//        centx = DisplayUtils.dip2px(22);
//        centy = DisplayUtils.dip2px(22);
//        radius = DisplayUtils.dip2px(19);
//        maxradius = DisplayUtils.dip2px(20);
//
//
//        paintWidth = DisplayUtils.dip2px(3);
//        paint = new Paint();
//        paint.setStrokeWidth(paintWidth);
//        //抗锯齿功能
//        paint.setAntiAlias(true);
//        //设置画笔颜色
//        paint.setColor(Color.WHITE);
//        //255
//        paint.setAlpha(currentAlpha);
//        //设置填充样
//        paint.setStyle(Paint.Style.STROKE);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
////        if (width != 0) {
////            path.reset();
////            path.lineTo(0, height * radio);
////            path.cubicTo(0, height * radio, width / 2f, height * 1.2f, width, height * radio);
////            path.lineTo(width, 0);
////            path.close();
////            canvas.drawPath(path, paint);
////        }
//
//        //设置画笔基本属性
//        canvas.drawCircle(centx, centy, radius, paint);
//
//
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        if (width == 0 && height == 0) {
//            width = w;
//            height = h;
//        }
//    }
//
//    int currentAlpha;
//
//
//    public void startAnimation() {
//        ValueAnimator stretchAnim1 = ValueAnimator.ofInt(radius / 4, radius);
//        stretchAnim1.setDuration(300);
//        stretchAnim1.setTarget(this);
//        stretchAnim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Integer integer = (Integer) animation.getAnimatedValue();
//                Integer myradius = radius;
//
//                Float alpha = (Float.valueOf(integer.toString()) / Float.valueOf(maxradius * 1.0f));
//                currentAlpha = (int) ((1 - alpha) * 255);
//                radius = integer;
//
////                alpha
//                //255
//                paint.setAlpha(currentAlpha);
////               CircleView.this.setAlpha(alpha);
//                invalidate();
//
//            }
//        });
//        stretchAnim1.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
////                CircleView.this.setVisibility(GONE);
////                animationEndListener.animationEnd();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                CircleView.this.setVisibility(VISIBLE);
//
//            }
//        });
//        stretchAnim1.start();
//
//    }
//
//    public void addAnimationEndListener(AnimationEndListener animationEndListener) {
//        this.animationEndListener = animationEndListener;
//    }
//
//    public AnimationEndListener animationEndListener;
//
//    public interface AnimationEndListener {
//
//        /**
//         * 动画执行结束
//         */
//        void animationEnd();
//
//    }
//
//
//}
