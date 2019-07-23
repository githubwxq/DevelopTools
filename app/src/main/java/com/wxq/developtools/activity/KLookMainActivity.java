package com.wxq.developtools.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.juziwl.uilibrary.BottomNavigationViewHelper;
import com.juziwl.uilibrary.viewpage.NoScrollViewPager;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;
import com.wxq.developtools.fragment.DestinationFragment;
import com.wxq.developtools.fragment.ExploreFragment;
import com.wxq.developtools.fragment.MySelfFragment;
import com.wxq.developtools.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/klook/main")
public class KLookMainActivity extends BaseActivity {

    @BindView(R.id.view_page)
    NoScrollViewPager viewPage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    List<Fragment> fragmentList=new ArrayList<>();
    MenuItem prevMenuItem;

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
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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


}
