package com.wxq.developtools;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.juziwl.uilibrary.design.BottomNavigationViewHelper;
import com.juziwl.uilibrary.viewpage.NoScrollViewPager;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/klook/main")
public class KLookMainActivity extends BaseActivity {

    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected void initViews() {
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId()==R.id.bottom_explore) {
                viewPage.setCurrentItem(0);
            }
            if (item.getItemId()==R.id.bottom_destination) {
                viewPage.setCurrentItem(1);
            }
            if (item.getItemId()==R.id.bottom_order) {
                viewPage.setCurrentItem(2);
            }
            if (item.getItemId()==R.id.bottom_setting) {
                viewPage.setCurrentItem(3);
            }
            return false;
        });
        fragmentList.add(ExploreFragment.newInstance());
        fragmentList.add(DestinationFragment.newInstance());
        fragmentList.add(OrderFragment.newInstance());
        fragmentList.add(MySelfFragment.newInstance());
        viewPage.setOffscreenPageLimit(fragmentList.size());
        viewPage.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(),fragmentList));

    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_klook_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
