package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.BaseListModeData;
import com.wxq.developtools.model.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class OrderFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TextView titleBar;
    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;
    List<String> list=new ArrayList<>();
    int page=1;
    int rows=10;
    List<OrderBean> orderBeanList=new ArrayList<>();

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
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
        return R.layout.fragment_order;
    }

    @Override
    protected void initViews() {
        recyclerView.setAdapter(new BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.order_item, orderBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, OrderBean item) {
//
                LoadingImgUtil.loadimg(item.pic,helper.getView(R.id.iv_product_pic),false);
                helper.setText(R.id.tv_order_time,"下单时间  "+item.createTime);
                helper.setText(R.id.tv_title,item.productName);
                helper.setText(R.id.tv_time,item.orderDate);
                helper.setText(R.id.tv_type,item.ticketType);
                helper.getView(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            //前往评论页面
                    }
                });
                helper.getView(R.id.tv_state).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                getData();
            }
        });
    }

    private void getData() {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .pageOrder(page,rows)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<BaseListModeData<OrderBean>>() {
                    @Override
                    protected void onSuccess(BaseListModeData<OrderBean> orderBeanBaseListModeData) {
                        if (page==1) {
                            orderBeanList.clear();
                        }
                        orderBeanList.addAll(orderBeanBaseListModeData.list);
                        recyclerView.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        getData();
    }
}
