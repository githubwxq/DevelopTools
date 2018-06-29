package com.wxq.developtools;


import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.base.BaseView;

public interface MvpMainContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<MvpMainContract.View> {
        void getData(int count);

    }
}
