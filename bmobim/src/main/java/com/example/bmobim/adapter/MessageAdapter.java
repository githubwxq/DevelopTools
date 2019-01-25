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
        addItemType(BmobIMMessageType.IMAGE.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.TEXT.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.VOICE.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.LOCATION.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.VIDEO.getValue(), R.layout.conversion_adapter_item);

        addItemType(BmobIMMessageType.IMAGE.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.TEXT.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.VOICE.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.LOCATION.getValue(), R.layout.conversion_adapter_item);
        addItemType(BmobIMMessageType.VIDEO.getValue(), R.layout.conversion_adapter_item);





    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {

    }
}
