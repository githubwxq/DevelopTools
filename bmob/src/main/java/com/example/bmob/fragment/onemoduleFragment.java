package com.example.bmob.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.presenter.onemoduleFragmentPresenter;
import com.example.bmob.contract.onemoduleContract;

import com.wxq.commonlibrary.util.ListUtils;
import com.wxq.commonlibrary.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public class onemoduleFragment extends BaseFragment<onemoduleContract.Presenter> implements onemoduleContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";

    public static onemoduleFragment getInstance(String parmer) {
        onemoduleFragment fragment = new onemoduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_onemodule;
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected onemoduleContract.Presenter initPresenter() {
        return new onemoduleFragmentPresenter(this);
    }


}
