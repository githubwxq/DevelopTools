package com.example.module_login.presenter;

import com.example.module_login.api.LoginModelApi;
import com.example.module_login.bean.User;
import com.example.module_login.contract.RegisterContract;
import com.wxq.commonlibrary.base.RxPresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;

import java.util.HashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by wxq on 2018/6/28.
 */
public class RegisterActivityPresent extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterActivityPresent(RegisterContract.View view) {
        super(view);
    }
    @Override
    public void initEventAndData() {

    }

    @Override
    public void signUp(String name, String passWord) {
        final User user = new User();
        user.setUsername(name);
        user.setPassword(passWord);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    mView.showToast("注册成功");
                    mView.finishActivity(name,passWord);
                } else {
                    mView.showToast("注册失败"+e.getMessage());
                }
            }
        });
    }

    @Override
    public void signUp(String phoneNumber, String passWord, String code) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("checkCode",code);
        hashMap.put("password",passWord);
        hashMap.put("phone",phoneNumber);
        hashMap.put("username","XXXX");
        Api.getInstance()
                .getApiService(LoginModelApi.class).register(hashMap)
                . compose(RxTransformer.transformFlowWithLoading(mView))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        mView.showToast("注册成功");
                        mView.registerSuccess();
                    }
                });
    }

    @Override
    public void sendCodeMessage(String phomeNumber) {
        Api.getInstance()
                .getApiService(LoginModelApi.class)
                .sendsms(phomeNumber)
                .compose(RxTransformer.transformFlowWithLoading(mView))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        mView.startTimeDown();
                    }
                });
    }
}
