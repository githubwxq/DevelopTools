package com.wxq.commonlibrary.model;

import java.io.Serializable;


/**
 * @author wxq
 * @version V_1.0.0
 * @date 2017/07/31
 * @description 统一的请求参数
 */
public class KlookResponseData<T> implements Serializable {
    public String msg = "";
    public int code = 0;
    public long timestamp = 0;
    public T data;
    public String timeStamp; //时间
    //  操作符单独处理的
    public boolean rxJust;



}
