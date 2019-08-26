package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.activity.CityActivity;
import com.wxq.developtools.activity.ProductActivity;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.HomePageData;
import com.wxq.developtools.model.HotCitiesBean;
import com.wxq.developtools.model.HotCitiesBeanWrap;
import com.wxq.developtools.model.HotSellProductsBean;
import com.wxq.developtools.model.HotSellProductsBeanWrap;
import com.wxq.developtools.model.RecomentProductsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


public class ExploreFragment extends BaseFragment {

    @BindView(R.id.recycler)
    PullRefreshRecycleView recycler;
    Unbinder unbinder;

    List<MultiItemEntity> itemEntities=new ArrayList<>();


    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
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
        return R.layout.fragment_explore;
    }

    @Override
    protected void initViews() {
        recycler.setRefreshEnable(true).setLoadMoreEnable(false).setAdapter(new ExploreAdapter(), new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        getData();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
//        BarUtils.setStatusBarAlpha(getActivity(), 0);
        BarUtils.setStatusBarLightMode(getActivity(), false);
    }



    private void getData() {
        Api.getInstance()
                .getApiService(KlookApi.class).homepage()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<HomePageData>() {
                    @Override
                    protected void onSuccess(HomePageData homePageData) {
                        // 显示试图
                         itemEntities.clear();
                         itemEntities.addAll(homePageData.getlist());
                         recycler.notifyDataSetChanged();
                    }
                });
    }

    private class ExploreAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
        public static final int TYPE_LEVEL_0 = 0;
        public static final int TYPE_LEVEL_1 = 1;
        public static final int TYPE_LEVEL_2 = 2;
        public static final int TYPE_LEVEL_3 = 3;

        public ExploreAdapter() {
            super(ExploreFragment.this.itemEntities);
            addItemType(TYPE_LEVEL_0,R.layout.layout_top_banner);
            addItemType(TYPE_LEVEL_1,R.layout.layout_hot_city);
            addItemType(TYPE_LEVEL_2,R.layout.layout_hot_sell);
            addItemType(TYPE_LEVEL_3,R.layout.layout_recomment_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {
            if (item.getItemType()==TYPE_LEVEL_0) {
            }else if (item.getItemType()==TYPE_LEVEL_1){
                dealWithHotCity(helper, item);
            }else if (item.getItemType()==TYPE_LEVEL_2){
                dealWithHotSellProducts(helper, item);
            }else if (item.getItemType()==TYPE_LEVEL_3){
                dealWithRecommend(helper, item);
            }
        }

        private void dealWithRecommend(BaseViewHolder helper, MultiItemEntity item) {
            helper.setText(R.id.tv_title,((RecomentProductsBean)item).name);
            LoadingImgUtil.loadimg(((RecomentProductsBean)item).cover,(ImageView) helper.getView(R.id.iv_bg),false);
            if (getData().indexOf(item)==2) {
                helper.setVisible(R.id.tv_recommend,true);
            }else {
                helper.setGone(R.id.tv_recommend,false);
            }
//            tv_recommend
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductActivity.navToActivity(mContext,((RecomentProductsBean)item).id);
                }
            });

        }

        private void dealWithHotCity(BaseViewHolder helper, MultiItemEntity item) {
            RecyclerView recyclerView=helper.getView(R.id.hot_recycle_view);
            recyclerView.setFocusable(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setAdapter(new BaseQuickAdapter<HotCitiesBean,BaseViewHolder>(R.layout.item_hot_city,((HotCitiesBeanWrap)(item)).hotCitiesBeanList) {
                @Override
                protected void convert(BaseViewHolder helper, HotCitiesBean item) {
                    helper.setText(R.id.tv_city_name,item.name);
                    LoadingImgUtil.loadimg(item.cover,helper.getView(R.id.iv_bg),false);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CityActivity.navToActivity(getActivity(),item.id);
                        }
                    });
                }
            });
        }


        private void dealWithHotSellProducts(BaseViewHolder helper, MultiItemEntity item) {
            RecyclerView recyclerView=helper.getView(R.id.hot_sell_recycle_view);
            recyclerView.setFocusable(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            recyclerView.setAdapter(new BaseQuickAdapter<HotSellProductsBean,BaseViewHolder>(R.layout.item_hot_sell_product,((HotSellProductsBeanWrap)(item)).list) {
                @Override
                protected void convert(BaseViewHolder helper, HotSellProductsBean item) {
                    helper.setText(R.id.tv_product_name,item.name);
                    LoadingImgUtil.loadimg(item.cover,helper.getView(R.id.iv_bg),false);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            showToast("前往热卖");
                            ProductActivity.navToActivity(mContext,item.id);
                        }
                    });
                }
            });
        }

    }
}
