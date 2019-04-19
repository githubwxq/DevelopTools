package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 中介 的抽象
 */

public interface Operator<T,R> extends Func1<NewObserver<? super T>, NewObserver<? super R>> {   //、、一个观察者转换为另一个

}
