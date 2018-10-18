package com.juziwl.uilibrary.recycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author nat.xue
 * @date 2017/11/1
 * @description
 */

public class ScoreLinearLayoutManager extends LinearLayoutManager{
    public ScoreLinearLayoutManager(Context context) {
        super(context);
    }

    //防止ScrollerView嵌套RecycleView，垂直滑动卡顿
    @Override
    public boolean canScrollVertically() {
        return false;
    }

    //防止ScrollerView嵌套RecycleView，水平滑动卡顿
    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}
