package com.example.module_login.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.RegisterContract;
import com.example.module_login.presenter.RegisterActivityPresent;
import com.juziwl.uilibrary.edittext.EditTextWithDel;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.RxBusManager;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R2.id.etd_tel)
    EditTextWithDel etdTel;
    @BindView(R2.id.tv_qcode)
    TextView tvQcode;
    @BindView(R2.id.etd_code)
    EditTextWithDel etdCode;
    @BindView(R2.id.btn_register)
    Button btn_register;

    private String passWord="";
    private String name="";

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("注册").setLeftAndRightListener(v -> {
          finishActivity(name, passWord);
        },null).setRightText("");

        click(btn_register, o -> {
            passWord=  etdCode.getText().toString();
            name=  etdTel.getText().toString();
            if (StringUtils.isEmpty(etdCode.getText().toString())||StringUtils.isEmpty(etdTel.getText().toString())) {
                ToastUtils.showShort("请输入用户名或密码");
            }else {
                mPresenter.signUp(name,passWord);
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
//        RxBusManager.getInstance().post(new Event());
        finish();
    }
}
