package com.juzi.win.gank.ui.fragment;


import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzi.win.gank.R;
import com.juzi.win.gank.bean.GankBaseResponse;
import com.juziwl.uilibrary.pullrefreshlayout.PullRefreshLayout;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.util.ListUtils;
import com.wxq.commonlibrary.base.BaseFragment;

import java.util.List;

import butterknife.BindView;


public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    @BindView(R.id.rv)
    PullRefreshRecycleView rv;

    public  static GankListFragment getInstance(String type) {
        GankListFragment fragment = new GankListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {

        mPresenter.reqInfo(getArguments().getString("type"));


    }

    @Override
    public void setData(List<GankBaseResponse.GankBean> list) {
        rv.setAdapter(new BaseQuickAdapter<GankBaseResponse.GankBean, BaseViewHolder>(R.layout.item_list_info, list) {

            @Override
            protected void convert(BaseViewHolder helper, GankBaseResponse.GankBean item) {
                helper.setText(R.id.tv_title, item.desc);
                helper.setText(R.id.tv_time, item.publishedAt);
                helper.setText(R.id.tv_tag, item.type);
                if (ListUtils.isNotEmpty(item.images)) {
//                    Loutil
                }
            }
        }, new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoading() {

            }
        });

        rv.completeRefrishOrLoadMore();
    }


    @Override
    protected GankListContract.Presenter initPresenter() {
        return new GankListPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_list;
    }



}
