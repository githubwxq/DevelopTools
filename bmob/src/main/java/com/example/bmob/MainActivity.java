package com.example.bmob;

import android.view.View;
import android.widget.TextView;

import com.example.bmob.model.User;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends BaseActivity {

    @BindView(R2.id.tv_add)
    TextView tvAdd;
    @BindView(R2.id.tv_delete)
    TextView tvDelete;
    @BindView(R2.id.tv_update)
    TextView tvUpdate;
    @BindView(R2.id.tv_query)
    TextView tvQuery;

    @Override
    protected void initViews() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @OnClick({R.id.tv_add, R.id.tv_delete, R.id.tv_update, R.id.tv_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                User  user=new User();
                user.name="wxq";
                user.age="18";
                user.email="805380422@qq.com";
                user.pic="http://bmob-cdn-23285.b0.upaiyun.com/2019/01/04/e6d6b68b409c7fa480f6335a1a008d8a.jpg";
                user.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e==null) {
                            Logger.e("objectId"+objectId);
                        }else {
                            ToastUtils.showShort(e.getMessage());
                        }

                    }
                });
                break;
            case R.id.tv_delete:

                User p3 = new User();
                p3.setObjectId("81122a163f");
                p3.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        ToastUtils.showShort("删除成功");
                    }
                });

                break;
            case R.id.tv_update:
                User p2 = new User();
                p2.name="wwwwxxxxxqqqq";
                p2.update("0a41079828", new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){

                        }else{

                        }
                    }

                });

                break;
            case R.id.tv_query:
//查找Person表里面id为6b6c11c537的数据
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject("0a41079828", new  QueryListener<User>() {
                @Override
                public void done(User object,BmobException e) {
                    if(e==null){
                    Logger.e(object.name+object.email);
                    }else{
                        Logger.e("查询失败：" + e.getMessage());
                    }
                }
            });
                break;
        }
    }
}
