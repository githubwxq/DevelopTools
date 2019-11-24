package com.juziwl.uilibrary.recycler;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshInternal;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

public class MyLoadingFoot extends InternalAbstract implements RefreshInternal  , RefreshFooter {

    protected MyLoadingFoot(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        initView(context);
    }

    ProgressBar loading_progress;
    TextView loading_text;
    FrameLayout load_more_load_fail_view;

    private void initView(Context context) {
        View.inflate(context, R.layout.view_load_more, this);
        loading_progress = findViewById(R.id.loading_progress);
        loading_text = findViewById(R.id.loading_text);
        load_more_load_fail_view = findViewById(R.id.load_more_load_fail_view);

    }


    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (!mNoMoreData) {
            super.onStartAnimator(refreshLayout, height, maxDragHeight);
        }
    }

//    @Override
//    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
//        if (!mNoMoreData) {
//            loading_text.setText("加载结束");
//            return super.onFinish(layout, success);
//        }
//        return 0;
//    }

    /**
     * ClassicsFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override@Deprecated
    public void setPrimaryColors(@ColorInt int ... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }



    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;

            if (noMoreData) {
                loading_text.setText("没有更多数据了");
//                arrowView.setVisibility(GONE);
                loading_progress.setVisibility(GONE);
            } else {
                loading_text.setText("上拉加载更多");
                loading_progress.setVisibility(VISIBLE);
//                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (!mNoMoreData) {
            loading_text.setText(success ? "加载完成" :"加载失败");
            return super.onFinish(layout, success);
        }
        return 0;
    }

    protected boolean mNoMoreData = false;

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    loading_progress.setVisibility(VISIBLE);
                case PullUpToLoad:
                    loading_text.setText("上拉加载更多");
//                    arrowView.animate().rotation(180);
                    break;
                case Loading:
                case LoadReleased:
                    loading_progress.setVisibility(VISIBLE);
                    loading_text.setText("正在加载...");
                    break;
                case ReleaseToLoad:
                    loading_text.setText("释放立即加载");
//                    arrowView.animate().rotation(0);
                    break;
                case Refreshing:
                    loading_text.setText("正在刷新...");
                    loading_progress.setVisibility(GONE);
                    break;
            }
        }
    }
    //</editor-fold>
}
