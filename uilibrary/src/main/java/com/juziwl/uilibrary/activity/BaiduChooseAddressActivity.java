package com.juziwl.uilibrary.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.http.common.LogUtil;
import com.wxq.commonlibrary.map.baidu.BaiDuLocationManager;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.List;

public class BaiduChooseAddressActivity extends BaseActivity {


    private PoiSearch mPoiSearch = null;
    private MapView baiduMapView;

    private   PullRefreshRecycleView recyclerView;

    private  BDLocation mbdLocation;


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, BaiduChooseAddressActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        baiduMapView = findViewById(R.id.baidu_mapView);
        recyclerView = findViewById(R.id.recyclerView);
        initPoiSearch();
        //拿自己定位请求数据 到时候也可以问接口要经纬度
        BaiDuLocationManager.getInstance(this).start(new BaiDuLocationManager.LocationListener() {
            @Override
            public void success(BDLocation bdLocation) {
                mbdLocation=bdLocation;
                LatLng   latLng = new LatLng(mbdLocation.getLatitude(), mbdLocation.getLongitude());
                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                        .keyword(bdLocation.getAddrStr())
                        .sortType(PoiSortType.distance_from_near_to_far)
                        .location(latLng)
                        .radius(100 * 1000)
                        .pageCapacity(10)
                        .pageNum(0)
                        .scope(1);
                mPoiSearch.searchNearby(nearbySearchOption);
            }
            @Override
            public void error() {
                ToastUtils.showShort("定位异常");
            }
        });

    }

    private void initPoiSearch() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                List<PoiInfo> list = poiResult.getAllPoi();
                for (PoiInfo poiInfo : list) {
                    LogUtil.e("poiInfo"+poiInfo.address);
                }
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