package com.example.module_login.presenter;

import com.example.module_login.contract.SplashContract;
import com.wxq.commonlibrary.base.RxPresenter;

//import com.example.module_login.bean.HomePageData;

/**
 * Created by wxq on 2018/6/28.
 */
public class SplashActivityPresent extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {


    public SplashActivityPresent(SplashContract.View view) {
        super(view);
    }


    @Override
    public void initEventAndData() {
//        getCode();
    }

//    public void getCode() {
//        Api.getInstance()
//                .getApiService(LoginModelApi.class)
//                .sendsms("17501461752")
//                .compose(RxTransformer.transformFlowWithLoading(mView))
////                .flatMap(new Function<KlookResponseData<Object>, Flowable<KlookResponseData<Object>>>() {
////                    @Override
////                    public  Flowable<KlookResponseData<Object>> apply(KlookResponseData<Object> stringKlookResponseData) throws Exception {
////                        ToastUtils.showShort("第一个");
////                        return  Api.getInstance()
////                                .getApiService(LoginModelApi.class)
////                                .sendsms("17501461752");
////                    }
////                })
//                .compose(ResponseTransformer.handleResult())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object s) throws Exception {
//                        ToastUtils.showShort("第二个");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                })
//                ;
//    }

    @Override
    public void getCode() {

    }

    @Override
    public void register() {

    }

    @Override
    public void getHomeData() {

    }


}
