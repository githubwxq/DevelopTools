//package com.wxq.commonlibrary.channel;
//
//import android.content.Context;
//
////import com.juziwl.library.channel.data.Apk;
//import com.orhanobut.logger.Logger;
//
//import java.io.File;
//import java.nio.ByteBuffer;
//
///**
// * Created by Administrator on 2017/7/11 0011.
// */
//
//public class DNChannelHelper {
//
//    public static String getChannel(Context context) throws Exception {
//        String comment = "";
//        Apk apk = DNApkParser.parser(new File(context.getApplicationInfo().sourceDir));
//        if (apk.isV1()) {
//            comment = v1Channel(apk);
//            Logger.d("channel v1");
//        } else if (apk.isV2()) {
//            comment = v2Channel(apk);
//            Logger.d("channel v2");
//        } else {
//            Logger.d("channel 没有签名");
//        }
//        return comment;
//    }
//
//    static String v1Channel(Apk apk) throws Exception {
//        ByteBuffer data = apk.getEocd().getData();
//        short commentlen = data.getShort(Constants.EOCD_COMMENT_LEN_OFFSET);
//        if (commentlen == 0) {
//            return null;
//        }
//        byte[] commentBytes = new byte[commentlen];
//        data.position(Constants.EOCD_COMMENT_OFFSET);
//        data.get(commentBytes);
//        Logger.d("channel 获取v1签名的渠道信息");
//        return new String(commentBytes, "utf-8");
//
//    }
//
//    static String v2Channel(Apk apk) throws Exception {
//        ByteBuffer buffer = apk.getV2SignBlock().getPair().get(Constants.CHANNLE_ID);
//        Logger.d("channel 获取v2签名的渠道信息");
//        return new String(buffer.array(), "utf-8");
//    }
//}
