package com.wxq.commonlibrary.rxjavaframwork;

/**
 * Created by Administrator on 2017/2/17 0017.
 * 具体的中介
 * T  猪肉
 * R  杀猪刀
 * map操作符
 */

public class OperatorMap<T, R> implements Operator<R, T> {

    Func1<? super T, ? extends R> transform;

    public OperatorMap(Func1<? super T, ? extends R> transform) {
        this.transform = transform;
    }

    //NewObserver<? super R> newObserver  R 转成T的观察者
    @Override
    public NewObserver<? super T> call(NewObserver<? super R> newObserver) {   //传一个上面一层的观察者经历啊变成另一个观察者
        /*
        返回打杀猪刀的朋友
         */
        return new MapSubscrble<>(newObserver, transform);
    }

    /**
     * 打杀猪刀的朋友
     *
     * @param <T>
     * @param <R> 我们自己见得观察者
     */
    private class MapSubscrble<T, R> extends NewObserver<T> {
        private NewObserver<? super R> actual;   // 未转化之前的观察者  当前是已经转化过的观察者 接收刚开始 onnext 数据   onnext（创建的第一个数据类似递归 ）

        private Func1<? super T, ? extends R> transform;    //t string  rbitmap

        /**
         * 观察者
         *
         * @param actual
         * @param transform
         */
        public MapSubscrble(NewObserver<? super R> actual, Func1<? super T, ? extends R> transform) {
            this.actual = actual;//最新一个观察者
            this.transform = transform;
        }

        @Override
        public void onNext(T t) {
            //这是从上往下    步骤二拿到 步骤yi传下来的   string
            //返回bitmap    这个T是谁传过来的？
            R r = transform.call(t); // map将string 转换bitmap的观察者

//            actual真实的那个 他的数据来自于 parent观察者发出来的数据经过转化产生的数据

            actual.onNext(r); //上一个观察者 刚好需要bitmap的 actual最终的 onnext方法
        }
    }
}
