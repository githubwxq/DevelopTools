package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.Friend;
import com.example.bmobim.contract.ContactContract;
import com.example.bmobim.presenter.ContactActivityPresenter;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.bmob.BmobImEvent;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * 创建日期：2019 1 29
 * 描述:
 *
 * @author:通讯录
 */
public class ContactActivity extends BaseActivity<ContactContract.Presenter> implements ContactContract.View {

    public static final String TITLE = "通讯录";
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    FriendAdapter adapter;
    ImageView msgTip;
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, ContactActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_contact;
    }


    @Override
    protected void initViews() {
        topHeard.setRightText("搜索").setTitle(TITLE).setLeftAndRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }, view -> {
            Intent intent=new Intent(context,SearchFriendActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (action== BmobImEvent.UPDATEFRIENDLIST) {
            //更新好友 你同意人家或者人家同意你
            mPresenter.findFriends();
        }

    }

    @Override
    protected ContactContract.Presenter initPresent() {
        return new ContactActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }


    @Override
    public void updateRecycleViewData(List<Friend> friendList) {
        if (adapter == null) {
            adapter=new FriendAdapter(friendList);
            recyclerView.setAdapter(adapter);
            View heardView=View.inflate(this,R.layout.header_new_friend,null);
            msgTip=   heardView.findViewById(R.id.iv_msg_tips);
            heardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 NewFriendActivity.navToActivity(context);
                }
            });
            recyclerView.addHeaderView(heardView,true);
        } else {
            recyclerView.notifyDataSetChanged();
        }
    }

    private class FriendAdapter extends BaseQuickAdapter<Friend, BaseViewHolder> {
        public FriendAdapter(List<Friend> friendList) {
            super(R.layout.item_contact, friendList);
        }

        @Override
        protected void convert(BaseViewHolder helper, Friend item) {
            CommonBmobUser user = item.getFriendUser();
            LoadingImgUtil.loadimg(user.avatar, helper.getView(R.id.iv_recent_avatar), true);
            helper.setText(R.id.tv_recent_name, user == null ? "未知" : user.getUsername());
            helper.getConvertView().setOnClickListener(view -> {
                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.avatar);
                //TODO 会话：4.1、创建一个常态会话入口，好友聊天
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("c", conversationEntrance);
                context.startActivity(intent);
            });
        }
    }
}
