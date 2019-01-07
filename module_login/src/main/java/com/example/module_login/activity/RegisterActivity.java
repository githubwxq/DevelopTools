package com.example.module_login.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.RegisterContract;
import com.example.module_login.presenter.RegisterActivityPresent;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterActivityPresent(this);
    }
}
