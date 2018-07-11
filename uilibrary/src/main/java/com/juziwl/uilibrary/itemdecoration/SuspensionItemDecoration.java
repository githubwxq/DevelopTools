package com.juziwl.uilibrary.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import com.juziwl.uilibrary.itemdecoration.listener.OnSuspensionListener;
import com.juziwl.uilibrary.utils.ConvertUtils;


/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/08/21
 * @description 配合recyclerview的悬浮的头
 */
public class SuspensionItemDecoration extends RecyclerView.ItemDecoration {

    private int headerCount;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private OnSuspensionListener onSuspensionListener = null;

    private int backgroundColor = Color.parseColor("#f7f7f9");
    private int textColor = Color.parseColor("#999999");

    public SuspensionItemDecoration(int headerCount) {
        this.headerCount = headerCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        if (TextUtils.isEmpty(getGroupName(pos))) {
            return;
        }
        if (isShowCityName(pos)) {
            outRect.top = onSuspensionListener.getSuspensionHeight();
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        float left = (parent.getLeft() + parent.getPaddingLeft());
        float right = (parent.getRight() - parent.getPaddingRight());
        String preCityName = null;
        String currentCityName = null;
        for (int index = 0; index < childCount; index++) {
            View view = parent.getChildAt(index);
            int position = parent.getChildAdapterPosition(view);
            if (position < headerCount) {
                continue;
            }
            preCityName = currentCityName;
            currentCityName = getGroupName(position);
            if (currentCityName.equals("") || currentCityName.equals(preCityName)) {
                continue;
            }
            int bottom;
            int groupHeight = 0;
            groupHeight = onSuspensionListener.getSuspensionHeight();
            bottom = Math.max(groupHeight, view.getTop());

            if (position + 1 < itemCount) {
                String nextGroupName = getGroupName(position + 1);
                int viewBottom = view.getBottom();
                if (!nextGroupName.equals(currentCityName) && viewBottom < bottom) {
                    bottom = viewBottom;
                }
            }
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);
            c.drawRect(left, (bottom - groupHeight), right, bottom, paint);
            paint.setColor(textColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextSize(12);
            Paint.FontMetrics fm = paint.getFontMetrics();
            float baseLine = bottom - (groupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(currentCityName, left + ConvertUtils.dp2px(12,parent.getContext()), baseLine + ConvertUtils.dp2px(5,parent.getContext()), paint);
        }
    }

    private boolean isShowCityName(int pos) {
        if (pos < headerCount) {
            return false;
        }
        if (pos == headerCount) {
            return true;
        }
        String currentCityName = getGroupName(pos);
        String preCityName = getGroupName(pos - 1);
        return !currentCityName.equals(preCityName);
    }

    private String getGroupName(int pos) {
        if (pos < headerCount) {
            return "";
        } else {
            return onSuspensionListener.getSuspensionName(pos - headerCount);
        }
    }

    public void setOnSuspensionListener(OnSuspensionListener onSuspensionListener) {
        this.onSuspensionListener = onSuspensionListener;
        int suspensionBackgroundColor = onSuspensionListener.getSuspensionBackgroundColor();
        if (suspensionBackgroundColor != 0) {
            backgroundColor = suspensionBackgroundColor;
        }
        int textColor = onSuspensionListener.getTextColor();
        if (textColor != 0) {
            this.textColor = textColor;
        }
    }
}
