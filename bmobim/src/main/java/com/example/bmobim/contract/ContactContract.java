package com.example.bmobim.contract;


import com.example.bmobim.bean.Friend;
import com.example.bmobim.bean.Message;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface ContactContract {
    interface View extends BaseView {
        /**
         * 更新列表数据
         * @param
         */
        void updateRecycleViewData(List<Friend> friendList);
    }

    interface Presenter extends BasePresenter<View> {
        public void findFriends();
    }

}
