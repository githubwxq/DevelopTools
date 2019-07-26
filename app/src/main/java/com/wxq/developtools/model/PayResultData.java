package com.wxq.developtools.model;

public class PayResultData {
    public String payInfo;
    public String weixinPayAppKey;

    @Override
    public String toString() {
        return "PayResultData{" +
                "payInfo='" + payInfo + '\'' +
                ", weixinPayAppKey='" + weixinPayAppKey + '\'' +
                '}';
    }
}
