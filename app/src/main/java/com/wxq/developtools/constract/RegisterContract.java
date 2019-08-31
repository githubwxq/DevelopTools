package com.wxq.developtools.constract;


import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.base.BaseView;

public interface RegisterContract {
    interface View extends BaseView {
        void finishActivity(String name, String passWord);

        void startTimeDown();

        /**
         * 注册成功
         */
        void registerSuccess();

    }

    interface Presenter extends BasePresenter<View> {

        void signUp(String name, String passWord);
        /**
         * 注册 根据手机号 验证码 和 密码
         * @param phomeNumber
         * @param passWord
         * @param code
         */
        void signUp(String phomeNumber, String passWord,String code);

        /**获取验证码
         * @param phomeNumber
         */
        void sendCodeMessage(String phomeNumber);
    }
}
