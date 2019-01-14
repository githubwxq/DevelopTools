package com.example.bmob.presenter;

import com.example.bmob.contract.MyselfContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.fragment.MyselfFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class MyselfFragmentPresenter extends RxPresenter<MyselfContract.View> implements MyselfContract.Presenter {


    public MyselfFragmentPresenter(MyselfContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
