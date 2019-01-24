package com.example.bmobim.contract;


import com.example.bmobim.bean.Conversation;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public interface ConversationContract {
    interface View extends BaseView {
        /**
         * 更新会话列表
         * @param conversationList
         */
        void updateList(List<Conversation> conversationList);
    }

    interface Presenter extends BasePresenter<View> {
        List<Conversation> getConversations();
    }

}
