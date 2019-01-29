package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.AgreeAddFriendMessage;
import com.wxq.commonlibrary.bmob.Config;
import com.example.bmobim.contract.NewFriendContract;
import com.example.bmobim.model.BmobUserModel;
import com.example.bmobim.presenter.NewFriendActivityPresenter;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.bmob.NewFriend;
import com.wxq.commonlibrary.bmob.NewFriendManager;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 创建日期：
 * 描述:新的朋友列表
 *
 * @author:wxq
 */
public class NewFriendActivity extends BaseActivity<NewFriendContract.Presenter> implements NewFriendContract.View {

    public static final String TITLE = "新的朋友";
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    List<NewFriend> friends=new ArrayList<>();

     public String currentUserId;
    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, NewFriendActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_new_friend;
    }



    @Override
    protected void initViews() {
        currentUserId=BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId();
        topHeard.setRightText("").setTitle(TITLE).setLeftListener(view -> onBackPressed());
        friends.addAll(NewFriendManager.getAllFriends(currentUserId));
        recyclerView.setAdapter(new BaseQuickAdapter<NewFriend,BaseViewHolder>(R.layout.item_new_friend,friends) {
            @Override
            protected void convert(BaseViewHolder holder, NewFriend add) {
                LoadingImgUtil.loadimg(add.getAvatar(),holder.getView(R.id.iv_recent_avatar),true);
                holder.getView(R.id.btn_aggree);
                holder.setText(R.id.tv_recent_name, add == null ? "未知" : add.getName());
                holder.setText(R.id.tv_recent_msg, add == null ? "未知" : add.getMsg());
                Button button=holder.getView(R.id.btn_aggree);
                Integer status = add.getStatus();
                //当状态是未添加或者是已读未添加
                if (status == null || status == Config.STATUS_VERIFY_NONE || status == Config.STATUS_VERIFY_READED) {
                    holder.setText(R.id.btn_aggree, "接受");
                    button.setEnabled(true);
                    holder.setOnClickListener(R.id.btn_aggree, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            agreeAdd(add, new SaveListener<Object>() {
                                @Override
                                public void done(Object o, BmobException e) {
                                    if (e == null) {
                                        holder.setText(R.id.btn_aggree, "已添加");
                                        button.setEnabled(false);
                                    } else {
                                        button.setEnabled(true);
                                        Logger.e("添加好友失败:" + e.getMessage());
                                        ToastUtils.showShort("添加好友失败:" + e.getMessage());
                                    }
                                }
                            });
                        }
                    });
                } else {
                    holder.setText(R.id.btn_aggree, "已添加");
                    button.setEnabled(false);
                }
            }
        });

        //设置状态为已读为添加
        NewFriendManager.updateBatchStatus(BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId());

    }

    /**
     * TODO 好友管理：9.10、添加到好友表中再发送同意添加好友的消息
     *
     * @param add
     * @param listener
     */
    private void agreeAdd(final NewFriend add, final SaveListener<Object> listener) {
        CommonBmobUser user = new CommonBmobUser();
        user.setObjectId(add.getUid());
        BmobUserModel.getInstance()
                .agreeAddFriend(user, new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            sendAgreeAddFriendMessage(add, listener);
                        } else {
                            Logger.e(e.getMessage());
                            listener.done(null, e);
                        }
                    }
                });
    }


    /**
     * 发送同意添加好友的消息
     */
    //TODO 好友管理：9.8、发送同意添加好友
    private void sendAgreeAddFriendMessage(final NewFriend add, final SaveListener<Object> listener) {
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            ToastUtils.showShort("尚未连接IM服务器");
            return;
        }
        BmobIMUserInfo info = new BmobIMUserInfo(add.getUid(), add.getName(), add.getAvatar());
        //TODO 会话：4.1、创建一个暂态会话入口，发送同意好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送同意好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        //而AgreeAddFriendMessage的isTransient设置为false，表明我希望在对方的会话数据库中保存该类型的消息
        AgreeAddFriendMessage msg = new AgreeAddFriendMessage();
        final CommonBmobUser currentUser = BmobUser.getCurrentUser(CommonBmobUser.class);
        msg.setContent("我通过了你的好友验证请求，我们可以开始 聊天了!");//这句话是直接存储到对方的消息表中的
        Map<String, Object> map = new HashMap<>();
        map.put("msg", currentUser.getUsername() + "同意添加你为好友");//显示在通知栏上面的内容
        map.put("uid", add.getUid());//发送者的uid-方便请求添加的发送方找到该条添加好友的请求
        map.put("time", add.getTime());//添加好友的请求时间
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功 更新当前item 为已经添加
                    NewFriendManager.updateNewFriend(add, Config.STATUS_VERIFIED,currentUserId);
                    listener.done(msg, e);
                } else {//发送失败
                    Logger.e(e.getMessage());
                    listener.done(msg, e);
                }
            }
        });
    }

    @Override
    protected NewFriendContract.Presenter initPresent() {
        return new NewFriendActivityPresenter(this);
    }
    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

}
