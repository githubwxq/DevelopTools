package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 商人
 * T  猪肉    String
 * R 杀猪刀   bitmap
 * 新的被订阅者内部含有他的上一级的被订阅者对象
 */
public class OnSubscribleLift<T, R> implements OnSubscrible<R> {
    //持有屠夫的引用
    OnSubscrible<T> parent;  //上一级的subscribe  T String类型
    /**
     * 中介
     * 拿着猪肉 去 换杀猪刀
     * 杀猪刀 作为返回类型    猪肉作为  参数类型
     * 杀猪刀  call (猪肉）
     * extends   返回类型的限定
     * super     参数类型的限定
     * T  猪肉
     * R 杀猪刀
     */
    Operator<? extends R, ? super T> operator;

    public OnSubscribleLift(OnSubscrible<T> parent, Operator<? extends R, ? super T> operator) {
        this.parent = parent; //持有上一级的被观察观察者
        this.operator = operator;
    }


//    call  方法 这内部处理 正常情况的call方法由外部重写调用此刻

//    最后会调用最新的观察者的call方法  map产生观察者的call 方法 并将 被观察者call方法同时传入最新的观察者
//

    /**
     * 目的  让中介 返回打杀猪刀的朋友
     *
     * @param newObserver 的时候 的call方法 被调用  但是这边的call map自己帮你处理对外不用关心
     */
    @Override
    public void call(NewObserver<? super R> newObserver) {    // bitmap的观察者 外面传景来的
    /*
    operator  中介
    NewObserver  打杀猪刀的人
     */
        NewObserver<? super T> st = operator.call(newObserver); //newObserver 转换新的观察者 String 类型
        // 商人直接将  打杀猪刀的朋友  丢给  屠夫
        //目的  打把杀猪刀  手动去掉
        parent.call(st); //回到上一级的被观察者 call方法（最上面的那个实现call 同时 st 的onnext方法被调用）需要我们实现的  开始调用 onnext 方法   这是从上往下    步骤一个  st 又会调用onnext方法
    }
}
