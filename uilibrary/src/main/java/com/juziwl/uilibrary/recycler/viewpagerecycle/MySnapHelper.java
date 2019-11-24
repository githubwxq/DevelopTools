package com.juziwl.uilibrary.recycler.viewpagerecycle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by 王晓清.
 * data 2018/9/5.
 * describe .
 * 当拖拽或滑动结束时会回调该方法，返回一个out = int[2]，out[0]x轴，out[1] y轴 ，这个值就是需要修正的你需要的位置的偏移量
 public abstract int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View targetView);
 上面方法有一个targetView吧 就是这个方法返回的
 public abstract View findSnapView(LayoutManager layoutManager);
 用于Fling，根据速度返回你要滑到的position
 public abstract int findTargetSnapPosition(LayoutManager layoutManager, int velocityX, int velocityY);
 *
 */

public class MySnapHelper extends LinearSnapHelper {

    private OrientationHelper mHorizontalHelper;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {

        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        Log.e("wxq","VVVVVVVVVVVVVVVV"+out[0]);

        out[1]=300;
        return out;

    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        Log.e("wxq","....................."+helper.getDecoratedStart(targetView) + helper.getStartAfterPadding());
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return findStartView(layoutManager, getHorizontalHelper(layoutManager));
    }


    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            Log.e("firstChild",firstChild+"");

            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }
            if (lastChild == layoutManager.getItemCount() - 1) {
                return layoutManager.findViewByPosition(lastChild);
            }

            View child = layoutManager.findViewByPosition(firstChild);

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                return layoutManager.findViewByPosition(firstChild + 1);
            }
        }

        //居中
        return super.findSnapView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        Log.e("wxq","findTargetSnapPosition"+super.findTargetSnapPosition(layoutManager, velocityX, velocityY));

        return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
    }
}
