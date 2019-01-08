package com.example.uitestdemo;

import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

public class TestThirdActivity extends BaseActivity {

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_third;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
