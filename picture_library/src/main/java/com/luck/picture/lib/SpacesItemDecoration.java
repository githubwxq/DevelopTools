package com.luck.picture.lib;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 右边有 最后一项没有 上面的间距  处理右面的间距
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int span;  //列数

    // 右边间距
    public SpacesItemDecoration(int space, int span) {
        this.space = space;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) <= span - 1) { //第一行上边距不需要
            outRect.top = 0;
        } else {
            outRect.top = (int) (parent.getResources().getDisplayMetrics().density * space + 0.5f);
        }

        if ((parent.getChildAdapterPosition(view) + 1) % span == 0) { //列的倍数右边距不需要
            outRect.right = 0;
        } else {
            outRect.right = (int) (parent.getResources().getDisplayMetrics().density * space + 0.5f);
        }


    }
}
//
//最后在说下我理解的等间距的原理
//        比如我想给间距设置成20
//那我们考虑到上面说的叠加 设置间距我只设置一半 就是10
//        在代码里在给recyclerview设置一个10的内边距
//这样就间距就都是20了