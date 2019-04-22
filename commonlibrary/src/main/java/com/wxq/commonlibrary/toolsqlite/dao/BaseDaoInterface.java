package com.wxq.commonlibrary.toolsqlite.dao;

import com.wxq.commonlibrary.toolsqlite.bean.DBInfo;

import java.util.List;



/**
 * 规范所有的数据库操作
 */
public interface BaseDaoInterface<T> {
    long insert(T entity);

    long insert(List<T> entity);

    long update(T entity, T where);

    int delete(T where);

    List<T> query();

    List<T> query(T where);

    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);

    boolean deleteTable();

    <M> int upgrade(DBInfo dbInfo, Class<M> newTable);

}
