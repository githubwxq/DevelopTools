package com.wxq.commonlibrary.map.baidu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.model.AppConfig;
import com.wxq.commonlibrary.util.AppUtils;
import com.wxq.commonlibrary.util.LogUtils;

import java.nio.file.Path;
import java.util.LinkedList;

/**
 * @author wusang on 2016/1/27 0027.
 * 处理百度地图的公共类
 */
public class BaiduMapUtils {

    private static BaiduMapUtils instance;

    public static BaiduMapUtils getInstance() {
        if (instance == null) {
            instance = new BaiduMapUtils();
        }
        return instance;
    }

    /**
     * @param mMapView
     * @param goneLogo
     * @param goneZoomControls
     * @return void
     * @throws
     * @Title: goneMapViewChild
     * @Description: 隱藏百度logo亦或百度自帶的縮放鍵
     */
    public static void goneMapViewChild(TextureMapView mMapView, boolean goneLogo, boolean goneZoomControls) {
        int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ImageView && goneLogo) { // 隐藏百度logo
                child.setVisibility(View.GONE);
            }
            if (child instanceof ZoomControls && goneZoomControls) { // 隐藏百度的縮放按鍵
                child.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param zoomLevel
     * @param mBaiduMap
     * @return void
     * @Title: setZoom
     * @Description: 縮放地圖的
     */
    public static void setZoom(float zoomLevel, BaiduMap mBaiduMap) {
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(zoomLevel));
    }

    /**
     * @param mMapView
     * @return void
     * @Title: zoomIn
     * @Description: 放大地圖
     */
    public static void zoomInMapView(TextureMapView mMapView) {
        try {
            setZoom(mMapView.getMap().getMapStatus().zoom + 1, mMapView.getMap());
        } catch (NumberFormatException e) {
        }
    }

    /**
     * @param mMapView
     * @return void
     * @Title: zoomOut
     * @Description: 縮小地圖
     */
    public static void zoomOutMapView(TextureMapView mMapView) {
        try {
            setZoom(mMapView.getMap().getMapStatus().zoom - 1, mMapView.getMap());
        } catch (NumberFormatException e) {
        }
    }


