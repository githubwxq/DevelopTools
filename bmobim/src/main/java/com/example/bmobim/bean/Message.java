package com.example.bmobim.bean;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wxq.commonlibrary.bmob.CommonBmobUser;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobUser;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/25
 * desc:
 * version:1.0
 */
public abstract class Message implements MultiItemEntity {
    public   BmobIMMessage bmobIMMessage = null;
    public String currentUid="";

    public  BmobIMUserInfo userInfo;
    public Message(BmobIMMessage message) {
        bmobIMMessage=message;
        try {
            currentUid = BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId();
            userInfo= message.getBmobIMUserInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isSelfMessage(){
        if (bmobIMMessage.getFromId().equals(currentUid)) {
            return true;
        }else {
            return false;
        }

    }


    public abstract void showTime(boolean isShow);

    public abstract void updateView(BaseViewHolder helper);
}
