package com.example.commonservice.sdk.service;

import com.example.commonservice.BaseIService;
import com.example.commonservice.sdk.bean.SdkBean;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/13
 * desc:模块交互
 * version:1.0
 */
public interface SdkService extends BaseIService {

    SdkBean getSdkBean(String uid);


}
