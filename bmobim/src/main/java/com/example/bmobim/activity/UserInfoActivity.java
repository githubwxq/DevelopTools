package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bmobim.R;
import com.example.bmobim.bean.AddFriendMessage;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 创建日期：
 * 描述:
 *
 * @author:
 */
public class UserInfoActivity extends BaseActivity {

    public static final String TITLE = "个人信息";
    @BindView(R.id.iv_avator)
    ImageView ivAvator;
    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.layout_name)
    RelativeLayout layoutName;
    @BindView(R.id.btn_add_friend)
    Button btnAddFriend;
    @BindView(R.id.btn_chat)
    Button btnChat;
    @BindView(R.id.layout_all)
    LinearLayout layoutAll;

    CommonBmobUser user;
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @Override
    protected void initViews() {
        topHeard.setTitle(TITLE).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        user= (CommonBmobUser) getIntent().getSerializableExtra("user");
        tvName.setText(user.getUsername());
        LoadingImgUtil.loadimg(user.avatar,ivAvator,true);
        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend();
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.avatar);
                //TODO 会话：4.1、创建一个常态会话入口，好友聊天
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("c", conversationEntrance);
                context.startActivity(intent);
            }
        });
    }

    private void addFriend() {
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            ToastUtils.showShort("尚未连接IM服务器");
            return;
        }
        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.avatar);
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        AddFriendMessage msg = new AddFriendMessage();
        CommonBmobUser currentUser = BmobUser.getCurrentUser(CommonBmobUser.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名
        map.put("avatar", currentUser.avatar);//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    ToastUtils.showShort("好友请求发送成功，等待验证");
                } else {//发送失败
                    ToastUtils.showShort("发送失败:" + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }



}
