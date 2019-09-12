package com.juziwl.uilibrary.rlayout;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.juziwl.uilibrary.rlayout.helper.RCheckHelper;
import com.juziwl.uilibrary.rlayout.iface.RHelper;


/**
 * RCheckBox
 *
 * @author ZhongDaFeng
 */
public class RCheckBox extends AppCompatCheckBox implements RHelper<RCheckHelper> {

    private RCheckHelper mHelper;

    public RCheckBox(Context context) {
        this(context, null);
    }

    public RCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new RCheckHelper(context, this, attrs);
    }

    @Override
    public RCheckHelper getHelper() {
        return mHelper;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mHelper != null) {
            mHelper.setEnabled(enabled);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mHelper != null) {
            mHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}
