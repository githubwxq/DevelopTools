package com.wxq.developtools.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


public class OrderFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TextView titleBar;
    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;
    Unbinder unbinder;
    List<String> list=new ArrayList<>();

    int page=1;
    int rows=10;

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
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {


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
            }
        });

        getData();
    }

    private void getData() {




    }


}
