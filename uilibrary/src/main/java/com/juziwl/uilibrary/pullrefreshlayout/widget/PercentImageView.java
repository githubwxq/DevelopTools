package com.juziwl.uilibrary.pullrefreshlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.juziwl.uilibrary.R;


/**
 * Created by yan on 2017/9/30 .
 */

public class PercentImageView extends AppCompatImageView {
    private float ratio;// h/w base on w

    public PercentImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentImageView);
        ratio = ta.getFloat(R.styleable.PercentImageView_ratio, -1);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio != -1) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, (int) (ratio * width));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
