package com.juziwl.uilibrary.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月23日
 * @description 不能滚动的GridView
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
