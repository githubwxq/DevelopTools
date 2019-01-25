package com.example.bmobim.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/25
 * desc:
 * version:1.0
 */
public abstract class Message implements MultiItemEntity {
    private  BmobIMMessage bmobIMMessage = null;

//    TEXT(1, "txt"),
//    IMAGE(2, "image"),
//    VOICE(3, "sound"),
//    LOCATION(4, "location"),
//    VIDEO(5, "video");


    private int itemType;
    public Message(BmobIMMessage message) {
        if(message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
            this.itemType = BmobIMMessageType.IMAGE.getValue();
        }

    }


    @Override
    public int getItemType() {
        return itemType;
    }
}
