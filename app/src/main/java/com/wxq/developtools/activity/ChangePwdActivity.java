package com.wxq.developtools.activity;

import android.content.Context;
import android.content.Intent;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

public class ChangePwdActivity extends BaseActivity {

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initViews() {
      topHeard.setTitle("修改密码");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
