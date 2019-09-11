package com.wxq.developtools.activity;

import com.gyf.immersionbar.ImmersionBar;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.UIHandler;
import com.wxq.developtools.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initViews() {
        ImmersionBar.with(this) .statusBarColor(com.wxq.commonlibrary.R.color.white)
                .statusBarDarkFont(true)
                .statusBarDarkFont(true).init();
        UIHandler.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                KlookLoginActivity.naveToActivity(context);
                finish();
            }
        },100);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }
}
