package com.juziwl.uilibrary.tablayout.listener;

/**
 * @author NULL
 * @modify Neil
 */
public interface OnTabClickListener {
    /**
     * Tab点击  返回值决定是否view滑动 默认false表示 viewpage不滑动 true表示滑动
     * @param position
     */
    boolean onTabClick(int position);


}