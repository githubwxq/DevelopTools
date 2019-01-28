package com.example.bmobim.presenter;

import android.text.TextUtils;

import com.example.bmobim.bean.ExtraMessageInfo;
import com.example.bmobim.bean.ImageMessage;
import com.example.bmobim.bean.Message;
import com.example.bmobim.bean.TextMessage;
import com.example.bmobim.bean.VoiceMessage;
import com.example.bmobim.contract.ChatContract;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.RxPresenter;

import java.io.File;
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
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ChatActivityPresenter extends RxPresenter<ChatContract.View> implements ChatContract.Presenter {

    public BmobIMConversation mConversationManager;

    /**
     * 集合数据
     */
    public List<Message> bmobIMMessageList = new ArrayList<>();

    @Override
    public void setCurrentConversation(BmobIMConversation conversation) {
        mConversationManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversation);
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
                        List<Message> messageList = new ArrayList<>();
                        for (BmobIMMessage bmobIMMessage : list) {
                            messageList.add(dealWithData(bmobIMMessage));
                        }
                        bmobIMMessageList.addAll(0, messageList);
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
        map.put("type", ExtraMessageInfo.TEXT);
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
        BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String bmobFileUrl = bmobFile.getUrl();
                    //TODO 发送消息：6.1、发送文本消息
                    BmobIMTextMessage msg = new BmobIMTextMessage();
                    msg.setContent(bmobFileUrl);
                    //可随意设置额外信息
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", ExtraMessageInfo.IAMGE);
                    msg.setExtraMap(map);
                    msg.setExtra("OK");
                    mConversationManager.sendMessage(msg, listener);
                }
            }
        });
    }

    @Override
    public void sendVideoMessage(String path) {
        BmobIMVideoMessage bmobIMVideoMessage = new BmobIMVideoMessage();
        bmobIMVideoMessage.setLocalPath(path);
        mConversationManager.sendMessage(bmobIMVideoMessage, listener);
    }

    @Override
    public void sendVoiceMessage(long length, String path) {
        BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String bmobFileUrl = bmobFile.getUrl();
                    //TODO 发送消息：6.1、发送语音
                    BmobIMTextMessage msg = new BmobIMTextMessage();
                    msg.setContent(bmobFileUrl);
                    //可随意设置额外信息
                    Map<String, Object> map = new HashMap<>();
                    map.put("type", ExtraMessageInfo.VOICE);
                    map.put("length", (int) length + "");
                    msg.setExtraMap(map);
                    msg.setExtra("OK");
                    mConversationManager.sendMessage(msg, listener);
                }
            }
        });
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
     *
     * @param item
     * @return
     */
    public Message dealWithData(BmobIMMessage item) {
        ExtraMessageInfo extraMessageInfo = new Gson().fromJson(item.getExtra(), ExtraMessageInfo.class);
        if (extraMessageInfo != null) {

            if ((ExtraMessageInfo.TEXT).equals(extraMessageInfo.type)) {
                return new TextMessage(item);
            }
            if (ExtraMessageInfo.IAMGE.equals(extraMessageInfo.type)) {
                return new ImageMessage(item);
            }

            if (ExtraMessageInfo.VOICE.equals(extraMessageInfo.type)) {
                return new VoiceMessage(item);
            }

        }


        return new TextMessage(item);
    }

}
