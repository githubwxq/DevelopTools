package com.wxq.mvplibrary.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {
//
//    void showErrorMsg(String msg);
//
//    void useNightMode(boolean isNight);
//
//    //=======  State  =======
//    void stateError();
//
//    void stateEmpty();
//
//    void stateLoading();
//
//    void stateMain();
//
//    /**
//     * iView 绑定生命周期  RxJava防止泄露专用
//     *
//     * @param <T>
//     * @return
//     */
    <T> LifecycleTransformer<T> bindToLife();


    //显示dialog
    void showLoadingDialog();

    //取消dialog
    void dismissLoadingDialog();

    void showToast(String message);


}
