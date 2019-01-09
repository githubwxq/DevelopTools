package com.juzi.win.gank.ui.fragment;


import com.juzi.win.gank.bean.GankBaseResponse;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

public interface GankListContract {
    interface View extends BaseView {
        void  setData(List<GankBaseResponse.GankBean>  list);
    }

    interface Presenter extends BasePresenter<View> {
        void reqInfo(String type);
    }

}
