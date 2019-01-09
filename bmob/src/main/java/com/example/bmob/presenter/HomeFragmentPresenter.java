package com.example.bmob.presenter;

import com.example.bmob.contract.HomeContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.fragment.HomeFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class HomeFragmentPresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {


    public HomeFragmentPresenter(HomeContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
