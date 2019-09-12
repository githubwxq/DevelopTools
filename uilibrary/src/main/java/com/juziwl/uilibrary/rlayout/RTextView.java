package com.juziwl.uilibrary.rlayout;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.juziwl.uilibrary.rlayout.helper.RTextViewHelper;
import com.juziwl.uilibrary.rlayout.iface.RHelper;
/**
 * RTextView
 *
 * @author ZhongDaFeng
 */
public class RTextView extends AppCompatTextView implements RHelper<RTextViewHelper> {

    private RTextViewHelper mHelper;

    public RTextView(Context context) {
        this(context, null);
    }

    public RTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new RTextViewHelper(context, this, attrs);
    }

    @Override
    public RTextViewHelper getHelper() {
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
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (mHelper != null) {
            mHelper.setSelected(selected);
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
