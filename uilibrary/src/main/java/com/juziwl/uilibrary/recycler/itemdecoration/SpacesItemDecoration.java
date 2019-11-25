package com.juziwl.uilibrary.recycler.itemdecoration;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by 赵梦阳 on 2016/5/7.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        //注释这两行是为了上下间距相同
//        if(parent.getChildAdapterPosition(view)==0){
            outRect.top=space;
//        }
    }
}
//
//最后在说下我理解的等间距的原理
//        比如我想给间距设置成20
//那我们考虑到上面说的叠加 设置间距我只设置一半 就是10
//        在代码里在给recyclerview设置一个10的内边距
//这样就间距就都是20了