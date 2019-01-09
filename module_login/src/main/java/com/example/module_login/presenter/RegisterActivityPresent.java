package com.example.module_login.presenter;

import com.example.module_login.bean.User;
import com.example.module_login.contract.RegisterContract;
import com.wxq.commonlibrary.base.RxPresenter;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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

    @Override
    public void signUp(String name, String passWord) {
        final User user = new User();
        user.setUsername(name);
        user.setPassword(passWord);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    mView.showToast("注册成功");
                    mView.finishActivity(name,passWord);
                } else {
                    mView.showToast("注册失败"+e.getMessage());
                }
            }
        });
    }
}
