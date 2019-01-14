package com.example.bmob.cardmodule.presenter;

import com.example.bmob.cardmodule.contract.PublishCardContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.cardmodule.activity.PublishCardActivity;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class PublishCardActivityPresenter extends RxPresenter<PublishCardContract.View> implements PublishCardContract.Presenter {
     public String publishContent="";

     public String publicPic="";

    public PublishCardActivityPresenter(PublishCardContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }


}
