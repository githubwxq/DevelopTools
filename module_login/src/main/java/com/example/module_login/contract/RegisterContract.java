package com.example.module_login.contract;


import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

public interface RegisterContract {
    interface View extends BaseView {


        void finishActivity(String name, String passWord);
    }

    interface Presenter extends BasePresenter<RegisterContract.View> {

        void signUp(String name, String passWord);
    }
}
