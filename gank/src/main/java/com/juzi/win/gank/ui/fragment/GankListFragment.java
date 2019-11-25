package com.juzi.win.gank.ui.fragment;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzi.win.gank.R;
import com.juzi.win.gank.bean.GankBaseResponse;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.ListUtils;

import java.util.List;

import butterknife.BindView;


public class GankListFragment extends BaseFragment<GankListContract.Presenter> implements GankListContract.View {

    @BindView(R.id.rv)
    PullRefreshRecycleView rv;

//    @BindView(R.id.smartRefreshLayout)
//    SmartRefreshLayout refreshLayout;
//    @BindView(R.id.rv)
//    RecyclerView rv;
//    BaseQuickAdapter adapter;

    public static GankListFragment getInstance(String type) {
        GankListFragment fragment = new GankListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {

        mPresenter.reqInfo(getArguments().getString("type"), GlobalContent.REFRESH);

//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.setAdapter(adapter = new BaseQuickAdapter<GankBaseResponse.GankBean, BaseViewHolder>(R.layout.item_list_info) {
//
//            @Override
//            protected void convert(BaseViewHolder helper, GankBaseResponse.GankBean item) {
//                helper.setText(R.id.tv_title, item.desc);
//                helper.setText(R.id.tv_time, item.publishedAt);
//                helper.setText(R.id.tv_tag, item.type);
//            }
//        });
//        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mPresenter.reqInfo(getArguments().getString("type"), GlobalContent.LOADMORE);
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mPresenter.reqInfo(getArguments().getString("type"), GlobalContent.REFRESH);
//            }
//        });

        Log.e("aaa", "" + rv);
        rv.setAdapter(new BaseQuickAdapter<GankBaseResponse.GankBean, BaseViewHolder>(R.layout.item_list_info) {

                          @Override
                          protected void convert(BaseViewHolder helper, GankBaseResponse.GankBean item) {
                              helper.setText(R.id.tv_title, item.desc);
                              helper.setText(R.id.tv_time, item.publishedAt);
                              helper.setText(R.id.tv_tag, item.type);
                              if (ListUtils.isNotEmpty(item.images)) {
//                    Loutil
                              }
                          }
                      }, new OnRefreshLoadMoreListener() {
                          @Override
                          public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                              mPresenter.reqInfo(getArguments().getString("type"), GlobalContent.LOADMORE);

                          }

                          @Override
                          public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                              mPresenter.reqInfo(getArguments().getString("type"), GlobalContent.REFRESH);
                          }
                      }


        );

        rv.setEmptyLayoutTV("hhah");
        TextView tv = new TextView(getActivity());
        tv.setText("aaaaaaaaaaaaaaaa");
        rv.addHeaderView(tv, true);


    }

    @Override
    public void setData(List<GankBaseResponse.GankBean> list) {
//        adapter.getData().clear();
//        adapter.notifyDataSetChanged();
//        refreshLayout.finishRefresh();

        rv.getAdapter().getData().clear();
        rv.getAdapter().notifyDataSetChanged();
        rv.completeRefrishOrLoadMore();
//        rv.setRecycleViewData(list);
    }

    @Override
    public void addData(List<GankBaseResponse.GankBean> list) {
//        adapter.addData(results);
//        refreshLayout.finishLoadMore();

        rv.addRecycleViewData(list);

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
