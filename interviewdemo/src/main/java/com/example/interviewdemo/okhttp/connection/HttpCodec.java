package com.example.interviewdemo.okhttp.connection;

import com.example.interviewdemo.okhttp.request.Request;
import com.example.interviewdemo.okhttp.request.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/06/03
 * desc:封装http协议，读写http请求
 * version:1.0
 */
public class HttpCodec {
    //回车和换行
    static final String CRLF = "\r\n";
    static final int CR = 13;
    static final int LF = 10;
    static final String SPACE = " ";
    static final String VERSION = "HTTP/1.1";
    static final String COLON = ":";


    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNKED = "chunked";

    ByteBuffer byteBuffer;

    public HttpCodec() {
        //申请足够大的内存记录读取的数据 (一行)
        byteBuffer = ByteBuffer.allocate(10 * 1024);
    }




    /**
     * 封装http协议，并发送http请求
     */
    public void writeRequest(OutputStream os, Request request) throws IOException {
        StringBuffer protocol = new StringBuffer();

        //请求行
        protocol.append(request.method());
        protocol.append(SPACE);
        protocol.append(request.url().getFile());
        protocol.append(SPACE);
        protocol.append(VERSION);
        protocol.append(CRLF);

        //http请求头
        Map<String, String> headers = request.headers();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            protocol.append(entry.getKey());
            protocol.append(COLON);
            protocol.append(SPACE);
            protocol.append(entry.getValue());
            protocol.append(CRLF);
        }
        protocol.append(CRLF);

        //http请求体 如果存在
        RequestBody body = request.body();
        if (null != body) {
            protocol.append(body.body());
        }

        //发送http请求
        os.write(protocol.toString().getBytes());
        os.flush();
    }

    /**
     * 读取响应头
     */
    public Map<String, String> readHeaders(InputStream is) throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        while (true) {
            String line = readLine(is);
            //读取到空行 则下面的为body
            if (isEmptyLine(line)) {
                break;
            }
            int index = line.indexOf(":");
            if (index > 0) {
                String name = line.substring(0, index);
                // ": "移动两位到 总长度减去两个("\r\n")
                String value = line.substring(index + 2, line.length() - 2);
                headers.put(name, value);
            }
        }
        return headers;
    }

    /**
     * 按行读取http响应数据
     */
    public String readLine(InputStream is) throws IOException {
        try {
            byte b;
            boolean isMabeyEofLine = false;
            //标记
            byteBuffer.clear();
            byteBuffer.mark();
            while ((b = (byte) is.read()) != -1) {
                byteBuffer.put(b);
                // 读取到/r则记录，判断下一个字节是否为/n
                if (b == CR) {
                    isMabeyEofLine = true;
                } else if (isMabeyEofLine) {
                    //上一个字节是/r 并且本次读取到/n
                    if (b == LF) {
                        //获得目前读取的所有字节
                        byte[] lineBytes = new byte[byteBuffer.position()];
                        //返回标记位置
                        byteBuffer.reset();
                        byteBuffer.get(lineBytes);
                        //清空所有index并重新标记
                        byteBuffer.clear();
                        byteBuffer.mark();
                        String line = new String(lineBytes);
                        return line;
                    }
                    isMabeyEofLine = false;
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        throw new IOException("Response Read Line.");
    }

    boolean isEmptyLine(String line) {
        return line.equals("\r\n");
    }

    /**
     * 按字节数组读取http响应数据
     */
    public byte[] readBytes(InputStream is, int len) throws IOException {
        byte[] bytes = new byte[len];
        int readNum = 0;
        while (true) {
            readNum += is.read(bytes, readNum, len - readNum);
            //读取完毕
            if (readNum == len) {
                return bytes;
            }
        }
    }

    /**
     * 按块读取http响应数据
     * 分块传输编码（Chunked transfer encoding）是超文本传输协议（HTTP）中的一种数据传输机制，
     * 允许HTTP由应用服务器发送给客户端应用（ 通常是网页浏览器）的数据可以分成多个部分。
     * 分块传输编码只在HTTP协议1.1版本（HTTP/1.1）中提供。
     */
    public String readChunked(InputStream is) throws IOException {
        int len = -1;
        boolean isEmptyData = false;
        StringBuffer chunked = new StringBuffer();
        while (true) {
            //解析下一个chunk长度
            if (len < 0) {
                String line = readLine(is);
                line = line.substring(0, line.length() - 2);
                len = Integer.valueOf(line, 16);
                //chunk编码的数据最后一段为 0\r\n\r\n
                isEmptyData = len == 0;
            } else {
                //块长度不包括\r\n  所以+2将 \r\n 读走
                byte[] bytes = readBytes(is, len + 2);
                chunked.append(new String(bytes));
                len = -1;
                if (isEmptyData) {
                    return chunked.toString();
                }
            }
        }
    }






}
