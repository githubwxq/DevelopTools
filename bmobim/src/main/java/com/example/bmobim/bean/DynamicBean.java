package com.example.bmobim.bean;

import com.wxq.commonlibrary.bmob.CommonBmobUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 王晓清.
 * data 2019/2/2.
 * describe .动态bean
 */
public class DynamicBean extends BmobObject {

    public String content;

    public String pics;

    public String video;

    public String videoImage;

    public String length;

    public String address;

    //发布人
    public CommonBmobUser publishUser;

    //评论和回复的集合
    /**
     * 一对多关系：用于存储喜欢该动态的所有评论
     */
    public BmobRelation allComments;

    /**
     * 直接存放评论集合
     */
    public List<DynamicComment> dynamicCommentList=new ArrayList<>();





}
