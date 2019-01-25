package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.contract.ChatContract;
import com.example.bmobim.presenter.ChatActivityPresenter;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * 创建日期：1.25
 * 描述:聊天页面
 *
 * @author:wxq
 */
public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View {

    public static final String TITLE = "聊天";
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;


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
        topHeard.setTitle(TITLE).setLeftListener(v -> onBackPressed());
        BmobIMConversation conversationEntrance = (BmobIMConversation) getIntent().getSerializableExtra("c");
        mPresenter.setCurrentConversation(conversationEntrance);
    }


    @Override
    public void updateRecycleViewData(List<BmobIMMessage> bmobIMMessageList) {
//        recyclerView.setAdapter(new BaseMultiItemQuickAdapter<BmobIMMessage,BaseViewHolder>(bmobIMMessageList) {
//
//            @Override
//            protected void convert(BaseViewHolder helper, BmobIMMessage item) {
//
//            }
//        });
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