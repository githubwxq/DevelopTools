package com.juziwl.uilibrary.tablayout.listener;

/**
 * @author NULL
 * @modify Neil
 */
public interface OnTabSelectListener {
    /**
     * Tab选择
     * @param position
     */
    void onTabSelect(int position);

    /**
     * Tab重选
     * @param position
     */
    void onTabReselect(int position);
}