package com.juziwl.uilibrary.rlayout;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.juziwl.uilibrary.rlayout.helper.RTextViewHelper;
import com.juziwl.uilibrary.rlayout.iface.RHelper;

/**
 * RButton
 *
 * @author ZhongDaFeng
 */
public class RButton extends AppCompatButton implements RHelper<RTextViewHelper> {

    private RTextViewHelper mHelper;

    public RButton(Context context) {
        this(context, null);
    }

    public RButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new RTextViewHelper(context, this, attrs);
    }

    @Override
    public RTextViewHelper getHelper() {
        return mHelper;
    }

}
