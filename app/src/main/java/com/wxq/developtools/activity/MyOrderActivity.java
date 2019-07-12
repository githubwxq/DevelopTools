package com.wxq.developtools.activity;

import android.view.View;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

public class MyOrderActivity extends BaseActivity
{


    @Override
    protected void initViews() {
        topHeard.setTitle("我的订单").setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_my_order;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
