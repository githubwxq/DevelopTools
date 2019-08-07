package com.example.module_login.bean;

//import cn.bmob.v3.BmobObject;  extends BmobObject

public class Comment  {

    public String content;
    // 评论的作者 一对一
    public User author;
    // 所评论的帖子 是一对多 一个评论对应一个帖子(一个帖子能有多个评论)
//    public Card card;

}