package com.wxq.commonlibrary.rxjavaimitate.imitate2;


/**
 * com.sxdsf.async.imitate2.CallerCallOn
 *
 * @author SXDSF
 * @date 2017/11/18 下午6:50
 * @desc 用于callOn
 */

public class CallerCallOn<T> extends CallerWithUpstream<T, T> {

    private Switcher mSwitcher;

    public CallerCallOn(Caller<T> source, Switcher switcher) {
        super(source);
        mSwitcher = switcher;
    }

    @Override
    protected void callActual(Callee<T> callee) {
        final CallOnCallee<T> tCallOnCallee = new CallOnCallee<>(callee);
        callee.onCall(tCallOnCallee);
        mSwitcher.switches(new Runnable() {
            @Override
            public void run() {
                source.call(tCallOnCallee);
            }
        });
    }

    private static final class CallOnCallee<T> implements Callee<T>, Release {

        private final Callee<T> mCallee;

        public CallOnCallee(Callee<T> callee) {
            mCallee = callee;
        }

        @Override
        public void onCall(Release release) {

        }

        @Override
        public void onReceive(T t) {
            mCallee.onReceive(t);
        }

        @Override
        public void onCompleted() {
            mCallee.onCompleted();
        }

        @Override
        public void onError(Throwable t) {
            mCallee.onError(t);
        }

        @Override
        public boolean isReleased() {
            return false;
        }

        @Override
        public void release() {

        }
    }
}
