package com.wxq.commonlibrary.mydb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.wxq.commonlibrary.mydb.annotion.DbFiled;
import com.wxq.commonlibrary.mydb.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/08
 * desc:抽象类实现接口处理一些通用步骤操作 减少子类而外方法添加
 * version:1.0
 */
public abstract class BaseDao<T> implements IBaseDao<T> {

    /**
     * ]
     * 持有数据库操作类的引用
     */
    private SQLiteDatabase database;
    /**
     * 保证实例化一次
     */
    private boolean isInit = false;

    /**
     * 持有操作数据库表所对应的java类型
     * User
     */
    private Class<T> entityClass;

    /**
     * 维护这表名与成员变量名的映射关系
     * key---》表名
     * value --》Field
     * class  methoFiled
     * {
     * Method  setMthod
     * Filed  fild
     * }
     */
    private HashMap<String, Field> cacheMap;

    private String tableName;

    /**
     * @param entity
     * @param sqLiteDatabase
     * @return 实例化一次
     */
    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            entityClass = entity;
            database = sqLiteDatabase;
            if (entity.getAnnotation(DbTable.class) == null) {
                tableName = entity.getClass().getSimpleName();
            } else {
                tableName = entity.getAnnotation(DbTable.class).value();
            }

            if (!database.isOpen()) {
                return false;
            }
            if (!TextUtils.isEmpty(createTable())) {
                database.execSQL(createTable());
            }
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    private void initCacheMap() {
 /*
        第一条数据  查0个数据
         */
        String sql = "select * from " + this.tableName + " limit 1 , 0";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(sql, null);
            /**
             * 表的列名数组
             */
            String[] columnNames = cursor.getColumnNames();
            /**
             * 拿到Filed数组
             */
            Field[] colmunFields = entityClass.getFields();
            for (Field filed : colmunFields) {
                filed.setAccessible(true);
            }
            /**
             * 开始找对应关系
             */
            for (String colmunName : columnNames) {
                /**
                 * 如果找到对应的Filed就赋值给他
                 * User
                 */
                Field colmunFiled = null;
                for (Field field : colmunFields) {
                    String fieldName = null;
                    if (field.getAnnotation(DbFiled.class) != null) {
                        fieldName = field.getAnnotation(DbFiled.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    /**
                     * 如果表的列名 等于了  成员变量的注解名字
                     */
                    if (colmunName.equals(fieldName)) {
                        colmunFiled = field;
                        break;
                    }
                }
                //找到了对应关系
                if (colmunFiled != null) {
                    cacheMap.put(colmunName, colmunFiled);
                }
            }

        } catch (Exception e) {

        } finally {
            cursor.close();
        }


    }


    /**
     * 创建表
     *
     * @return
     */
    protected abstract String createTable();
}
