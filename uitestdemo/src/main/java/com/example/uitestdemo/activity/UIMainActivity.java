package com.example.uitestdemo.activity;

import androidx.fragment.app.Fragment;

import com.example.uitestdemo.R;
import com.example.uitestdemo.fragment.ComponentFragment;
import com.example.uitestdemo.fragment.TextViewFragment;
import com.google.android.material.tabs.TabLayout;
import com.juziwl.uilibrary.viewpage.ViewPagerWithFragment;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UIMainActivity extends BaseActivity {

    @BindView(R.id.viewpage)
    ViewPagerWithFragment viewpage;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @Override
    protected void initViews() {
        initTab();
    }

    /**
     * 初始化Tab
     */
    private void initTab() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ComponentFragment.newInstance());
        fragments.add(TextViewFragment.newInstance());
        fragments.add(TextViewFragment.newInstance());
        viewpage.setFragmentList(this, fragments, null);
        TabLayout.Tab component = mTabLayout.newTab();
        component.setText("组件");
//        component.setIcon(SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_component : R.drawable.selector_icon_tabbar_component);
        mTabLayout.addTab(component);

        TabLayout.Tab util = mTabLayout.newTab();
        util.setText("工具");
//        util.setIcon(SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_util : R.drawable.selector_icon_tabbar_util);
        mTabLayout.addTab(util);
        TabLayout.Tab expand = mTabLayout.newTab();
        expand.setText("拓展");
//        expand.setIcon(SettingSPUtils.getInstance().isUseCustomTheme() ? R.drawable.custom_selector_icon_tabbar_expand : R.drawable.selector_icon_tabbar_expand);
        mTabLayout.addTab(expand);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewpage.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_uimain;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }
}
