package com.wxq.commonlibrary.bmob;

import com.wxq.commonlibrary.dao.NewFriendDao;
import com.wxq.commonlibrary.dbmanager.DbManager;
import com.wxq.commonlibrary.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/25
 * @description 用户数据库的管理类
 */
public class NewFriendManager {

    /**
     * 添加一个好友请求
     *
     * @return
     */
    public static long insertFriend(NewFriend friend) {
        long insert = DbManager.getInstance().getDaoSession().getNewFriendDao().insert(friend);
        return insert;
    }


    public static boolean insertOrReplaceFriend(NewFriend friend) {
        List<NewFriend> list = DbManager.getInstance().getDaoSession().getNewFriendDao().queryBuilder()
                .where(NewFriendDao.Properties.StoreId.eq(friend.getStoreId()),NewFriendDao.Properties.Uid.eq(friend.getUid()))
                .build().list();
        if (list==null||list.size()==0) {
            insertFriend(friend);
        }
        return list.size()>0;
    }


    /**
     * 获取一个用户的所有好友请求
     *
     * @param storeUid
     * @return
     */
    public static List<NewFriend> getAllFriends(String storeUid) {
        try {
            List<NewFriend> list = DbManager.getInstance().getDaoSession().getNewFriendDao().queryBuilder()
                    .where(NewFriendDao.Properties.StoreId.eq(storeUid))
                    .build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<NewFriend> getNoVerticalAllFriends(String storeUid) {
        try {
            List<NewFriend> list = DbManager.getInstance().getDaoSession().getNewFriendDao().queryBuilder()
                    .where(NewFriendDao.Properties.StoreId.eq(storeUid),NewFriendDao.Properties.Status.eq(Config.STATUS_VERIFY_NONE))
                    .build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }





    /**
     * 批量更新未读未验证的状态为已读
     */
    public static void updateBatchStatus(String storeUid){
        List<NewFriend> infos =getNoVerticalAllFriends(storeUid);
        if(infos!=null && infos.size()>0){
            int size =infos.size();
            List<NewFriend> all =new ArrayList<>();
            for (int i = 0; i < size; i++) {
                NewFriend msg =infos.get(i);
                msg.setStatus(Config.STATUS_VERIFY_READED);
                all.add(msg);
            }
            insertBatchMessages(infos);
        }
    }

    private  static  void insertBatchMessages(List<NewFriend> infos) {
        //批量更新
        DbManager.getInstance().getDaoSession().getNewFriendDao().insertOrReplaceInTx(infos);
    }


    public static void updateNewFriend(NewFriend add, int statusVerified, String currentUserId) {
        add.setStatus(statusVerified);
        DbManager.getInstance().getDaoSession().getNewFriendDao().insertOrReplaceInTx(add);
    }
}
