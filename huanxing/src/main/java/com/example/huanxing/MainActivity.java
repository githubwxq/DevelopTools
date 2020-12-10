package com.example.huanxing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

public class MainActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}