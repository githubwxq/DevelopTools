package com.wxq.mvplibrary.dbmanager;

import com.wxq.mvplibrary.dao.DaoSession;
import com.wxq.mvplibrary.dao.UserDao;
import com.wxq.mvplibrary.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/25
 * @description 用户数据库的管理类
 */
public class UserManager {

    /**
     * 获取一个用户的所有好友
     * @param storeUid
     * @return
     */
    public static List<User> getAllFriends(String storeUid) {
        try {
            List<User> list =  DbManager.getInstance().getDaoSession().getUserDao().queryBuilder()
                    .where(UserDao.Properties.Flag.eq(-1),
                            UserDao.Properties.StoreUid.eq(storeUid))
                    .build().list();
            List<User> listNew = new ArrayList<>(list.size());

            return listNew;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 插入多个好友
     * @param users
     */
    public static void insertFriends(List<User> users) {
        List<User> usersNew = new ArrayList<>(users.size());

        DbManager.getInstance().getDaoSession().getUserDao().insertOrReplaceInTx(usersNew);
    }

    /**
     * 删除一个好友
     * @param storeUid
     * @param forUid
     * @return
     */
    public static User getOneFirend(String storeUid, String forUid) {
        try {
            List<User> users = DbManager.getInstance().getDaoSession().getUserDao().queryBuilder()
                    .where(UserDao.Properties.Flag.eq(-1),
                            UserDao.Properties.StoreUid.eq(storeUid),
                            UserDao.Properties.UserId.eq(forUid))
                    .build().list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除一个用户的所有好友
     * @param storeUid
     */
    public static void deleteFriends(String storeUid) {
        UserDao userDao = DbManager.getInstance().getDaoSession().getUserDao();
        List<User> users = userDao.queryBuilder()
                .where(UserDao.Properties.Flag.eq(-1),
                UserDao.Properties.StoreUid.eq(storeUid))
                .build().list();
        userDao.deleteInTx(users);
    }
}
