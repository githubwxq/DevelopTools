package com.example.uitestdemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.uitestdemo.DataCenter;
import com.example.uitestdemo.R;
import com.example.uitestdemo.bean.ItemBean;
import com.example.uitestdemo.fragment.NineGrideFragment;
import com.example.uitestdemo.fragment.TestMemoryFragment;
import com.juziwl.uilibrary.tablayout.PagerSlidingTabStrip;
import com.juziwl.uilibrary.viewpage.ViewPagerWithFragment;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;
    @BindView(R.id.viewpage)
    ViewPagerWithFragment viewpage;


    public static void navToActivity(Context context, String key) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        topHeard.setTitle("案列详情");
        String key = (String) getIntent().getSerializableExtra("key");
        List<ItemBean> itemBeans = DataCenter.getItemWithName(key);
        List<String> titles =new ArrayList<>();
        for (ItemBean itemBean : itemBeans) {
            titles.add(itemBean.getName());
        }
        List<Fragment> fragments =new ArrayList<>();
        for (ItemBean itemBean : itemBeans) {
            fragments.add(itemBean.getFragment());
        }
        viewpage.setFragmentList(this,fragments,titles,null);
        tabLayout.setViewPager(viewpage);
        viewpage.setNoScroll(false);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_detail;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}