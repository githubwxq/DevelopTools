package com.juziwl.uilibrary.tools.abslistview;

import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/5/22.
 */
public abstract class SwipeAdapter<T> extends CommonAdapter<T> {
    public SwipeAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);


    }

    public boolean getSwipEnableByPosition(int position) {
        return true;
    }

}
