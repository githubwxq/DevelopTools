package com.juziwl.uilibrary.flowlayout;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NestedFlowLayoutRecyclerView extends RecyclerView {
    public NestedFlowLayoutRecyclerView(Context context) {
        super(context);
    }

    public NestedFlowLayoutRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedFlowLayoutRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        FlowLayoutManager layoutManager = (FlowLayoutManager) getLayoutManager();
        int widthMode = MeasureSpec.getMode(widthSpec);
        int measureWidth = MeasureSpec.getSize(widthSpec);
        int heightMode = MeasureSpec.getMode(heightSpec);
        int measureHeight = MeasureSpec.getSize(heightSpec);
        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            //以实际屏宽为标准
            width = getContext().getResources().getDisplayMetrics().widthPixels;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = measureHeight;
        } else {
            height = layoutManager.getTotalHeight() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);

    }
}
