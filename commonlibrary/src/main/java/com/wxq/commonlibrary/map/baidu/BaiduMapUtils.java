package com.wxq.commonlibrary.map.baidu;

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
import com.baidu.mapapi.utils.DistanceUtil;
import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.util.LogUtils;

import java.util.LinkedList;

/**
 * @author wusang on 2016/1/27 0027.
 * 处理百度地图的公共类
 */
public class BaiduMapUtils {

    private LinkedList<LocationEntity> locationList = new LinkedList<>(); // 存放历史定位结果的链表，最大存放当前结果的前5次定位结果
    private static BaiduMapUtils instance;

    public static BaiduMapUtils getInstance() {
        if (instance == null) {
            instance = new BaiduMapUtils();
        }
        return instance;
    }

    public void LocateMyPosition(LocationClient mLocClient, final LocationCallBack callBack) {
        LocationClientOption mOption = new LocationClientOption();

        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setOpenGps(true);// 打开gps
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mLocClient.setLocOption(mOption);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    callBack.LocateFailed();
                    return;
                }
                LogUtils.e("BaiduMapUtils", "LocateMyPosition;location info===>"
                        + "\nlatitude==>" + location.getLatitude()
                        + "\nlongitude==>" + location.getLongitude()
                        + "\naddress===>" + location.getAddrStr());
                if (location.getLocType() == 62 || location.getLongitude() == (4.9E-324) || location.getLongitude() == 4.9E-324) {//失败
                    callBack.LocateFailed();
                } else {
                    callBack.LocateSuccess(location);
                }

            }
        });
        mLocClient.start();
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


//    public Bundle Algorithm(BDLocation location) {
//        Bundle locData = new Bundle();
//        double curSpeed = 0;
//        if (locationList.isEmpty() || locationList.size() < 2) {
//            LocationEntity temp = new LocationEntity();
//            temp.location = location;
//            temp.time = System.currentTimeMillis();
//            locData.putInt(StaticUtil.BaiduMap.HAS_CALCULATE_MY_LOCATION, 0);
//            locationList.add(temp);
//        } else {
//            if (locationList.size() > 5)
//                locationList.removeFirst();
//            double score = 0;
//            for (int i = 0; i < locationList.size(); ++i) {
//                LatLng lastPoint = new LatLng(locationList.get(i).location.getLatitude(),
//                        locationList.get(i).location.getLongitude());
//                LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
//                double distance = DistanceUtil.getDistance(lastPoint, curPoint);
//                curSpeed = distance / (System.currentTimeMillis() - locationList.get(i).time) / 1000;
//                score += curSpeed * StaticUtil.BaiduMap.EARTH_WEIGHT[i];
//            }
//            if (score > 0.00000999 && score < 0.00005) { // 经验值,开发者可根据业务自行调整，也可以不使用这种算法
//                location.setLongitude(
//                        (locationList.get(locationList.size() - 1).location.getLongitude() + location.getLongitude())
//                                / 2);
//                location.setLatitude(
//                        (locationList.get(locationList.size() - 1).location.getLatitude() + location.getLatitude())
//                                / 2);
//                locData.putInt(StaticUtil.BaiduMap.HAS_CALCULATE_MY_LOCATION, 1);
//            } else {
//                locData.putInt(StaticUtil.BaiduMap.HAS_CALCULATE_MY_LOCATION, 0);
//            }
//            LocationEntity newLocation = new LocationEntity();
//            newLocation.location = location;
//            newLocation.time = System.currentTimeMillis();
//            locationList.add(newLocation);
//
//        }
//        return locData;
//    }


    public static Marker move2MyLocation(BDLocation location, BaiduMap mBaiduMap, boolean showMark) {
        Marker mMarker = null;
        if (location != null) {
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            // 构建Marker图标
            BitmapDescriptor bitmap = null;
            bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_choose_location); // 非推算结果
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


//    public Marker move2MyLocation(double latitude, double longitude, BaiduMap mBaiduMap, int iscal, boolean showMark) {
//        Marker mMarker = null;
//        if (latitude != 0 && longitude != 0) {
//            LatLng point = new LatLng(latitude, longitude);
//            // 构建Marker图标
//            BitmapDescriptor bitmap = null;
//            if (iscal == 0) {
//                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_mylocation); // 非推算结果
//            } else {
//                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_mylocation_guess); // 推算结果
//            }
//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(point));
//
//            if (showMark) {
//                // 构建MarkerOption，用于在地图上添加Marker
//                OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(20);
//                // 在地图上添加Marker，并显示
//                mMarker = (Marker) mBaiduMap.addOverlay(option);
//            }
//        }
//        return mMarker;
//    }

    public void clearLocationList() {
        locationList.clear();
    }

    /**
     * 封装定位结果和时间的实体类
     *
     * @author baidu
     */
    class LocationEntity {
        BDLocation location;
        long time;
    }

    public interface LocationCallBack {
        public void LocateSuccess(BDLocation location);

        public void LocateFailed();
    }
}
