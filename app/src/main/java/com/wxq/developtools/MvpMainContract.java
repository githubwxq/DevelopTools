package com.wxq.developtools;


import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

public interface MvpMainContract {
    interface View extends BaseView {

        void showRx();
    }

    interface Presenter extends BasePresenter<MvpMainContract.View> {

        void getData(int count);

    }
}
