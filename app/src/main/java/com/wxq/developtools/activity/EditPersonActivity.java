package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPersonActivity extends BaseActivity {
    @BindView(R.id.iv_heard)
    ImageView ivHeard;
    @BindView(R.id.rl_change_heard)
    RelativeLayout rlChangeHeard;
    @BindView(R.id.rl_acount_detail)
    RelativeLayout rlAcountDetail;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.tv_out)
    TextView tvOut;


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, EditPersonActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void initViews() {
        topHeard.setTitle("编辑账户").setLeftListener(
                v -> finish()
        );
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_person;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_heard, R.id.rl_change_heard, R.id.rl_acount_detail, R.id.rl_change_pwd, R.id.tv_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_heard:
                break;
            case R.id.rl_change_heard:
                break;
            case R.id.rl_acount_detail:
                break;
            case R.id.rl_change_pwd:
                break;
            case R.id.tv_out:
                break;
        }
    }
}
