package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;

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
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.R;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ToastUtils;
import java.util.ArrayList;
import java.util.List;
/**
 * @author wxq
 * @version V_5.0.0
 * @date 2019年02月18日
 * @description 选址
 */
public class ChooseMapAddressActivity extends BaseActivity {


    MapView mapView;

    private AMap aMap;


    TabLayout tabLayout;

    RecyclerView listView;
    PoiItemAdapter adapter;

    FloatingActionButton btn_done;

    private String[] items = {"住宅区", "学校", "楼宇", "商场"};
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
                    doSearchQuery();
                }
            } else {
                ToastUtils.showShort("error code is ");
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };
    private PoiSearch.OnPoiSearchListener onPoiSearchListener = new PoiSearch.OnPoiSearchListener() {

        @Override
        public void onPoiSearched(PoiResult poiResult, int resultCode) {
            if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
                if (poiResult != null && poiResult.getQuery() != null) {
                    if (poiResult.getQuery().equals(query)) {
                        poiItems = poiResult.getPois();
                        if (poiItems != null && poiItems.size() > 0) {
                            resultData.clear();
                            resultData.add(firstItem);
                            resultData.addAll(poiItems);
                            adapter.setSelectedPosition(0);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShort("无搜索结果");
                        }
                    }
                } else {
                    ToastUtils.showShort("无搜索结果");
                }
            }
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };








    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(searchKey, searchType, "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(onPoiSearchListener);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));//
            poiSearch.searchPOIAsyn();
        }
    }

    /**
     * 定图标记
     */
    private Marker locationMarker;

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
                    //获取定位的城市
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
                    geoAddress();
                    startJumpAnimation();
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


        geocoderSearch = new GeocodeSearch(this);

//        、、searchlistener
        geocoderSearch.setOnGeocodeSearchListener(onGeocodeSearchListener);

    }

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
        RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 屏幕中心marker 跳动
     */
    public void startJumpAnimation() {
        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = locationMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            locationMarker.setAnimation(animation);
            //开始动画
            locationMarker.startAnimation();
        } else {
            ToastUtils.showShort("screenMarker is null");
        }
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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

        tabLayout=findViewById(R.id.tab_layout);
        listView=findViewById(R.id.listview);
        for (int i = 0; i < items.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(items[i]));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                searchType = items[position];
                geoAddress();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter=new PoiItemAdapter(resultData));


        btn_done=findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chooseFinish();
                search("火车站", new Inputtips.InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> list, int i) {
                        ToastUtils.showShort(list.size()+"");
                        LatLonPoint point = list.get(0).getPoint();
                        String address= list.get(0).getAddress();
                        searchPoi(list.get(0));
                    }
                });
            }
        });
    }

    private void chooseFinish() {
        int pos = adapter.getSelectedPosition();
        String area;
        String addresss;
        PoiItem poiItem = resultData.get(pos);
        area = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName();
        if (pos == 0) {
            addresss = poiItem.getTitle()
                    .replace(poiItem.getProvinceName(), "")
                    .replace(poiItem.getCityName(), "")
                    .replace(poiItem.getAdName(), "");
        } else {
            addresss = poiItem.getSnippet() + poiItem.getTitle();
        }
        Intent data = new Intent();
        data.putExtra("area", area);
        data.putExtra("address", addresss);
        setResult(RESULT_OK, data);
        finish();
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

    private  class PoiItemAdapter extends BaseQuickAdapter<PoiItem,BaseViewHolder> {
        private int selectedPosition = 0;
        public int getSelectedPosition() {
            return selectedPosition;
        }

        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }


        public PoiItemAdapter(List<PoiItem> resultData) {
            super(R.layout.poiitem_item, resultData);
        }

        @Override
        protected void convert(BaseViewHolder helper, PoiItem item) {
            helper.setText(R.id.text_title,item.getTitle());
            helper.setText(R.id.text_title_sub,item.getCityName()+item.getAdName()+item.getSnippet());
            helper.setVisible(R.id.image_check,helper.getAdapterPosition() == selectedPosition ? true : false);

//            imageCheck.setVisibility(helper.getAdapterPosition() == selectedPosition ? View.VISIBLE : View.INVISIBLE);

            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LatLng curLatlng = new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());
                    isItemClickAction = true;
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                    setSelectedPosition(helper.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

//下面是搜索代码

    private void search(String text,Inputtips.InputtipsListener inputtipsListener) {
        InputtipsQuery inputquery = new InputtipsQuery(text, city);
        Inputtips inputTips = new Inputtips(this, inputquery);
        inputquery.setCityLimit(true);
        inputTips.setInputtipsListener(inputtipsListener);
        inputTips.requestInputtipsAsyn();
    }

    private String inputSearchKey;
    public void searchPoi(Tip result) {
        Logger.i("District: " + result.getDistrict() + ",Address: " + result.getAddress() + ",Name: " + result.getName());
        isInputKeySearch = true;
        inputSearchKey = result.getName();
        searchLatlonPoint = result.getPoint();
        firstItem = new PoiItem("tip", searchLatlonPoint, inputSearchKey, result.getAddress());
        firstItem.setCityName(result.getDistrict());
        firstItem.setAdName("");
        resultData.clear();

        adapter.setSelectedPosition(0);

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude()), 16f));

        doSearchQuery();
    }




}




