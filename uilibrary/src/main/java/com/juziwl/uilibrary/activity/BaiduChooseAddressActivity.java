package com.juziwl.uilibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.http.common.CommonApi;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.map.baidu.BaiDuLocationManager;
import com.wxq.commonlibrary.map.baidu.BaiduMapUtils;
import com.wxq.commonlibrary.model.BaiduResule;
import com.wxq.commonlibrary.util.LogUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiduChooseAddressActivity extends BaseActivity {


    private PoiSearch mPoiSearch = null;
    private TextureMapView baiduMapView;

    private PullRefreshRecycleView recyclerView;

    private BDLocation mbdLocation;

    private BaiduMap mBaiduMap;

    private List<BaiduResule.ResultBean.PoisBean> poiInfoArrayList = new ArrayList<>();


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, BaiduChooseAddressActivity.class);
        context.startActivity(intent);
    }

    Marker marker;
    private String urlV3;

    @Override
    protected void initViews() {

        topHeard.setTitle("所在位置");
        topHeard.setRightText("搜索地址").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mbdLocation!=null) {
                    BaiduSearchListActivity.navToActivity(context,mbdLocation.getCity());
                }else {
                    ToastUtils.showShort("等待获取定位信息。。。。");
                }

            }
        });
        initPoiSearch();
        //拿自己定位请求数据 到时候也可以问接口要经纬度
        BaiDuLocationManager.getInstance(this).start(new BaiDuLocationManager.LocationListener() {
            @Override
            public void success(BDLocation bdLocation) {
                mbdLocation = bdLocation;
                LatLng latLng = new LatLng(mbdLocation.getLatitude(), mbdLocation.getLongitude());
                marker = BaiduMapUtils.move2MyLocation(mbdLocation, mBaiduMap, true);
                LogUtils.e("bdLocation.getAddrStr()" + bdLocation.getAddrStr());
                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                        .keyword(bdLocation.getAddrStr())
                        .sortType(PoiSortType.distance_from_near_to_far)
                        .location(latLng)
                        .radius(100 * 1000)
                        .pageCapacity(10)
                        .pageNum(0)
                        .scope(1);
                mPoiSearch.searchNearby(nearbySearchOption);

                //根据经纬获取附近poi
                String location = latLng.latitude + "," + latLng.longitude;
                getPoiBuyUrl(location);
            }

            @Override
            public void error() {
                ToastUtils.showShort("定位异常");
            }
        });
        initBaiduMap();
        initRecycleview();
    }

    private void initRecycleview() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new BaseQuickAdapter<BaiduResule.ResultBean.PoisBean, BaseViewHolder>(R.layout.item_choosepoi) {
            @Override
            protected void convert(BaseViewHolder helper, BaiduResule.ResultBean.PoisBean item) {
                helper.setText(R.id.poi_info, item.name);
                helper.setText(R.id.poi_location, item.addr);
            }
        });
    }

    private void initBaiduMap() {
        baiduMapView = findViewById(R.id.baidu_mapView);
        mBaiduMap = baiduMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(false);//关闭定位图层
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng latInfo = mapStatus.target;
                LogUtils.e("onMapStatusChangeFinish", "latitude===>" + latInfo.latitude
                        + "\nlongitude==>" + latInfo.longitude);
//                if (marker != null) {
//                    marker.remove();
//                }
//                // 构建Marker图标
//                BitmapDescriptor bitmap = null;
//                bitmap = BitmapDescriptorFactory.fromResource(com.wxq.commonlibrary.R.mipmap.icon_mylocation); // 非推算结果
//                OverlayOptions option = new MarkerOptions().position(latInfo).icon(bitmap).zIndex(20);
//                // 在地图上添加Marker，并显示
//                Marker mMarker = (Marker) mBaiduMap.addOverlay(option);
//                marker = mMarker;
//
                //根据经纬获取附近poi
                String location = latInfo.latitude + "," + latInfo.longitude;
                getPoiBuyUrl(location);

            }
        });
    }

//    http://api.map.baidu.com/geocoder/v2/?ak=WfTtY7BjvXORbd5aYRPXdb6w8D8Qq1oD&output=json&pois=1&location=31.818386,120.000756



    private void getPoiBuyUrl(String location) {
        String safeCode="69:20:39:88:79:7D:CF:1E:88:60:DA:B5:93:7C:E2:0E:CD:15:04:31;com.wxq.developtools" ;

        String baiduApikey = "HbIjSh9qQ612BwLlGhCucjPO5vGXxOyP";
        String LONGITUDE_TO_ADDRESS_URL = "http://api.map.baidu.com/reverse_geocoding/v3/?output=json&extensions_poi=true"; //&coordtype=BD09&pois=1

        String url = LONGITUDE_TO_ADDRESS_URL + "&ak=" + baiduApikey + "&location=" +location+"&mcode="+safeCode;
        Api.getInstance().getApiService(CommonApi.class).getPoiDetail(url).enqueue(new Callback<BaiduResule>() {
            @Override
            public void onResponse(Call<BaiduResule> call, Response<BaiduResule> response) {
                BaiduResule baiduResule = response.body();
//                baiduResule.result.pois
                        recyclerView.updataData( baiduResule.result.pois);
                LogUtil.e("getPoiDetail==========onResponse"+ baiduResule);
            }

            @Override
            public void onFailure(Call<BaiduResule> call, Throwable t) {
                LogUtil.e("getPoiDetail==========onFailure"+ t.getMessage());
            }
        });
    }


    /**
     * PoiSearch获取周边信息 通过回调拿到数据
     */
    private void initPoiSearch() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                List<PoiInfo> list = poiResult.getAllPoi();
//                for (PoiInfo poiInfo : list) {
//                    LogUtil.e("poiInfo" + poiInfo.address);
//                }
//                recyclerView.updataData(list);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });

    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_baidu_choose_address;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}


//1先定位再说 2根据位子查找信息 3显示相关信息
//    PoiNearbySearchOption
//         pageNum	分页编号，默认返回第0页结果
//        pageCapacity	设置每页容量，默认为10条结果
//        tag	设置检索分类，如“美食”
//        scope	值为1 或 空，返回基本信息
//        值为2，返回POI详细信息
//        cityLimit	是否限制检索区域为城市内
//        poiFilter	设置检索过滤条件，scope为2时有效