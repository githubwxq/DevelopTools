package com.wxq.commonlibrary.util;

/**
 *单列通用类
 */
public class SingletonVoliate{
    //当单例对象被修饰成voliate后，每一次instance内存中的读取都会从主内存中获取，而不会从缓存中获取，这样就解决了双重效验锁单例模式的缺陷
    private static volatile SingletonVoliate instance =null;
    private SingletonVoliate(){}
    public static SingletonVoliate getInstance(){
      if(instance ==null){
        synchronized(SingletonVoliate.class){
            if(instance ==null){
                instance = new SingletonVoliate();
            }
         }
      }
        return instance;
    }
}