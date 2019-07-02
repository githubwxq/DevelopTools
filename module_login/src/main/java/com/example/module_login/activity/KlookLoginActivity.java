package com.example.module_login.activity;

import com.example.module_login.R;
import com.example.module_login.contract.LoginContract;
import com.example.module_login.presenter.KlookLoginActivityPresent;
import com.wxq.commonlibrary.base.BaseActivity;

public class KlookLoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {


    @Override
    public void naveToMainActivity() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_klook_login;
    }

    @Override
    protected LoginContract.Presenter initPresent() {
        return new KlookLoginActivityPresent(this);
    }
}
