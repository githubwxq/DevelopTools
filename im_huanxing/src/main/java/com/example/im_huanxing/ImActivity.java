package com.example.im_huanxing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ruffian.library.widget.RTextView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImActivity extends BaseActivity {


    @BindView(R.id.tv_init)
    RTextView tvInit;
    @BindView(R.id.tv_login)
    RTextView tvLogin;
    @BindView(R.id.tv_login_out)
    RTextView tvLoginOut;
    @BindView(R.id.tv_send_message)
    RTextView tvSendMessage;
    @BindView(R.id.tv_receive_message)
    RTextView tvReceiveMessage;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ImActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_im;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.tv_init, R.id.tv_login, R.id.tv_login_out, R.id.tv_send_message, R.id.tv_receive_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_init:
                showToast("init");
                break;
            case R.id.tv_login:
                showToast("tv_login");
                break;
            case R.id.tv_login_out:

                break;
            case R.id.tv_send_message:

                break;
            case R.id.tv_receive_message:

                break;

        }
    }
}
