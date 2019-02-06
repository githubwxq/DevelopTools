package com.example.bmobim.bean;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.adapter.MessageAdapter;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by 王晓清.
 * data 2019/1/25.
 * describe . 视频类消息处理
 */

public class VideoMessage extends Message {
    MessageAdapter messageAdapter;

    public VideoMessage(BmobIMMessage message) {
        super(message);
    }

    @Override
    public void updateDifferentView(BaseViewHolder helper) {
        ImageView iv_picture=helper.getView(R.id.iv_picture);
        if (extraMessageInfo.videoImage!=null) {
            LoadingImgUtil.loadimg(extraMessageInfo.videoImage,iv_picture,false);
        }
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureVideoPlayActivity.navToVideoPlay(helper.getConvertView().getContext(), bmobIMMessage.getContent(), extraMessageInfo.videoImage, true, GlobalContent.VIDEOPATH);
            }
        });
    }


    @Override
    public int getItemType() {
        if (isSelfMessage()) {
            return R.layout.item_chat_sent_video;
        } else {
            return R.layout.item_chat_received_video;
        }
    }

    public void setAdapter(MessageAdapter messageAdapter) {
        this.messageAdapter=messageAdapter;
    }
}
