package com.wxq.developtools;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.juziwl.uilibrary.niceplayer.NiceVideoPlayer;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.mvplibrary.base.BaseActivity;
import com.wxq.mvplibrary.baserx.Event;
import com.wxq.mvplibrary.baserx.RxBus;
import com.wxq.mvplibrary.baserx.RxBusManager;
import com.wxq.mvplibrary.dbmanager.DbManager;
import com.wxq.mvplibrary.model.User;
import com.wxq.mvplibrary.router.RouterContent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxq on 2018/6/28.
 */

public class MvpMainActivity extends BaseActivity<MvpMainContract.Presenter> implements MvpMainContract.View {


    @BindView(R.id.iv_test_pic)
    ImageView ivTestPic;
    @BindView(R.id.player)
    NiceVideoPlayer player;
    @BindView(R.id.tv_hello)
    TextView tvHello;

  @BindView(R.id.tv_hello2)
    TextView tv_hello2;


    @Override
    protected void initViews() {

//        BarUtils.setStatusBarColor(this,getResources().getColor(R.color.common_account_red),255);
        tvHello.setText("000000000000000000000000");

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData(1);
                User user = new User();
                user.setUserName("wxq");
                user.setFlag(0);
                user.setAccessToken("token");
                DbManager.getInstance().getDaoSession().getUserDao().save(user);

                int size = DbManager.getInstance().getDaoSession().getUserDao().queryBuilder().list().size();

                showToast(size + "数据裤中数据");

                Event event = new Event(2, "wxq");

                RxBusManager.getInstance().post(event);

//                nesttes

                try {
                    ARouter.getInstance()
                            .build(RouterContent.AROUTER_MAIN)
                            .withString("name","name")
                            .withString("wxq","wxq")
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



        tv_hello2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ARouter.getInstance().build(RouterContent.NETTEST_MAIN).navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void dealWithRxEvent(int action, Event event) {
        super.dealWithRxEvent(action, event);
        if (action == 2) {
//            showToast(event.getObject() + "rxgetsuccess");

        }

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected MvpMainContract.Presenter initPresent() {
        return new MvpMainPresent(this);
    }


    @Override
    public void showRx() {

    }
}
