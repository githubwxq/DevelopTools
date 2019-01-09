package com.example.module_login.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.example.module_login.R;
import com.example.module_login.R2;
import com.example.module_login.contract.SplashContract;
import com.example.module_login.presenter.SplashActivityPresent;
import com.luck.picture.lib.PictureSelectAdapter;
import com.luck.picture.lib.PictureSelectorView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.DisplayUtils;
import com.luck.picture.lib.utils.ImageSeclctUtils;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<SplashContract.Presenter> implements SplashContract.View {

    @BindView(R2.id.iv_bg)
    ImageView ivBg;
    @BindView(R2.id.pic_select_view)
    PictureSelectorView picSelectView;
    private static final int COLUME = 3;
    private static final int MAXSELECTNUM = 30;
    @Override
    protected void initViews() {
        //设置全屏
        BarUtils.setStatusBarVisibility(this, false);
        //点击背景如果当前有广告
        click(ivBg, o -> {
//            LoginActivity.navToActivity(this);
            finish();
        });
        picSelectView.setOutputCameraPath(GlobalContent.imgPath);
        picSelectView.setOutputVideoPath(GlobalContent.VIDEOPATH);
        picSelectView.setLoginType(2);
        picSelectView.setVideoDuration(30_000L);
        picSelectView.initData(this, COLUME, MAXSELECTNUM, DisplayUtils.getScreenWidth(this) - DensityUtil.dip2px(this, 30), new PictureSelectAdapter.OnItemShowPicClickListener() {
            @Override
            public void onClick() {
                // 选择图片和视频
                ImageSeclctUtils.openBulm(SplashActivity.this, selectList);
            }
        });
    }
    public List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> needList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picSelectView.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    needList.clear();
                    selectList = picSelectView.getSelectList();
                    ToastUtils.showShort("选择了"+selectList.size()+"张图片");
                default:
                    break;
            }
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashContract.Presenter initPresent() {
        return new SplashActivityPresent(this);
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

}
