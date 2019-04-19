package com.wxq.commonlibrary.rxjavaframwork;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 集市
 * T   猪肉
 */
public class Observable<T> {

    //被观察者 由用户new出来并且call 方法里面 调用   上一级的observable
    private  OnSubscrible<T> onSubscrible;

    private  Observable(OnSubscrible<T> onSubscrible) {
        this.onSubscrible = onSubscrible;
    }

    public static <T> Observable<T> create(OnSubscrible<T> onSubscrible)
    {
        return new Observable<T>(onSubscrible);
    }

    /**
     * super  1
     * extends 2
     */
    public void subscrible(NewObserver<? super T> newObserver)
    {
        //newObserver 观察者 主动调用被观察者的call方法 传入观察者对象

//        newObserver 调用被观察者的call 方法事件开始流动

        onSubscrible.call(newObserver);  //回调onnext
    }

    /**
     * <R> Observable<R>
     *     返回杀猪刀的集市
     *     记住  这是对象方法
     *     对象  猪肉集市
     *     new OperatorMap<>(func1)
     *     sh实例化 中介者
     * @param func1
     * @param <R>
     * @return     参数新的观察者 但是 新的观察者会拥有老的观察者对象 ，外面subscrbe 观察者会 嗲用 OnSubscribleLift （m最新的观察者）
     */
    public <R> Observable<R> map(Func1<? super T,? extends R> func1)
    {
        //T 最初的数据  r待转的数据
        return lift(new OperatorMap<>(func1));
    }
    /**
     * new OnSubscribleLift<>(onSubscrible,trOperatorMap)    实例化一个商人
     * 并把商人丢到 杀猪刀的集市
     * @param trOperatorMap
     * @param <R>
     * @return
     * zh
     */
    public  <R> Observable<R> lift(OperatorMap<T, R> trOperatorMap) {
        //返回新的被观察者  重新设置了被观察者  trOperatorMap构建者   返回 新的被观察者 注意不是老的是新的 新的里面
        return new Observable<>(new OnSubscribleLift<>(onSubscrible,trOperatorMap)); //新对象了 （被观察者是 带t的）
    }

    public  Observable<T> subscribleOnIO()
    {
        return create(new OnSubscribleOnIO<T>(this));
    }
    public Observable<T> subscribleMain()
    {
        //每次都重新创建 Observable   内部包含一个被观察者 当sbscribe 的时候调用了
        return  create(new OnSubscrbleMain<T>(new Handler(Looper.getMainLooper()),this));
    }
}