    public static Marker move2MyLocation(BDLocation location, BaiduMap mBaiduMap, boolean showMark) {
        Marker mMarker = null;
        if (location != null) {
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            // 构建Marker图标
            BitmapDescriptor bitmap = null;
            bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_mylocation); // 非推算结果
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
            if (showMark) {
                // 构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(20);
                // 在地图上添加Marker，并显示
                mMarker = (Marker) mBaiduMap.addOverlay(option);
            }
        }
        return mMarker;
    }


    /**
     * @param context
     * @param appName
     * @param slat    开始精度
     * @param slon    维度
     * @param sname   名称
     * @param dlat    目的地精度
     * @param dlon    目的地维度
     * @param dname   目的地名称
     * @param city    所在城市 可以 传递空串
     * @return
     */
    public boolean openBaiduMap(Context context, String appName, double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        if (isBaiduMapInstalled()) {
            try {
                String uri = getBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                        String.valueOf(dlat), String.valueOf(dlon), dname, city, appName);
                Intent intent = Intent.parseUri(uri, 0);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 打开高德地图
     */
    public boolean openGaoDeMap(Context context, String appname, double slat, double slon, String sname, double dlat, double dlon, String dname) {
        if (isGdMapInstalled()) {
            try {
                //百度地图定位坐标转换成高德地图可识别坐标
                double[] sPoint = GPSUtil.bd09_To_Gcj02(slat, slon);
                double[] dPoint = GPSUtil.bd09_To_Gcj02(dlat, dlon);

                if (sPoint != null && dPoint != null) {
                    String uri = getGdMapUri(appname, String.valueOf(sPoint[0]), String.valueOf(sPoint[1]),
                            sname, String.valueOf(dPoint[0]), String.valueOf(dPoint[1]), dname);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setPackage("com.autonavi.minimap");
                    intent.setData(Uri.parse(uri));
                    context.startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取打开百度地图应用uri [http://lbsyun.baidu.com/index.php?title=uri/api/android]
     *
     * @param originLat currentLocation.getLatitude()
     * @param originLon currentLocation.  getLongitude()
     * @param desLat
     * @param desLon
     * @return
     */
    public static String getBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String src) {
        String uri = "intent://map/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end";

        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, src);
    }

    /**
     * 获取打开高德地图应用uri
     */
    public static String getGdMapUri(String appName, String slat, String slon, String sname, String dlat, String dlon, String dname) {
        String uri = "androidamap://route?sourceApplication=%1$s&slat=%2$s&slon=%3$s&sname=%4$s&dlat=%5$s&dlon=%6$s&dname=%7$s&dev=0&m=0&t=2";
        return String.format(uri, appName, slat, slon, sname, dlat, dlon, dname);
    }


    /**
     * 高德地图应用是否安装
     *
     * @return
     */
    public boolean isGdMapInstalled() {
        return AppUtils.isAppInstalled("com.autonavi.minimap");
    }

    /**
     * 百度地图应用是否安装
     *
     * @return
     */
    public boolean isBaiduMapInstalled() {
        return AppUtils.isAppInstalled("com.baidu.BaiduMap");
    }

//=====================================================================打开外部百度app相关功能================================================================
    /**
     * 启动百度地图导航(Native)
     */
    public void startNavi(Context context, LatLng pt1, LatLng pt2, String startName, String endName) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2)
                .startName(startName).endName(endName);
        try {
            BaiduMapNavigation.openBaiduMapNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图导航(Web)
     */
    public void startWebNavi(Context context, LatLng pt1, LatLng pt2) {

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2);

        BaiduMapNavigation.openWebBaiduMapNavi(para, context);
    }

    /**
     * 启动百度地图步行导航(Native)
     */
    public void startWalkingNavi(Context context, LatLng pt1, LatLng pt2, String startName, String endName) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2)
                .startName(startName).endName(endName);
        try {
            BaiduMapNavigation.openBaiduMapWalkNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图步行AR导航(Native)
     */
    public void startWalkingNaviAR(Context context, LatLng pt1, LatLng pt2, String startName, String endName) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2)
                .startName(startName).endName(endName);
        try {
            BaiduMapNavigation.openBaiduMapWalkNaviAR(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图骑行导航(Native)
     */
    public void startBikingNavi(Context context, LatLng pt1, LatLng pt2, String startName, String endName) {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2)
                .startName(startName).endName(endName);
        try {
            BaiduMapNavigation.openBaiduMapBikeNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图Poi周边检索
     */
    public void startPoiNearbySearch(Context context, double pt1, double pt2, String keyWord) {
        LatLng ptCenter = new LatLng(pt1, pt2); // 天安门
        PoiParaOption para = new PoiParaOption()
                .key(keyWord)
                .center(ptCenter)
                .radius(2000);
        try {
            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图Poi详情页面
     */
    public void startPoiDetails(Context context, String uid) {
//        PoiParaOption para = new PoiParaOption().uid("65e1ee886c885190f60e77ff"); // 天安门
        PoiParaOption para = new PoiParaOption().uid(uid); // 天安门
        try {
            BaiduMapPoiSearch.openBaiduMapPoiDetialsPage(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图POI全景页面
     */
    public void startPoiPanoShow(Context context, String parmer) {
        try {
//            BaiduMapPoiSearch.openBaiduMapPanoShow("65e1ee886c885190f60e77ff", context); // 天安门
            BaiduMapPoiSearch.openBaiduMapPanoShow(parmer, context); // 天安门
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 启动百度地图步行路线规划
     */
    public void startRoutePlanWalking(Context context, LatLng pt1, LatLng pt2, String startName, String endName, String cityName) {
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(pt1)
                .startName(startName)
                .endPoint(pt2)
                .endName(endName)
                .cityName(cityName);

//      RouteParaOption para = new RouteParaOption()
//      .startName("天安门").endName("百度大厦");

//      RouteParaOption para = new RouteParaOption()
//      .startPoint(pt_start).endPoint(pt_end);
        try {
            BaiduMapRoutePlan.openBaiduMapWalkingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图驾车路线规划
     */
    public void startRoutePlanDriving(Context context, LatLng pt1, LatLng pt2, String startName, String endName, String cityName) {
        // 构建 route搜索参数
//        RouteParaOption para = new RouteParaOption()
//                .startPoint(ptStart)
//            .startName("天安门")
//            .endPoint(ptEnd);
//                .endName("大雁塔")
//                .cityName("西安");

//        RouteParaOption para = new RouteParaOption()
//                .startName("天安门").endName("百度大厦");
        RouteParaOption para = new RouteParaOption()
        .startPoint(pt1).endPoint(pt2);
        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 启动百度地图公交路线规划
     */
    public void startRoutePlanTransit(Context context, LatLng pt1, LatLng pt2, String startName, String endName, String cityName) {
        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
            .startPoint(pt1)
                .startName(startName)
                .endPoint(pt2)
                .endName(endName)
                .cityName(cityName)
                .busStrategyType(RouteParaOption.EBusStrategyType.bus_recommend_way);

//        RouteParaOption para = new RouteParaOption()
//                .startName("天安门").endName("百度大厦").busStrategyType(EBusStrategyType.bus_recommend_way);

//        RouteParaOption para = new RouteParaOption()
//        .startPoint(ptStart).endPoint(ptEnd).busStrategyType(EBusStrategyType.bus_recommend_way);
        try {
            BaiduMapRoutePlan.openBaiduMapTransitRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
