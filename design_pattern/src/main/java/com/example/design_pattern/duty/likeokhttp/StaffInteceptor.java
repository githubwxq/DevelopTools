package com.example.design_pattern.duty.likeokhttp;

public class StaffInteceptor implements ModelOkhttpInterceptor {

    @Override
    public void intercept(Chain chain) {
        String user=((RealChain)chain).getmUser()+"职员审批通过";

        chain.procced(500,user);
    }
}
