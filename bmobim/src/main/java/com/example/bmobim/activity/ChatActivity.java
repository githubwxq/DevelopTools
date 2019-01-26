package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.bmobim.R;
import com.example.bmobim.adapter.MessageAdapter;
import com.example.bmobim.bean.Message;
import com.example.bmobim.contract.ChatContract;
import com.example.bmobim.presenter.ChatActivityPresenter;
import com.juziwl.uilibrary.chatview.ChatInput;
import com.juziwl.uilibrary.chatview.ChatView;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMConversation;

/**
 * 创建日期：1.25
 * 描述:聊天页面
 *
 * @author:wxq
 */
public class ChatActivity extends BaseActivity<ChatContract.Presenter> implements ChatContract.View, ChatView {

    public static final String TITLE = "聊天";
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    MessageAdapter adapter;
    @BindView(R.id.input_panel)
    ChatInput inputPanel;

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
        inputPanel.setChatView(this);

    }

    @Override
    public void updateRecycleViewData(List<Message> messageList) {
        if (adapter == null) {
            adapter = new MessageAdapter(messageList);
            recyclerView.setAdapter(adapter, new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clearEditAndMoveToBottom() {
        inputPanel.setText("");
        recyclerView.smoothToLastPosition();
    }

    @Override
    protected ChatContract.Presenter initPresent() {
        return new ChatActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }


    @Override
    public void showRevokeMessageErrorTips(int code) {

    }

    @Override
    public void clearAllMessage() {

    }

    @Override
    public void sendImage() {

    }

    @Override
    public void takePhotos() {

    }

    @Override
    public void sendText() {
        String textMessage = inputPanel.getEditText().getText().toString();
        mPresenter.sendTextMessage(textMessage);
    }

    @Override
    public void sendFile() {

    }

    @Override
    public void sendVideo() {

    }

    @Override
    public void sendVoice(long length, String path) {

    }

    @Override
    public void sending(boolean flag) {

    }

    @Override
    public void scrollBottom() {

    }

    @Override
    public void showAtPage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
