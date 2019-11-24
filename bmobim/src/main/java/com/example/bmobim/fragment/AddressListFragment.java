package com.example.bmobim.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.CityBeanWithTop;
import com.juziwl.uilibrary.recycler.IndexBar.widget.IndexBar;
import com.juziwl.uilibrary.recycler.suspension.SuspensionDecoration;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 创建日期：
 * 描述:通讯录 测试版本
 * 高仿微信的列表界面
 *
 * @author:wxq
 */
public class AddressListFragment extends BaseFragment {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;
    CityAdapter adapter;
    List<CityBeanWithTop> mDatas = new ArrayList<>();

    public static AddressListFragment getInstance(String parmer) {
        AddressListFragment fragment = new AddressListFragment();
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
        return R.layout.fragment_address_list;
    }

    private static final String INDEX_STRING_TOP = "↑";

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));
        adapter = new CityAdapter(mDatas);
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.item_comment,null);
        adapter.addHeaderView(view);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        indexBar.setmPressedShowTextView(tvSideBarHint).setNeedRealIndex(true).setmLayoutManager(mManager);

        // 获取数据
        CityBeanWithTop cityBeanWithTop1 = new CityBeanWithTop();
        cityBeanWithTop1.setCity("新朋友");
        cityBeanWithTop1.setTop(true);
        cityBeanWithTop1.setBaseIndexTag(INDEX_STRING_TOP);
        mDatas.add(cityBeanWithTop1);

        CityBeanWithTop cityBeanWithTop2 = new CityBeanWithTop();
        cityBeanWithTop2.setCity("新朋友");
        cityBeanWithTop2.setTop(true);
        cityBeanWithTop2.setBaseIndexTag(INDEX_STRING_TOP);
        mDatas.add(cityBeanWithTop2);

        for (int i = 0; i < 3; i++) {
            CityBeanWithTop cityBeanWithTop = new CityBeanWithTop();
            cityBeanWithTop.setCity("常州" + i);
            mDatas.add(cityBeanWithTop);
        }

        for (int i = 0; i < 3; i++) {
            CityBeanWithTop cityBeanWithTop = new CityBeanWithTop();
            cityBeanWithTop.setCity("苏州" + i);
            mDatas.add(cityBeanWithTop);
        }

        for (int i = 0; i < 3; i++) {
            CityBeanWithTop cityBeanWithTop = new CityBeanWithTop();
            cityBeanWithTop.setCity(i+"河北" + i);
            mDatas.add(cityBeanWithTop);
        }
   for (int i = 0; i < 3; i++) {
            CityBeanWithTop cityBeanWithTop = new CityBeanWithTop();
            cityBeanWithTop.setCity("a" + i);
            mDatas.add(cityBeanWithTop);
        }

        indexBar.setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
        adapter.notifyDataSetChanged();
    }

    private static class CityAdapter extends BaseQuickAdapter<CityBeanWithTop, BaseViewHolder> {

        public CityAdapter(List<CityBeanWithTop> mDatas) {
            super(R.layout.item_city, mDatas);
        }

        @Override
        protected void convert(BaseViewHolder helper, CityBeanWithTop item) {

        }
    }
}
