package com.example.uitestdemo.fragment.components.floatview;

import android.os.Bundle;
import android.widget.TextView;

import com.example.uitestdemo.R;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class FloatViewFragment extends BaseFragment {

    @BindView(R.id.tv_float_easy)
    TextView tvFloatEasy;

    public static FloatViewFragment newInstance() {
        FloatViewFragment fragment = new FloatViewFragment();
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
        return R.layout.fragment_float_view;
    }

    @Override
    protected void initViews() {


    }

    @OnClick(R.id.tv_float_easy)
    public void onViewClicked() {


    }
}