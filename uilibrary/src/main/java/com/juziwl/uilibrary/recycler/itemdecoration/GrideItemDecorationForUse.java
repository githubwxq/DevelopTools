package com.juziwl.uilibrary.recycler.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**键盘的装饰器 设置间隔的
 * @author zhy
 */
public class GrideItemDecorationForUse extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    private int horizonWidth = 12;
    private int verticalWidth = 6;
    private  Context mContext;
//    android:layout_marginLeft="12dp"
//    android:layout_marginTop="6dp"



    public GrideItemDecorationForUse(int color) {
        mDivider = new ColorDrawable(color);
    }


    public GrideItemDecorationForUse(Context context) {
        this(Color.parseColor("#FFFFFF"));
        mContext=context;
        horizonWidth= dp2px(10,context);
        verticalWidth= dp2px(5,context);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + horizonWidth;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + verticalWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + horizonWidth;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
//        Log.e("liao", state.toString());
        boolean b = state.willRunPredictiveAnimations();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
//        if (isLastRaw(parent, itemPosition, spanCount, childCount))// 如果是最后一行，则不需要绘制底部
//        {
//            outRect.set(0, 0, lineWidth, 0);
//        }
//        else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
//        {
////            if (b){
////                outRect.set(0, 0, lineWidth, lineWidth);
////            }else {
//                outRect.set(0, 0, 0, lineWidth);
////            }
//        }
//        else {

//        if(itemPosition==12||itemPosition==13){
//            outRect.set(0, 0, 0, lineWidth);
//        }else {
            outRect.set(0, 0, horizonWidth, verticalWidth);
//        }


//        }
    }

    public    int dp2px(final float dpValue,Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}


//以下方法设置间隔也是可以的最外层matchparent 里面一层设置margin形成间距
//以下方法动态设置间距方便
//    CardView ll_inner = helper.getView(R.id.cardView_item_root);
//    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) ll_inner.getLayoutParams();
//                        if (position % 2 == 0) {
//                                //左边的item
//                                layoutParams.setMargins(DisplayUtils.dip2px(15), DisplayUtils.dip2px(5), DisplayUtils.dip2px(5), DisplayUtils.dip2px(5));
//                                } else {
//                                //右边的item
//                                layoutParams.setMargins(DisplayUtils.dip2px(5), DisplayUtils.dip2px(5), DisplayUtils.dip2px(15), DisplayUtils.dip2px(5));
//                                }
