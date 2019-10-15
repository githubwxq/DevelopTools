package com.example.uitestdemo;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.uitestdemo.adapter.GridLayoutAdapter;
import com.example.uitestdemo.adapter.LinearLayoutAdapter;
import com.example.uitestdemo.adapter.SingleLayoutAdapter;
import com.example.uitestdemo.adapter.StaggeredGridLayoutAdapter;
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

        List<String> strageStrings=new ArrayList<>();
        List<String> linearStrings=new ArrayList<>();
        List<String> singleStrings=new ArrayList<>();
        List<String> grideStrings=new ArrayList<>();
        strageStrings.add("11111111");
        strageStrings.add("2");
        strageStrings.add("3");
        strageStrings.add("4");
        singleStrings.add("sing1");
        singleStrings.add("sing2");
        linearStrings.add("4");
        linearStrings.add("4");
        linearStrings.add("4");
        linearStrings.add("4");
        singleStrings.add("sing4");
        singleStrings.add("sing5");
        grideStrings.add("gridlayout1");
        grideStrings.add("gridlayout2");
        grideStrings.add("gridlayout3");
        grideStrings.add("gridlayout4");
        grideStrings.add("gridlayout6");
        grideStrings.add("gridlayout7");
        grideStrings.add("gridlayout8");

        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(mContext);
        recycler.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);


        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycler.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(StaggeredGridLayoutAdapter.CURRENTTYPE,strageStrings.size());
        viewPool.setMaxRecycledViews(LinearLayoutAdapter.CURRENTTYPE,strageStrings.size());
        viewPool.setMaxRecycledViews(SingleLayoutAdapter.CURRENTTYPE,1);
        viewPool.setMaxRecycledViews(GridLayoutAdapter.CURRENTTYPE,grideStrings.size());
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(new StaggeredGridLayoutAdapter(mContext,strageStrings));
        adapters.add(new LinearLayoutAdapter(mContext,linearStrings));
        adapters.add(new GridLayoutAdapter(mContext,grideStrings));
        adapters.add(new SingleLayoutAdapter(mContext,singleStrings));

        delegateAdapter.setAdapters(adapters);
        recycler.setAdapter(delegateAdapter);

    }

}
