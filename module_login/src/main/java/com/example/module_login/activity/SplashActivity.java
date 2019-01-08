package com.example.module_login.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.SplashContract;
import com.example.module_login.presenter.SplashActivityPresent;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.mvplibrary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity<SplashContract.Presenter>implements SplashContract.View {

    @BindView(R2.id.iv_bg)
    ImageView ivBg;

    @Override
    protected void initViews() {
        //设置全屏
        BarUtils.setStatusBarVisibility(this, false);
        //点击背景如果当前有广告
        click(ivBg, o -> {
            LoginActivity.navToActivity(this);
            finish();
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashContract.Presenter initPresent() {
        return new SplashActivityPresent(this);
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }
}
