package com.wxq.commonlibrary.toolsqlite.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wxq.commonlibrary.R;
import com.wxq.commonlibrary.toolsqlite.bean.DBInfo;
import com.wxq.commonlibrary.toolsqlite.dao.BaseDao;
import com.wxq.commonlibrary.toolsqlite.dao.BaseDaoFactory;
import com.wxq.commonlibrary.toolsqlite.dao.BaseDaoInterface;
import java.util.List;

public class ActivitySqlite extends AppCompatActivity {
    //显示查询结果
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sqlite);
//        result = findViewById(R.id.showResult);
    }

    /**
     * 添加数据
     *
     * @param view
     */
    public void clickInsert(View view) {
        BaseDaoInterface baseDao = BaseDaoFactory.getInstance().getBaseDao(BeanPerson.class);
        baseDao.insert(new BeanPerson("0", 12));
        select(null, BeanPerson.class);
    }

    /**
     * 删除数据
     *
     * @param view
     */
    public void clickDelete(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BeanPerson.class);
        baseDao.delete(new BeanPerson());
        select(null, BeanPerson.class);
    }

    /**
     * 修改数据
     *
     * @param view
     */
    public void clickUpdate(View view) {
        BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(BeanPerson.class);
        BeanPerson user = new BeanPerson();
        user.age = 13;
        BeanPerson where = new BeanPerson();
        where.age = 12;
        baseDao.update(user, where);
        select(null, BeanPerson.class);
    }

    /**
     * 查询数据
     *
     * @param view
     */
    public void clickSelect(View view) {
        select(null, BeanPerson.class);
    }

    /**
     * 显示查询结果
     */
    private void select(DBInfo dbInfo, Class entry) {
        BaseDaoInterface baseDao = BaseDaoFactory.getInstance(dbInfo).getBaseDao(entry);
        List list = baseDao.query();
        result.setText(BaseDaoFactory.getDBPath() + "\n" + list.toString());
    }

    /**
     * 多用户登录
     *
     * @param view
     */
    public void clickLogin(View view) {
        String showResult = "";
        for (int i = 1; i < 4; i++) {
            BeanUser user = new BeanUser(Integer.toString(i), "李涛" + i, "123456");
            DBInfo dbInfo = new DBInfo("litao" + i, "test" + i);
            //根据用户名创建用户目录，根据数据库名创建数据库
            BaseDao baseDao = BaseDaoFactory.getInstance(dbInfo).getBaseDao(BeanUser.class);
            baseDao.delete(null);
            baseDao.insert(user);

            showResult += BaseDaoFactory.getDBPath() + "\n" + baseDao.query().toString() + "\n";
        }
        result.setText(showResult);
    }

    /**
     * 升级数据库
     *
     * @param view
     */
    public void clickUpdateDB(View view) {
        DBInfo dbInfo = new DBInfo("lili", "test");
        BeanUser user = new BeanUser(Integer.toString(11), "莉莉", "123456");
        BaseDao baseDao = BaseDaoFactory.getInstance(dbInfo).getBaseDao(BeanUser.class);
        baseDao.deleteTable();
        //重启数据库，清除Cursor缓存
        BaseDaoFactory.getInstance(dbInfo).restartDB();
        baseDao = BaseDaoFactory.getInstance(dbInfo).getBaseDao(BeanUser.class);
        baseDao.insert(user);

        String showResult = "";
        showResult += BaseDaoFactory.getDBPath() + "\n 旧表数据：\n" + baseDao.query().toString() + "\n";
        result.setText(showResult);

        //升级数据库
        baseDao.upgrade(dbInfo, BeanUser2.class);
        baseDao = BaseDaoFactory.getInstance(dbInfo).getBaseDao(BeanUser2.class);
        showResult += " 新表数据：\n" + baseDao.query().toString() + "\n";
        result.setText(showResult);
    }
}








