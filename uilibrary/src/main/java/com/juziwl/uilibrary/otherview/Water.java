//package com.juziwl.uilibrary.otherview;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PathMeasure;
//import android.support.v4.content.ContextCompat;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.juziwl.commonlibrary.utils.DisplayUtils;
//import com.juziwl.uilibrary.R;
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/11/21
// * @description 配合字母侧边栏一起显示的
// */
//public class Water extends View {
//    private Paint paint;
//    private Path path;
//    private int height = 0;
//    private float textBaseline = -1;
//    private String text = "";
//    private int textSize = 0;
//    private int bgColor;
//
//    public Water(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    private void init(Context context) {
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.FILL);
//        path = new Path();
//        textSize = DisplayUtils.sp2px(22);
//        bgColor = ContextCompat.getColor(context, R.color.common_grey_cccccc);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        paint.setColor(bgColor);
//        canvas.drawPath(path, paint);
//        paint.setTextSize(textSize);
//        paint.setColor(Color.WHITE);
//        paint.setTextAlign(Paint.Align.CENTER);
//        if (textBaseline == -1) {
//            Paint.FontMetrics metrics = paint.getFontMetrics();
//            textBaseline = height / 2 - (metrics.bottom - metrics.top) / 2 - metrics.top;
//        }
//        canvas.drawText(text, height / 2f, textBaseline, paint);
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        if (height == 0) {
//            height = h;
//            path.addCircle(height / 2, height / 2, height / 2, Path.Direction.CW);
//            PathMeasure measure = new PathMeasure(path, false);
//            float[] coords = new float[2];
//            float[] tans = new float[2];
//            measure.getPosTan(measure.getLength() * 7 / 8, coords, tans);
//            float s = (float) (height / 2 / Math.sin(Math.atan2(tans[1], tans[0])));
//            path.moveTo(coords[0], coords[1]);
//            path.lineTo(s + height / 2, height / 2);
//            path.lineTo(coords[0], height - coords[1]);
//            path.close();
//        }
//    }
//
//    public void setText(String text) {
//        this.text = text;
//        postInvalidate();
//    }
//}
