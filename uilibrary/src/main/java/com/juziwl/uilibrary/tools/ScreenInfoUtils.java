package com.juziwl.uilibrary.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.wxq.commonlibrary.util.Utils;


/**
 * 作者　　: 李坤
 * 创建时间: 16:35 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：屏幕信息工具类
 */

public class ScreenInfoUtils {
    /**
     */
    private int width;
    /**
     */
    private int height;
    /**
     */
    private float density;
    /**
     */
    private int densityDpi;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public ScreenInfoUtils() {
        init();
    }

    private void init() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
        density = metric.density;
        densityDpi = metric.densityDpi;
    }
}
