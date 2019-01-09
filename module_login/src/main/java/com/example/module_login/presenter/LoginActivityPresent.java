package com.example.module_login.presenter;

import com.example.module_login.bean.User;
import com.example.module_login.contract.LoginContract;
import com.wxq.commonlibrary.base.RxPresenter;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

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

    @Override
    public void loginWithAccountAndPwd(String account, String password) {
        //此处替换为你的用户名密码
         BmobUser.loginByAccount(account, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                  mView.naveToMainActivity();
                } else {
                    mView.showToast(e.getMessage());
                }
            }
        });
    }
}
