package com.example.bmobim.activity;

import android.Manifest;
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
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.baserx.RxBusManager;
import com.wxq.commonlibrary.bmob.BmobImEvent;
import com.wxq.commonlibrary.util.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;

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
        BmobIMConversation conversationEntrance = (BmobIMConversation) getIntent().getSerializableExtra("c");
        topHeard.setTitle(conversationEntrance.getConversationTitle()).setLeftListener(v -> onBackPressed());
        mPresenter.setCurrentConversation(conversationEntrance);
        inputPanel.setChatView(this);
        recyclerView.setLoadMoreEnable(false);
        //获取权限
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(aBoolean -> {
                    if (aBoolean) {

                    } else {
                        ToastUtils.showShort("请打开权限否则影响功能");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputPanel.onActivityResult(requestCode, resultCode, data);
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
                    mPresenter.getPreMessages();
                }
            });
        } else {
            recyclerView.notifyDataSetChanged();
        }
    }

    @Override
    public void clearEdit() {
        inputPanel.setText("");
        moveToBottom();
    }

    @Override
    public void moveToBottom() {
        recyclerView.smoothToLastPosition();
    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (action == BmobImEvent.RECEIVENEWMESSAGE) {
            //登入成功 获取会话数据
            MessageEvent msgEvent = event.getObject();
            mPresenter.receiveNewMessage(msgEvent);
        }

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
    public void sendImage(List<String> paths) {
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                if (file.length() > 1024 * 1024 * 10) {
                    ToastUtils.showShort(R.string.chat_file_too_large);
                } else {
                    mPresenter.sendImageMessage(path);
                }
            } else {
                ToastUtils.showShort(R.string.chat_file_not_exist);
            }
        }
    }


    @Override
    public void takePhotos() {

    }

    @Override
    public void sendText( String textMessage) {
        mPresenter.sendTextMessage(textMessage);
    }

    @Override
    public void sendFile() {

    }

    @Override
    public void sendVideo(String path,long length) {
        mPresenter.sendVideoMessage(path,length);
    }

    @Override
    public void sendVoice(long length, String path) {
        mPresenter.sendVoiceMessage(length,path);
    }

    @Override
    public void sending(boolean flag) {

    }

    @Override
    public void scrollBottom() {
        recyclerView.smoothToLastPosition();
    }

    @Override
    public void showAtPage() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.setHasRead();
        //發通知 更新會話
        BmobImEvent bmobImEvent=new BmobImEvent(BmobImEvent.UPDATECONVERRSION);
        RxBusManager.getInstance().post(bmobImEvent);
        super.onDestroy();
    }
}
