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

    //被观察者
    private CallerOnCall<T> mCallerOnCall;

    public CallerCreate(CallerOnCall<T> callerOnCall) {
        mCallerOnCall = callerOnCall;
    }

    @Override
    protected void callActual(Callee<T> callee) {
        //观察者被放到了emitter里面
        CreateEmitter<T> tCreateEmitter = new CreateEmitter<>(callee);
        callee.onCall(tCreateEmitter);
        //回调外面 实现方法 然后 手动调用 tCreateEmitter onnext方法传递数据  由于emitter当中有callee
        // 会调用callee的方法 receive方法数据到后面监听到了
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
//                、、手抖动调用next方法
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
