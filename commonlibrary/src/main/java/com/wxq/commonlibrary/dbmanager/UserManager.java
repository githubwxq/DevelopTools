package com.wxq.commonlibrary.dbmanager;

import com.wxq.commonlibrary.dao.UserDao;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/25
 * @description 用户数据库对应的每一张表的操作  每个mode对应一张表每张表对应一个manager
 */
public class UserManager {

    /**
     * 获取个人信息条目根据手机号
     * @return
     */
    public static List<User> getUserByPhoto(String photo) {
        try {
            List<User> list = AllDataCenterManager.getInstance().getDaoSession(photo).getUserDao().queryBuilder()
                    .where(UserDao.Properties.Phone.eq(photo))
                    .build().list();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }






}
