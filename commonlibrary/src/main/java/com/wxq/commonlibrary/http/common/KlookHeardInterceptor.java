package com.wxq.commonlibrary.http.common;

import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.util.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class KlookHeardInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!StringUtils.isEmpty(AllDataCenterManager.getInstance().token)) {
            Request.Builder builder = request.newBuilder().header(GlobalContent.ACCESSTOKEN,AllDataCenterManager.getInstance().token);
            return chain.proceed(builder.build());
        }
        return chain.proceed(request);
    }
}
