package com.juziwl.uilibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import java.io.Serializable;

/**
 * @author wxq
 * @version V_5.0.0
 * @date 2019年02月18日
 * @description 选择城市
 */
public class ChooseCityActivity extends BaseActivity {

    public static void navToActivity(Activity activity) {
        Intent intent = new Intent(activity, ChooseCityActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_choose_city;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}
