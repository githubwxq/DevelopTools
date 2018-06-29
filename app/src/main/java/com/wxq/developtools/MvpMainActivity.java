package com.wxq.developtools;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juziwl.uilibrary.niceplayer.NiceVideoPlayer;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.mvplibrary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxq on 2018/6/28.
 */

public class MvpMainActivity extends BaseActivity<MvpMainContract.Presenter> implements MvpMainContract.View {


    @BindView(R.id.iv_test_pic)
    ImageView ivTestPic;
    @BindView(R.id.player)
    NiceVideoPlayer player;
    @BindView(R.id.tv_hello)
    TextView tvHello;


    @Override
    protected void initViews() {

        BarUtils.setStatusBarColor(this,getResources().getColor(R.color.common_account_red),255);
        tvHello.setText("000000000000000000000000");
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData(1);
            }
        });
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected MvpMainContract.Presenter initPresent() {
        return new MvpMainPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
