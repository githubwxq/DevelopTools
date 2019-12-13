//package com.wxq.commonlibrary.mydb;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import com.wxq.commonlibrary.R;
//import com.wxq.commonlibrary.model.User;
//import com.wxq.commonlibrary.mydb.db.BaseDao;
//import com.wxq.commonlibrary.mydb.db.BaseDaoFactory;
//import com.wxq.commonlibrary.mydb.db.BaseDaoNewImpl;
//import com.wxq.commonlibrary.mydb.db.IBaseDao;
//
//import java.util.List;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//    public void clickInsert(View view){
//        IBaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class, User.class);
//        baseDao.insert(new User(6,"abcd","123"));
//        Toast.makeText(this,"执行成功!",Toast.LENGTH_SHORT).show();
//    }
//    public void clickUpdate(View view){
//        BaseDaoNewImpl baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class,User.class);
//        //update tb_user where name='jett' set password='1111'
//        User user=new User();
//        user.setName("abcd");
//        User where=new User();
//        where.setId(6);
//        baseDao.update(user,where);
//        Toast.makeText(this,"执行成功!",Toast.LENGTH_SHORT).show();
//    }
//    public void clickDelete(View view){
//        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class,User.class);
//        User where=new User();
//        where.setName("abcd");
//        where.setId(6);
//        baseDao.delete(where);
//    }
//    public void clickSelect(View view){
//        BaseDaoNewImpl baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDaoNewImpl.class,User.class);
//        User where=new User();
//        where.setName("abcd");
//        List<User> list=baseDao.query(where);
//        Log.i("jett","listsize="+list.size());
//        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i)+" i="+i);
//        }
//    }
//}
//
//
//
//
//
//
//
//
