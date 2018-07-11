package com.juziwl.uilibrary.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by wxq on 2018/7/12.
 */

public class ConvertUtils {

    public  static  int dp2px(final float dpValue,Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
