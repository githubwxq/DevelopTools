package com.example.bmobim.contract;


import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface ChatContract {
    interface View extends BaseView {
        /**
         * 更新列表数据
         * @param
         */
        void updateRecycleViewData(List<BmobIMMessage> bmobIMMessageList);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 设置当前会话
         *
         * @param conversation
         */
        void setCurrentConversation(BmobIMConversation conversation);

        /**
         * 首次加载，可设置msg为null，下拉刷新的时候，默认取消息表的第一个msg作为刷新的起始时间点，默认按照消息时间的降序排列
         *
         * @param msg
         */
        void queryMessages(BmobIMMessage msg);

    }

}
