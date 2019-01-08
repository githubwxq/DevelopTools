package com.example.nettestdemo;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wxq.commonlibrary.Service.WanAndroidApi;
import com.wxq.commonlibrary.model.LoginResponse;
import com.wxq.commonlibrary.model.ResponseData;
import com.wxq.commonlibrary.model.User;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.http.common.Api;
import com.wxq.mvplibrary.router.RouterContent;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterContent.NETTEST_MAIN)
public class NetTestActivity extends BaseActivity {
    TextView tv_test;



    @Override
    protected void initViews() {
        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                testRx();
//                testFlow();
//                testOkhttp();
                testListResponse();
            }
        });
    }

    private void testListResponse() {
        Api.getInstance().getApiService(WanAndroidApi.class)
                .getNameList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<List<User>>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ResponseData<List<User>> s) {
                        Log.e("wxq","onNext"+s.toString());
                        ToastUtils.showShort(s.content.size()+"");
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void testOkhttp() {

        Api.getInstance().getApiService(WanAndroidApi.class)
                .getLoginDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseData<LoginResponse>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ResponseData<LoginResponse> s) {
                Log.e("wxq",s.toString());
                ToastUtils.showShort(s.toString());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

    }



//    getNameList

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_net_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    /**
     * 测试rxjava
     */
    public void testRx() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("lx", " subscribe: " + Thread.currentThread().getName());
                Thread.sleep(500);
                e.onNext("eeeeeeeeeeeeeee");
                e.onComplete();
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String old) throws Exception {
                return "110";
            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("lx", " onSubscribe: " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String str) {
                Log.i("lx", " onNext : " + str);
                Log.i("lx", " onNext: " + Thread.currentThread().getName());

                tv_test.setText(str);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("lx", " onError : " + e.getMessage());
                Log.i("lx", " onError: " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.i("lx", " onComplete");
                Log.i("lx", " onComplete: " + Thread.currentThread().getName());
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void testFlow(){
        Flowable<String> stringFlowable = Flowable.<String>create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                Log.i("lx", " subscribe: " + Thread.currentThread().getName());
                e.onNext("flowable");
                e.onComplete();
            }
        }, BackpressureStrategy.DROP)

                .map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                Log.i("lx", " apply: " + Thread.currentThread().getName());
                return "wxq";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        //预约者; 签署者
        Subscriber<String> stringSubscriber=new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.i("lx", " onSubscribe: " + Thread.currentThread().getName());
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.i("lx", " onNext: " + Thread.currentThread().getName());
                Log.i("lx", s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.i("lx", " complete: " + Thread.currentThread().getName());
                Log.i("lx", "complete");
            }
        };
        stringFlowable.compose(new FlowableTransformer<String, String>() {
            @Override
            public Publisher<String> apply(Flowable<String> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        }).subscribe(stringSubscriber);

//        RxTransformer.<String>transformFlow(this)
    }
}


