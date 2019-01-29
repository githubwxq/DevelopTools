package com.example.bmobim.presenter;

import com.example.bmobim.contract.NewFriendContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.NewFriendActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class NewFriendActivityPresenter extends RxPresenter<NewFriendContract.View> implements NewFriendContract.Presenter {


    public NewFriendActivityPresenter(NewFriendContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
