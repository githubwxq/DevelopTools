package com.wxq.developtools.present;

import com.wxq.commonlibrary.base.RxPresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.dbmanager.DbManager;
import com.wxq.commonlibrary.dbmanager.RealUserDao;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.model.User;
import com.wxq.commonlibrary.util.BuglyUtils;
import com.wxq.developtools.api.LoginModelApi;
import com.wxq.developtools.constract.LoginContract;

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
                        AllDataCenterManager.getInstance().token=s;
                        //保存到公有数据share里面下次退出还要用到
                        AllDataCenterManager.getInstance().getPublicPreference().storeAccount(account);
                        AllDataCenterManager.getInstance().getPublicPreference().storePwd(password);
                        AllDataCenterManager.getInstance().getPublicPreference().storeAutoLogin(1);
                        DbManager.getInstance().setDATA_BASE_NAME(account+".db");
                        User user = RealUserDao.getInstance().queryUserByPhone(account);
                        if (user==null) {
                            user=new User();
                            user.phone=account;
                            user.pwd=password;
                            RealUserDao.getInstance().insertUser(user);
                        }else {
                            user.phone=account;
                            user.pwd=password;
                            RealUserDao.getInstance().updateUser(user);
                        }

                        BuglyUtils.setUserId("账号信息"+account);
                        // 数据库系统配置信息
//                        DaoSession appconfigDao = AllDataCenterManager.getInstance().getDaoSession("appconfig");
//                        AppConfig appConfig=new AppConfig();
//                        appConfig.setLoginStatue("0");
//                        appConfig.token="110";
//                        appconfigDao.getAppConfigDao().save(appConfig);
                        mView.showToast("登录成功");
                        mView.naveToMainActivity();
                    }
                });
    }
}
