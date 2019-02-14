package com.example.bmobim.contract;


import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface AddressListContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
        void lazyLoad();
    }

}
