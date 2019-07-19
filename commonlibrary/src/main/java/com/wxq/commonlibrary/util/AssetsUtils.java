package com.wxq.commonlibrary.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetsUtils {

    /**
     * 获取assets中的json
     * @param fileName
     * @param context
     * @return
     */
    public static String getJson(String fileName, Context context){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
//
//作者：solocoder
//链接：https://juejin.im/post/5d2d8715f265da1b8608b926
//来源：掘金
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。