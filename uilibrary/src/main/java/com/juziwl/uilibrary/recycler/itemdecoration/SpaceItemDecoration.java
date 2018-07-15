package com.juziwl.uilibrary.recycler.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    //12dp
        private int space;  
  
        public SpaceItemDecoration(int space) {  
            this.space = space;  
        }  
  
        @Override  
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int itemCount = parent.getAdapter().getItemCount();
            int pos = parent.getChildAdapterPosition(view);
            outRect.bottom=space;

            if (pos %2==0) {
                outRect.right = space;
            } else {
                outRect.right = 0;
            }


             }

    }