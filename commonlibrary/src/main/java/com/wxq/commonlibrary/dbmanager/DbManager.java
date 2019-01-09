package com.wxq.commonlibrary.dbmanager;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.wxq.commonlibrary.base.BaseApp;
import com.wxq.commonlibrary.dao.DaoMaster;
import com.wxq.commonlibrary.dao.DaoSession;
import com.wxq.commonlibrary.dao.UserDao;


/**
 * @author wxq
 * @version V_1.0.0
 * @date 2017/04/25
 * @description
 */
public class DbManager {

    private static DbManager dbManager;
    private final String DATA_BASE_NAME = "common.db";
    private DaoSession daoSession;
    DaoMaster daoMaster;



    MyOpenHelper devOpenHelper;

    private DbManager() {
        devOpenHelper = new MyOpenHelper(BaseApp.getContext(), DATA_BASE_NAME);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    /**
     * 单一实例
     */
    public static DbManager getInstance() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }


    public DaoSession provideDaoSession(Application context) {

        return daoMaster.newSession();
    }

    private class MyOpenHelper extends DaoMaster.OpenHelper {

        MyOpenHelper(Context context, String name) {
            super(context, name);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //数据库更新
            MigrationHelper.migrate(db, UserDao.class);
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public MyOpenHelper getDevOpenHelper() {
        return devOpenHelper;
    }
}
