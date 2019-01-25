package com.example.bmobim.presenter;

import com.example.bmobim.contract.ChatContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.activity.ChatActivity;
import com.wxq.commonlibrary.util.ToastUtils;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.exception.BmobException;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ChatActivityPresenter extends RxPresenter<ChatContract.View> implements ChatContract.Presenter {

    public  BmobIMConversation mConversationManager;

    /**
     * 集合数据
     */
    public List<BmobIMMessage> bmobIMMessageList=new ArrayList<>();
    @Override
    public void setCurrentConversation(BmobIMConversation conversation) {
        mConversationManager= BmobIMConversation.obtain(BmobIMClient.getInstance(), conversation);
    }



    public ChatActivityPresenter(ChatContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
        //获取
        queryMessages(null);
    }

    @Override
    public void queryMessages(BmobIMMessage msg) {
        mConversationManager.queryMessages(msg, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e == null) {
                    if (null != list && list.size() > 0) {
                        bmobIMMessageList.addAll(0,list);
                        mView.updateRecycleViewData(bmobIMMessageList);
                    }
                } else {
                    ToastUtils.showShort(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }
}
