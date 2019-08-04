package com.wxq.commonlibrary.map.navigation;

import android.content.Context;
import android.content.Intent;

import com.amap.api.maps.model.LatLng;
import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.net.URISyntaxException;

public class MapNavigationUtils {
    public static final String GD_PACKAGE_NAME = "com.autonavi.minimap";
    public static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";

    public static void goBaidu(Context context, LatLng start, LatLng endPt) {
        Intent intent = null;
        if (AppUtils.isAppInstalled(BAIDU_PACKAGE_NAME)) {
            try {
                String pareUrl = "baidumap://map/direction?region=" +
                        "&origin=" + start.latitude + "," + start.longitude +
                        "&destination=" + endPt.latitude + "," + endPt.longitude + "&mode=walking";
                intent = Intent.getIntent(pareUrl);
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort("请先安装百度地图");
        }
    }


    /**
     * 只需要终点
     * @param context
     * @param endPt
     */
    public static void goBaidu(Context context, LatLng endPt) {
        Intent intent = null;
        if (AppUtils.isAppInstalled(BAIDU_PACKAGE_NAME)) {
            try {
                String pareUrl = "baidumap://map/direction?destination=latlng:" + endPt.latitude + ","
                        + endPt.longitude +
                        "&mode=driving";
                intent = Intent.getIntent(pareUrl);
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort("请先安装百度地图");
        }
    }



    public static void goGd(Context context, LatLng endPt) {
        if (AppUtils.isAppInstalled(GD_PACKAGE_NAME)) {
            Intent intent = new Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("androidamap://route?sourceApplication=" + AppUtils.getAppName() + "&poiname=&dlat=" + endPt.latitude
                            + "&dlon=" + endPt.longitude + "&dev=0&t=0&style=2"));
            context.startActivity(intent);
        } else {
            ToastUtils.showShort("请先安装高德地图");
        }
    }
}
