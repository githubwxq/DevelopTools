package com.juziwl.uilibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/03
 * desc:
 * version:1.0
 */
public class BaseGroupView  extends FrameLayout implements View.OnClickListener{
    public BaseGroupView( @NonNull Context context) {
        super(context);
    }

    public BaseGroupView( @NonNull Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseGroupView( @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void onClick(View v) {

    }
}
