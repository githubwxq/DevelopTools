package com.wxq.commonlibrary.toolsqlite.dao;

import android.database.sqlite.SQLiteDatabase;

import com.wxq.commonlibrary.toolsqlite.bean.DBInfo;
import com.wxq.commonlibrary.toolsqlite.util.Tool;
import com.wxq.commonlibrary.toolsqlite.util.ToolFile;


/**
 * 工厂类
 */
public class BaseDaoFactory {
    private static BaseDaoFactory baseDaoFactory;
    protected SQLiteDatabase sqLiteDatabase;
    //数据库的路径，建议写到SD卡中。好处：APP让删除了，下次再安装的时候，数据还在。
    protected static String dbPath = null;
    //数据库默认基础路径
    protected String dbDefaultBasePath = "common";
    //数据库默认名字
    protected String dbDefaultName = "common.db";
    //用户名和数据库名
    protected static DBInfo myDBInfo;

    /**
     * 创建默认数据库
     */
    protected BaseDaoFactory() {
        this(null);
    }

    /**
     * 创建指定数据库
     *
     * @param dbInfo
     */
    public BaseDaoFactory(DBInfo dbInfo) {
        if (dbInfo == null) {
            //创建数据库文件的父目录
            dbPath = ToolFile.getSDDirPath(dbDefaultBasePath) + "/" + dbDefaultName;
        } else {
            String path = dbDefaultBasePath;
            String name = dbDefaultName;
            if (!Tool.isEmpty(myDBInfo.getUserName())) {
                path += "/" + myDBInfo.getUserName();
            }
            if (!Tool.isEmpty(myDBInfo.getDbName())) {
                name = myDBInfo.getDbName() + ".db";
            }
            dbPath = ToolFile.getSDDirPath(path) + "/" + name;
        }

        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    /**
     * 获取数据库实例
     *
     * @return
     */
    public static BaseDaoFactory getInstance() {
        return getInstance(null);
    }

    /**
     * 获取指定数据库实例
     *
     * @param dbInfo 用户名和数据库名
     * @return
     */
    public static BaseDaoFactory getInstance(DBInfo dbInfo) {
        //dbInfo为null时，返回默认的baseDaoFactory
        if (dbInfo == null) {
            if (baseDaoFactory == null) {
                synchronized (BaseDaoFactory.class) {
                    if (baseDaoFactory == null) {
                        baseDaoFactory = new BaseDaoFactory();
                    }
                }
            }
            return baseDaoFactory;
        }

        //使用新的dbInfo时，根据新的dbInfo生成新的baseDaoFactory
        if (baseDaoFactory == null || !dbInfo.equals(myDBInfo)) {
            synchronized (BaseDaoFactory.class) {
                if (baseDaoFactory == null || !dbInfo.equals(myDBInfo)) {
                    myDBInfo = dbInfo;
                    baseDaoFactory = new BaseDaoFactory(dbInfo);
                }
            }
        }
        return baseDaoFactory;
    }


    /**
     * 用来生产basedao对象
     */
    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao<T> baseDao = new BaseDao<T>();
        baseDao.init(sqLiteDatabase, entityClass);
        return baseDao;
    }

    /**
     * 获取数据库路径
     *
     * @return 返回数据库路径
     */
    public static String getDBPath() {
        return dbPath;
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
        baseDaoFactory = null;
    }

    /**
     * 重启数据库
     */
    public void restartDB() {
        baseDaoFactory = null;
        getInstance(myDBInfo);
    }

}
