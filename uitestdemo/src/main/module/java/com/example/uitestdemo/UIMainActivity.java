package com.example.uitestdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.router.RouterContent;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UIMainActivity extends BaseActivity {


    @BindView(R.id.tv_test)
    TextView tvTestUi;

    @Override
    protected void initViews() {
        topHeard.setRightText("测试顶部栏目");
        tvTestUi.setText("点击进入ui模块");
        tvTestUi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build(RouterContent.UI_MAIN)
                        .navigation();
            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_uimain;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


}