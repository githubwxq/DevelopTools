package com.example.huanxing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import org.android.agoo.xiaomi.MiPushRegistar;

public class MainActivity extends BaseActivity {


    @Override
    protected void initViews() {
        MiPushRegistar.register(this, "2882303761518886194","5151888682194");
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