package com.example.bmobim.bean;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.bmobim.R;
import com.juziwl.uilibrary.easycommonadapter.BaseAdapterHelper;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;

import java.text.SimpleDateFormat;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/25
 * desc:
 * version:1.0
 */
public abstract class Message implements MultiItemEntity {
    public   BmobIMMessage bmobIMMessage = null;
    public String currentUid="";
    public  BmobIMUserInfo userInfo;
    public  BmobIMConversation bmobIMConversation;

    public Message(BmobIMMessage message) {
        bmobIMMessage=message;
        bmobIMConversation=bmobIMMessage.getBmobIMConversation();
        try {
            currentUid = BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId();
            userInfo= message.getBmobIMUserInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *是否是自己发的
     */
    public boolean isSelfMessage(){
        if (bmobIMMessage.getFromId().equals(currentUid)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 初始化相同的控件行爲
     * @param helper
     */
    public void initSameViewAndListener(BaseViewHolder helper) {
        // 消息头像设置
        ImageView ivAvatar=helper.getView(R.id.iv_avatar);
        LoadingImgUtil.loadimg(userInfo.getAvatar(),ivAvatar,true);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击了头像");
            }
        });
        // 消息时间设置
       if (isShowTime){
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
           String time = dateFormat.format(bmobIMMessage.getCreateTime());
           helper.setVisible(R.id.tv_time,true).setText(R.id.tv_time,time);
       }else {
           helper.setVisible(R.id.tv_time,false);
       }
       // 消息状态设置
        if (isSelfMessage()) {
            int status =bmobIMMessage.getSendStatus();
            if (status == BmobIMSendStatus.SEND_FAILED.getStatus()) {
                helper.setVisible(R.id.iv_fail_resend,true);
                helper.setVisible(R.id.progress_load,false);
            } else if (status== BmobIMSendStatus.SENDING.getStatus()) {
                helper.setVisible(R.id.iv_fail_resend,false);
                helper.setVisible(R.id.progress_load,true);
            } else {
                helper.setVisible(R.id.iv_fail_resend,false);
                helper.setVisible(R.id.progress_load,false);
            }
        }
        if (isSelfMessage()){
            //重发
            helper.getView(R.id.iv_fail_resend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bmobIMConversation.resendMessage(bmobIMMessage, new MessageSendListener() {
                        @Override
                        public void onStart(BmobIMMessage msg) {
                            helper.setVisible(R.id.progress_load,true);
                            helper.setVisible(R.id.iv_fail_resend,false);
                            helper.setVisible(R.id.tv_send_status,false);
                        }

                        @Override
                        public void done(BmobIMMessage msg, BmobException e) {
                            if(e==null){
                                helper.setVisible(R.id.tv_send_status,true)
                                        .setText(R.id.tv_send_status,"已发送")
                                        .setVisible(R.id.iv_fail_resend,false)
                                        .setVisible(R.id.progress_load,false);
                            }else{
                                helper.setVisible(R.id.iv_fail_resend,true);
                                helper.setVisible(R.id.progress_load,false);
                                helper.setVisible(R.id.tv_send_status,false);
                            }
                        }
                    });
                }
            });
        }
    }

    boolean isShowTime=false;

    public  void setIsShowTime(boolean isShow){
        isShowTime=isShow;
    };

    public  void updateView(BaseViewHolder helper){
        initSameViewAndListener(helper);
        updateDifferentView(helper);
    };

    public abstract void updateDifferentView(BaseViewHolder helper);
}
