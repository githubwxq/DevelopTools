package com.wxq.commonlibrary.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式测试  https://www.runoob.com/regexp/regexp-syntax.html
 */
public class MatcherTest {


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String REGEX_EMOJI = "\\[s:[0-9]+\\]";
        String REGEX_WORLD = "\\[s:[0-9]+\\]";
        String str = "World!Java,World![s:100]";
        Pattern pattern = Pattern.compile("W(or)(ld!)"+"|("+   REGEX_EMOJI+")");

        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
             System.out.println(matcher.group(0));
             System.out.println(matcher.group(1));
             System.out.println(matcher.group(2));
             System.out.println(matcher.group(3));
            System.out.println(matcher.start(0));
            System.out.println(matcher.start(1));
            System.out.println(matcher.start(2));
            System.out.println(matcher.start(3));
            System.out.println("=====================");
        }

    }

}
