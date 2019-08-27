package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.juziwl.uilibrary.edittext.SuperEditText;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxBusManager;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.PersonInfo;

import java.util.HashMap;

import butterknife.BindView;

import static com.wxq.developtools.activity.ConfirmAndPayActivity.UPDATELIST;

public class AddPeopleActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    SuperEditText etPhone;
    @BindView(R.id.et_email)
    SuperEditText etEmail;
    @BindView(R.id.et_shengfeng)
    SuperEditText etShengfeng;
    @BindView(R.id.et_huzhao)
    SuperEditText etHuzhao;
    @BindView(R.id.et_name)
    SuperEditText etName;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, AddPeopleActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        topHeard.setTitle("添加出行人").setRightText("保存").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        etPhone.setPhoneStyle();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_add_people;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    public void save() {
        if (etPhone.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (etEmail.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入邮箱");
            return;
        }

        if (etShengfeng.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入身份证");
            return;
        }

        if (etHuzhao.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入护照");
            return;
        }

        if (etName.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入姓名");
            return;
        }

        HashMap<String,String> parmer=new HashMap<>();
        parmer.put("contactNumber",etPhone.getText().toString());
        parmer.put("email",etEmail.getText().toString());
        parmer.put("idCard",etShengfeng.getText().toString());
        parmer.put("passportNum",etHuzhao.getText().toString());
        parmer.put("realName",etName.getText().toString());
        Api.getInstance()
                .getApiService(KlookApi.class)
                .saveUserAccount(parmer)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<PersonInfo>() {
                    @Override
                    protected void onSuccess(PersonInfo data) {
                        ToastUtils.showShort("添加用户成功");
                        RxBusManager.getInstance().post(new Event(UPDATELIST,data));
                        finishActivity();
                    }
                });
    }
}
