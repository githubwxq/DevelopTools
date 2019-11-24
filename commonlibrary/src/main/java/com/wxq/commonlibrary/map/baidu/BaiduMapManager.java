package com.wxq.commonlibrary.map.baidu;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wxq.commonlibrary.util.NetworkUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class BaiduMapManager {

    public static final String Tag = BaiduMapManager.class.getSimpleName();

    private Context context;
    private static BaiduMapManager mInstance;
    LocationService locationService;

    public static BaiduMapManager getInstance( ) {
        if (mInstance == null) {
            synchronized (BaiduMapManager.class) {
                if (mInstance == null)
                    mInstance = new BaiduMapManager();
            }
        }
        return mInstance;
    }


    public void initBaidu(Application application) {
        this.context = application;
        locationService = new LocationService(application);
        SDKInitializer.initialize(application);
    }


    public void getBDLocation(Activity activity, LocationListener listener) {
        if (!(NetworkUtils.isAvailableByPing())) {
            ToastUtils.showShort("打开手机网络");
            listener.otherError("打开手机网络");
            return;
        }
        if (!(isGpsEnable())) {
            ToastUtils.showShort("打开GPS定位");
            listener.otherError("打开GPS定位");
            return;
        }
        //判断权限
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    locationService.registerListener(new BDAbstractLocationListener() {
                        @Override
                        public void onReceiveLocation(BDLocation bdLocation) {
                            if (locationService != null) {
                                locationService.stop();
                            }
                            if (bdLocation.getLocType() == 62 || bdLocation.getLatitude() == (4.9E-324) || bdLocation.getLongitude() == 4.9E-324) {
                                listener.otherError("定位失败");
                            } else {
                                listener.success(bdLocation);
                            }
                        }
                    });
                } else {
                    ToastUtils.showShort("请打开定位权限");
                    listener.permissionError();
                }
            }
        });
    }



    private boolean isGpsEnable() {
        LocationManager locationManager =
                ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /***** 详细的定位信息（占时不需要）
     * @see null copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDAbstractLocationListener locationListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                final JSONObject objs = new JSONObject();
                final JSONObject locationobj = new JSONObject();
                if (location.getLongitude() == (4.9E-324) || location.getLatitude() == 0) {//失败
                    Toast.makeText(context, "定位失败", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("GPS", "latitude==>" + location.getLatitude() + "\nlongitude==>" + location.getLongitude());
                }
            }
        }

    };


}
