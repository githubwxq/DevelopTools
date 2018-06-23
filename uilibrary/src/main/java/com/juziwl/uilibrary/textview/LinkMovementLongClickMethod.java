package com.juziwl.uilibrary.textview;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2018/5/7
 * @description 处理和长按时间的冲突问题
 */
public class LinkMovementLongClickMethod extends LinkMovementMethod {
    private long lastClickTime;

    public static LinkMovementLongClickMethod getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementLongClickMethod();
        }
        return sInstance;
    }

    private static LinkMovementLongClickMethod sInstance;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - lastClickTime < ViewConfiguration.getLongPressTimeout()) {
                        link[0].onClick(widget);
                    }
                } else {
                    lastClickTime = System.currentTimeMillis();
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return Touch.onTouchEvent(widget, buffer, event);
    }
}
