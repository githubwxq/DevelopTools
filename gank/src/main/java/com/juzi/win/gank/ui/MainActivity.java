package com.juzi.win.gank.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.juzi.win.gank.R;
import com.juzi.win.gank.api.ApiService;
import com.juzi.win.gank.ui.fragment.GankListFragment;
import com.juziwl.uilibrary.tablayout.CommonTabLayout;
import com.juziwl.uilibrary.tablayout.TabEntity;
import com.juziwl.uilibrary.tablayout.listener.CustomTabEntity;
import com.juziwl.uilibrary.tablayout.listener.OnTabSelectListener;
import com.juziwl.uilibrary.viewpage.adapter.BaseFragmentAdapter;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import butterknife.BindView;

/**
 * @author admin
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected void initViews() {
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < ApiService.GANK_ARR.length; i++) {
            mTabEntities.add(new TabEntity(ApiService.GANK_ARR[i]));
        }
        tabLayout.setTabData(mTabEntities);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        tabLayout.setCurrentTab(0);


        List<Fragment> fragments = new ArrayList<>(ApiService.GANK_ARR.length);
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[0]));
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[1]));
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[2]));
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[3]));
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[4]));
        fragments.add(GankListFragment.getInstance(ApiService.GANK_ARR[5]));

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                fragments, Arrays.asList( ApiService.GANK_ARR));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
