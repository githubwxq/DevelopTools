package com.juziwl.uilibrary.easycommonadapter;

import java.util.List;

/**
 * 项目名称: CommonAdapter
 * 包 名 称: com.classic.adapter
 * @author null
 * @modify Neil
 * 类 描 述: 数据操作接口规范
 * 创 建 人: 续写经典
 * 创建时间: 2016/1/21 17:54.
 */
@SuppressWarnings("unused") public interface IData<T> {

    /**
     * 获取当前数据列表
     * @return
     */
    List<T> getData();

    /**
     * 添加一条数据
     * @param item
     */
    void add(T item);

    /**
     * 添加多条数据
     * @param list
     */
    void addAll(List<T> list);

    /**
     * 添加多条数据
     * @param list
     */
    void addAll(int index, List<T> list);

    /**
     * 更新一条数据
     * @param oldItem
     * @param newItem
     */
    void set(T oldItem, T newItem);

    /**
     * 根据下标更新一条数据
     * @param index
     * @param item
     */
    void set(int index, T item);

    /**
     * 删除一条数据
     * @param item
     */
    void remove(T item);

    /**
     * 根据下标删除一条数据
     * @param index
     */
    void remove(int index);

    /**
     * 替换所有数据
     * @param item
     */
    void replaceAll(List<T> item);

    /**
     * 是否存在某个对象
     * @param item
     * @return
     */
    boolean contains(T item);

    /**
     * 清空数据
     */
    void clear();
}
