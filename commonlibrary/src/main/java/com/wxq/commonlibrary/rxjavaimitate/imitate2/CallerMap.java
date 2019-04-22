package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * com.sxdsf.async.imitate2.TelephonerMap
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:36
 * @desc map操作符
 */

public class CallerMap<T, R> extends CallerWithUpstream<T, R> {

    private Function<T, R> mFunction;

    public CallerMap(Caller<T> source, Function<T, R> function) {
        super(source);
        mFunction = function;
    }

    @Override
    protected void callActual(Callee<R> callee) { //最新的callee 观察者 ，先调用上一个被观察的
        source.call(new MapCallee<>(callee, mFunction));
    }

    static class MapCallee<T, R> implements Callee<T> {   //手动创建的观察者被上一次的被上一次观察对象使用 然后会回调数据回来 这个数据来自于上一个被观察者发送的数据

        private final Callee<R> mCallee;

        private final Function<T, R> mFunction;

        public MapCallee(Callee<R> callee, Function<T, R> function) {
            mCallee = callee;
            mFunction = function;
        }

        @Override
        public void onCall(Release release) {
            mCallee.onCall(release);
        }

        @Override
        public void onReceive(T t) {
            R tR = mFunction.call(t);
            mCallee.onReceive(tR);
        }

        @Override
        public void onCompleted() {
            mCallee.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            mCallee.onError(t);
        }
    }
}
