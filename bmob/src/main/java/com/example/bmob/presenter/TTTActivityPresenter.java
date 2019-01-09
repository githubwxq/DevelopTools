package com.example.bmob.presenter;

import com.example.bmob.contract.TTTContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.activity.TTTActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class TTTActivityPresenter extends RxPresenter<TTTContract.View> implements TTTContract.Presenter {


    public TTTActivityPresenter(TTTContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
