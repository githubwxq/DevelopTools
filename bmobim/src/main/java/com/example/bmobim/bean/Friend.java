package com.example.bmobim.bean;

import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.model.User;

import cn.bmob.v3.BmobObject;

/**
 * 好友表
 *wxq
 * @author smile
 * @project Friend
 * @date 2016-04-26
 */
//TODO 好友管理：9.1、创建好友表
public class Friend extends BmobObject {
    public CommonBmobUser getUser() {
        return user;
    }

    public void setUser(CommonBmobUser user) {
        this.user = user;
    }

    public CommonBmobUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(CommonBmobUser friendUser) {
        this.friendUser = friendUser;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public CommonBmobUser user;
    public CommonBmobUser friendUser;
    public transient String pinyin;
}
