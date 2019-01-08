package com.example.module_login.contract;


import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void naveToMainActivity();
    }

    interface Presenter extends BasePresenter<LoginContract.View> {
        void loginWithAccountAndPwd(String s, String s1);
    }
}
