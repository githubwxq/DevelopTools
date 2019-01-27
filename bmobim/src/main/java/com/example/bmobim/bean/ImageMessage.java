package com.example.bmobim.bean;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 王晓清.
 * data 2019/1/25.
 * describe . 图片类消息处理
 */

public class ImageMessage extends Message {


    public ImageMessage(BmobIMMessage message) {
        super(message);
    }

    @Override
    public void updateDifferentView(BaseViewHolder helper) {
        final BmobIMImageMessage message = BmobIMImageMessage.buildFromDB(true, bmobIMMessage);
        ImageView iv_picture=helper.getView(R.id.iv_picture);
        LoadingImgUtil.loadimg(TextUtils.isEmpty(message.getRemoteUrl()) ? message.getLocalPath():message.getRemoteUrl(),iv_picture,false);
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 前往预览页面

            }
        });

    }


    @Override
    public int getItemType() {
        if (isSelfMessage()) {
            return R.layout.item_chat_sent_image;
        } else {
            return R.layout.item_chat_received_image;
        }
    }
}
