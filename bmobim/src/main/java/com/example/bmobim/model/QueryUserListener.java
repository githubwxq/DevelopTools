package com.example.bmobim.model;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * @author :smile
 * @project:QueryUserListener
 * @date :2016-02-01-16:23
 */
public abstract class QueryUserListener extends BmobListener1<CommonBmobUser> {

    public abstract void done(CommonBmobUser s, BmobException e);

    @Override
    protected void postDone(CommonBmobUser o, BmobException e) {
        done(o, e);
    }
}
