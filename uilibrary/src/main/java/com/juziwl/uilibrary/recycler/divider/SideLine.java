package com.juziwl.uilibrary.recycler.divider;
import androidx.annotation.ColorInt;

/**
 * Created by mac on 2017/5/17.
 */

public class SideLine {

    public boolean isHave = false;

    public int color;

    public int size;

    public int startPadding;
    public int endPadding;

    public SideLine(int color, int size, int startPadding, int endPadding) {
        this(true, color, size, startPadding, endPadding);
    }

    public SideLine(boolean isHave, @ColorInt int color, int size, int startPadding, int endPadding) {
        this.isHave = isHave;
        this.color = color;
        this.size = size;
        this.startPadding = startPadding;
        this.endPadding = endPadding;
    }

    public int getStartPadding() {
        return startPadding;
    }

    public void setStartPadding(int startPadding) {
        this.startPadding = startPadding;
    }

    public int getEndPadding() {
        return endPadding;
    }

    public void setEndPadding(int endPadding) {
        this.endPadding = endPadding;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
