package com.example.module_login.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.module_login.R;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

/*
*登录页面
* */
public class LoginActivity extends BaseActivity {

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

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
