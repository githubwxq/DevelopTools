package com.example.uitestdemo.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.uitestdemo.R;
import com.example.uitestdemo.bean.ItemBean;
import com.example.uitestdemo.fragment.NineGrideFragment;
import com.example.uitestdemo.fragment.TestMemoryFragment;
import com.juziwl.uilibrary.tablayout.PagerSlidingTabStrip;
import com.juziwl.uilibrary.viewpage.ViewPagerWithFragment;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;
    @BindView(R.id.viewpage)
    ViewPagerWithFragment viewpage;


    public static void navToActivity(Context context, ItemBean itemBean) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("itemBean", itemBean);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        topHeard.setTitle("案列详情");
//        setTitle("");
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(TestMemoryFragment.newInstance());
        fragments.add(NineGrideFragment.newInstance());

        fragments.add(NineGrideFragment.newInstance());

        fragments.add(NineGrideFragment.newInstance());


        List<String> titles=new ArrayList<>();
        titles.add("測試1");
        titles.add("測試2");
        titles.add("測試3");
        titles.add("測試4");


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