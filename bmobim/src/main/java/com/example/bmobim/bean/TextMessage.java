package com.example.bmobim.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by 王晓清.
 * data 2019/1/25.
 * describe . 文本消息处理
 */

public class TextMessage extends Message {

//    @BindView(R.id.tv_time)
    TextView tvTime;
//    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
//    @BindView(R.id.tv_message)
    TextView tvMessage;
//    @BindView(R.id.iv_fail_resend)
    ImageView ivFailResend;
//    @BindView(R.id.tv_send_status)
    TextView tvSendStatus;
//    @BindView(R.id.progress_load)
    ProgressBar progressLoad;

    public TextMessage(BmobIMMessage message) {
        super(message);
    }

    @Override
    public void showTime(boolean isShow) {
        tvTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateView(BaseViewHolder helper) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(bmobIMMessage.getCreateTime());
        tvTime=helper.getView(R.id.tv_time);
        ivAvatar=helper.getView(R.id.iv_avatar);
        tvTime.setText(time);
        LoadingImgUtil.loadimg(userInfo.getAvatar(),ivAvatar,true);
        helper.setText(R.id.tv_message,bmobIMMessage.getContent());
    }


    @Override
    public int getItemType() {
        if (isSelfMessage()) {
            return R.layout.item_chat_sent_message;
        } else {
            return R.layout.item_chat_received_message;
        }
    }
}
