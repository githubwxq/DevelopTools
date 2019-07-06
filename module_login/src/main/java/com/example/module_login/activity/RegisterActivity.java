package com.example.module_login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.RegisterContract;
import com.example.module_login.presenter.RegisterActivityPresent;
import com.juziwl.uilibrary.edittext.EditTextWithDel;
import com.juziwl.uilibrary.textview.CodeTextView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R2.id.etd_tel)
    EditTextWithDel etdTel;
    @BindView(R2.id.tv_qcode)
    CodeTextView tvQcode;
    @BindView(R2.id.etd_code)
    EditTextWithDel etdCode;
    @BindView(R2.id.btn_register)
    Button btn_register;


    private String passWord = "";
    private String name = "";
    private String code = "";

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("注册").setLeftAndRightListener(v -> {
            finishActivity(name, passWord);
        }, null).setRightText("");
        click(btn_register, o -> {
            passWord = etdCode.getText().toString();
            name = etdTel.getText().toString();
            if (StringUtils.isEmpty(etdCode.getText().toString()) || StringUtils.isEmpty(etdTel.getText().toString())) {
                ToastUtils.showShort("请输入用户名或密码");
            } else {
                mPresenter.signUp(name, passWord);
            }
        });

        tvQcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isBlank(etdTel.getText().toString())) {
                    showToast("请先输入手机号");
                    return;
                }
                mPresenter.sendCodeMessage(etdTel.getText().toString());
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passWord = etdCode.getText().toString();
                name = etdTel.getText().toString();
                code=etdCode.getText().toString();

                if (StringUtils.isBlank(name)) {
                    showToast("请输入手机号");
                    return;
                }
                if (StringUtils.isBlank(passWord)) {
                    showToast("请输入密码");
                    return;
                }
                if (StringUtils.isBlank(name)) {
                    showToast("请输入验证码");
                    return;
                }
                mPresenter.signUp(name, passWord,code);
            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterContract.Presenter initPresent() {
        return new RegisterActivityPresent(this);
    }

    @Override
    public void finishActivity(String name, String passWord) {
        finish();
    }
    @Override
    public void startTimeDown() {
        tvQcode.start();
    }

    @Override
    public void registerSuccess() {
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
