package com.juziwl.uilibrary.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wxq.commonlibrary.util.ConvertUtils;


/**
 * 右边有 最后一项没有
 */
public class RightSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public RightSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) { //第一项左边的间距不需要
            outRect.left = 0;
        } else {
            outRect.left = ConvertUtils.dp2px(space);
        }
    }
}
//
//最后在说下我理解的等间距的原理
//        比如我想给间距设置成20
//那我们考虑到上面说的叠加 设置间距我只设置一半 就是10
//        在代码里在给recyclerview设置一个10的内边距
//这样就间距就都是20了