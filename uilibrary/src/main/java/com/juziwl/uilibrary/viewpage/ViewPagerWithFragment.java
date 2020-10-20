package com.juziwl.uilibrary.viewpage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FrameMetrics;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;

import java.util.List;

/**
 * @author Army
 * @description 直接集成fragment的viewpage
 */
public class ViewPagerWithFragment extends ViewPager {
    /**
     * true 代表不能滑动 false 代表能滑动
     */
    private boolean noScroll = true;

    public ViewPagerWithFragment(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ViewPagerWithFragment(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }

    }

    @Override
    public void setCurrentItem(int item) {
        //表示切换的时候，不需要切换时间。
        super.setCurrentItem(item, false);
    }

    List<Fragment> fragments;

    /**
     * 设置fragment 集合
      */
    public void  setFragmentList(AppCompatActivity context, List<Fragment> fragments,OnPageChangeListener listener){
        this.fragments=fragments;
        setOffscreenPageLimit(fragments.size());
        setAdapter(new BaseFragmentAdapter(context.getSupportFragmentManager(),fragments));
        addOnPageChangeListener(listener);
    }
    /**
     * 设置fragment 集合
     */
    public void  setFragmentList(AppCompatActivity context, List<Fragment> fragments, List<String> titles,OnPageChangeListener listener){
        this.fragments=fragments;
        setOffscreenPageLimit(fragments.size());
        setAdapter(new BaseFragmentAdapter(context.getSupportFragmentManager(),fragments,titles));
        addOnPageChangeListener(listener);
    }


}