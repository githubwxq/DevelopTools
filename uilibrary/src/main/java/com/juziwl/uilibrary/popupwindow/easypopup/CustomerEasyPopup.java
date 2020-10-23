package com.juziwl.uilibrary.popupwindow.easypopup;

import android.view.View;

public class CustomerEasyPopup extends BasePopup<CustomerEasyPopup> {


    public CustomerEasyPopup(String parmer, CustomerInterface clickInterface) {
        this.parmer = parmer;
        this.clickInterface = clickInterface;
    }

    //班群的id
    String parmer = "";
    CustomerInterface clickInterface;

    @Override
    protected void onPopupWindowCreated() {
        super.onPopupWindowCreated();
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void onPopupWindowViewCreated(View contentView) {
        super.onPopupWindowViewCreated(contentView);
    }

    @Override
    protected void initViews(View view, CustomerEasyPopup popup) {

    }

    public  interface CustomerInterface {
        void clickFile();

        void clickResource();


    }
}
