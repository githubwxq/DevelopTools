package com.wxq.commonlibrary.channel;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class Constants {

    //注释内容的下标
    public  static final int  EOCD_COMMENT_OFFSET = 22;
    //注释长度的下标
    public  static final int  EOCD_COMMENT_LEN_OFFSET = 20;

    //v2签名id
    public static final int V2_ID = 0x7109871a;

    public static final int CHANNLE_ID = 0x7109871b;

    public static final int  EOCD_TAG = 0x06054b50;

    //最长注释内容
    public static final int EOCD_MAX_LEN = 0xffff;

    //v2签名的magic
    public static final byte[] APK_SIGNING_BLOCK_MAGIC =
            new byte[] {
                    0x41, 0x50, 0x4b, 0x20, 0x53, 0x69, 0x67, 0x20,
                    0x42, 0x6c, 0x6f, 0x63, 0x6b, 0x20, 0x34, 0x32,
            };
}
