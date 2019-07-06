package com.example.module_login.presenter;

import com.example.module_login.api.LoginModelApi;
import com.example.module_login.contract.LoginContract;
import com.wxq.commonlibrary.base.RxPresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;

import java.util.HashMap;

/**
 * Created by wxq on 2018/6/28.
 */
public class KlookLoginActivityPresent extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    public KlookLoginActivityPresent(LoginContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {


    }

    @Override
    public void loginWithAccountAndPwd(String account, String password) {
        HashMap<String,String> parmer=new HashMap<>()   ;
        parmer.put("password",password);
        parmer.put("phone",account);
        Api.getInstance()
                .getApiService(LoginModelApi.class).login(parmer)
                . compose(RxTransformer.transformFlowWithLoading(mView))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        mView.showToast("登录成功");
                        mView.naveToMainActivity();
                    }
                });
    }
}
