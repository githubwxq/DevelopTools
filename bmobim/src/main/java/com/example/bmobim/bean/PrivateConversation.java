package com.example.bmobim.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.example.bmobim.R;
import com.example.bmobim.activity.ChatActivity;
import com.google.gson.Gson;

import java.util.List;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMConversationType;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;

/**
 * 私聊会话
 * Created by Administrator on 2016/5/25.
 */
public class PrivateConversation extends Conversation{

    private BmobIMConversation conversation;
    private BmobIMMessage lastMsg;

    public PrivateConversation(BmobIMConversation conversation){
        this.conversation = conversation;
        cType = BmobIMConversationType.setValue(conversation.getConversationType());
        cId = conversation.getConversationId();
        if (cType == BmobIMConversationType.PRIVATE){
            cName=conversation.getConversationTitle();
            if (TextUtils.isEmpty(cName)) cName = cId;
        }else{
            cName="未知会话";
        }
        List<BmobIMMessage> msgs =conversation.getMessages();
        if(msgs!=null && msgs.size()>0){
            lastMsg =msgs.get(0);
        }
    }

    @Override
    public void readAllMessages() {
        conversation.updateLocalCache();
    }

    @Override
    public Object getAvatar() {
        if (cType == BmobIMConversationType.PRIVATE){
            String avatar =  conversation.getConversationIcon();
            if (TextUtils.isEmpty(avatar)){//头像为空，使用默认头像
                return R.mipmap.common_default_head;
            }else{
                return avatar;
            }
        }else{
            return R.mipmap.common_default_head;
        }
    }

    @Override
    public String getLastMessageContent() {
        if(lastMsg!=null){
            String content =lastMsg.getContent();
            ExtraMessageInfo extraMessageInfo = new Gson().fromJson(lastMsg.getExtra(), ExtraMessageInfo.class);
            if (extraMessageInfo==null) {
                return "[未知]";
            }else {
                if ((ExtraMessageInfo.TEXT).equals(extraMessageInfo.type)||lastMsg.getMsgType().equals("agree")) {
                    return content;
                }
                if (ExtraMessageInfo.IAMGE.equals(extraMessageInfo.type)) {
                    return "[图片]";
                }
                if (ExtraMessageInfo.VOICE.equals(extraMessageInfo.type)) {
                    return "[语音]";
                }
                if (ExtraMessageInfo.VIDEO.equals(extraMessageInfo.type)) {
                    return "[视频]";
                }
                return "[未知]";
            }
        }else{  //防止消息错乱
            return "";
        }
    }

    @Override
    public long getLastMessageTime() {
        if(lastMsg!=null) {
            return lastMsg.getCreateTime();
        }else{
            return 0;
        }
    }

    @Override
    public int getUnReadCount() {
        //TODO 会话：4.3、查询指定会话下的未读消息数
        return (int) BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChatActivity.class);
        intent.putExtra("c",conversation);
        context.startActivity(intent);
    }

    @Override
    public void onLongClick(Context context) {
        //TODO 会话：4.5、删除会话，以下两种方式均可以删除会话
        //BmobIM.getInstance().deleteConversation(conversation.getConversationId());
        BmobIM.getInstance().deleteConversation(conversation);
    }
}
