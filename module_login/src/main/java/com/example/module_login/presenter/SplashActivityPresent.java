package com.example.module_login.presenter;

import com.example.module_login.contract.SplashContract;
import com.wxq.commonlibrary.base.RxPresenter;

import cn.bmob.v3.BmobUser;

/**
 * Created by wxq on 2018/6/28.
 */
public class SplashActivityPresent extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {
    public SplashActivityPresent(SplashContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
        if (BmobUser.isLogin()){


        }else {


        }
    }
}
