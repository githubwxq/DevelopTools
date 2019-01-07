package com.example.module_login.presenter;

import com.example.module_login.contract.LoginContract;
import com.example.module_login.contract.RegisterContract;
import com.wxq.mvplibrary.base.RxPresenter;

/**
 * Created by wxq on 2018/6/28.
 */
public class RegisterActivityPresent extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterActivityPresent(RegisterContract.View view) {
        super(view);
    }
    @Override
    public void initEventAndData() {

    }
}
