package com.juziwl.uilibrary.recycler.itemdecoration.listener;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/08/21
 * @description 悬浮的文字的配置
 */
public interface OnSuspensionListener {
    String getSuspensionName(int position);

    int getSuspensionHeight();

    int getSuspensionBackgroundColor();

    int getTextColor();
}
