package com.juziwl.uilibrary.pullrefreshlayout.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.dialog.ProgressDrawable;

/**
 * Created by yan on 2017/7/4.
 */

public class FootView2 extends PullRefreshView {


    protected TextView tv_tip,tv_complete;
    protected ImageView loading_view;
//        private AVLoadingIndicatorView loadingView;
//        protected FrameLayout rlContainer;

    private boolean isStateFinish;
    private boolean isHolding;

//        public FootView(@NonNull Context context, @Nullable AttributeSet attrs) {
//            super(context, attrs);
//            LayoutInflater.from(getContext()).inflate(contentView(), this, true);
//            initView();
//        }
//
//        public FootView(Context context, String animationName) {
//            this(context);
//            LayoutInflater.from(getContext()).inflate(contentView(), this, true);
//            initView();
//        }
//
//        public FootView(Context context, String animationName, int color) {
//            this(context);
//            LayoutInflater.from(getContext()).inflate(contentView(), this, true);
//            initView();
//        }

    public FootView2(Context context) {
        super(context);
//            LayoutInflater.from(getContext()).inflate(contentView(), this, true);
//            initView();

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//            loadingView.smoothToHide();
//            loadingView.clearAnimation();
    }

    @Override
    protected int contentView() {
        return R.layout.foot2_view;
    }

    @Override
    protected void initView() {
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        loading_view = (ImageView) findViewById(R.id.loading_view);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
    }

    @Override
    public void onPullChange(float percent) {
        super.onPullChange(percent);
        if (isStateFinish || isHolding) {
            return;
        }


        percent = Math.abs(percent);
        if (percent > 0.5 && percent < 1) {
//                if (loadingView.getVisibility() != VISIBLE) {
//                    loadingView.smoothToShow();
//                }
//                if (percent < 1) {
//                    loadingView.setScaleX(percent);
//                    loadingView.setScaleY(percent);
//                }
//                tv_tip.setText("可以松开手啦");
        } else {
//                tv_tip.setText("可以松开手啦");

        }
//            else if (percent <= 0.2 && loadingView.getVisibility() == VISIBLE) {
////                loadingView.smoothToHide();
//            }
    }


    @Override
    public void onPullHoldTrigger() {
        super.onPullHoldTrigger();
        ObjectAnimator anim = ObjectAnimator.ofFloat(loading_view, "rotation", 0f, 180f);
        // 动画的持续时间，执行多久？
        anim.setDuration(200);
        anim.start();
        tv_tip.setText("可以松开手啦");
    }

    boolean isShangla;


    @Override
    public void onPullHoldUnTrigger() {
        super.onPullHoldUnTrigger();
        ObjectAnimator anim = ObjectAnimator.ofFloat(loading_view, "rotation", 180f, 0f);
        // 动画的持续时间，执行多久？
        anim.setDuration(200);
        anim.start();
        tv_tip.setText("上拉可以加载更多");
    }

    public void isShowComplete(boolean isShowComplete){
        if (isShowComplete){
            tv_complete.setVisibility(VISIBLE);
            tv_tip.setVisibility(GONE);
            loading_view.setVisibility(GONE);
        }else{
            tv_tip.setVisibility(VISIBLE);
            loading_view.setVisibility(VISIBLE);
            tv_complete.setVisibility(GONE);
        }

    }

    public void setCompleteText(String str){
        tv_complete.setText(str);
    }

    @Override
    public void onPullHolding() {
        super.onPullHolding();
        isHolding = true;
        tv_tip.setText("正在加载中");
        loading_view.setImageDrawable(new ProgressDrawable());
        ((ProgressDrawable) loading_view.getDrawable()).start();
    }


    public void onPullFinish() {
        tv_tip.setText("上拉可以加载更多");
        loading_view.setImageResource(R.mipmap.jiantou);

    }

    @Override
    public void onPullReset() {
        super.onPullReset();
        tv_tip.setText("上拉可以加载更多");
        loading_view.setImageResource(R.mipmap.jiantou);
        isStateFinish = false;
        isHolding = false;

    }

    public void getRefreshState(final ValueAnimator resetHeaderAnimator) {

//        tv_tip.setText("加载完成");
//        isStateFinish = true;
        tv_tip.setText("正在加载中");
        loading_view.setImageDrawable(new ProgressDrawable());
        ((ProgressDrawable) loading_view.getDrawable()).start();


        postDelayed(new Runnable() {
            @Override
            public void run() {
                resetHeaderAnimator.start();
            }
        }, 200);
    }
}


//    // 刷新或加载过程中位置相刷新或加载触发位置的百分比，时刻调用
//    void onPullChange(float percent);
//    void onPullReset();// 数据重置调用
//    void onPullHoldTrigger();// 拖拽超过触发位置调用
//    void onPullHoldUnTrigger();// 拖拽回到触发位置之前调用
//    void onPullHolding();      // 正在刷新
//    void onPullFinish();       // 刷新完成