package com.example.uitestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;

public class TestStepActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_step);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_step;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
