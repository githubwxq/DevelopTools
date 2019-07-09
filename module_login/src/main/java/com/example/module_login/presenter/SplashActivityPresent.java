package com.example.module_login.presenter;

import com.example.module_login.api.LoginModelApi;
//import com.example.module_login.bean.HomePageData;
import com.example.module_login.contract.SplashContract;
import com.wxq.commonlibrary.base.RxPresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

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

    public void getCode() {
        Api.getInstance()
                .getApiService(LoginModelApi.class)
                .sendsms("17501461752")
                .compose(RxTransformer.transformFlowWithLoading(mView))
//                .flatMap(new Function<KlookResponseData<Object>, Flowable<KlookResponseData<Object>>>() {
//                    @Override
//                    public  Flowable<KlookResponseData<Object>> apply(KlookResponseData<Object> stringKlookResponseData) throws Exception {
//                        ToastUtils.showShort("第一个");
//                        return  Api.getInstance()
//                                .getApiService(LoginModelApi.class)
//                                .sendsms("17501461752");
//                    }
//                })
                .compose(ResponseTransformer.handleResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {
                        ToastUtils.showShort("第二个");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
                ;
    }

    @Override
    public void register() {

//        {
//            "checkCode": "",
//                "password": "",
//                "phone": "",
//                "username": ""
//        }

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("checkCode","386131");
        hashMap.put("password","111111");
        hashMap.put("phone","17501461752");
        hashMap.put("username","wxq123");

        Api.getInstance()
                .getApiService(LoginModelApi.class).register(hashMap)
                . compose(RxTransformer.transformFlowWithLoading(mView))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {
                        ToastUtils.showShort("注册陈工");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void getHomeData() {

    }


}
