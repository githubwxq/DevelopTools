package com.wxq.developtools;

import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;


public class DestinationFragment extends BaseFragment {

    public static DestinationFragment newInstance() {
        DestinationFragment fragment = new DestinationFragment();
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
        return R.layout.fragment_destination;
    }

    @Override
    protected void initViews() {

    }
}
