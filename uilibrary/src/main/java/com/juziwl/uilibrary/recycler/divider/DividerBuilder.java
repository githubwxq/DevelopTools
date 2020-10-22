package com.juziwl.uilibrary.recycler.divider;

import androidx.annotation.ColorInt;

/**
 * Created by mac on 2017/5/17.
 */

public class DividerBuilder {

    private SideLine leftSideLine;
    private SideLine topSideLine;
    private SideLine rightSideLine;
    private SideLine bottomSideLine;


    public DividerBuilder setLeftSideLine(@ColorInt int color, int size, int startPadding, int endPadding) {
        this.leftSideLine = new SideLine(color, size, startPadding, endPadding);
        return this;
    }

    public DividerBuilder setTopSideLine(@ColorInt int color, int size, int startPadding, int endPadding) {
        this.topSideLine = new SideLine(color, size, startPadding, endPadding);
        return this;
    }

    public DividerBuilder setRightSideLine(@ColorInt int color, int size, int startPadding, int endPadding) {
        this.rightSideLine = new SideLine(color, size, startPadding, endPadding);
        return this;
    }

    public DividerBuilder setBottomSideLine(@ColorInt int color, int size, int startPadding, int endPadding) {
        this.bottomSideLine = new SideLine(color, size, startPadding, endPadding);
        return this;
    }


    public Divider create() {
        //提供一个默认不显示的sideline，防止空指针
        SideLine defaultSideLine = new SideLine(false, 0xff666666, 0, 0, 0);

        leftSideLine = (leftSideLine != null ? leftSideLine : defaultSideLine);
        topSideLine = (topSideLine != null ? topSideLine : defaultSideLine);
        rightSideLine = (rightSideLine != null ? rightSideLine : defaultSideLine);
        bottomSideLine = (bottomSideLine != null ? bottomSideLine : defaultSideLine);

        return new Divider(leftSideLine, topSideLine, rightSideLine, bottomSideLine);
    }


}



