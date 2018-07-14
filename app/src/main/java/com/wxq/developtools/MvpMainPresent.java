package com.wxq.developtools;

import com.wxq.mvplibrary.base.BaseView;
import com.wxq.mvplibrary.base.RxPresenter;

/**
 * Created by wxq on 2018/6/28.
 *
 * //p成拿到view成數據
 */

public class MvpMainPresent extends RxPresenter<MvpMainContract.View> implements MvpMainContract.Presenter {


    public MvpMainPresent(MvpMainContract.View view) {
        super(view);
    }

    @Override
    public void getData(int count) {

       mView.showToast(count*3+"wawawawaa");
       mView.showRx();
    }


    @Override
    public void initEventAndData() {

    }
}
