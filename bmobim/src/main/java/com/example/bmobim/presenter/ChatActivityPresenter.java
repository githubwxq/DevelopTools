package com.example.bmobim.presenter;

import android.text.TextUtils;

import com.example.bmobim.bean.Message;
import com.example.bmobim.bean.TextMessage;
import com.example.bmobim.contract.ChatContract;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.example.bmobim.activity.ChatActivity;
import com.wxq.commonlibrary.util.ToastUtils;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ChatActivityPresenter extends RxPresenter<ChatContract.View> implements ChatContract.Presenter {

    public  BmobIMConversation mConversationManager;

    /**
     * 集合数据
     */
    public List<Message> bmobIMMessageList=new ArrayList<>();
    @Override
    public void setCurrentConversation(BmobIMConversation conversation) {
        mConversationManager= BmobIMConversation.obtain(BmobIMClient.getInstance(), conversation);
    }



    public ChatActivityPresenter(ChatContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
        //获取
        queryMessages(null);
    }

    @Override
    public void queryMessages(BmobIMMessage msg) {
        mConversationManager.queryMessages(msg, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e == null) {
                    if (null != list && list.size() > 0) {
                        List<Message> messageList=new ArrayList<>();
                        for (BmobIMMessage bmobIMMessage : list) {
                            messageList.add(dealWithData(bmobIMMessage));
                        }
                        bmobIMMessageList.addAll(0,messageList);
                        mView.updateRecycleViewData(bmobIMMessageList);
                    }
                } else {
                    ToastUtils.showShort(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }

    @Override
    public void sendTextMessage(String textMessage) {
        if (TextUtils.isEmpty(textMessage.trim())) {
            ToastUtils.showShort("请输入内容");
            return;
        }
        //TODO 发送消息：6.1、发送文本消息
        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(textMessage);
        //可随意设置额外信息
        Map<String, Object> map = new HashMap<>();
        map.put("level", "1");
        msg.setExtraMap(map);
        msg.setExtra("OK");
        mConversationManager.sendMessage(msg, listener);
    }

    /**
     * 消息发送监听器
     */

    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            Logger.i("onProgress：" + value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            bmobIMMessageList.add(dealWithData(msg));
            mView.clearEditAndMoveToBottom();

        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            mView.updateRecycleViewData(bmobIMMessageList);
            mView.clearEditAndMoveToBottom();
            if (e != null) {
                ToastUtils.showShort(e.getMessage());
            }
        }
    };
    /**
     * 数据处理写道工具类里面
     * @param item
     * @return
     */
    public Message dealWithData(BmobIMMessage item){
        if (item.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
            return new TextMessage(item);
        }
        return null;
    }

}
