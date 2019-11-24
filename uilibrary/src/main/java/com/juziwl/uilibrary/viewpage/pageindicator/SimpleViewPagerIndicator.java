package com.juziwl.uilibrary.viewpage.pageindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class SimpleViewPagerIndicator extends LinearLayout
{
 
    private static final int COLOR_TEXT_NORMAL = 0xFF999999;
 
    private static final int COLOR_INDICATOR = Color.BLACK;
 
    private String[] mTitles;
 
    private int mTabCount;
 
    private int mIndicatorColor = COLOR_INDICATOR;
 
    private float mTranslationX;
 
    private Paint mPaint = new Paint();
 
    private int mTabWidth;
 
    private int oldPosition = 0;
 
    private ViewPager viewPager;
 
    public SimpleViewPagerIndicator(Context context)
    {
        this(context, null);
    }
 
    public SimpleViewPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(10.0F);
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }
 
    public void setTitles(String[] titles)
    {
        mTitles = titles;
        mTabCount = titles.length;
    }
 
    public void setIndicatorColor(int indicatorColor)
    {
        this.mIndicatorColor = indicatorColor;
    }
 
    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }
 
    public void setViewPager(ViewPager viewPager)
    {
 
        if (viewPager instanceof ViewPager)
        {
            this.viewPager = viewPager;
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageSelected(int position)
                {
                    View v = getChildAt(position);
                    if (v instanceof TextView)
                    {
                    	TextView oldTv = (TextView) getChildAt(oldPosition);
                    	oldTv.setTextColor(COLOR_TEXT_NORMAL);
                    	
                        TextView tv = (TextView) v;
                        tv.setTextColor(COLOR_INDICATOR);
 
                        oldPosition = position;
                    }
                    if (onPageChangeListener != null)
                    {
                        onPageChangeListener.onPageSelected(position);
                    }
                }
 
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                {
                    scroll(position, positionOffset);
                }
 
                @Override
                public void onPageScrollStateChanged(int state)
                {
 
                }
            });
        }
 
    }
 
    public void scroll(int position, float offset)
    {
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }
 
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return super.dispatchTouchEvent(ev);
    }
 
    private void generateIndicators()
    {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;
 
        setWeightSum(count);
        for (int i = 0; i < count; i++)
        {
            TextView tv = new TextView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setTag(i);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(COLOR_TEXT_NORMAL);
            tv.setText(mTitles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setLayoutParams(lp);
            tv.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = (Integer) v.getTag();
                    if (viewPager != null)
                    {
                        viewPager.setCurrentItem(position);
                    }
                    if(onItemClickListener!=null){
                    	onItemClickListener.onItemClick(v, position);
                    }
                }
            });
            addView(tv);
        }
    }
 
    private OnPageChangeListener onPageChangeListener;
 
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener)
    {
        this.onPageChangeListener = onPageChangeListener;
    }
 
    public interface OnPageChangeListener
    {
        public void onPageSelected(int position);
    }
 
    
    private OnItemClickListener onItemClickListener;
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
 
	public interface OnItemClickListener
    {
        public void onItemClick(View view,int position);
    }
    /**
     * 选中当前页 。此方法一定要在setTitles()，setViewPager()之后调用！
     * 
     * @param position
     */
    public void setCurrentItem(int position)
    {
        oldPosition = position;
        generateIndicators();
        if (viewPager != null)
        {
            viewPager.setCurrentItem(position);
        }
    }
 
    
}