package com.example.bmobim.bean;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.adapter.NewRecordPlayClickListener;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 王晓清.
 * data 2019/1/25.
 * describe . 图片类消息处理
 */

public class VoiceMessage extends Message {


    public VoiceMessage(BmobIMMessage message) {
        super(message);
    }

    @Override
    public void updateDifferentView(BaseViewHolder helper) {
        helper.setText(R.id.tv_voice_length,extraMessageInfo.length);
        ImageView imageView= helper.getView(R.id.iv_voice);
        imageView.setOnClickListener(new NewRecordPlayClickListener(helper.convertView.getContext(),bmobIMMessage,imageView));
    }


    @Override
    public int getItemType() {
        if (isSelfMessage()) {
            return R.layout.item_chat_sent_voice;
        } else {
            return R.layout.item_chat_received_voice;
        }
    }
}
