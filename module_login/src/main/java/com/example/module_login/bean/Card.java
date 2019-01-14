package com.example.module_login.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/14
 * desc:wxq 帖子
 * version:1.0
 */
public class Card extends BmobObject {

    public String name;
    public String content;
    public User user; //作者
//    public BmobFile image;

    public BmobRelation likes; //关注的人
    public String images;  //图片




}
