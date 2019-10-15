package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import com.juziwl.uilibrary.edittext.SuperEditText;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.activity.CityActivity;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.AreaAndCountry;
import com.wxq.developtools.model.CityVosBean;
import com.wxq.developtools.model.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 目的地
 */
public class DestinationFragment extends BaseFragment {

    @BindView(R.id.search_close_btn)
    ImageView searchCloseBtn;
    @BindView(R.id.area_recycler_view)
    RecyclerView areaRecyclerView;
    @BindView(R.id.country_recycler_view)
    RecyclerView countryRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.edit)
    SuperEditText edit;

    @BindView(R.id.rl_top_view)
    RelativeLayout rl_top_view;



    List<Region> allRegion = new ArrayList<>();
    RegionBaseViewHolderBaseQuickAdapter regionBaseViewHolderBaseQuickAdapter;

    List<AreaAndCountry> areaAndCountries = new ArrayList<>();
    AreaAndCountryBaseViewHolderBaseQuickAdapter areaAndCountryBaseViewHolderBaseQuickAdapter;

    public static DestinationFragment newInstance() {
        DestinationFragment fragment = new DestinationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_destination;
    }

    @Override
    protected void initViews() {
        searchCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
            }
        });

        edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        edit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edit.setOnEditorActionListener((v, actionId, event) -> {
         if (actionId == EditorInfo.IME_ACTION_SEARCH) {
           //前往搜索页面

         return true;
         }
         return false;
         });

        areaRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        areaRecyclerView.setAdapter(regionBaseViewHolderBaseQuickAdapter = new RegionBaseViewHolderBaseQuickAdapter());
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        countryRecyclerView.setAdapter(areaAndCountryBaseViewHolderBaseQuickAdapter = new AreaAndCountryBaseViewHolderBaseQuickAdapter());
    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        getRegionData();
        findCountryAndCity("", "");
    }

    private void getRegionData() {
        Api.getInstance()
                .getApiService(KlookApi.class).findAllRegion()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<List<Region>>() {
                    @Override
                    protected void onSuccess(List<Region> data) {
                        allRegion.clear();
                        allRegion.add(new Region("", "", true));
                        allRegion.addAll(data);
                        regionBaseViewHolderBaseQuickAdapter.notifyDataSetChanged();
                    }
                });
    }


    /**
     * 根据区域 id 或者名称查找数据
     *
     * @param name
     * @param regionId
     */
    public void findCountryAndCity(String name, String regionId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("regionId", regionId);
        String parmer = jsonObject.toString();
//
        HashMap<String, String> map = new HashMap<>();
        map.put("appCountrySearchDT", parmer);
        Api.getInstance()
                .getApiService(KlookApi.class).findCountryAndCity(map)
                .compose(RxTransformer.transformFlow(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<List<AreaAndCountry>>() {
                    @Override
                    protected void onSuccess(List<AreaAndCountry> data) {
                        areaAndCountries.clear();
                        areaAndCountries.addAll(data);
                        areaAndCountryBaseViewHolderBaseQuickAdapter.notifyDataSetChanged();
                    }
                });
    }


    private class RegionBaseViewHolderBaseQuickAdapter extends BaseQuickAdapter<Region, BaseViewHolder> {
        public RegionBaseViewHolderBaseQuickAdapter() {
            super(R.layout.item_region, DestinationFragment.this.allRegion);
        }

        @Override
        protected void convert(BaseViewHolder helper, Region item) {
            //
            TextView view = helper.getView(R.id.area_name);
            view.setText(item.getName());
            view.setSelected(item.isSelect);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Region datum : mData) {
                        datum.isSelect = false;
                    }
                    item.isSelect = true;
                    notifyDataSetChanged();
                    findCountryAndCity(item.name, item.id);
                }
            });

        }
    }

    private class AreaAndCountryBaseViewHolderBaseQuickAdapter extends BaseQuickAdapter<AreaAndCountry, BaseViewHolder> {

        public AreaAndCountryBaseViewHolderBaseQuickAdapter() {
            super(R.layout.item_area_country, DestinationFragment.this.areaAndCountries);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaAndCountry item) {
            helper.setText(R.id.tv_country_name, item.name);
            RecyclerView recyclerView = helper.getView(R.id.country_area_recycle);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

            recyclerView.setAdapter(new BaseQuickAdapter<CityVosBean, BaseViewHolder>(R.layout.item_region, item.cityVos) {
                @Override
                protected void convert(BaseViewHolder helper, CityVosBean item) {
                    TextView view = helper.getView(R.id.area_name);
                    view.setText(item.name);
                    view.setSelected(item.isSelect);
                    view.setOnClickListener(v -> {
                        for (AreaAndCountry areaAndCountry : areaAndCountries) {
                            for (CityVosBean cityVo : areaAndCountry.cityVos) {
                                cityVo.isSelect = false;
                            }
                        }
                        item.isSelect = true;
                        AreaAndCountryBaseViewHolderBaseQuickAdapter.this.notifyDataSetChanged();
                        CityActivity.navToActivity(getActivity(),item.id);
                    });
                }
            });
        }
    }


    public void onFragmentResume() {
        ImmersionBar.with(this) .statusBarColor(com.wxq.commonlibrary.R.color.white)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true).init();
    }

}
