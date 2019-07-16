package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.CollectionBean;
import com.wxq.developtools.model.CollectionData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;

    List<CollectionBean> collectionBeans = new ArrayList<>();

    int page = 1;

    int rows = 10;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        topHeard.setTitle("我的收藏").setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setLoadMoreEnable(true).setRefreshEnable(true).setAdapter(
                new BaseQuickAdapter<CollectionBean, BaseViewHolder>(
                        R.layout.item_collection, collectionBeans) {
                    @Override
                    protected void convert(BaseViewHolder helper, CollectionBean item) {
                        helper.setText(R.id.tv_activity_name, item.name);
                        helper.setText(R.id.tv_money, item.price + "");
                        LoadingImgUtil.loadimg(item.cover, helper.getView(R.id.iv_picture), false);
                        ImageView iv_collect_icon = helper.getView(R.id.iv_collect_icon);
                        iv_collect_icon.setOnClickListener(
                                v -> cancelCollection(item)
                        );

                    }
                }, new OnRefreshLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        page++;
                        getData(page, rows);
                    }

                    @Override
                    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                        page = 1;
                        getData(page, rows);
                    }
                });
        getData(page, rows);
    }

    /**
     * 取消收藏
     *
     * @param item
     */
    private void cancelCollection(CollectionBean item) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .deleteProduct(item.productId)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        collectionBeans.remove(item);
                    }

                    @Override
                    public void onComplete() {
                        recyclerView.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 枫叶获取数据
     *
     * @param
     */
    private void getData(int page, int rows) {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageCollectProduct(page, rows)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .safeSubscribe(new RxSubscriber<CollectionData>() {
                    @Override
                    protected void onSuccess(CollectionData collectionData) {
                        List<CollectionBean> data=collectionData.list;
                        if (page == 1) {
                            collectionBeans.clear();
                        } else {
                            if (data.size() < rows) {
                                recyclerView.setLoadMoreEnable(false);
                            } else {
                                recyclerView.setLoadMoreEnable(true);
                            }
                        }
                        collectionBeans.addAll(data);
                        recyclerView.notifyDataSetChanged();
                    }

                    @Override
                    public void onComplete() {
                        recyclerView.notifyDataSetChanged();
                    }
                });
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
