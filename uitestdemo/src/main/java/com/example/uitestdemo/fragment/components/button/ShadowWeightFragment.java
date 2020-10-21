package com.example.uitestdemo.fragment.components.button;

import android.view.View;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.button.CountDownButton;
import com.juziwl.uilibrary.button.CountDownButtonHelper;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;


public class ShadowWeightFragment extends BaseFragment {

    public static ShadowWeightFragment newInstance() {
        ShadowWeightFragment fragment = new ShadowWeightFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_shadow_weight;
    }

    @Override
    protected void initViews() {

    }
}