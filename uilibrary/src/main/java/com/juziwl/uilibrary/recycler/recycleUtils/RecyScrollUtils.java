package com.juziwl.uilibrary.recycler.recycleUtils;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 王晓清
 * @version V_1.0.0
 * @date 2017/11/27
 * @description  recycleview 滑动距离的监听 以及计算
 */

public class RecyScrollUtils {

    /**
     * 还能向下滑动多少
     */
    private int getDistance(RecyclerView mRecyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        View firstVisibItem = mRecyclerView.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = mRecyclerView.getHeight();
        int itemHeight = firstVisibItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem);
        return (itemCount - firstItemPosition - 1)* itemHeight - recycleViewHeight+firstItemBottom;
    }

    /**
     * 已滑动的距离
     */
    private int getHasMoveDistance(RecyclerView mRecyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        View firstVisibItem = mRecyclerView.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = mRecyclerView.getHeight();
        int itemHeight = firstVisibItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem);
        return (firstItemPosition + 1)*itemHeight - firstItemBottom;
    }


    /**
     * 获取RecyclerView滚动位置
     */
    private int getCurrentPosition(RecyclerView mRecyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        View firstVisibItem = mRecyclerView.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recycleViewHeight = mRecyclerView.getHeight();
        int itemHeight = firstVisibItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibItem);
        return (itemCount - firstItemPosition - 1)* itemHeight - recycleViewHeight;
    }



    public int getCurrentViewIndex( LinearLayoutManager mLineManager) {
        int firstVisibleItem = mLineManager.findFirstVisibleItemPosition();
        int lastVisibleItem = mLineManager.findLastVisibleItemPosition();
        int currentIndex = firstVisibleItem;
        int lastHeight = 0;
        for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
            View view = mLineManager.getChildAt(i - firstVisibleItem);
            if (null == view) {
                continue;
            }
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Rect localRect = new Rect();
            view.getLocalVisibleRect(localRect);
            int showHeight = localRect.bottom - localRect.top;
            if (showHeight > lastHeight) {
                currentIndex = i;
                lastHeight = showHeight;
            }
        }

        if (currentIndex < 0) {
            currentIndex = 0;
        }
        return currentIndex;
    }
}
