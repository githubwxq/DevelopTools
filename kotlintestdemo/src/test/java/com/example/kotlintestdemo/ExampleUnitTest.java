package com.example.kotlintestdemo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String content = "I am noob " +
                "from runoob.com.";

//        String pattern = ".*runoob.*";
//
//        boolean isMatch = Pattern.matches(pattern, content);
//        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
//


        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK? QT3000This order was placed for QT3000! OK? QT3000This order was placed for QT3000! OK? QT3000This order was placed for QT3000! OK? QT3000";
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find( )) {

            System.out.println("Found value:0 " + m.group(0) );
            System.out.println("Found value: 1" + m.group(1) );
            System.out.println("Found value: 2" + m.group(2) );
            System.out.println("Found value: 3" + m.group(3) );


        }

         String REGEX_EMOJI = "[s:[0-9]+]";
          String REGEX_TOPIC = "#[^#]+#";

        String  regex = "(" + REGEX_TOPIC + ")|(" + REGEX_EMOJI + ")";

        Pattern pattern2 = Pattern.compile(regex);

        Matcher matcher = pattern2.matcher("[s:3][s:4][s:5][s:55][s:355][s:553][s:355][s:35][s:53]");

        while (matcher.find()) {
            final String emojiStr = matcher.group(2);
            System.out.println("emojiStr" + emojiStr );
        }

    }
}