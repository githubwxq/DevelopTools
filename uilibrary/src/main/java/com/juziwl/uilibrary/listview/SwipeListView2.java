package com.juziwl.uilibrary.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.juziwl.uilibrary.R;


/**
 * Created by Administrator on 2016/9/22 0022.
 * @author 王晓清
 * @modify Neil
 */
public class SwipeListView2 extends ListView {
    private Boolean mIsHorizontal;
    private View mPreItemView;
    private View mCurrentItemView;
    private float mFirstX;
    private float mFirstY;
    private int mRightViewWidth;
    private static final int NUMBER_2 = 2;
    private static final int NUMBER_5 = 5;
    private static final int NUMBER_30 = 30;
    private final int mDuration = 100;
    private final int mDurationStep = 10;
    private boolean mIsShown;
    /**
     * 用来记录第一次按下坐标点,在整个滑动的过程中 只记录一次
     */
    private boolean isRecored = false;

    public SwipeListView2(Context context) {
        this(context, null);
    }
    public SwipeListView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SwipeListView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.common_swipelistviewstyle);
        // 获取自定义属性和默认值
        mRightViewWidth = (int) mTypedArray.getDimension(
                R.styleable.common_swipelistviewstyle_common_right_width, 300);
        mTypedArray.recycle();
    }

    /**
     * return true, deliver to listView. return false, deliver to child. if
     * move, return true
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float lastX = ev.getX();
        float lastY = ev.getY();
        int s = pointToPosition((int) lastX, (int) lastY);
        if(getAdapter().getItemViewType(s) == 0){
            hiddenRight(mCurrentItemView);
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsHorizontal = null;
                mFirstX = lastX;
                mFirstY = lastY;
                int motionPosition = pointToPosition((int) mFirstX, (int) mFirstY);
                if (motionPosition >=0) {
                    View currentItemView = getChildAt(motionPosition
                            - getFirstVisiblePosition());
                    mPreItemView = mCurrentItemView;
                    mCurrentItemView = currentItemView;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = lastX - mFirstX;
                float dy = lastY - mFirstY;

                if (Math.abs(dx) >= NUMBER_5 && Math.abs(dy) >= NUMBER_5) {
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsShown && (mPreItemView != mCurrentItemView || isHitCurItemLeft(lastX))) {
                    /**
                     * 情况一： 一个Item的右边布局已经显示， 这时候点击任意一个item, 那么那个右边布局显示的item隐藏其右边布局
                     */
                    hiddenRight(mPreItemView);
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isHitCurItemLeft(float x) {
        return x < getWidth() - mRightViewWidth;
    }

    /**
     * @param dx
     * @param dy
     * @return judge if can judge scroll direction
     */
    private boolean judgeScrollDirection(float dx, float dy) {
        boolean canJudge = true;
        if (Math.abs(dx) > NUMBER_30 && Math.abs(dx) > NUMBER_2 * Math.abs(dy)) {
            mIsHorizontal = true;
        } else if (Math.abs(dy) > NUMBER_30 && Math.abs(dy) > NUMBER_2 * Math.abs(dx)) {
            mIsHorizontal = false;
        } else {
            canJudge = false;
        }
        return canJudge;
    }

    /**
     * return false, can't move any direction. return true, cant't move
     * vertical, can move horizontal. return super.onTouchEvent(ev), can move
     * both.
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float lastX = ev.getX();
        float lastY = ev.getY();
        int ps = pointToPosition((int) lastX, (int) lastY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isRecored) {
                    isRecored = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isRecored) {
                    isRecored = true;
                }
                float dx = lastX - mFirstX;
                float dy = lastY - mFirstY;
                if (mIsHorizontal == null) {
                    if (!judgeScrollDirection(dx, dy)) {
                        break;
                    }
                }

                if (mIsHorizontal) {
                    if (mIsShown && mPreItemView != mCurrentItemView) {
                        /**
                         * 情况二： 一个Item的右边布局已经显示，
                         * 这时候左右滑动另外一个item,那个右边布局显示的item隐藏其右边布局
                         * 向左滑动只触发该情况，向右滑动还会触发情况五
                         */
                        hiddenRight(mPreItemView);
                    }

                    if (mIsShown && mPreItemView == mCurrentItemView) {
                        dx = dx - mRightViewWidth;
                    }
                    if (dx < 0 && dx > -mRightViewWidth) {
                        if(mCurrentItemView!=null&&ps!=1){
                            mCurrentItemView.scrollTo((int) (-dx), 0);
                        }
                    }

                    return true;
                } else {
                    if (mIsShown) {
                        /**
                         * 情况三： 一个Item的右边布局已经显示，
                         * 这时候上下滚动ListView,那么那个右边布局显示的item隐藏其右边布局
                         */
                        hiddenRight(mPreItemView);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                clearPressedState();
                if (mIsShown) {
                    /**
                     * 情况四： 一个Item的右边布局已经显示， 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
                     */
                    hiddenRight(mPreItemView);
                }
                if (mIsHorizontal != null && mIsHorizontal) {
                    if ((mFirstX - lastX > mRightViewWidth / NUMBER_2)) {
                        showRight(mCurrentItemView);
                    } else {
                        /**
                         * 情况五：
                         * <p>
                         * 向右滑动一个item,且滑动的距离超过了右边View的宽度的一半，隐藏之。
                         */
                        hiddenRight(mCurrentItemView);
                    }
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * HeadView的状态变化效果
     */

    private void clearPressedState() {
        if(mCurrentItemView!=null){
            mCurrentItemView.setPressed(false);
            setPressed(false);
            refreshDrawableState();
            dispatchSetPressed(true);
        }
    }

    private void showRight(View view) {

        if(view!=null){
            try {
                Message msg = new MoveHandler().obtainMessage();
                msg.obj = view;
                msg.arg1 = view.getScrollX();
                msg.arg2 = mRightViewWidth;
                msg.sendToTarget();
                mIsShown = true;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private void hiddenRight(View view) {
        if (mCurrentItemView == null) {
            return;
        }
        if(view!=null){
            Message msg = new MoveHandler().obtainMessage();
            msg.obj = view;
            msg.arg1 = view.getScrollX();
            msg.arg2 = 0;
            msg.sendToTarget();
            mIsShown = false;
        }
    }

    /**
     * show or hide right layout animation
     */
    @SuppressLint("HandlerLeak")
    class MoveHandler extends Handler {
        int stepX = 0;
        int fromX;
        int toX;
        View view;
        private boolean mIsInAnimation = false;

        private void animatioOver() {
            mIsInAnimation = false;
            stepX = 0;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (stepX == 0) {
                if (mIsInAnimation) {
                    return;
                }
                mIsInAnimation = true;
                view = (View) msg.obj;
                fromX = msg.arg1;
                toX = msg.arg2;
                stepX = (int) ((toX - fromX) * mDurationStep * 1.0 / mDuration);
                if (stepX < 0 && stepX > -1) {
                    stepX = -1;
                } else if (stepX > 0 && stepX < 1) {
                    stepX = 1;
                }
                if (Math.abs(toX - fromX) < mDurationStep) {
                    view.scrollTo(toX, 0);
                    animatioOver();
                    return;
                }
            }

            fromX += stepX;
            boolean isLastStep = (stepX > 0 && fromX > toX)
                    || (stepX < 0 && fromX < toX);
            if (isLastStep) {
                fromX = toX;
            }
            view.scrollTo(fromX, 0);
            invalidate();
            if (!isLastStep) {
                this.sendEmptyMessageDelayed(0, mDurationStep);
            } else {
                animatioOver();
            }
        }
    }

    public View getCurrentView() {
        return mCurrentItemView;
    }

    public int getRightViewWidth() {
        return mRightViewWidth;
    }

    public void setRightViewWidth(int mRightViewWidth) {
        this.mRightViewWidth = mRightViewWidth;
    }

    public void deleteItem(View v) {
        hiddenRight(v);
    }

}
