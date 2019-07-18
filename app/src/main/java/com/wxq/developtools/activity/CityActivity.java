package com.wxq.developtools.activity;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.developtools.R;

public class CityActivity extends BaseActivity {



    @Override
    protected void initViews() {
        BarUtils.setStatusBarAlpha(this, 0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}