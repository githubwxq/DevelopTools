package com.example.uitestdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTestActivity extends BaseActivity {

    @BindView(R2.id.viewpage)
    ViewPager viewpage;
    List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected void initViews() {
//        fragmentList.add(new OneFragment());
        fragmentList.add(new TextViewFragment());
        fragmentList.add(new ViewLocationFragment());
        fragmentList.add(new ViewLifeCycleFragment());
        viewpage.setOffscreenPageLimit(3);
        viewpage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewpage.setCurrentItem(0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_fragment_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
