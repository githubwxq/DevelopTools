package com.example.bmobim.activity;

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
import com.example.bmobim.presenter.ChatActivityPresenter;
import com.example.bmobim.contract.ChatContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.bmobim.R;
import com.example.bmobim.R2;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    public static final String TITLE = "";


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_chat;
    }


    @Override
    protected void initViews() {
    }


    @Override
    protected ChatContract.Presenter initPresent() {
        return new ChatActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }


}
