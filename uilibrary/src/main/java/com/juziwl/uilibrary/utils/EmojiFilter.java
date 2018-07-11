package com.juziwl.uilibrary.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;


/**
 * @author Army
 * @version V_5.0.0
 * @date 2016/5/28
 * @description 表情过滤器
 */
public class EmojiFilter implements InputFilter {

    public EmojiFilter(Context context) {
        super();
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                               int dend) {
        // check black-list set
        if (containsEmoji(source.toString())) {
            return "";
        }
        return source;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            //如果不能匹配,则该字符是Emoji表情
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
//            Logger.d("codePoint = " + (int) codePoint);
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0) || (codePoint == 9) || (codePoint == 10) || (codePoint == 13)
                || ((codePoint >= 32) && (codePoint <= 55295)
                && !(codePoint >= 8598 && codePoint <= 8601)
                && !(codePoint >= 9193 && codePoint <= 9210)
                && !(codePoint >= 9723 && codePoint <= 9732)
                && !(codePoint >= 9800 && codePoint <= 9811)
                && !isContain(codePoint))
                || ((codePoint >= 57344) && (codePoint <= 65533) && codePoint != 65039);
    }

    public static final int[] INTEGERS = {
            169, 174, 8265, 8419, 8482, 8505, 8596, 8597, 8617, 8618, 8986, 8987, 9000, 9410, 9642, 9643,
            9654, 9664, 9742, 9745, 9748, 9749, 9752, 9760, 9786, 9824, 9827, 9829, 9830, 9832, 9855,
            9875, 9888, 9889, 9898, 9899, 9917, 9918, 9924, 9925, 9934, 9937, 9940, 9962, 9970, 9971,
            9973, 9978, 9981, 9986, 9989, 9992, 9994, 9996, 9999, 10002, 10004, 10006, 10024, 10035, 10036,
            10052, 10060, 10067, 10068, 10069, 10071, 10084, 10133, 10134, 10135, 10145, 10160, 10175, 10548,
            10549, 11013, 11014, 11015, 11036, 11088, 11093, 12336, 12349, 12951, 12953
    };

    private boolean isContain(int target) {
        for (int integer : INTEGERS) {
            if (target == integer) {
                return true;
            }
        }
        return false;
    }

}
