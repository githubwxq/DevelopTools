package com.example.design_pattern.duty.likeokhttp;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args){
        List<ModelOkhttpInterceptor> okhttpInterceptors=new ArrayList<>();
        okhttpInterceptors.add(new StaffInteceptor());
        okhttpInterceptors.add(new ManagerInterceptor());
        okhttpInterceptors.add(new BossInterceptor());

        Chain chain=new RealChain(okhttpInterceptors,0,"王晓清");

        chain.procced(100,"小李");

    }

}
