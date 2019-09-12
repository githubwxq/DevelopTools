package com.juziwl.uilibrary.rlayout;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.juziwl.uilibrary.rlayout.helper.RCheckHelper;
import com.juziwl.uilibrary.rlayout.iface.RHelper;

/**
 * RRadioButton
 *
 * @author ZhongDaFeng
 */
public class RRadioButton extends AppCompatRadioButton implements RHelper<RCheckHelper> {

    private RCheckHelper mHelper;

    public RRadioButton(Context context) {
        this(context, null);
    }

    public RRadioButton(Context context, AttributeSet attrs) {
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
