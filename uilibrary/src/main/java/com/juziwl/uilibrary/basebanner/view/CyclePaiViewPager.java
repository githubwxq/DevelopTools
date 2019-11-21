package com.juziwl.uilibrary.basebanner.view;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.view.ViewPager;

public class CyclePaiViewPager extends ViewPager  implements View.OnTouchListener, LifecycleObserver {

    private int mLoopTime = 3000;
    private InnerPagerAdapter mAdapter;
    private int mItemCount = 0;
    private int mCurrentPosition = 1;  //当前 由于添加了两个数据   前后连个
    private int pagerType = 1;
    private boolean isLooping=true;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            CyclePaiViewPager.this.setCurrentItem(mCurrentPosition);
            if (mCurrentPosition < mItemCount) {
                mCurrentPosition++;
            } else {
                mCurrentPosition = 1;
            }
            if (isLooping&&isDataConfigFinish) {

                loopHandler.postDelayed(runnable, mLoopTime);
            }
        }
    };
    private Handler loopHandler = new Handler();
    private boolean isDataConfigFinish=true;

    public CyclePaiViewPager(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public CyclePaiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public void setDragLayout(DragLayout dl) {
//        if (dl!=null){
//            this.dragLayout = dl;
//        }
//    }

    /**
     * 设置viewpager的类型
     *
     * @param type 0 -- 普通类型
     *             1 -- 循环滚动
     */
    public void setPagerType(int type) {
        pagerType = type;
    }

    public void startLoop() {
        if (mItemCount > 1) {
            isLooping=true;
            loopHandler.removeCallbacks(runnable);
            loopHandler.postDelayed(runnable, mLoopTime);
        }
    }

    public void stopLoop(){
        isLooping=false;
    }


    public void setItemCount(int count) {
        this.mItemCount = count;
    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        this.mItemCount = arg0.getCount();


        switch (pagerType) {
            case 1:
                mAdapter = new InnerPagerAdapter(arg0);
                super.setAdapter(mAdapter);
                setCurrentItem(1);
                break;
            case 0:
                super.setAdapter(arg0);
                break;
        }
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        switch (pagerType) {
            case 1:
                super.addOnPageChangeListener(new InnerOnPageChangeListener(listener));
                break;
            case 0:
                super.addOnPageChangeListener(listener);
                break;
        }
    }



    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int position;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (null != listener) {
                listener.onPageScrollStateChanged(arg0);
            }
            if (arg0 == ViewPager.SCROLL_STATE_IDLE) {
                if (position == mAdapter.getCount() - 1) {   //最后一项 滑动到 第二项（实际第一项）
                    setCurrentItem(1, false);
                } else if (position == 0) {    //第一项（看不见的） 滑动到最后第二项（实际第后项）
                    setCurrentItem(mAdapter.getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (null != listener) {
                listener.onPageScrolled(arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if (arg0 != mCurrentPosition) {
                mCurrentPosition = arg0;
                loopHandler.postDelayed(runnable, mLoopTime);
            }
            if (null != listener) {
                listener.onPageSelected(arg0);
            }
        }
    }


    private class InnerPagerAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
            adapter.registerDataSetObserver(new DataSetObserver() {

                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        public int getCount() {
            return adapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return adapter.isViewFromObject(arg0, arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                position = adapter.getCount() - 1;
            } else if (position == adapter.getCount() + 1) {
                position = 0;
            } else {
                position -= 1;
            }
            return adapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem(container, position, object);
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        loopHandler.removeCallbacks(runnable);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDataConfigFinish = false;
                break;
            case MotionEvent.ACTION_UP:
                isDataConfigFinish = true;
                if (pagerType==1) {
                    loopHandler.postDelayed(runnable, mLoopTime);
                }
                break;

            default:
                break;
        }
        return false;
    }


    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(runnable);
        super.onDetachedFromWindow();
    }



}
