package com.example.bmob.presenter;

import com.example.bmob.contract.BmobHomeContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.activity.BmobHomeActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class BmobHomeActivityPresenter extends RxPresenter<BmobHomeContract.View> implements BmobHomeContract.Presenter {


    public BmobHomeActivityPresenter(BmobHomeContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
