package com.juziwl.uilibrary.basebanner.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.basebanner.callback.Indicator;

public class NormalIndicator extends LinearLayout implements Indicator {

    private static final String TAG = "zsr";

    /**
     * normal and logic
     */
    private Context mContext;

//    private int mLastPosition;

    private int mCount = 0;

    /**
     * attrs
     */
    private int mSelector;

    private int mLeftMargin;

    public NormalIndicator(Context context) {
        this(context, null);
    }

    public NormalIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NormalIndicator);
        mSelector = ta.getResourceId(R.styleable.NormalIndicator_normal_selector,
                R.drawable.select_banner_rectangle);
        mLeftMargin = (int) ta.getDimension(R.styleable.NormalIndicator_normal_leftmargin, 15);
        setGravity(Gravity.CENTER);
        ta.recycle();
    }

    /**
     * 用于viewpager 滑动时，底部圆点的状态显示
     *
     * @param position
     */
    @Override
    public void setSelect(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setSelected(false);
            getChildAt(i).requestLayout();
        }
        View currentView = getChildAt(position);
        if (currentView != null) {
            currentView.setSelected(true);
            currentView.requestLayout();
        }
    }

    @Override
    public void setTotalSize(int totalSize,boolean isFirstInit) {
        //移除所有控件
        removeAllViews();
        //这里加小圆点
        LayoutParams params = new
                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(mLeftMargin, 0, 0, 0);
        for (int i = 0; i < totalSize; i++) {
            ImageView imageView = new ImageView(mContext);
            if (isFirstInit) {
                if (i == 0) {
                    imageView.setSelected(true);
                } else {
                    imageView.setSelected(false);
                }
            }
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mSelector);
            addView(imageView);
        }
    }
}
