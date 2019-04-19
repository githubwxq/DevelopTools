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
    protected void callActual(Callee<R> callee) {
        source.call(new MapCallee<>(callee, mFunction));
    }

    static class MapCallee<T, R> implements Callee<T> {

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
