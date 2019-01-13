package com.wxq.commonlibrary.datacenter;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.wxq.commonlibrary.dao.DaoMaster;
import com.wxq.commonlibrary.dao.DaoSession;
import com.wxq.commonlibrary.dao.UserDao;
import com.wxq.commonlibrary.model.User;
import com.wxq.commonlibrary.util.Utils;

/**
 * @author wxq
 * @version V_1.0.0
 * @date 2017/04/25
 * @description app内所有数据获取管理 userperference  数据库 静态变量关于用户的 单列
 */
public class AllDataCenterManager {

    private final String DATA_BASE_NAME = "exuecloud.db";
    /**
     * 用户数据
     */
    public  UserPreference userPreference;

    /**
     * 应用共有数据
     */
    public  PublicPreference publicPreference;

    /**
     * 数据库需要
     */
    public DaoSession daoSession;

    /**
     * 接口需要的token
     */
    public String token;

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
        if (daoSession==null) {
            MyOpenHelper devOpenHelper = new MyOpenHelper(sApplication, DATA_BASE_NAME);
            DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession= daoMaster.newSession();
        }
        return daoSession;
    }




    private class MyOpenHelper extends DaoMaster.OpenHelper {
        MyOpenHelper(Context context, String name) {
            super(context, name);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            MigrationHelper.migrate(db);
        }
    }

    public UserPreference getUserPreference() {
        if (userPreference==null) {
            userPreference=new UserPreference();
        }
        return userPreference;
    }


    public PublicPreference getPublicPreference() {
        if (publicPreference==null) {
            publicPreference=new PublicPreference(sApplication);
        }
        return publicPreference;
    }



}
