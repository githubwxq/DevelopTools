package com.example.bmobim.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bmobim.R;
import com.example.bmobim.activity.MainActivity;
import com.example.bmobim.contract.MySelfContract;
import com.example.bmobim.presenter.MySelfFragmentPresenter;
import com.juziwl.uilibrary.tablayout.SlidingTabLayout;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.juziwl.uilibrary.viewpage.adapter.BaseViewPagerAdapter;
import com.wxq.commonlibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：2019 2 14
 * 描述:我的页面
 *
 * @author:wxq
 */
public class MySelfFragment extends BaseFragment<MySelfContract.Presenter> implements MySelfContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";
//    @BindView(R.id.tablayout)
//    SlidingTabLayout tablayout;
//    @BindView(R.id.viewpage)
//    ViewPager viewpage;
    BaseFragmentAdapter adapter;
    Unbinder unbinder;
    List<Fragment> fragments=new ArrayList<>();
    List<String>  title=new ArrayList<>();

    public static MySelfFragment getInstance(String parmer) {
        MySelfFragment fragment = new MySelfFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_my_self;
    }

    @Override
    protected void initViews() {
//        fragments.add(new DynamicFragment());
//        fragments.add(new AddressListFragment());
//        title.add("动态");
//        title.add("地址");
//        viewpage.setAdapter(adapter=new BaseFragmentAdapter(getChildFragmentManager(),fragments,title));
//        viewpage.setOffscreenPageLimit(2);
//        viewpage.setAdapter(adapter);
//        tablayout.setViewPager(viewpage);
    }

    @Override
    protected MySelfContract.Presenter initPresenter() {
        return new MySelfFragmentPresenter(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
