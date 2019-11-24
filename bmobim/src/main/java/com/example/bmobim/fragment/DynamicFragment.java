package com.example.bmobim.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bmobim.R;
import com.example.bmobim.activity.PublishDynamicActivity;
import com.example.bmobim.adapter.DynamicAdapter;
import com.example.bmobim.bean.DynamicBean;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.bmob.SimpleFindListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

/**
 * 创建日期：2019 2 2
 * 描述:动态fragment页面
 *
 * @author:wxq
 */
public class DynamicFragment extends BaseFragment {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    DynamicAdapter adapter;
    Unbinder unbinder;
    int currentPage=0;
    public List<DynamicBean> dynamicBeanList=new ArrayList<>();

    public static DynamicFragment getInstance(String parmer) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViews() {
        recyclerView.setAdapter(adapter=new DynamicAdapter(dynamicBeanList,getActivity()), new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getDynamicList(2);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDynamicList(1);
            }
        });
        getDynamicList(0);
    }

    private void getDynamicList(int type) {
        if (type==0||type==1) {
            currentPage=0;
        }else {
            currentPage++;
        }
        BmobQuery<DynamicBean> query = new BmobQuery<>();
        query.include("publishUser").setLimit(10).setSkip(10*currentPage).order("-createdAt")
                .findObjects(new SimpleFindListener<DynamicBean>(getActivity()) {
                    @Override
                    public void success(List<DynamicBean> list) {
                        if (type==0||type==1) {
                            dynamicBeanList.clear();
                        }
                        dynamicBeanList.addAll(list);
                        recyclerView.notifyDataSetChanged();
                    }

                    @Override
                    protected void error(BmobException e) {
                        super.error(e);
                        recyclerView.notifyDataSetChanged();
                    }
                });
    }


    @OnClick({R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_publish:
                PublishDynamicActivity.navToActivity(getActivity());
                break;
            case R.id.recyclerView:
                break;
        }
    }
}
