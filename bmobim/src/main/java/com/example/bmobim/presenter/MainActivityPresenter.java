package com.example.bmobim.presenter;

import android.text.TextUtils;

import com.example.bmobim.contract.MainContract;
import com.luck.picture.lib.rxbus2.RxBus;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.MainActivity;
import com.wxq.commonlibrary.baserx.RxBusManager;
import com.wxq.commonlibrary.bmob.BmobImEvent;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.model.User;
import com.wxq.commonlibrary.util.ToastUtils;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class MainActivityPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {


    public MainActivityPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
       //登入账号
        final CommonBmobUser user = new CommonBmobUser();
        user.setUsername("wxq789");
        user.setPassword("111111");
        user.login(new SaveListener<CommonBmobUser>() {
            @Override
            public void done(CommonBmobUser user, BmobException e) {
                if (e == null) {
                  //登入成功后 登入im链接
                 loginIm();
                } else {
                 ToastUtils.showShort("登入失败");
                }
            }
        });



    }

    private void loginIm() {
        final CommonBmobUser user = BmobUser.getCurrentUser(CommonBmobUser.class);
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        //判断用户是否登录，并且连接状态不是已连接，则进行连接操作
        if (!TextUtils.isEmpty(user.getObjectId()) &&
                BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                        //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().
                                updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                                        user.getUsername(), user.avatar));
//                        EventBus.getDefault().post(new RefreshEvent());
                        RxBusManager.getInstance().post(new BmobImEvent(BmobImEvent.ImLOGINSUCCESS));
//                        RxBus.getDefault().post(new BmobImEvent(BmobImEvent.ImLOGINSUCCESS));
                    } else {
                        ToastUtils.showShort(e.getMessage());
                        RxBusManager.getInstance().post(new BmobImEvent(BmobImEvent.ImLOGINSUCCESS));
                    }
                }
            });
            //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
            BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                @Override
                public void onChange(ConnectionStatus status) {
                    ToastUtils.showShort(status.getMsg());
                    Logger.i(BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        }else {
            RxBusManager.getInstance().post(new BmobImEvent(BmobImEvent.ImLOGINSUCCESS));
        }
        //解决leancanary提示InputMethodManager内存泄露的问题
//        IMMLeaks.fixFocusedViewLeak(getApplication());

    }


}
