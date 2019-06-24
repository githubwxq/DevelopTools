package com.wxq.commonlibrary.http.common;

import android.util.Log;

import com.wxq.commonlibrary.datacenter.AllDataCenterManager;
import com.wxq.commonlibrary.model.KlookResponseData;
import com.wxq.commonlibrary.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

public class KlookTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response response=chain.proceed(request);
        ResponseBody body = response.body();
//        Log.e("wxq","bodybodybodybodybody"+body.string());
        if (body != null && response.isSuccessful()) {
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE);
            MediaType contentType = body.contentType();
            Charset charset = Util.UTF_8;
            if (contentType != null) {
                charset = contentType.charset(Util.UTF_8);
            }
            Buffer buffer = source.buffer().clone();
            String content = buffer.readString(charset);
            if (!StringUtils.isEmpty(content)) {
                // 打印内容
                if (content.contains("token过期,请重新登陆")) {
                    //获取新的token 新的token 通过调用登入接口 用户名和密码获取
                    String newToken = getNewToken();
                    AllDataCenterManager.getInstance().token=newToken;
                    Log.e("wxq","newToken========================"+newToken);

                    if (!newToken.isEmpty()) {
                        //重新请求原先这个接口
                        Request.Builder builder = request.newBuilder().header("AccessToken", newToken);
                        return chain.proceed(builder.build());
                    }
                }

            }
        }
        return response;
    }

    private String getNewToken() {
        HashMap<String,String> parmer=new HashMap<>()   ;
        parmer.put("password","111111");
        parmer.put("phone","17501461752");
        try {
            KlookResponseData<String> body = Api.getInstance().getApiService(CommonApi.class).login(parmer).execute().body();
            if (body.code==200) {
                return body.data;// 返回token
            }else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }




}
