package com.juziwl.uilibrary;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * desc:自定义组合控件模板类
 * version:1.0
 */
public abstract class BaseGroupView  extends FrameLayout implements View.OnClickListener{
    public BaseGroupView( @NonNull Context context) {
        this(context,null);
    }

    public BaseGroupView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseGroupView( @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, getRescourseId(), this);
        initView(view);
    }

    protected abstract void initView(View view);

    public abstract int getRescourseId();

    @Override
    public void onClick(View v) {

    }
    public  int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
