package com.juziwl.uilibrary.tools;

import com.wxq.commonlibrary.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者　　: 李坤
 * 创建时间:2016/10/12　14:03
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class EncodingUtils {

    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{2,4})");

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        if (StringUtils.isEmpty(unicode)) {
            return null;
        }
        Matcher m = reUnicode.matcher(unicode);
        StringBuffer sb = new StringBuffer(unicode.length());
        while (m.find()) {
            m.appendReplacement(sb,
                    Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();
        String hex;
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            hex = Integer.toHexString(c);
            if (hex.length() == 4) {
                unicode.append("\\u");
            } else if (hex.length() == 2) {
                unicode.append("\\u00");
            } else if (hex.length() == 3) {
                unicode.append("\\u0");
            } else if (hex.length() == 1) {
                unicode.append("\\u0");
            } else {
                unicode.append("\\u");
            }
            unicode.append(hex);
        }
        return unicode.toString();
    }
}
