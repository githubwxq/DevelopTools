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
 * @description 数据库获取类 不同的用户对应不同的数据库
 */
public class DbManager {

    private static DbManager dbManager;

    /**
     * 用户登录获取用户的唯一标识 作为数据库的名字
     *
     * @param DATA_BASE_NAME
     */
    public void setDATA_BASE_NAME(String DATA_BASE_NAME) {
        this.DATA_BASE_NAME = DATA_BASE_NAME;
        // 重新登入或者切换用户都需要更改这个 数据库名称然后 还需要重新设置daomaster
        needChangeDaoMaster = true;
    }

    private String DATA_BASE_NAME = "common.db"; //必须先设置
    //数据库名字对应每个用户的uid
    private DaoSession daoSession;
    private DaoMaster daoMaster;


    private MyOpenHelper devOpenHelper;

    private DbManager() {

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


    boolean needChangeDaoMaster = true; //是否需要更新daomaster 默认需要 当设置过了就不需要 除非 重新更改了需要的数据库名称

    public DaoSession getDaoSession() {
        if (daoMaster == null || needChangeDaoMaster) {
            devOpenHelper = new MyOpenHelper(BaseApp.getContext(), DATA_BASE_NAME);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            needChangeDaoMaster = false;
        }
        daoSession = daoMaster.newSession();
        return daoSession;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public MyOpenHelper getDevOpenHelper() {
        return devOpenHelper;
    }
}
