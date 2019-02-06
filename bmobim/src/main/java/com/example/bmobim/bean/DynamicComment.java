package com.example.bmobim.bean;

import com.wxq.commonlibrary.bmob.CommonBmobUser;

import cn.bmob.v3.BmobObject;

public class DynamicComment extends BmobObject {

    public String content;
    public String pic;
    public String video;
    // 评论的作者 一对一
    public CommonBmobUser author;
    // 回复的那个人的信息
    public CommonBmobUser otherPeople;
    // 所评论的帖子 是一对多 一个评论对应一个帖子(一个帖子能有多个评论)
    public DynamicBean card;
}