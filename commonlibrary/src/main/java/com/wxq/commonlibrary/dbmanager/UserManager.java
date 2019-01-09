package com.wxq.commonlibrary.dbmanager;

import com.wxq.commonlibrary.dao.DaoSession;
import com.wxq.commonlibrary.dao.UserDao;
import com.wxq.commonlibrary.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/25
 * @description 用户数据库的管理类
 */
public class UserManager {

//    /**
//     * 获取一个用户的所有好友
//     * @param storeUid
//     * @return
//     */
//    public static List<User> getAllFriends(String storeUid) {
//        try {
//            List<User> list =  DbManager.getInstance().getDaoSession().getUserDao().queryBuilder()
//                    .where(UserDao.Properties.Flag.eq(-1),
//                            UserDao.Properties.StoreUid.eq(storeUid))
//                    .build().list();
//            List<User> listNew = new ArrayList<>(list.size());
//
//            return listNew;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }

}
