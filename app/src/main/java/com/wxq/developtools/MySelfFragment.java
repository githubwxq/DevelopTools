package com.wxq.developtools;

import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;


public class MySelfFragment extends BaseFragment {

    public static MySelfFragment newInstance() {
        MySelfFragment fragment = new MySelfFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_myself;
    }

    @Override
    protected void initViews() {

    }
}
