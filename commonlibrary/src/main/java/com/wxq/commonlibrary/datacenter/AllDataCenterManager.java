package com.wxq.commonlibrary.datacenter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.wxq.commonlibrary.dao.DaoSession;
import com.wxq.commonlibrary.dbmanager.DbManager;
import com.wxq.commonlibrary.model.UserInfo;

/**
 * @author wxq
 * @version V_1.0.0
 * @date 2017/04/25
 * @description app内所有数据获取管理 userperference  数据库 静态变量关于用户的 单列
 */
public class AllDataCenterManager {

    private final String DATA_BASE_NAME = "develop.db";  //不同用户对应不同数据库
    /**
     * 用户数据
     */
    public UserPreference userPreference;

    /**
     * 应用共有数据
     */
    public PublicPreference publicPreference;

    /**
     * 数据库需要
     */
    public DaoSession daoSession;

    /**
     * 接口需要的token
     */
    public String token;

    /**
     * 个人信息临时变量
     */
    public UserInfo userInfo;

    private static Application sApplication;

    public static void init(@NonNull final Application app) {
        if (sApplication == null) {
            sApplication = app;
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    private static class SingletonHolder {
        private static AllDataCenterManager instance = new AllDataCenterManager();
    }

    public static AllDataCenterManager getInstance() {
        return SingletonHolder.instance;
    }

    public DaoSession getDaoSession() {
        daoSession =  DbManager.getInstance().getDaoSession();
        return daoSession;
    }

    public DaoSession getDaoSession(String dbName) {
        DbManager.getInstance().setDATA_BASE_NAME(dbName);
        daoSession =  DbManager.getInstance().getDaoSession();
        return daoSession;
    }

    public UserPreference getUserPreference() {
        if (userPreference == null) {
            userPreference = new UserPreference();
        }
        return userPreference;
    }


    public PublicPreference getPublicPreference() {
        if (publicPreference == null) {
            publicPreference = new PublicPreference(sApplication);
        }
        return publicPreference;
    }


}


//更具不同的用户采取不同的数据库 不同数据库里面有相同的表
//              int size = AllDataCenterManager.getInstance().getDaoSession().getUserDao().queryBuilder().list().size();
//               ToastUtils.showShort(size+"大小");
//                Log.e("wxq",size+"大小");
//                int size2 = AllDataCenterManager.getInstance().getDaoSession("wxq.db").getUserDao().queryBuilder().list().size();
//                Log.e("wxq",size2+"大小");
//更具不同的用户采取不同的share 不同数据库里面有相同的表
//                       setUserid 设置用户的id