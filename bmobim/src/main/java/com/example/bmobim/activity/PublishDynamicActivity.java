package com.example.bmobim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.bmobim.R;
import com.example.bmobim.bean.DynamicBean;
import com.juziwl.uilibrary.edittext.EmojiFilterEditText;
import com.luck.picture.lib.PictureSelectAdapter;
import com.luck.picture.lib.PictureSelectorView;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.utils.DisplayUtils;
import com.luck.picture.lib.utils.ImageSeclctUtils;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.bmob.SimpleUploadBatchListener;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.util.DensityUtil;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.VideoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建日期：2019 2 2 号
 * 描述:发布动态页面
 *
 * @author:wxq
 */
public class PublishDynamicActivity extends BaseActivity {

    public static final String TITLE = "发布动态";
    @BindView(R.id.line)
    View line;
    @BindView(R.id.et_content)
    EmojiFilterEditText etContent;
    @BindView(R.id.lv_img)
    ImageView lvImg;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.ll_mob)
    LinearLayout llMob;
    @BindView(R.id.pic_select_view)
    PictureSelectorView picSelectView;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;
    @BindView(R.id.rl_common)
    LinearLayout rlCommon;
    @BindView(R.id.rl_one)
    LinearLayout rlOne;

    private static final int COLUME = 3;
    private static final int MAXSELECTNUM = 30;


    public List<LocalMedia> selectList = new ArrayList<>();


    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, PublishDynamicActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @Override
    protected void initViews() {
        topHeard.setTitle(TITLE).setRightText("发布").setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }).setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断内容是否为空
                if (StringUtils.isEmpty(etContent.getText().toString())) {
                    ToastUtils.showShort("请输入内容");
                }else {
                    if (selectList.size()==1&&selectList.get(0).getDuration()>0) {
                        sendTextWithVideo(etContent.getText().toString(),selectList.get(0).getPath(),selectList.get(0).getDuration());
                    }else {
                        sendTextWithImages(etContent.getText().toString(),selectList);
                    }
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
                ImageSeclctUtils.openAlbum(PublishDynamicActivity.this, selectList);
            }
        });
    }


    private void sendTextWithImages(String content, List<LocalMedia> selectList) {
        showLoadingDialog();
        final String[] filePaths = new String[selectList.size()];
        for (int i = 0; i < selectList.size(); i++) {
            filePaths[i] = selectList.get(i).getPath();
        }
        if (selectList.size()>0) {
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files,List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if(urls.size()==filePaths.length){ //如果数量相等，则代表文件全部上传完成
                        //do something
                        DynamicBean dynamicBean=new DynamicBean();
                        dynamicBean.content=content;
                        StringBuffer stringBuffer=new StringBuffer();
                        for (int i = 0; i < urls.size(); i++) {
                            if (i==urls.size()-1) {
                                stringBuffer.append(urls.get(i));
                            }else {
                                stringBuffer.append(urls.get(i)+";");
                            }
                        }
                        dynamicBean.pics=stringBuffer.toString();
                        dynamicBean.video="";
                        dynamicBean.videoImage="";
                        dynamicBean.length="";
                        dynamicBean.publishUser=BmobUser.getCurrentUser(CommonBmobUser.class);
                        dynamicBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                dismissLoadingDialog();
                                if (e==null) {
                                    ToastUtils.showShort("发布成功");
                                    finishActivity();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    ToastUtils.showShort("错误码"+statuscode +",错误描述："+errormsg);
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {

                }
            });
        }else {
            //do something
            DynamicBean dynamicBean=new DynamicBean();
            dynamicBean.content=content;
            dynamicBean.pics="";
            dynamicBean.video="";
            dynamicBean.length="";
            dynamicBean.videoImage="";
            dynamicBean.publishUser=BmobUser.getCurrentUser(CommonBmobUser.class);
            dynamicBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    dismissLoadingDialog();
                    if (e==null) {
                        ToastUtils.showShort("发布成功");
                        finishActivity();
                    }
                }
            });
        }
    }

    private void sendTextWithVideo(String content, String path, long duration) {
        showLoadingDialog();
        String videoimagePath=  VideoUtils.getVideoFirstImage(path);
        String[] filePaths=new String[2];
        filePaths[0]=path;
        filePaths[1]=videoimagePath;
        BmobFile.uploadBatch(filePaths, new SimpleUploadBatchListener(getContext(),2) {
            @Override
            public void success(List<BmobFile> list, List<String> urls) {
                DynamicBean dynamicBean=new DynamicBean();
                dynamicBean.content=content;
                dynamicBean.pics="";
                dynamicBean.video=urls.get(0);
                dynamicBean.videoImage=urls.get(1);
                dynamicBean.length=(int)duration+"";
                dynamicBean.publishUser=BmobUser.getCurrentUser(CommonBmobUser.class);
                dynamicBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null) {
                            ToastUtils.showShort("发布成功");
                            finishActivity();
                        }
                        dismissLoadingDialog();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picSelectView.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = picSelectView.getSelectList();
                    ToastUtils.showShort("选择了"+selectList.size()+"张图片或者视频");
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }
}
