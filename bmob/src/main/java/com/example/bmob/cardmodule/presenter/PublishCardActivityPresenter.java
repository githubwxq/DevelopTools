package com.example.bmob.cardmodule.presenter;

import com.example.bmob.cardmodule.contract.PublishCardContract;
import com.example.module_login.bean.Card;
import com.example.module_login.bean.User;
import com.luck.picture.lib.entity.LocalMedia;
import com.wxq.commonlibrary.base.RxPresenter;

import java.util.List;
import java.util.ArrayList;

import com.example.bmob.cardmodule.activity.PublishCardActivity;
import com.wxq.commonlibrary.util.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 作者
 * <p>
 * 邮箱：
 */
public class PublishCardActivityPresenter extends RxPresenter<PublishCardContract.View> implements PublishCardContract.Presenter {

     public String publishContent="";
     public String publicPic="";

    public PublishCardActivityPresenter(PublishCardContract.View view) {
        super(view);
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void saveCard(String cardContent, List<LocalMedia> selectList) {
        mView.showLoadingDialog();
        if (selectList.size()>0) {
            final String[] filePaths = new String[selectList.size()];
            for (int i = 0; i < selectList.size(); i++) {
                if (selectList.get(i).getPictureType().contains("video/")) {
                    filePaths[i]=selectList.get(i).getPath();
                }else {
                    filePaths[i]=selectList.get(i).getCompressPath();
                }
            }
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> files,List<String> urls) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                        StringBuffer stringBuffer=new StringBuffer();
                        for (int i = 0; i < urls.size(); i++) {
                            stringBuffer.append(urls.get(i)+",");
                        }
                        lastSaveCard(cardContent,stringBuffer.substring(0,stringBuffer.length()-1));
                    }
                }

                @Override
                public void onError(int statuscode, String errormsg) {
                    ToastUtils.showShort("错误码"+statuscode +",错误描述："+errormsg);
                    mView.dismissLoadingDialog();
                }

                @Override
                public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {
                    //1、curIndex--表示当前第几个文件正在上传
                    //2、curPercent--表示当前上传文件的进度值（百分比）
                    //3、total--表示总的上传文件数
                    //4、totalPercent--表示总的上传进度（百分比）
                }
            });
        }else {
            lastSaveCard(cardContent,"");
        }
    }

    /**
     * 最后上传数据
     * @param cardContent
     * @param images
     */
    private void lastSaveCard(String cardContent, String images) {
        Card card=new Card();
        card.user= BmobUser.getCurrentUser(User.class);
        card.content=cardContent;
        card.images=images;
        card.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                mView.dismissLoadingDialog();
                ToastUtils.showShort("上传成功");
            }
        });
    }
}
