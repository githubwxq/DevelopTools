package com.wxq.commonlibrary.util;

import android.util.Log;

import com.orhanobut.logger.LogAdapter;
import com.wxq.commonlibrary.constant.GlobalContent;

import java.io.File;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/05/27
 * @description 把日志写到文件里
 */
public class FileLogAdapter implements LogAdapter {

    private void writeFile(final String message) {

        ThreadExecutorManager.getInstance().runInThreadPool(new Runnable() {
            @Override
            public void run() {
                try {
                     String filePath = GlobalContent.logPath + TimeUtils.getNowString() + ".txt";
                    String content = String.format("%s\n\n%s\n\n", TimeUtils.getNowString(), message);
                    File file = new File(filePath);
                    if (file != null) {
                        if (file.length() > 30 * 1024 * 1024) { //每隔文件最多30m超过以后不再记入
                        } else {
                            FileUtils.appendToFile(content, filePath);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
        if (message != null) {
            if (!(message.startsWith("{") || message.startsWith("["))) {
                writeFile(message);
            }
        }
    }

    @Override
    public void e(String tag, String message) {
        Log.e(tag, message);
        writeFile(message);
    }

    @Override
    public void w(String tag, String message) {
        Log.w(tag, message);
        writeFile(message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
        writeFile(message);
    }

    @Override
    public void v(String tag, String message) {
        Log.v(tag, message);
        writeFile(message);
    }

    @Override
    public void wtf(String tag, String message) {
        Log.wtf(tag, message);
        writeFile(message);
    }
}
