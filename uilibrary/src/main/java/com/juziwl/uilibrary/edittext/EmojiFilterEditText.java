package com.juziwl.uilibrary.edittext;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.juziwl.uilibrary.utils.EmojiFilter;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/3/19
 * @description
 */
public class EmojiFilterEditText extends androidx.appcompat.widget.AppCompatEditText {
    public EmojiFilterEditText(Context context) {
        this(context, null);
    }

    public EmojiFilterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        InputFilter[] filters = getFilters();
        if (filters != null) {
            EmojiFilter emojiFilter = new EmojiFilter(context);
            InputFilter[] newFilters = new InputFilter[filters.length + 1];
            System.arraycopy(filters, 0, newFilters, 0, filters.length);
            newFilters[filters.length] = emojiFilter;
            setFilters(newFilters);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //请求所有父控件及祖宗控件不要拦截事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
