package com.example.kotlintestdemo;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        assertEquals("com.example.kotlintestdemo", appContext.getPackageName());


            String content = "I am noob " +
                    "from runoob.com.";

            String pattern = ".*runoob.*";

            boolean isMatch = Pattern.matches(pattern, content);
            System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);


    }
}
