package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.model.UserInfo;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;

import butterknife.BindView;

/**
 * 账户详情页面
 */
public class AccountDetailActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_identify)
    TextView tvIdentify;
    @BindView(R.id.tv_huzhao)
    TextView tvHuzhao;
    @BindView(R.id.tv_youxiang)
    TextView tvYouxiang;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("账户详情");
        String id = AllDataCenterManager.getInstance().userInfo.id;
        Api.getInstance().getApiService(KlookApi.class).findUserAccountById(id)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<UserInfo>() {
                    @Override
                    protected void onSuccess(UserInfo data) {
                        AllDataCenterManager.getInstance().userInfo = data;
                       tvName.setText(data.realName);
                        tvPhone.setText("手机号  "+data.phone);
                        tvIdentify.setText("身份证  "+data.idCard);
                        tvHuzhao.setText("护照  "+data.passportNum);
                        tvYouxiang.setText("邮箱  "+data.email);
                    }
                });

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_account_detail;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}
