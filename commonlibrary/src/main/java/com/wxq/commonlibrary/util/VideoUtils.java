package com.wxq.commonlibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

/**
 * Created by 王晓清.
 * data 2019/1/28.
 * describe .
 */
public class VideoUtils {

//    private String ReadVideoTime(File source) {
//        Encoder encoder = new Encoder();
//        String length = "";
//        try {
//            MultimediaInfo m = encoder.getInfo(source);
//            long ls = m.getDuration()/1000;
//            int hour = (int) (ls/3600);
//            int minute = (int) (ls%3600)/60;
//            int second = (int) (ls-hour*3600-minute*60);
//            length = hour+"'"+minute+"''"+second+"'''";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return length;
//    }




    /**
     * 获取视频大小
     * @param source
     * @return
     */
    private String ReadVideoSize(File source) {
        FileChannel fc= null;
        String size = "";
        try {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(source);
            fc= fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null!=fc){
                try{
                    fc.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

}
