package com.juziwl.uilibrary.imageview;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SuperImageView extends AppCompatImageView {

    //常用配置提前处理了 列如设置centercrop
    private void initView() {
        setScaleType(ScaleType.CENTER_CROP);


    }



    public SuperImageView(Context context) {
        super(context);
        initView();
    }

    public SuperImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public SuperImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
