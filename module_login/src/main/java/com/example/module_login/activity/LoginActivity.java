package com.example.module_login.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.module_login.R;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

/*
*
* */
public class LoginActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
