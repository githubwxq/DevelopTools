//package com.wxq.commonlibrary.rxjavaframwork;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//
//import com.wxq.commonlibrary.R;
//
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "dongnao";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//    public  void click(View view)
//    {
//        /**
//         * String  类型  代表猪肉
//         * Bitmap  代表杀猪刀
//         * new OnSubscrible  屠夫
//         */
//        Observable.create(new OnSubscrible<String>() {
//            @Override
//            public void call(NewObserver<? super String> subscrible) {
//                Log.i(TAG,"1");//  1
//                subscrible.onNext("http://www.baidu.com");
//                Log.i(TAG,"5");//   调用  1  没调用  2
//                Log.i(TAG,Thread.currentThread().getName());
//            }
//            }).map(new Func1<String,Bitmap>() {
//            //具体的转换类型角色
//            @Override
//            public Bitmap call(String s) {
//                Log.i(TAG,"2");
//                Log.i(TAG,s);
//                Log.i(TAG,Thread.currentThread().getName());
//                Bitmap bitmap= BitmapFactory.
//                        decodeResource(MainActivity.this.getResources(),R.mipmap.icon_back_black);
//                return bitmap;
//            }
//            }).subscribleMain().subscrible(new NewObserver<Bitmap>() {
//            @Override
//            public void onNext(Bitmap bitmap) {
//
//            }
//        });
//
//        //
////
////        Observable.create(new OnSubscrible<String>() {
////            @Override
////            public void call(NewObserver<? super String> subscrible) {
////                subscrible.onNext("该醒醒了");
////            }
////            //subscrible    1
////            //new subscrible  2  订阅与发布
////        }).subscrible(new NewObserver<String>() {
////            @Override
////            public void onNext(String s) {
////
////            }
////        });
//
//
//        io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//    }
//}
