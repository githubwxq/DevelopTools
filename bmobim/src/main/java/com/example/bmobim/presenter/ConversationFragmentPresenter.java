package com.example.bmobim.presenter;

import android.content.Context;

import com.example.bmobim.bean.Conversation;
import com.example.bmobim.bean.NewFriend;
import com.example.bmobim.bean.NewFriendConversation;
import com.example.bmobim.bean.PrivateConversation;
import com.example.bmobim.contract.ConversationContract;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.example.bmobim.fragment.ConversationFragment;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class ConversationFragmentPresenter extends RxPresenter<ConversationContract.View> implements ConversationContract.Presenter {

     List<Conversation> conversationList=new ArrayList<>();


    public ConversationFragmentPresenter(ConversationContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {
          mView.updateList(conversationList);
    }

    /**
     * 获取会话列表的数据：增加新朋友会话
     * @return
     */
    public List<Conversation> getConversations(){
        //添加会话
        conversationList.clear();
        //TODO 会话：4.2、查询全部会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if(list!=null && list.size()>0){
            for (BmobIMConversation item:list){
                switch (item.getConversationType()){
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        //添加新朋友会话-获取好友请求表中最新一条记录
//        List<NewFriend> friends = NewFriendManager.getInstance(mView.getContext()).getAllNewFriend();
//        if(friends!=null && friends.size()>0){
//            conversationList.add(new NewFriendConversation(friends.get(0)));
//        }
        //重新排序
        Collections.sort(conversationList);
        mView.updateList(conversationList);
        return conversationList;
    }


}
