package com.example.bmob.cardmodule.contract;


import com.luck.picture.lib.entity.LocalMedia;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface PublishCardContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {
        void saveCard(String s, List<LocalMedia> selectList);
    }

}
