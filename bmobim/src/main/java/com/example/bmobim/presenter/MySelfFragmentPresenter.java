package com.example.bmobim.presenter;

import com.example.bmobim.contract.MySelfContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.fragment.MySelfFragment;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class MySelfFragmentPresenter extends RxPresenter<MySelfContract.View> implements MySelfContract.Presenter {


    public MySelfFragmentPresenter(MySelfContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void lazyLoad() {

    }
}
