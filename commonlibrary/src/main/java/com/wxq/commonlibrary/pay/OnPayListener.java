package com.wxq.commonlibrary.pay;

public interface OnPayListener {
        void beforePay();

        void paySuccess(String orderNumber);

        void payFailure(int code, String message);

        void payInterfaceFailure();
    }
