package com.example.commonservice;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;

public class RouteServiceManager {

    private static final String TAG = "RouteServiceManager";

    public static <T extends IProvider> T provide(Class<T> clz, String path) {

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        IProvider provider = null;

        try {
            provider = (IProvider) ARouter.getInstance()
                    .build(path)
                    .navigation();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return (T) provider;
    }

    public static <T extends IProvider> T provide(Class<T> clz) {

        IProvider provider = null;
        try {
            provider = ARouter.getInstance().navigation(clz);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return (T) provider;
    }


    /**
     * 获取用户名字的服务
     *
     * @param userId
     * @return
     */
//    public static String getUserName(String userId) {
//
//        IUserInfoService userInfoModuleService = null;
//
//        //方式1：模块间通过类名称发现服务
//        userInfoModuleService = ARouter.getInstance().navigation(IUserInfoService.class);
//
//        //方式2：模块间通过路径名称发现服务
//        userInfoModuleService = (IUserInfoService) ARouter.getInstance()
//                .build(ServicePathTag.Service_UserInfo)
//                .navigation();
//
//        if (userInfoModuleService != null) {
//            return userInfoModuleService.getUserName(userId);
//        }
//
//        return "";
//    }


}
//---------------------
//作者：千里逐梦
//来源：CSDN
//原文：https://blog.csdn.net/user11223344abc/article/details/79913830
//版权声明：本文为博主原创文章，转载请附上博文链接！