package com.juziwl.uilibrary.videoview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {
    public MyVideoView(Context context) {
        this(context, null);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //widthMeasureSpec : 期望的宽度（可以理解为布局文件的宽度）
    //heightMeasureSpec : 期望的高度（可以理解为布局文件的高度）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取控件的宽度，手动进行测量
        //获取被父控件约束的宽度或者是高度
        //参数1：默认控件的宽/高
        //参数2：父控件约束的宽/高
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        this.setMeasuredDimension(width, height);
    }
}