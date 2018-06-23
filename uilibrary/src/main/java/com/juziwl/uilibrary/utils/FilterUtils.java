package com.juziwl.uilibrary.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王晓清
 * @version V_1.0.0
 * @date 2017/11/20
 * @description
 */

public class FilterUtils {

    /**
     * 过滤掉常见特殊字符,常见的表情
     */
    public static void setEtFilter(EditText et , int number) {
        if (et == null) {
            return;
        }
        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };

        //过滤出字母数字中文其他什么的不要
        InputFilter otherFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
               return source.toString().replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
            }
        };




        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };


        et.setFilters(new InputFilter[]{specialCharFilter,otherFilter, new InputFilter.LengthFilter(number)});
    }




  /**
     * 只能输入字母和汉字
     */
    public static void setEngAndChinaEtFilter(EditText et , int number) {
        if (et == null) {
            return;
        }
        //过滤出字母数字中文其他什么的不要
        //过滤出字母中文其他什么的不要
        InputFilter engAndChineseFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                       int dstart, int dend) {
                return source.toString().replaceAll("[^(a-zA-Z\\u4e00-\\u9fa5)]", "");
            }
        };


        //特殊字符过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };


        et.setFilters(new InputFilter[]{specialCharFilter,engAndChineseFilter, new InputFilter.LengthFilter(number)});
    }











}
