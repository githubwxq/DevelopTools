package com.juziwl.uilibrary.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * @author wy
 * @version V_5.0.0
 * @date 2016/3/2
 * @description 应用程序Activity的模板类
 */
public class ScoreItemDecoration extends RecyclerView.ItemDecoration {
    private final int[] attrs = new int[]{android.R.attr.listDivider};
    private final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private final int VERTICAL = LinearLayoutManager.VERTICAL;
    private int orientation;
    private Drawable mDivider;

    public ScoreItemDecoration(Context context, int orientation) {
        TypedArray ta = context.obtainStyledAttributes(attrs);
        mDivider = ta.getDrawable(0);
        setOrientation(orientation);
        ta.recycle();
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
            int top = view.getBottom() + lp.bottomMargin;
            int bottom = top + 1;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        // 因为最后一个item不需要divider，所以
        // 一种方法是i<childCount-1，那在getItemOffsets里就不用判断是不是最后一个了
        // 还有一种办法，就是在getItemOffsets里判断是不是最后一行或最后一列，这样的话i<childCount
        // 是的话，就不执行outRect.set(...)方法
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getRight() + lp.rightMargin;
            int right = left + 1;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int pos = ((RecyclerView.LayoutParams) view.getLayoutParams())
                .getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();
        if (pos < childCount - 1) {
            if (orientation == HORIZONTAL) {
                outRect.set(0, 0, 1, 0);
            } else {
                outRect.set(0, 0, 0, 1);
            }
        }
    }
}
