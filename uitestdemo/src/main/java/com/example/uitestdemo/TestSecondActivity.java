package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

public class TestSecondActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_second;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
