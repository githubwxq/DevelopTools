package com.wxq.commonlibrary.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : utils about string
 * </pre>
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the string is null or 0-length.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * Return whether the string is null or whitespace.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * Return whether the string is null or white space.
     *
     * @param s The string.
     * @return {@code true}: yes<br> {@code false}: no
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether string1 is equals to string2.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether string1 is equals to string2, ignoring case considerations..
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * Return {@code ""} if string equals null.
     *
     * @param s The string.
     * @return {@code ""} if string equals null
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * Return the length of string.
     *
     * @param s The string.
     * @return the length of string
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * Set the first letter of string upper.
     *
     * @param s The string.
     * @return the string with first letter upper.
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * Set the first letter of string lower.
     *
     * @param s The string.
     * @return the string with first letter lower.
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * Reverse the string.
     *
     * @param s The string.
     * @return the reverse string.
     */
    public static String reverse(final String s) {
        if (s == null) return "";
        int len = s.length();
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * Convert string to DBC.
     *
     * @param s The string.
     * @return the DBC string
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * Convert string to SBC.
     *
     * @param s The string.
     * @return the SBC string
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) return "";
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }



    /**
     * 方法功能：去除字符串空字符
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * 是否绝对的空
     *
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0 || str.equals("null"));
    }

    /**
     * 是否正常的字符串
     *
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }


    public static boolean isEquals(String actual, String expected) {
        return actual == expected
                || (actual == null ? expected == null : actual.equals(expected));
    }


    /**
     * bytes[]转换成Hex字符串,可用于URL转换，IP地址转换
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 字节转换成合适的单位
     *
     * @param value
     * @return
     */
    public static String prettyBytes(long value) {
        String args[] = {"B", "KB", "MB", "GB", "TB"};
        StringBuilder sb = new StringBuilder();
        int i;
        if (value < 1024L) {
            sb.append(String.valueOf(value));
            i = 0;
        } else if (value < 1048576L) {
            sb.append(String.format("%.1f", value / 1024.0));
            i = 1;
        } else if (value < 1073741824L) {
            sb.append(String.format("%.2f", value / 1048576.0));
            i = 2;
        } else if (value < 1099511627776L) {
            sb.append(String.format("%.3f", value / 1073741824.0));
            i = 3;
        } else {
            sb.append(String.format("%.4f", value / 1099511627776.0));
            i = 4;
        }
        sb.append(' ');
        sb.append(args[i]);
        return sb.toString();
    }


    /**
     * 判断View 的Text是否为空
     */
    public static boolean isEmpty(TextView textView) {
        if (textView != null && !TextUtils.isEmpty(textView.getText().toString().trim())) {
            return false;
        }
        return true;
    }


    /**
     * 非空判断处理和转换为String类型
     * dataFilter("aaa")  -> aaa
     * dataFilter(null)    ->"未知"
     * dataFilter("aaa","未知")  -> aaa
     * <p>
     * dataFilter(123.456  ,  2) -> 123.46
     * dataFilter(123.456  ,  0) -> 123
     * dataFilter(123.456  )    -> 123.46
     * <p>
     * dataFilter(56  )    -> "56"
     * <p>
     * dataFilter(true)        ->true
     *
     * @param source 主要对String,Integer,Double这三种类型进行处理
     * @param filter 要改的内容，这个要转换的内容可以不传，
     *               1如传的是String类型就会认为String为空时要转换的内容，不传为空时默认转换为未知，
     *               2如果传入的是intent类型，会认为double类型要保留的小数位数，
     *               3如是传入的是0会认为double要取整
     * @return 把内容转换为String返回
     */
    public static String dataFilter(Object source, Object filter) {
        try {
            if (source != null && !isBlank(source.toString())) {//数据源没有异常
                if (source instanceof String) {//String 处理
                    return source.toString().trim();
                } else if (source instanceof Double) {//小数处理，
                    BigDecimal bd = new BigDecimal(Double.parseDouble(source.toString()));
                    if (filter != null && filter instanceof Integer) {
                        if ((int) filter == 0) {
                            return String.valueOf((int) (bd.setScale(0, BigDecimal.ROUND_HALF_EVEN).doubleValue()));
                        } else {
                            return String.valueOf(bd.setScale(Math.abs((int) filter), BigDecimal.ROUND_HALF_EVEN).doubleValue());
                        }
                    }
                    return String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue());
                } else if (source instanceof Integer || source instanceof Boolean) {
                    return source.toString();
                } else {
                    return "未知";
                }
            } else if (filter != null) {//数据源异常 并且filter不为空
                return filter.toString();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "未知";
    }

    public static String dataFilter(Object source) {
        return dataFilter(source, null);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/10/12 14:07
     * <p>
     * 方法功能：返回指定长度的字符串
     */
    public static String isNullToConvert(String str, int maxLenght) {
        return isBlank(str) ? "未知" : str.substring(0, str.length() < maxLenght ? str.length() : maxLenght);

    }

    /**
     * 小数 四舍五入 19.0->19.0    返回Double
     */
    public static Double roundDouble(double val, int precision) {
        int resQ = (int) Math.round(val * Math.pow(10.0, precision));
        double res = resQ / Math.pow(10.0, precision);
        return res;
    }

    /**
     * 小数后两位
     *
     * @param val
     * @return
     */
    public static Double roundDouble(double val) {
        int resQ = (int) Math.round(val * Math.pow(10.0, 2));
        double res = resQ / Math.pow(10.0, 2);
        return res;
    }

    /**
     * 小数 四舍五入 19.0->19.0   返回字符串
     */

    public static String roundString(double val, int precision) {
        return String.valueOf(roundDouble(val, precision));

    }


    public static String getUrlToFileName(String url) {
        String res = null;
        if (url != null) {
            String[] ress = url.split("/");
            if (ress.length > 0) {
                res = ress[ress.length - 1];
            }
        }
        return res;
    }


    public static String getMoney(int money) {
        return money <= 0 ? "- -" : String.valueOf(money);
    }

    public static String getMessageCount(int count) {
        if (count > 0) {
            if (count >= 100) {
                return "99+";
            } else {
                return String.valueOf(count);
            }
        } else {
            return String.valueOf("");
        }
    }


    /**
     * /**
     * 匹配正则表达式
     *
     * @param str
     * @return
     * @throws PatternSyntaxException EditText 只能输入数字,字母和中文
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字-
        //String   regEx  =  "[^a-zA-Z0-9-\u4E00-\u9FA5]";
        String regEx = "[^a-zA-Z0-9\\-\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
/*
 * 5.阅读数：前端app不显示真实的阅读数，后台显示真实的阅读数；
     前端app显示的阅读数假设为N，真实阅读数为n，
     当 0 < N < 500 时，每次真实阅读量n+1时，N+（1~50）随机数
     当 500 < N < 100,000 时，每次真实阅读量n+1时，N+（1~100）随机数
     当 100,000 < N 时，每次真实阅读量n+1时，N+（1~5）随机数

    */

    public static String dealWithReadNumber(String realNumber) {
        int needNumber = 0, r = 0;
        Random random = new Random();
        if (!StringUtils.isEmpty(realNumber)) {
            int rNum = Integer.parseInt(realNumber);
            if (rNum > 0 && rNum < 500) {
                int max = 50, min = 1;
                r = random.nextInt(max) % (max - min + 1) + min;
                needNumber = rNum + r;
                return String.valueOf(needNumber);
            } else if (rNum >= 500 && rNum < 100000) {
                int max = 100, min = 1;
                r = random.nextInt(max) % (max - min + 1) + min;
                needNumber = rNum + r;
                return String.valueOf(needNumber);
            } else if (rNum > 100000) {
                int max = 5, min = 1;
                r = random.nextInt(max) % (max - min + 1) + min;
                needNumber = rNum + r;
                return String.valueOf(needNumber);
            }else {
                return "0";
            }
        }else{
            return "0";
        }
    }






}


//        isEmpty         : 判断字符串是否为 null 或长度为 0
//        isTrimEmpty     : 判断字符串是否为 null 或全为空格
//        isSpace         : 判断字符串是否为 null 或全为空白字符
//        equals          : 判断两字符串是否相等
//        equalsIgnoreCase: 判断两字符串忽略大小写是否相等
//        null2Length0    : null 转为长度为 0 的字符串
//        length          : 返回字符串长度
//        upperFirstLetter: 首字母大写
//        lowerFirstLetter: 首字母小写
//        reverse         : 反转字符串
//        toDBC           : 转化为半角字符
//        toSBC           : 转化为全角字符