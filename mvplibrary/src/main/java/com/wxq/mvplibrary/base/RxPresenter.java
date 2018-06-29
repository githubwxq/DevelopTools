package com.wxq.mvplibrary.base;



import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by codeest on 2016/8/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
public  abstract  class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    public RxPresenter(T view) {
        mView = view;
    }


    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public BasePresenter attachView(T view) {
        mView=view;
        return this;
    }

    //    protected <U> void addRxBusSubscribe(Class<U> eventType, Consumer<U> act) {
//        if (mCompositeDisposable == null) {
//            mCompositeDisposable = new CompositeDisposable();
//        }
//        mCompositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType, act));
//    }




    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    @Override
    public void addDisposable(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
