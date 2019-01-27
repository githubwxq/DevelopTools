package com.example.bmobim.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.Message;

import java.util.List;

import cn.bmob.newim.bean.BmobIMMessageType;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/01/25
 * desc:
 * version:1.0
 */
public class MessageAdapter extends BaseMultiItemQuickAdapter<Message, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MessageAdapter(List<Message> data) {
        super(data);
        addItemType(R.layout.item_chat_sent_message, R.layout.item_chat_sent_message);
        addItemType(R.layout.item_chat_received_message, R.layout.item_chat_received_message);


    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        int position = mData.indexOf(item);
        item.setIsShowTime(shouldShowTime(position));
        item.updateView(helper);
    }


    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;
    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = mData.get(position - 1).bmobIMMessage.getCreateTime();
        long curTime = mData.get(position).bmobIMMessage.getCreateTime();
        return curTime - lastTime > TIME_INTERVAL;
    }
}
