package com.example.bmobim.presenter;

import com.example.bmobim.contract.UserInfoContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.UserInfoActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class UserInfoActivityPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter {


    public UserInfoActivityPresenter(UserInfoContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
