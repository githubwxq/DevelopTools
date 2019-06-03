package com.example.interviewdemo.okhttp.connection;

import android.text.TextUtils;

import com.example.interviewdemo.okhttp.request.HttpUrl;
import com.example.interviewdemo.okhttp.request.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:
 * version:1.0
 */
public class HttpConnection {
    static final String HTTPS = "https";
    Socket socket;
    //请求信息
    Request request;
    InputStream is;
    OutputStream os;
    //最后使用时间
    long lastUsetime;

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 判断是否是相同的请求地址
     */
    public boolean isSameAddress(String host, int port) {
        if (null == socket) {
            return false;
        }
        return TextUtils.equals(socket.getInetAddress().getHostName(), host) && port == socket
                .getPort();
    }

    /**
     * 创建socket，支持Https协议
     */
    private void createSocket() throws IOException {
        if (null == socket || socket.isClosed()) {
            HttpUrl url = request.url();
            //需要sslsocket
            if (url.getProtocol().equalsIgnoreCase(HTTPS)) {
                socket = SSLSocketFactory.getDefault().createSocket();
            } else {
                socket = new Socket();
            }
            socket.connect(new InetSocketAddress(url.getHost(), url.getPort()));
            os = socket.getOutputStream();
            is = socket.getInputStream();
        }
    }

    /**
     * 发送请求
     *
     * @param httpCodec
     * @return
     */
    public InputStream call(HttpCodec httpCodec) throws IOException {
        try {
            createSocket();
            //写出请求
            httpCodec.writeRequest(os, request);
            return is;
        } catch (Exception e) {
            closeQuietly();
            throw new IOException(e);
        }
    }

    /**
     * 断开连接
     */
    public void closeQuietly() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket = null;
            }
        }
    }

    /**
     * 更新最后使用时间
     */
    public void updateLastUseTime() {
        //更新最后使用时间
        lastUsetime = System.currentTimeMillis();
    }




}
