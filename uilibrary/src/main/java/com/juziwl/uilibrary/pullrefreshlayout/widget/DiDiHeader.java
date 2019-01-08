package com.juziwl.uilibrary.pullrefreshlayout.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.pullrefreshlayout.PullRefreshLayout;
import com.juziwl.uilibrary.pullrefreshlayout.ShowGravity;


/**
 * Created by Administrator on 2017/10/1 0001.
 */

public class DiDiHeader extends FrameLayout implements PullRefreshLayout.OnPullListener, NestedScrollView.OnScrollChangeListener {
    private PullRefreshLayout prl;
    private ClassicsHeader loadingView;
    private View fixedHeader;
    private boolean isNeedDispatchTouchEvent;

    public DiDiHeader(Context context, final PullRefreshLayout prl) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -1));
        this.prl = prl;
        prl.setHeaderFront(false);
        prl.setHeaderShowGravity(ShowGravity.PLACEHOLDER);
        scrollInit();
        LayoutInflater.from(context).inflate(R.layout.didi_header, this, true);
        fixedHeader = findViewById(R.id.fixed_top);
        fixedHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext().getApplicationContext(), "you touch the header", Toast.LENGTH_SHORT).show();
            }
        });
        loadingView = (ClassicsHeader) findViewById(R.id.loading);
    }

    private void scrollInit() {
        post(new Runnable() {
            @Override
            public void run() {
                View target = prl.getTargetView();
                prl.setRefreshTriggerDistance(loadingView.getHeight());
                target.setOverScrollMode(OVER_SCROLL_NEVER);
                target.setPadding(target.getPaddingLeft()
                        , fixedHeader.getHeight()
                        , target.getPaddingRight()
                        , target.getPaddingBottom());
                ((ViewGroup) target).setClipToPadding(false);

                target.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                            isNeedDispatchTouchEvent = true;//这里重置是否需要分发事件的标志位
                        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE && prl.isDragVertical()) {
                            isNeedDispatchTouchEvent = false;//如果prl处在纵向拖动，则取消header的事件分发
                        }
                        if (isNeedDispatchTouchEvent) {
                            DiDiHeader.this.dispatchTouchEvent(event);
                        }
                        return false;
                    }
                });
                if (target instanceof NestedScrollView) {
                    ((NestedScrollView) target).setOnScrollChangeListener(DiDiHeader.this);
                } else if (target instanceof RecyclerView) {
                    ((RecyclerView) target).addOnScrollListener(getRvOnScrollListener());
                    ((RecyclerView) target).scrollToPosition(0);
                }
            }
        });
    }

    @Override
    public void onPullChange(float percent) {
        loadingView.setTranslationY(prl.getMoveDistance());
    }

    @Override
    public void onPullHoldTrigger() {
        loadingView.onPullHoldTrigger();
    }

    @Override
    public void onPullHoldUnTrigger() {
        loadingView.onPullHoldUnTrigger();
    }

    @Override
    public void onPullHolding() {
        loadingView.onPullHolding();
    }

    @Override
    public void onPullFinish(boolean flag) {
        loadingView.onPullFinish(false);
    }

    @Override
    public void onPullReset() {
        loadingView.onPullReset();
    }

    private RecyclerView.OnScrollListener getRvOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);
                float offset = viewHolder != null && (prl.isTargetAbleScrollDown() || prl.isTargetAbleScrollUp()) ? viewHolder.itemView.getTop() : 0;
                getChildAt(0).setTranslationY(-fixedHeader.getHeight() + Math.max(0, offset));
            }
        };
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        getChildAt(0).setTranslationY(-scrollY);//scrollTo() 有明显的延迟,改为移动子view
    }
}
