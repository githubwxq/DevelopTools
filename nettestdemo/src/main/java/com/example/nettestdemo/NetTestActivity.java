package com.example.nettestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;
import com.wxq.mvplibrary.router.RouterContent;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterContent.NETTEST_MAIN)
public class NetTestActivity extends AppCompatActivity {
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testRx();

            }
        });
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


                return "new";
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

}
