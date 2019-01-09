package com.wxq.commonlibrary.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by wxq on 2018/8/2.
 * View基类
 */
public interface BaseView {

    <T> LifecycleTransformer<T> bindToLife();

    <T> LifecycleTransformer<T> bindDestory();

    //显示dialog
    void showLoadingDialog();

    //取消dialog
    void dismissLoadingDialog();

    void showToast(String message);




}
