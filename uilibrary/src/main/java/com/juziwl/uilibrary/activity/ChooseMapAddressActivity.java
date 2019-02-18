package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author wxq
 * @version V_5.0.0
 * @date 2019年02月18日
 * @description 选址
 */
public class ChooseMapAddressActivity extends BaseActivity {


    MapView mapView;

    private AMap aMap;

    /**
     * 激活定位源监听
     */
    private LocationSource.OnLocationChangedListener mListener;
    /**
     * 激活定位
     */
    private AMapLocationClient mlocationClient;
    /**
     * 设置定位
     */
    private AMapLocationClientOption mLocationOption;


    private LatLonPoint searchLatlonPoint;
    private boolean isItemClickAction;
    private boolean isInputKeySearch;
    /**
     * 设置地理编码
     */
    private GeocodeSearch geocoderSearch;

    private GeocodeSearch.OnGeocodeSearchListener onGeocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {

        @Override
        public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                if (result != null && result.getRegeocodeAddress() != null
                        && result.getRegeocodeAddress().getFormatAddress() != null) {

//                    String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
//                    firstItem = new PoiItem("regeo", searchLatlonPoint, address, address);

                    RegeocodeAddress regeocodeAddress = result.getRegeocodeAddress();
                    String title = regeocodeAddress.getFormatAddress();
                    String snippet = regeocodeAddress.getTownship();
                    firstItem = new PoiItem("regeo", searchLatlonPoint, title, snippet);
                    firstItem.setProvinceName(regeocodeAddress.getProvince());
                    firstItem.setCityName(regeocodeAddress.getCity());
                    firstItem.setAdName(regeocodeAddress.getDistrict());

//                    doSearchQuery();
                }
            } else {
                ToastUtils.showShort("error code is ");
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };


    /**
     * 定图标记
     */
    private Marker locationMarker;

    private String[] items = {"住宅区", "学校", "楼宇", "商场"};



    private List<PoiItem> resultData = new ArrayList<>();
//    private SearchResultAdapter searchResultAdapter;
    private PoiItem firstItem;

    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private String searchKey = "";
    private String searchType = items[0];
    private List<PoiItem> poiItems;

    private String city;


    private LocationSource locationSource = new LocationSource() {

        /**
         * 激活定位源
         *
         * @param listener
         */
        @Override
        public void activate(OnLocationChangedListener listener) {
            mListener = listener;
            if (mlocationClient == null) {
                mlocationClient = new AMapLocationClient(context);
                mLocationOption = new AMapLocationClientOption();
                //设置定位监听
                mlocationClient.setLocationListener(aMapLocationListener);
                //设置为高精度定位模式
                mLocationOption.setOnceLocation(true);
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //设置定位参数
                mlocationClient.setLocationOption(mLocationOption);
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                mlocationClient.startLocation();
            }
        }

        @Override
        public void deactivate() {

        }
    };
    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {

        /**
         * 定位成功后回调函数
         */
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (mListener != null && amapLocation != null) {
                if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                    mListener.onLocationChanged(amapLocation);
                    LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                    searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);

                    city = amapLocation.getCity();
                } else {
                    String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                    ToastUtils.showShort(errText);
                }
            }
        }
    };

    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, ChooseMapAddressActivity.class);
        activity.startActivity(intent);
    }







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        initMap();


    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
// 在移动或者缩放地图的动作结束时，都会进 onCameraChangeFinish 回调中，获取此时的相机坐标作为 Marker 的坐标
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() { // 在移动或者缩放地图的动作结束时，都会进 onCameraChangeFinish 回调中，获取此时的相机坐标作为 Marker 的坐标
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isItemClickAction && !isInputKeySearch) {
//                    geoAddress();
//                    startJumpAnimation();
                }
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                isInputKeySearch = false;
                isItemClickAction = false;
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter(null);
            }
        });




//        geocoderSearch = new GeocodeSearch(this);
//        geocoderSearch.setOnGeocodeSearchListener(onGeocodeSearchListener);

    }
    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
//        locationMarker = aMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed)));

//        locationMarker = aMap.addMarker(new MarkerOptions()
//                .anchor(0.5f,0.5f)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));

        locationMarker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }

    //dip和p
    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
        aMap.setLocationSource(locationSource);// 设置定位监听
        aMap.getUiSettings().setZoomControlsEnabled(true);// 设置地图默认的缩放按钮是否显示
        aMap.getUiSettings().setCompassEnabled(true);// 设置地图默认的指南针是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setRotateGesturesEnabled(false);// 禁止旋转
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        setupLocationStyle();
    }

    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(180, 3, 145, 255));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(10, 0, 0, 180));
        // 将自定义的 myLocationStyle 对象添加到地图上



        aMap.setMyLocationStyle(myLocationStyle);
    }


    @Override
    protected void initViews() {
        mapView=findViewById(R.id.map);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
//        deactivate();
    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
//        if (null != mlocationClient) {
//            mlocationClient.onDestroy();
//        }
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_choose_address;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}
