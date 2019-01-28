package com.example.bmobim.bean;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.adapter.MessageAdapter;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 王晓清.
 * data 2019/1/25.
 * describe . 图片类消息处理
 */

public class ImageMessage extends Message {
    MessageAdapter messageAdapter;

    public ImageMessage(BmobIMMessage message) {
        super(message);
    }

    @Override
    public void updateDifferentView(BaseViewHolder helper) {
        ImageView iv_picture=helper.getView(R.id.iv_picture);
        LoadingImgUtil.loadimg(bmobIMMessage.getContent(),iv_picture,false);
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPositon=-1;
                // 前往预览页面
                List<String> images=new ArrayList<>();
                for (Message message : messageAdapter.getData()) {
                    if ((ExtraMessageInfo.IAMGE).equals(message.extraMessageInfo.type)) {
                        images.add(message.bmobIMMessage.getContent());
                    }
                }
                currentPositon=images.indexOf(bmobIMMessage.getContent());
                StringBuffer stringBuffer=new StringBuffer();
                for (int i = 0; i < images.size(); i++) {
                    if (i==images.size()-1) {
                        stringBuffer.append(images.get(i));
                    }else {
                        stringBuffer.append(images.get(i)+";");
                    }
                }
                WatchImagesActivity.navToWatchImages(helper.getConvertView().getContext(),stringBuffer.toString(),currentPositon);
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

    public void setAdapter(MessageAdapter messageAdapter) {
        this.messageAdapter=messageAdapter;
    }
}
