package com.example.bmob.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.router.RouterContent;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.example.bmob.presenter.TTTActivityPresenter;
import com.example.bmob.contract.TTTContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.bmob.R;
import com.example.bmob.R2;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public class TTTActivity extends BaseActivity<TTTContract.Presenter> implements TTTContract.View {

    public static final String TITLE = "";


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, TTTActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_ttt;
    }


    @Override
    protected void initViews() {
    }


    @Override
    protected TTTContract.Presenter initPresent() {
        return new TTTActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }


}
