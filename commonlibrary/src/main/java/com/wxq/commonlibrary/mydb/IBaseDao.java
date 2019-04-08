package com.wxq.commonlibrary.mydb;

import java.util.List;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/08
 * desc:
 * version:1.0
 */
public interface IBaseDao<T> {
    /**
     * 插入数据
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     *
     * @param entity
     * @param where
     * @return
     */
    int  update(T entity,T where);

    /**
     * 删除数据
     * @param where
     * @return
     */
    int  delete(T where);

    /**
     * 查询数据
     */
    List<T> query(T where);

    /**
     * 查询列表数据
     */
    List<T> query(T where,String orderBy,Integer startIndex,Integer limit);

    /**
     * sql原生查询
     */
    List<T> query(String sql);



}
