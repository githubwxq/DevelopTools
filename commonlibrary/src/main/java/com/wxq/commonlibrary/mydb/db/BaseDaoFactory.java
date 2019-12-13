package com.wxq.commonlibrary.mydb.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wxq on 2018/3/7.
 */

public  class BaseDaoFactory {
    private static final BaseDaoFactory ourInstance=new BaseDaoFactory();
    public static BaseDaoFactory getOurInstance(){
        return ourInstance;
    }
    private SQLiteDatabase sqLiteDatabase;
    //定义建数据数据的路径
    //建议写到SD卡中，好处，APP让删除了，下次再安装的时候，数据还在
    private String sqliteDatabasePath;
    private BaseDaoFactory(){
        //可以先判断有没有SD卡
        sqliteDatabasePath="data/data/com.example.wxq/jett.db";
        sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath,null);
    }

    /**
     * 用来生产basedao对象
     * M  User
     * T  BaseDao
     */
    public  <T extends BaseDao<M>,M> T  getBaseDao(Class<T> daoClass,Class<M> entityClass){
        BaseDao baseDao=null;
        try {
            baseDao=daoClass.newInstance();
            baseDao.init(sqLiteDatabase,entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T)baseDao;
    }
}










