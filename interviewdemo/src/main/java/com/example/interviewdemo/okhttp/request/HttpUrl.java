package com.example.interviewdemo.okhttp.request;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:封装请求地址
 * <p>
 * version:1.0
 */
public class HttpUrl {
    /**
     * 主机协议
     */
    String protocol;


    /**
     * 主机名
     */
    String host;

    /**
     * 请求路径
     */
    String file;


    /**
     * 端口号
     */
    int port;

    /**
     * scheme://host:port/path?query#fragment
     *
     * @param url 请求地址
     */
    public HttpUrl(String url) throws MalformedURLException {
        URL url1 = new URL(url);
        host = url1.getHost();
        file = url1.getFile();
        file = TextUtils.isEmpty(file) ? "/" : file;
        protocol = url1.getProtocol();
        port = url1.getPort();
        port = port == -1 ? url1.getDefaultPort() : port;

    }


    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getFile() {
        return file;
    }

    public int getPort() {
        return port;
    }

}
