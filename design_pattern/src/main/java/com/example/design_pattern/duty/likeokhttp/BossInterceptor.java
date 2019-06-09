package com.example.design_pattern.duty.likeokhttp;

public class BossInterceptor implements ModelOkhttpInterceptor {
    @Override
    public void intercept(Chain chain) {
        String user=((RealChain)chain).getmUser()+"老板审批通过";
        System.out.println(user);
        chain.procced(500,user);
    }
}
