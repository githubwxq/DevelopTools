package com.example.bmobim.bean;

import com.wxq.commonlibrary.bmob.CommonBmobUser;

import cn.bmob.v3.BmobObject;

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

}
