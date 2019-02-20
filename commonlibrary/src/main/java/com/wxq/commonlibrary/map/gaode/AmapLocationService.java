package com.wxq.commonlibrary.map.gaode;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * @author zh
 * @date 2017/8/16
 * @description 高德地图的定位服务
 */
public class AmapLocationService {
    private AMapLocationClientOption mOption, DIYoption;
    private AMapLocationClient aMapLocationClient = null;
    private Object objLock = new Object();

    /**
     * @param amContext
     */
    public AmapLocationService(Context amContext) {
        synchronized (objLock) {
            if (aMapLocationClient == null) {
                aMapLocationClient = new AMapLocationClient(amContext);
                aMapLocationClient.setLocationOption(getDefaualtAMapLocationClientOption());
            }
        }
    }

    /**
     * 注册位置监听以获取定位的结果。
     *
     * @param listener
     * @return
     */
    public boolean registerListener(AMapLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null && aMapLocationClient != null) {
            aMapLocationClient.setLocationListener(listener);
        }
        return isSuccess;
    }

    /**
     * 解除监听。
     *
     * @param listener
     */
    public void unRegisterListener(AMapLocationListener listener) {
        if (listener != null) {
            aMapLocationClient.unRegisterLocationListener(listener);
        }
    }

    /**
     * 提供设置自定义的 {@link AMapLocationClientOption }
     *
     * @param option
     * @return
     */

    public boolean setLoactionOption(AMapLocationClientOption option) {
        boolean isSuccess = false;
        if (option != null) {
            if (aMapLocationClient.isStarted())
                aMapLocationClient.stopLocation();
            DIYoption = option;
            aMapLocationClient.setLocationOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * @return AMapLocationClientOption  返回设置基本的高德地图的参数信息。
     */
    public AMapLocationClientOption getDefaualtAMapLocationClientOption() {
        if (mOption == null) {
            mOption = new AMapLocationClientOption();
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); //高精度。
            mOption.setOnceLocation(true);
            mOption.setOnceLocation(true);
            mOption.setOnceLocationLatest(true);
            mOption.setNeedAddress(true);
        }
        return mOption;
    }

    /**
     * 获得AMapLocationClientOption
     *
     * @return
     */
    public AMapLocationClientOption getOption() {
        return DIYoption;
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        synchronized (objLock) {
            if (aMapLocationClient != null && !aMapLocationClient.isStarted()) {
                aMapLocationClient.startLocation();
            }
        }
    }

    /**
     * 停止定位。
     */
    public void stopLocation() {
        synchronized (objLock) {
            if (aMapLocationClient != null && aMapLocationClient.isStarted()) {
                aMapLocationClient.stopLocation();
            }
        }
    }

    /**
     * 销毁定位
     */
    public void destoryLoaction() {
        synchronized (objLock) {
            if (aMapLocationClient != null) {
                aMapLocationClient.onDestroy();
                aMapLocationClient = null;
                mOption = null;
            }
        }
    }

}
