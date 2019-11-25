package com.juziwl.uilibrary.tablayout.listener;

import androidx.annotation.DrawableRes;

/**
 * @author null
 * @modify Neil
 */
public interface CustomTabEntity {
    /**
     * 获取Tab标题
     *
     * @return
     */
    String getTabTitle();

    /**
     * 获取Tab所在的Icon
     *
     * @return
     */
    @DrawableRes
    int getTabSelectedIcon();

    /**
     * 获取Tab不在的Icon
     *
     * @return
     */
    @DrawableRes
    int getTabUnselectedIcon();
}