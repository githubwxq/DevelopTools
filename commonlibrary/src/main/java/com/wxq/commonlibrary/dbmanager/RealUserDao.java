package com.wxq.commonlibrary.dbmanager;
import com.facebook.stetho.common.LogUtil;
import com.wxq.commonlibrary.dao.UserDao;
import com.wxq.commonlibrary.model.User;
import java.util.List;

public class RealUserDao {
    private static String TAG = "RealUserDao";
    private static RealUserDao INSTANCE;

    private UserDao userDao;

    public RealUserDao() {
        this.userDao = DbManager.getInstance().getDaoSession().getUserDao();
    }

    public static RealUserDao getInstance() {
        if (INSTANCE == null) {
            synchronized (RealUserDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RealUserDao();
                }
            }
        }
        return INSTANCE;
    }

    public User addUser(User user) {
        try {
            userDao.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG, "new user is inserted, the id is " + user.getName());
        return user;
    }

    public synchronized User queryUser(int id) {
        User user = userDao.queryBuilder().
                where(UserDao.Properties.Uid.eq(id)).build().unique();
        return user;
    }
    public synchronized User queryUserByPhone(String phone) {
       List<User>  userList = userDao.queryBuilder().
                where(UserDao.Properties.Phone.eq(phone)).build().list();
        if (userList.size()>0) {
            return userList.get(0);
        }else {
            return null ;
        }
    }

    public synchronized List<User> queryAllUsers() {
        List<User> userList = userDao.queryBuilder().
                orderDesc(UserDao.Properties.Uid).list();
        return userList;
    }

    public synchronized void updateUser(User user) {
        if (user == null) {
            return;
        }
        userDao.update(user);
    }

    public synchronized void deleteUser(String name) {
        User user = userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique(); //获取单个对象可能为空就是不存在
        if (user == null) {
            return;
        }
        userDao.delete(user);
    }

    public synchronized void insertUser(User user) {
        if (user == null) {
            return;
        }
        userDao.insert(user);
    }
}
