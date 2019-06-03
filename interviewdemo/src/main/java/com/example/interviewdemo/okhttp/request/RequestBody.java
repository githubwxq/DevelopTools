package com.example.interviewdemo.okhttp.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class RequestBody {
    //表单提交 使用urlencoded编码
    private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    //字符编码
    private final static String CHARSET = "utf-8";
    //请求参数
    Map<String, String> encodedBodys = new HashMap<>();

    public String contentType() {
        return CONTENT_TYPE;
    }

    public long contentLength() {
        return body().getBytes().length;
    }



    public String body(){
        StringBuffer sb = new StringBuffer();

        for (Map.Entry<String, String> entry : encodedBodys.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();


    }

    //添加请求参数
    public RequestBody add(String name, String value) {
        try {
            encodedBodys.put(URLEncoder.encode(name, CHARSET), URLEncoder.encode(value, CHARSET));

            System.out.println(URLEncoder.encode(name, CHARSET));

            System.out.println(URLEncoder.encode(value, CHARSET));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

}
