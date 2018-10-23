package com.wxq.commonlibrary.model;

import java.io.Serializable;


/**
 * @author wxq
 * @version V_1.0.0
 * @date 2017/07/31
 * @description 统一的请求参数
 */
public class ResponseData<T> implements Serializable {
    public String errorMsg = "";
    public String status = "";
    public T content;
    public String timeStamp; //时间
    //  操作符单独处理的
    public boolean rxJust;

    @Override
    public String toString() {
        return "ResponseData{" +
                "code='" + status + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", content=" + content.toString() +
                '}';
    }
}
