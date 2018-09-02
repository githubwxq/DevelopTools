package com.example.uitestdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.base.BasePresenter;
import com.wxq.mvplibrary.router.RouterContent;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterContent.UI_MAIN)
public class MainUiTestActivity extends BaseActivity {

    @BindView(R.id.tv_test_ui)
    TextView tvTestUi;

    @Override
    protected void initViews() {
        tvTestUi.setText("需要顶部栏目");

        topHeard.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitle("UI测试主界面");

    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main_ui_test;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}
