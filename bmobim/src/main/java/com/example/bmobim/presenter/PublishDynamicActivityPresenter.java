package com.example.bmobim.presenter;

import com.example.bmobim.contract.PublishDynamicContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.PublishDynamicActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class PublishDynamicActivityPresenter extends RxPresenter<PublishDynamicContract.View> implements PublishDynamicContract.Presenter {


    public PublishDynamicActivityPresenter(PublishDynamicContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
