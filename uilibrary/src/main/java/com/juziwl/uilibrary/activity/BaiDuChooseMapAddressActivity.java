package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

public class BaiDuChooseMapAddressActivity extends BaseActivity {

    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, BaiDuChooseMapAddressActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void initViews() {

    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_bai_du_choose_map_address;
    }
    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
