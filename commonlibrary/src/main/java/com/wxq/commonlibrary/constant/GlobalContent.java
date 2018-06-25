package com.wxq.commonlibrary.constant;

import android.os.Environment;

/**
 * @author nat.xue
 * @date 2017/10/21
 * @description 全局常量
 */

public class GlobalContent {

    /**
     * 是否登录,家长版需要，暂时设置成true，默认是false
     */
    public static boolean isLogin = true;
    public static final String ENCODING = "UTF-8";
    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DevelopTools/";

    /**
     * 语音存放路径
     */
    public static String VOICEPATH;

    /**
     * 视频存放路径
     */
    public static String VIDEOPATH;

    /**
     * 课件保存路径
     */
    public static String COURSEWARE = filePath + "courseware/";


    public static String SAVE_PIC = filePath + "/savePic/";


    /**
     * 图片保存路径
     */
    public static String SAVEPICTURE = filePath + "savepictures/";

    /**
     * 选图片缓存和glide缓存的图片路径
     */
    public static String imgPath= filePath + "savepictures/";

    /**
     * 文件的存放地址
     */
    public static String SAVEFILEPATH= filePath + "savefile/";

    /**
     * log路径
     */
    public static String logPath=filePath + "log/";



}
