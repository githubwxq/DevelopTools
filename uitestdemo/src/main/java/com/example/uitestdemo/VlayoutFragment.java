package com.example.uitestdemo;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.uitestdemo.adapter.BannerAdapter;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


public class VlayoutFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_vlayout;
    }
    DelegateAdapter delegateAdapter;
    @Override
    protected void initViews() {

        List<String> strings=new ArrayList<>();
        strings.add("11111111");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");
        strings.add("4");

        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(mContext);
        recycler.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycler.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0,strings.size());
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        adapters.add(new BannerAdapter(mContext,strings
        ));
        delegateAdapter.setAdapters(adapters);
        recycler.setAdapter(delegateAdapter);

    }

}
