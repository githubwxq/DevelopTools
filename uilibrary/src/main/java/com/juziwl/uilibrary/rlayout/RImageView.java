package com.juziwl.uilibrary.rlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.juziwl.uilibrary.rlayout.helper.RImageViewHelper;

/**
 * RImageView
 *
 * @author ZhongDaFeng
 */
public class RImageView extends AppCompatImageView {

    private RImageViewHelper mHelper;

    public RImageView(Context context) {
        this(context, null);
    }

    public RImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new RImageViewHelper(context, this, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mHelper.isNormal()) {
            super.onDraw(canvas);
        } else {
            mHelper.onDraw(canvas);
        }
    }

    public RImageViewHelper getHelper() {
        return mHelper;
    }


}
