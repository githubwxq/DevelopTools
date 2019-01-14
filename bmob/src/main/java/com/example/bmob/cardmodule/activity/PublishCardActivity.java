package com.example.bmob.cardmodule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.bmob.R;
import com.example.bmob.R2;
import com.example.bmob.cardmodule.contract.PublishCardContract;
import com.example.bmob.cardmodule.presenter.PublishCardActivityPresenter;
import com.example.module_login.activity.SplashActivity;
import com.example.module_login.bean.User;
import com.juziwl.uilibrary.edittext.SuperEditText;
import com.luck.picture.lib.PictureSelectAdapter;
import com.luck.picture.lib.PictureSelectorView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.DisplayUtils;
import com.luck.picture.lib.utils.ImageSeclctUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.router.RouterContent;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.FileUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * 创建日期：2019 1 14
 * 描述:发布帖子动态页面
 *
 * @author:wxq
 */
@Route(path = RouterContent.BMOB_PUBLISHCARD)
public class PublishCardActivity extends BaseActivity<PublishCardContract.Presenter> implements PublishCardContract.View {

    public static final String TITLE = "";
    @BindView(R2.id.content)
    SuperEditText content;
    @BindView(R2.id.pic_select_view)
    PictureSelectorView picSelectView;
    @BindView(R2.id.rl_pic)
    RelativeLayout rlPic;


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, PublishCardActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_publish_card;
    }


    @Override
    protected void initViews() {
        //模拟登入
        BmobUser.loginByAccount("wxq123", "123", new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                } else {
                     ToastUtils.showShort("登入异常");
                }
            }
        });

        topHeard.setTitle("发布帖子").setRightText("发布").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty( content.getText().toString())) {
                    ToastUtils.showShort("请输入相关内容");
                }else {
                    mPresenter.saveCard( content.getText().toString(),selectList);
                }
            }
        });

        picSelectView.setOutputCameraPath(GlobalContent.imgPath);
        picSelectView.setOutputVideoPath(GlobalContent.VIDEOPATH);
        picSelectView.setLoginType(2);
        picSelectView.setVideoDuration(30_000L);
        picSelectView.initData(this, COLUME, MAXSELECTNUM, DisplayUtils.getScreenWidth(this) - DensityUtil.dip2px(this, 30), new PictureSelectAdapter.OnItemShowPicClickListener() {
            @Override
            public void onClick() {
                // 选择图片和视频
                ImageSeclctUtils.openAlbum(PublishCardActivity.this, selectList);
            }
        });
    }

    public List<LocalMedia> selectList = new ArrayList<>();
    private static final int COLUME = 3;
    private static final int MAXSELECTNUM = 30;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //设置控件
        picSelectView.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    selectList = picSelectView.getSelectList();
                default:
                    break;
            }
        }
    }

    @Override
    protected PublishCardContract.Presenter initPresent() {
        return new PublishCardActivityPresenter(this);
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }


}
