package com.example.module_login.presenter;

import com.example.module_login.contract.LoginContract;
import com.example.module_login.contract.SplashContract;
import com.wxq.mvplibrary.base.RxPresenter;

/**
 * Created by wxq on 2018/6/28.
 */
public class LoginActivityPresent extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    public LoginActivityPresent(LoginContract.View view) {
        super(view);
    }
    @Override
    public void initEventAndData() {

    }
}
