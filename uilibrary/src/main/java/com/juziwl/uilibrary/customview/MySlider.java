package com.juziwl.uilibrary.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/02/28
 * desc:自定义通讯录侧边栏
 * version:1.0
 */
public class MySlider extends CustomView {

    private int width;
    private int height;

    private float textWidth;
    private float textHeight;
    private Paint  mPaintText;
    private Paint  mPaintRed;
    private int index=-1;

    private String[] array={"#", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "G",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    //定义一个接口对象listerner
    private OnItemSelectListener listener;



    public MySlider(Context context) {
        super(context);
    }

    public MySlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        super.init();
        mPaintText = new Paint();
        mPaintText.setColor(Color.GRAY);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintRed = new Paint();
        mPaintRed.setColor(Color.BLUE);
        mPaintRed.setAntiAlias(true);
        mPaintRed.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
        textHeight = height/27f-2;
        mPaintText.setTextSize(textHeight);
        mPaintRed.setTextSize(textHeight);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textWidth =mPaintText.measureText("M");

        for(int i=0; i<26;i++ ){
            if(index==i){
                canvas.drawText(""+array[i], width-textWidth, height/27*(i+1), mPaintRed);
            }else {
                canvas.drawText(""+array[i], width-textWidth, height/27*(i+1), mPaintText);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                float x=event.getX();
                float y = event.getY();
                if(x>width-2*textWidth){
                    index = (int) (y/(height/27));
                    //此处有增加，当屏幕被点击后，将参数传入。
                    if(listener!=null){
                        listener.onItemSelect(index, array[index]);
                    }
                    invalidate();
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                index=-1;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }





    //获得接口对象的方法。
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }
    //定义一个接口
    public interface  OnItemSelectListener{
        public void onItemSelect(int index, String indexString);
    }
}



//<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        tools:context="com.example.administrator.mywidgetdemo.activity.BitmapActivity">
//
//<com.example.administrator.mywidgetdemo.event.MySlider
//        android:id="@+id/mysilider"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent" />
//<TextView
//        android:id="@+id/textview"
//                android:layout_width="200dp"
//                android:layout_height="200dp"
//                android:textSize="100sp"
//                android:text="A"
//                android:layout_centerInParent="true"
//                android:gravity="center"
//                />
//</RelativeLayout>