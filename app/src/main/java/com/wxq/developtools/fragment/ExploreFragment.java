package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gyf.immersionbar.ImmersionBar;
import com.juziwl.uilibrary.banner.Banner;
import com.juziwl.uilibrary.banner.GlideImageLoader;
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
import com.wxq.developtools.R;
import com.wxq.developtools.activity.CityActivity;
import com.wxq.developtools.activity.CityProductSearchListActivity;
import com.wxq.developtools.activity.ProductActivity;
import com.wxq.developtools.activity.ShopCarListActivity;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.HomePageData;
import com.wxq.developtools.model.HotCitiesBean;
import com.wxq.developtools.model.HotCitiesBeanWrap;
import com.wxq.developtools.model.HotSellProductsBean;
import com.wxq.developtools.model.RecomentProductsBean;
import com.wxq.developtools.model.RecommendBeanWrap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


public class ExploreFragment extends BaseFragment {

    @BindView(R.id.recycler)
    PullRefreshRecycleView recycler;
    Unbinder unbinder;

    List<MultiItemEntity> itemEntities = new ArrayList<>();


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
        ImmersionBar.with(this).transparentStatusBar().init();
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
            addItemType(TYPE_LEVEL_0, R.layout.layout_top_banner);
            addItemType(TYPE_LEVEL_1, R.layout.layout_hot_city);
            addItemType(TYPE_LEVEL_2, R.layout.layout_hot_sell_old);
            addItemType(TYPE_LEVEL_3, R.layout.layout_hot_sell);
        }

        @Override
        protected void convert(BaseViewHolder helper, MultiItemEntity item) {
            if (item.getItemType() == TYPE_LEVEL_0) {
                dealWithBanner(helper, item);
            } else if (item.getItemType() == TYPE_LEVEL_1) {
                dealWithHotCity(helper, item);
            } else if (item.getItemType() == TYPE_LEVEL_2) {
//                dealWithHotSellProducts(helper, item);
            } else if (item.getItemType() == TYPE_LEVEL_3) {
                dealWithHotSellProducts(helper, item);
            }
        }

        private void dealWithHotSellProducts(BaseViewHolder helper, MultiItemEntity item) {
            helper.setText(R.id.tv_title, ((HotSellProductsBean) item).name);
            LoadingImgUtil.loadimg(((HotSellProductsBean) item).cover, (ImageView) helper.getView(R.id.iv_bg), false);
            if (getData().indexOf(item) == 2) {
                helper.setVisible(R.id.tv_recommend, true);
            } else {
                helper.setGone(R.id.tv_recommend, false);
            }
//            tv_recommend
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductActivity.navToActivity(mContext, ((HotSellProductsBean) item).id);
                }
            });

        }

        private void dealWithHotCity(BaseViewHolder helper, MultiItemEntity item) {
            RecyclerView recyclerView = helper.getView(R.id.hot_recycle_view);
            recyclerView.setFocusable(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new BaseQuickAdapter<HotCitiesBean, BaseViewHolder>(R.layout.item_hot_city, ((HotCitiesBeanWrap) (item)).hotCitiesBeanList) {
                @Override
                protected void convert(BaseViewHolder helper, HotCitiesBean item) {
                    helper.setText(R.id.tv_city_name, item.name);
                    LoadingImgUtil.loadimg(item.cover, helper.getView(R.id.iv_bg), false);
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CityActivity.navToActivity(getActivity(), item.id);
                        }
                    });
                }
            });
        }


//        private void dealWithHotSellProducts(BaseViewHolder helper, MultiItemEntity item) {
//            RecyclerView recyclerView=helper.getView(R.id.hot_sell_recycle_view);
//            recyclerView.setFocusable(false);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//            recyclerView.setAdapter(new BaseQuickAdapter<HotSellProductsBean,BaseViewHolder>(R.layout.item_hot_sell_product,((HotSellProductsBeanWrap)(item)).list) {
//                @Override
//                protected void convert(BaseViewHolder helper, HotSellProductsBean item) {
//                    helper.setText(R.id.tv_product_name,item.name);
//                    LoadingImgUtil.loadimg(item.cover,helper.getView(R.id.iv_bg),false);
//                    helper.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
////                            showToast("前往热卖");
//                            ProductActivity.navToActivity(mContext,item.id);
//                        }
//                    });
//                }
//            });
//        }

    }

    private void dealWithBanner(BaseViewHolder helper, MultiItemEntity item) {
        List<RecomentProductsBean> list = ((RecommendBeanWrap) (item)).list;
        List<String> bannerStrings = new ArrayList<String>();
        for (RecomentProductsBean bean : list) {
            bannerStrings.add(bean.cover);
        }
        Banner banner = helper.getView(R.id.banner);

        TextView tv_name = helper.getView(R.id.tv_name);
        if (list.size()>0) {
            tv_name.setText(list.get(0).name);
        }


        if (banner.isAutoPlay()) {
            banner.stopAutoPlay();
        }
        if (bannerStrings.size() <= 0) {
            banner.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.VISIBLE);
            banner.setImages(bannerStrings)
                    .setImageLoader(new GlideImageLoader(GlideImageLoader.RECT))
                    .setDelayTime(3000)
                    .setAutonPlay(true)
                    .setOnBannerListener(position -> {
                        ProductActivity.navToActivity(getActivity(), list.get(position).id);

                    })
                   .start();
            banner.setBannerPageChangeListener(new Banner.BannerPageChangeListener() {
                @Override
                public void onPageChanged(int positon) {
                    tv_name.setText(list.get(positon).name);
                }
            });
        }


        helper.getView(R.id.iv_shop_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopCarListActivity.navToActivity(getActivity());
            }
        });
        //前往搜索页面
        helper.getView(R.id.iv_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityProductSearchListActivity.navToActivity(getActivity(), "", "");
            }
        });
    }
}
