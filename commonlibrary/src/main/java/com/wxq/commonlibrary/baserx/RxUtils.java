//package com.wxq.commonlibrary.baserx;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Looper;
//import android.view.View;
//import android.widget.TextView;
//import com.jakewharton.rxbinding2.view.RxView;
//import com.jakewharton.rxbinding2.widget.RxTextView;
//import com.juziwl.uilibrary.dialog.DialogManager;
//import com.trello.rxlifecycle2.android.ActivityEvent;
//import com.trello.rxlifecycle2.android.FragmentEvent;
//import com.wxq.mvplibrary.R;
//import com.wxq.commonlibrary.base.BaseView;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.Flowable;
//import io.reactivex.FlowableTransformer;
//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.annotations.NonNull;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2017/4/29
// * @description
// */
//public class RxUtils {
//    private static final int DISPLAYONCE = 1;
//
//    /**
//     * Activity里面统一Observable处理
//     *
//     * @param activity     BaseActivity对象
//     * @param observable   需要耗时操作的Observable
//     * @param isNeedDialog 是否需要显示加载框
//     * @return 处理后的Observable
//     */
//    public static <T> Flowable<T> getActivityObservable(bas activity, Flowable<T> observable,
//                                                        boolean isNeedDialog) {
//        return observable.compose(rxSchedulerHelper(isNeedDialog ? activity : null))
//                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
//    }
//
//    /**
//     * fragment里面统一Observable处理
//     *
//     * @param fragment     BaseFragment对象
//     * @param observable   需要耗时操作的Observable
//     * @param isNeedDialog 是否需要显示加载框
//     * @return 处理后的Observable
//     */
//    public static <T> Flowable<T> getFragmentObservable(BaseView fragment, Flowable<T> observable,
//                                                        boolean isNeedDialog) {
//        return observable.compose(rxSchedulerHelper(isNeedDialog ? fragment.getActivity() : null))
//                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
//    }
//
//
//    /**
//     * 统一线程处理
//     */
//    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(Context activity) {    //compose简化线程
//        return upstream -> upstream.subscribeOn(Schedulers.io())
//                .doOnSubscribe(subscription -> {
//                    if (activity != null && Looper.myLooper() == Looper.getMainLooper()) {
//                        if (!DialogManager.getInstance().isShow()) {
//                            DialogManager.getInstance().createLoadingDialog(activity, activity.getString(R.string.common_onloading)).show();
//                        }
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    /**
//     * 全在子线程
//     */
//    public static <T> FlowableTransformer<T, T> rxThreadHelper() {
//        return upstream -> upstream.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io());
//    }
//
//
//    public static <T> FlowableTransformer<T, T> rxSchedulerHelperOnce(Activity activity, int flag) {    //compose简化线程
//        return upstream -> upstream.subscribeOn(Schedulers.io())
//                .doOnSubscribe(subscription -> {
//                    if (activity != null && Looper.myLooper() == Looper.getMainLooper()) {
//                        if (!DialogManager.getInstance().isShow()) {
//                            if (flag != DISPLAYONCE) {
//                                DialogManager.getInstance().createLoadingDialog(activity, activity.getString(R.string.common_onloading)).show();
//                            }
//                        }
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//    public static Flowable<Integer> countDown(int count) {
//        return Flowable.interval(1, TimeUnit.SECONDS)
//                .take(count + 1).map(aLong -> count - aLong.intValue());
//    }
//
//    private static AllClickInvocationHandler invocationHandler = null;
//
//    public static void click(View v, Consumer<Object> onNext, boolean... isNeedJudgeLogin) {
//        Consumer consumer;
//        if (isNeedJudgeLogin != null && isNeedJudgeLogin.length > 0 && isNeedJudgeLogin[0]) {
//            if (invocationHandler == null) {
//                invocationHandler = new AllClickInvocationHandler();
//            }
//            consumer = (Consumer) invocationHandler.bind(onNext);
//        } else {
//            consumer = onNext;
//        }
//        RxView.clicks(v).throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(consumer);
//    }
//
//    public static void longClick(View v, Consumer<Object> onNext) {
//        RxView.longClicks(v).subscribe(onNext);
//    }
//
//    private static class AllClickInvocationHandler implements InvocationHandler {
//
//        private Object object = null;
//
//        public Object bind(Object obj) {
//            object = obj;
//            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            if (Global.isLogin) {
//                return method.invoke(object, args);
//            } else {
//                Activity topActivity = AppManager.getInstance().getTopActivity();
//                Context context;
//                Intent intent = new Intent();
//                if (topActivity == null) {
//                    context = Global.application;
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                } else {
//                    context = topActivity;
//                }
//                intent.setClassName(context, Global.loginActivity);
//                if (intent.resolveActivity(context.getPackageManager()) != null) {
//                    context.startActivity(intent);
//                }
//            }
//            return null;
//        }
//    }
//
//    public static Observable<Boolean> meetMultiConditionDo(Function<Object[], Boolean> combiner, TextView... tvs) {
//        if (tvs != null && tvs.length > 0) {
//            List<Observable<CharSequence>> observableList = new ArrayList<>();
//            for (int i = 0; i < tvs.length; i++) {
//                observableList.add(RxTextView.textChanges(tvs[i]).skip(1));
//            }
//            return Observable.combineLatest(observableList, combiner);
//        }
//        return null;
//    }
//
//
//    //定时任务以及循环任务
//    private static Disposable mDisposable;
//
//    /**
//     * milliseconds毫秒后执行next操作
//     *
//     * @param milliseconds
//     * @param next
//     */
//    public static void timer(long milliseconds, final IRxNext next) {
//        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable disposable) {
//                        mDisposable = disposable;
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Long number) {
//                        if (next != null) {
//                            next.doNext(number);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        //取消订阅
//                        cancel();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //取消订阅
//                        cancel();
//                    }
//                });
//    }
//
//    /**
//     * 每隔milliseconds毫秒后执行next操作
//     *
//     * @param milliseconds
//     * @param next
//     */
//    public static void interval(long milliseconds, final IRxNext next) {
//        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable disposable) {
//                        mDisposable = disposable;
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Long number) {
//                        if (next != null) {
//                            next.doNext(number);
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }
//
//
//    /**
//     * 取消订阅
//     */
//    public static void cancel() {
//        if (mDisposable != null && !mDisposable.isDisposed()) {
//            mDisposable.dispose();
//        }
//    }
//
//    public interface IRxNext {
//        void doNext(long number);
//    }
//
//    public static <T> ObservableTransformer<T,T> io_main(){
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return  upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//}
