package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.PersonInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 账户详情页面
 */
public class AccountDetailActivity extends BaseActivity  {


    @BindView(R.id.recycler_view)
    PullRefreshRecycleView recyclerView;
    /**
     * 所有用户根据是否都选择处理
     */
    private List<PersonInfo> allPersonInfoList = new ArrayList<>(); // 点击不同列表展示不同的选中效果


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, AccountDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews() {
        topHeard.setTitle("账户详情");
        recyclerView.setAdapter(new BaseQuickAdapter<PersonInfo,BaseViewHolder>(R.layout.item_account_detail,allPersonInfoList) {
            @Override
            protected void convert(BaseViewHolder helper, PersonInfo item) {
                helper.setText(R.id.tv_name,item.realName);
                helper.setText(R.id.tv_phone,"手机号  "+item.contactNumber);
                helper.setText(R.id.tv_identify,"身份证  " + item.idCard);
                helper.setText(R.id.tv_huzhao,"护照  " + item.passportNum);
                helper.setText(R.id.tv_youxiang,"邮箱  " + item.email);
            }
        });
        getPersonListData();
    }


    /**
     * 获取所有的可添加用户
     */
    private void getPersonListData() {
        // 获取用户数据
        Api.getInstance()
                .getApiService(KlookApi.class)
                .findUserAccountByUserId()
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<List<PersonInfo>>() {
                    @Override
                    protected void onSuccess(List<PersonInfo> data) {
                        recyclerView.updataData(data);
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
