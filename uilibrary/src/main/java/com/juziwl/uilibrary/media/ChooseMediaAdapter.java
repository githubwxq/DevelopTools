package com.juziwl.uilibrary.media;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.glide.LoadingImgUtil;

import java.util.List;

public class ChooseMediaAdapter extends BaseQuickAdapter<LocalMediaEntity, BaseViewHolder> {

    public ChooseMediaAdapter( List<LocalMediaEntity> data) {
        super(R.layout.choose_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMediaEntity item) {
        LoadingImgUtil.loadimg(item.getPath(),helper.getView(R.id.image),false);
    }
}
