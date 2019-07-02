package com.example.module_login.presenter;

import com.example.module_login.contract.LoginContract;
import com.wxq.commonlibrary.base.RxPresenter;

/**
 * Created by wxq on 2018/6/28.
 */
public class KlookLoginActivityPresent extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    public KlookLoginActivityPresent(LoginContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {


    }

    @Override
    public void loginWithAccountAndPwd(String account, String password) {

    }
}
