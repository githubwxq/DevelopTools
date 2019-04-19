package com.wxq.commonlibrary.rxjavaimitate.imitate2;

import java.util.concurrent.atomic.AtomicReference;

/**
 * com.sxdsf.async.imitate2.CallerCreate
 *
 * @author SXDSF
 * @date 2017/11/5 下午11:52
 * @desc 实际的Caller
 */

public final class CallerCreate<T> extends Caller<T> {

    private CallerOnCall<T> mCallerOnCall;

    public CallerCreate(CallerOnCall<T> callerOnCall) {
        mCallerOnCall = callerOnCall;
    }

    @Override
    protected void callActual(Callee<T> callee) {
        CreateEmitter<T> tCreateEmitter = new CreateEmitter<>(callee);
        callee.onCall(tCreateEmitter);
        mCallerOnCall.call(tCreateEmitter);
    }

    public static final class CreateEmitter<T> extends AtomicReference<Release> implements CallerEmitter<T>, Release {

        private Callee<T> mCallee;

        public CreateEmitter(Callee<T> callee) {
            mCallee = callee;
        }

        @Override
        public void onReceive(T t) {
            if (!isReleased()) {
                mCallee.onReceive(t);
            }
        }

        @Override
        public void onCompleted() {
            if (!isReleased()) {
                mCallee.onCompleted();
            }
        }

        @Override
        public void onError(Throwable t) {
            if (!isReleased()) {
                mCallee.onError(t);
            }
        }

        @Override
        public boolean isReleased() {
            return ReleaseHelper.isReleased(get());
        }

        @Override
        public void release() {
            ReleaseHelper.release(this);
        }
    }
}
