package com.wxq.commonlibrary.base;

import io.reactivex.disposables.Disposable;


public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void initEventAndData();

    void detachView();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable subscription);

    //注销所有请求
    void unDisposable();

}
