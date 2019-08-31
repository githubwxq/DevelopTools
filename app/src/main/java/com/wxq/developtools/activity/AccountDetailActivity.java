package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

/**
 * 账户详情页面
 */
public class AccountDetailActivity extends BaseActivity {

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_account_detail;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
