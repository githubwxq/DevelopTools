package com.example.sdktestdemo;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.commonservice.BaseService;
import com.example.commonservice.ServiceCallBack;
import com.example.commonservice.sdk.bean.SdkBean;
import com.example.commonservice.sdk.service.SdkService;
import com.wxq.mvplibrary.router.RouterContent;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2018/11/13
 * desc: 其实这是一个单列
 * version:1.0
 */

@Route(path = RouterContent.SDK_SERVICE)
public class SdkServiceImpl extends BaseService implements SdkService {
    @Override
    public SdkBean getSdkBean(String uid) {
//        getDataBack(1111);
        Toast.makeText(mContext, uid, Toast.LENGTH_SHORT).show();
        setDataBack("我是返回的東西");

        ARouter.getInstance()
                .build(RouterContent.UI_MAIN)
                .navigation();

        return new SdkBean(uid + "22222");
    }
}
