package com.juziwl.uilibrary.itemdecoration;

import android.content.Context;
import android.support.annotation.ColorInt;

/**
 * Created by Administrator on 2017/4/16.
 */
public class LinearItemDecoration extends BaseItemDecoration {
    public LinearItemDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB) {
        super(context, lineWidthDp, colorRGB, 0);
    }

    public LinearItemDecoration(Context context, int lineWidthDp, @ColorInt int colorRGB, int bottomMarginLeft) {
        super(context, lineWidthDp, colorRGB, bottomMarginLeft);
    }

    public LinearItemDecoration(Context context, float lineWidthDp, int colorRGB, int bottomMarginLeft, int[] positions) {
        super(context, lineWidthDp, colorRGB, bottomMarginLeft, positions);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        //顺时针顺序:left, top, right, bottom
        //默认只有bottom显示分割线
        return new boolean[]{false, false, false, true};
    }
}
