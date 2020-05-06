package com.example.uitestdemo;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestRxActivity extends BaseActivity {

    private static final String TAG = "TestRxActivity";
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_test_rx;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.tv_1,
            R.id.tv_2,
            R.id.tv_3,
            R.id.tv_4,
            R.id.tv_5,
            R.id.tv_6,
            R.id.tv_7,
            R.id.tv_8,
            R.id.tv_9,
            R.id.tv_10,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
//                test1();
//                fromArray();
//                timer();
//                interval();
//                concatArray();
//                zip();
//                subscribOn();
//                doOn();
                zip();
                break;


            case R.id.tv_2:
                testDisposable();
                break;
            case R.id.tv_3:
                testThread();
                break;
            case R.id.tv_4:
                testMap();
                break;
            case R.id.tv_5:
                testFlatMap();
                break;

            case R.id.tv_6:
                testConcatMap();
                break;


        }
    }


    private void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    list.add("Value is:" + integer + "  " + i);
                }
                return Observable.fromIterable(list).delay(1000, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept: " + s);
            }
        });

    }


    private void testConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> stringList = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    stringList.add("Value is" + integer + "  " + i);
                }
                return Observable.fromIterable(stringList).delay(100, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept: " + s);
            }
        });

    }

    private void testMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "这是:" + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, s);
            }
        });


    }

    /**
     * 测试线程
     */
    private void testThread() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: " + integer);
                        Log.i(TAG, "accept: " + Thread.currentThread().getName());
                    }
                });


    }

    private void testDisposable() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i(TAG, "subscribe: 1");
                e.onNext(1);
                Log.i(TAG, "subscribe: 2");
                e.onNext(2);
                Log.i(TAG, "subscribe: 3");
                e.onNext(3);
                Log.i(TAG, "subscribe: complete");
                e.onComplete();
                Log.i(TAG, "subscribe: 4");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable disposable;
            private int count;

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.i(TAG, "onNext: " + value);
                count++;
                if (count == 2) {
                    disposable.dispose();
                    Log.i(TAG, "onNext: dispose");
                    Log.i(TAG, "onNext: " + disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });

    }

    /**
     * 简单测试
     */
    private void test1() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.i(TAG, "emitter1: ");
                emitter.onNext(1);
                Log.i(TAG, "emitter2: ");
                emitter.onNext(2);
                Log.i(TAG, "emitter3: ");
                emitter.onNext(3);
                Log.i(TAG, "onComplete: ");
                emitter.onComplete();
                Log.i(TAG, "emitter4: ");
                emitter.onNext(4);
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };

        observable.subscribe(observer);



        Observable.just(1, 2, 3, 4).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
               Log.i(TAG, "开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
               Log.i(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
               Log.i(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
               Log.i(TAG, "对Complete事件作出响应");
            }

        });


    }

    public void fromArray() {
        // 1. 设置需要传入的数组, 快速创建被观察者对象（Observable） & 可发送10个以上事件（数组形式）
        // 可用于对数组进行遍历
        // 注：若直接传递一个list集合进去，否则会直接把list当做一个数据元素发送
        Integer[] items = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        Observable.fromArray(items).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }


    public void timer() {
        //延时2s发送一个事件, 会发送一个long类型数值0，等同于延时2s后调用一次onNext(0)触发事件
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    public void interval() {
        // 延迟3s后，每隔1秒发送1个事件, 产生1个数字（从0开始递增1，无限个）
        Observable.interval(3, 1, TimeUnit.SECONDS)
                //.interval(300, TimeUnit.MILLISECONDS) //每个0.3秒发送一个事件
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件"+ value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }


    public void concatArray() {
        // concatArray（）：组合多个被观察者一起发送数据（可＞4个）
        // 注：串行执行
        Observable.concatArray(Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8),
                Observable.just(9, 10))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    public void zip() {
        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
        Observable<String> observable2 = Observable.just("A", "B", "C");
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return s + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }



    public void subscribOn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "被观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                //.subscribeOn(Schedulers.newThread())
                //.subscribeOn(Schedulers.computation())
                //.subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "观察者 Observer的工作线程是: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void doOn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
                e.onComplete();
            }
        }).doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Log.d(TAG, "doOnEach: " + integerNotification.getValue());
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "doOnNext: " + integer);
            }
        }).doAfterNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "doAfterNext: " + integer);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doOnComplete: ");
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "doOnError: " + throwable.getMessage());
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                Log.e(TAG, "doOnSubscribe: ");
            }
        }).doOnTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doOnTerminate: ");
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doAfterTerminate: ");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "doFinally: ");
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }
            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "接收到了事件"+ value  );
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }






}
