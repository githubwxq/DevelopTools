package com.example.design_pattern.duty.likeokhttp;

import java.util.List;

public class RealChain implements Chain {
    private List<ModelOkhttpInterceptor> modelOkhttpInterceptors;

    private  int mIndex;

    private  int mFree;

    public String getmUser() {
        return mUser;
    }

    private String mUser;

    public RealChain(List<ModelOkhttpInterceptor> modelOkhttpInterceptors, int mIndex, String mUser) {
        this.modelOkhttpInterceptors = modelOkhttpInterceptors;
        this.mIndex = mIndex;
        this.mUser = mUser;
    }


    @Override
    public void procced(int free, String user) {
        this.mFree=free;
        this.mUser=user;
        if (mIndex>=modelOkhttpInterceptors.size()) {
            System.out.println("审批错误，没有人能审批了");
            return;
        }
        RealChain next=new RealChain(modelOkhttpInterceptors,mIndex+1,user);
        ModelOkhttpInterceptor interceptor=modelOkhttpInterceptors.get(mIndex);
        interceptor.intercept(next);
    }
}
