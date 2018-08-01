package com.example.aroutertestdemo;

import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.base.BaseView;
import com.wxq.mvplibrary.base.RxPresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by wxq on 2018/7/20.
 */

public class FirstFragmentPresent extends RxPresenter {
    public FirstFragmentPresent(BaseView view) {
        super(view);
    }

    @Override
    public void attachView(BaseView view) {

    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void addDisposable(Disposable subscription) {

    }

    @Override
    public void unDisposable() {

    }
}
