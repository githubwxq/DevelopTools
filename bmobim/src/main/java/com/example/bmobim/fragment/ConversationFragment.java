package com.example.bmobim.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.Conversation;
import com.example.bmobim.contract.ConversationContract;
import com.example.bmobim.presenter.ConversationFragmentPresenter;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.baserx.Event;
import com.wxq.commonlibrary.bmob.BmobImEvent;
import com.wxq.commonlibrary.util.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建日期：2019 1 24
 * 描述:联系人fragment
 *
 * @author:wxq
 */
public class ConversationFragment extends BaseFragment<ConversationContract.Presenter> implements ConversationContract.View {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    ConversionAdapter adapter;
    Unbinder unbinder;

    public static ConversationFragment getInstance(String parmer) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_conversation;
    }

    @Override
    protected void initViews() {
//          new Handler().postDelayed(new Runnable() {
//              @Override
//              public void run() {
//                  mPresenter.getConversations();
//              }
//          },5000);

    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        if (action== BmobImEvent.ImLOGINSUCCESS) {
            //登入成功 获取会话数据
            mPresenter.getConversations();
        }

        if (action== BmobImEvent.RECEIVENEWMESSAGE) {
            //收到新消息获取会话数据
            mPresenter.getConversations();
        }

          if (action== BmobImEvent.UPDATECONVERRSION) {
            //更新会话数据
            mPresenter.getConversations();
        }



    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.getConversations();
    }

    @Override
    protected ConversationContract.Presenter initPresenter() {
        return new ConversationFragmentPresenter(this);
    }



    @Override
    public void updateList(List<Conversation> conversationList) {
        if (adapter == null) {
            recyclerView.setAdapter(adapter = new ConversionAdapter(conversationList), new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mPresenter.getConversations();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                       mPresenter.getConversations();
                }
            });
        } else {
            recyclerView.setRecycleViewData(conversationList);
        }
    }

    private class ConversionAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {
        public ConversionAdapter(List<Conversation> conversationList) {
            super(R.layout.conversion_adapter_item, conversationList);
        }

        @Override
        protected void convert(BaseViewHolder helper, Conversation item) {
            helper.getView(R.id.rl_main).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onClick(getContext());
                }
            });
            helper.setText(R.id.tv_recent_name,item.getcName());
            helper.setText(R.id.tv_recent_msg,item.getLastMessageContent());
            helper.setText(R.id.tv_recent_time, TimeUtils.millis2String(item.getLastMessageTime()));
            if (item.getUnReadCount()==0) {
                helper.setVisible(R.id.tv_recent_unread,false);
            }else {
                helper.setVisible(R.id.tv_recent_unread,true);
            }
            helper.setText(R.id.tv_recent_unread, item.getUnReadCount()+"");
        }
    }
}
