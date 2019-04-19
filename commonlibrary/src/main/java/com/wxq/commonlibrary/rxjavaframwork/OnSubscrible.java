package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 被观察者
 * 养羊的人
 * T  代表打铁
 * 命令铁匠   打铁
 * 铁匠 --》  打铁的工匠
 *没有添加成员
 *
 */

//被观察者  有个call方法  到时候手动调用的
public interface OnSubscrible<T>  extends  Action1<NewObserver<? super T>> {


}
