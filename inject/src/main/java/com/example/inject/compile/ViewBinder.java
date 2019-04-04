package com.example.inject.compile;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/03/28
 * desc:编译时传入
 * version:1.0
 */
public interface ViewBinder<T>
{
    //
    void bind(T target);

}
