package com.wxq.developtools.activity;
import android.view.View;

import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.developtools.R;

public class ConfirmOrderActivity extends BaseActivity {

    @Override
    protected void initViews() {
       topHeard.setTitle("确认订单页").setLeftListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });





    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }
}
