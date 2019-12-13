package com.wxq.commonlibrary.mydb.db;

import java.util.List;

/**
 * 规范所有的数据库操作
 */

public interface IBaseDao<T> {
    long insert(T entity);

    long update(T entity, T where);

    int delete(T where);

    List<T> query(T where);
    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);


}












