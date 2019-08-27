package com.wxq.commonlibrary.pay;

public interface OnPayListener {
//        void beforePay();

        void paySuccess(String orderNumber);

        void payFailure(String message);

//        void payInterfaceFailure();
    }
