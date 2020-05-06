package com.example.uitestdemo.bean;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;

import retrofit2.http.POST;

public class TipItem  extends PostItem {

    public String getTipMessage() {
        return tipMessage;
    }

    public void setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
    }

    private String tipMessage;


    @Override
    public void bindViewWidthData(BaseViewHolder helper) {
        helper.setText(R.id.tip_info,tipMessage);
    }

    @Override
    public int getItemType() {
        return R.layout.tip_item;
    }
}
