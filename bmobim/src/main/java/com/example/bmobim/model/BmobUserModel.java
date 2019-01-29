package com.example.bmobim.model;
import android.content.Context;
import com.example.bmobim.bean.Friend;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *单列通用类
 */
public class BmobUserModel {

    public int CODE_NULL=1000;
    public static int CODE_NOT_EQUAL=1001;

    public static final int DEFAULT_LIMIT=20;

    public Context getContext(){
        return null;
    }
    
    /**
     * 更新用户资料和会话资料
     *
     * @param event
     * @param listener
     */
    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String username = info.getName();
        String avatar = info.getAvatar();
        String title = conversation.getConversationTitle();
        String icon = conversation.getConversationIcon();
        //SDK内部将新会话的会话标题用objectId表示，因此需要比对用户名和私聊会话标题，后续会根据会话类型进行判断
        if (!username.equals(title) || (avatar != null && !avatar.equals(icon))) {
            BmobUserModel.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(CommonBmobUser s, BmobException e) {
                    if (e == null) {
                        String name = s.getUsername();
                        String avatar = s.avatar;
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        info.setName(name);
                        info.setAvatar(avatar);
                        //TODO 用户管理：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().updateUserInfo(info);
                        //TODO 会话：4.7、更新会话资料-如果消息是暂态消息，则不更新会话资料
                        if (!msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        ToastUtils.showShort("出错了");
//                        Logger.e(e);
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.done(null);
        }
    }


    /**
     * TODO 用户管理：2.6、查询指定用户信息
     *
     * @param objectId
     * @param listener
     */
    public void queryUserInfo(String objectId, final QueryUserListener listener) {
        BmobQuery<CommonBmobUser> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(
                new FindListener<CommonBmobUser>() {
                    @Override
                    public void done(List<CommonBmobUser> list, BmobException e) {
                        if (e == null) {

                            if (list != null && list.size() > 0) {
                                listener.done(list.get(0), null);
                            } else {
                                listener.done(null, new BmobException(000, "查无此人"));
                            }
                        } else {
                            listener.done(null, e);
                        }
                    }
                });
    }

    //TODO 好友管理：9.12、添加好友
    public void agreeAddFriend(CommonBmobUser friend, SaveListener<String> listener) {
        CommonBmobUser user = BmobUser.getCurrentUser(CommonBmobUser.class);
        BmobQuery<Friend> query = new BmobQuery<>();
        query.addWhereEqualTo("user",user);
        query.addWhereEqualTo("friendUser",friend);
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e==null) {
                    //她两不是好友
                    if (list.size()==0) {
                        Friend f = new Friend();
                        f.setUser(user);
                        f.setFriendUser(friend);
                        f.save(listener);
                    }else {
                        ToastUtils.showShort("已经是好友");
                    }
                }else {
                    ToastUtils.showShort(e.getMessage());
                }
            }
        });
    }

    /**
     * 查询好友
     * @param listener
     */
    //TODO 好友管理：9.2、查询好友
    public void queryFriends(final FindListener<Friend> listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        CommonBmobUser user = BmobUser.getCurrentUser(CommonBmobUser.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Friend>() {
            @Override
            public void done(List<Friend> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(0, "暂无联系人"));
                    }
                } else {
                    listener.done(list, e);
                }
            }
        });
    }


    /**
     * TODO 用户管理：2.5、查询用户
     *
     * @param username
     * @param page
     * @param listener
     */
    public void queryUsers(String username, final int page, final FindListener<CommonBmobUser> listener) {
        BmobQuery<CommonBmobUser> query = new BmobQuery<>();
        //去掉当前用户
        try {
            CommonBmobUser user = BmobUser.getCurrentUser(CommonBmobUser.class);
            BmobQuery<CommonBmobUser> eq1 = new BmobQuery<CommonBmobUser>();
            eq1.addWhereNotEqualTo("username", user.getUsername());
            BmobQuery<CommonBmobUser> eq2 = new BmobQuery<CommonBmobUser>();
            eq2.addWhereContains("username", username);
            List<BmobQuery<CommonBmobUser>> andQuerys = new ArrayList<BmobQuery<CommonBmobUser>>();
            andQuerys.add(eq1);
//            andQuerys.add(eq2);
            query.and(andQuerys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        query.setLimit(10);
        query.setSkip(10*page);
        query.order("-createdAt");
        query.findObjects(new FindListener<CommonBmobUser>() {
            @Override
            public void done(List<CommonBmobUser> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(CODE_NULL, "查无此人"));
                    }
                } else {
                    listener.done(list, e);
                }
            }
        });
    }


    /**
     * 删除好友
     *
     * @param f
     * @param listener
     */
    //TODO 好友管理：9.3、删除好友
    public void deleteFriend(Friend f, UpdateListener listener) {
        Friend friend = new Friend();
        friend.delete(f.getObjectId(), listener);
    }






    //当单例对象被修饰成voliate后，每一次instance内存中的读取都会从主内存中获取，而不会从缓存中获取，这样就解决了双重效验锁单例模式的缺陷
    private static volatile BmobUserModel instance =null;
    private BmobUserModel(){}
    public static BmobUserModel getInstance(){
      if(instance ==null){
        synchronized(BmobUserModel.class){
            if(instance ==null){
                instance = new BmobUserModel();
            }
         }
      }
        return instance;
    }

}