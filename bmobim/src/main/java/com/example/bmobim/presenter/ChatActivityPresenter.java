package com.example.bmobim.presenter;

import android.text.TextUtils;

import com.example.bmobim.bean.ImageMessage;
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
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMVideoMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
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
                    }

                } else {
                    ToastUtils.showShort(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
                mView.updateRecycleViewData(bmobIMMessageList);
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

    @Override
    public void getPreMessages() {
       queryMessages(bmobIMMessageList.get(0).bmobIMMessage);
    }

    @Override
    public void receiveNewMessage(MessageEvent msgEvent) {
        Message message = dealWithData(msgEvent.getMessage());
        bmobIMMessageList.add(message);
        mView.updateRecycleViewData(bmobIMMessageList);
        mView.moveToBottom();
    }

    @Override
    public void setHasRead() {
        mConversationManager.setUnreadCount(0);
    }

    @Override
    public void sendImageMessage(String path) {
        BmobIMImageMessage bmobIMImageMessage=new BmobIMImageMessage();
        bmobIMImageMessage.setLocalPath(path);
//        bmobIMImageMessage.setRemoteUrl("https://avatars3.githubusercontent.com/u/11643472?v=4&u=df609c8370b3ef7a567457eafd113b3ba6ba3bb6&s=400");
//        BmobIMImageMessage bmobIMImageMessage=new BmobIMImageMessage(path);
        mConversationManager.sendMessage(bmobIMImageMessage, listener);
    }

    @Override
    public void sendVideoMessage(String path) {
        BmobIMVideoMessage bmobIMVideoMessage=new BmobIMVideoMessage();
        bmobIMVideoMessage.setLocalPath(path);
        mConversationManager.sendMessage(bmobIMVideoMessage, listener);
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
            if (msg.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
                mView.clearEdit();
            }
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            mView.updateRecycleViewData(bmobIMMessageList);
            if (msg.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
                mView.clearEdit();
            }

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
        if (item.getMsgType().equals(BmobIMMessageType.IMAGE.getType())) {
            return new ImageMessage(item);
        }

        return new TextMessage(item);
    }

}
