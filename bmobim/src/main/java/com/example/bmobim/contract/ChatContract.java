package com.example.bmobim.contract;


import com.example.bmobim.bean.Message;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.event.MessageEvent;

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
        void updateRecycleViewData(List<Message> bmobIMMessageList);
        /**
         * 清空输入框
         * @param
         */
        void clearEdit();
        /**
         * 滚动到最后
         * @param
         */
        void moveToBottom();
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
        /**
         * 发送文本
         * * @param msg
         */
        void sendTextMessage(String textMessage);
        /**
         * 获取之前的消息
         * * * @param msg
         */
        void getPreMessages();
        /**
         * 接收新消息
         * * * @param msg
         */
        void receiveNewMessage(MessageEvent msgEvent);
    }

}
