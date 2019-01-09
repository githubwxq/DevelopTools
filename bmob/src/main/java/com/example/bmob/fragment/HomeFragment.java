package com.example.bmob.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.presenter.HomeFragmentPresenter;
import com.example.bmob.contract.HomeContract;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.wxq.commonlibrary.util.ListUtils;
import com.wxq.commonlibrary.base.BaseFragment;
import java.util.List;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 创建日期：
 * 描述: 首页
 * @author:wxq
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";

    public static HomeFragment getInstance(String parmer) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        currentType= (String) getArguments().get("PARMER");
        Logger.e("onAttach_______"+ currentType);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }



    @Override
    protected void initViews() {


    }

    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomeFragmentPresenter(this);
    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        Logger.e("lazyLoadData________"+currentType);
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Logger.e("onFragmentResume________"+currentType);
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        Logger.e("onFragmentPause________"+currentType);

    }
}
