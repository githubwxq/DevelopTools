package com.example.algorithm;

//import org.openqa.selenium.By;
//

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//
//import java.util.List;
//import io.appium.java_client.*;
//import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.offset.PointOption;
import sun.rmi.runtime.Log;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/09
 * desc: 测试appium自动化框架
 * version:1.0
 */
public class AppiumTestDemo {

    public static void main(String[] args) throws Exception {
        AndroidDriver driver;
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("automationName", "Appium");
        cap.setCapability("deviceName", "4d82c1ef");
        cap.setCapability("automationName", "Appium");
        cap.setCapability("platformName", "Android");
        //
//        cap.setCapability("udid", "192.168.56.101:5555"); //设备的udid (adb devices 查看到的)
        cap.setCapability("appPackage", "com.juziwl.exue_comprehensive");//被测app的包名
        cap.setCapability("appActivity", "com.juziwl.exue_comprehensive.ui.splash.activity.SplashActivity");//被测app的入口Activity名称
        cap.setCapability("unicodeKeyboard", "True"); //支持中文输入
//        cap.setCapability("noReset", "True");
        cap.setCapability("resetKeyboard", "True");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);//把以上配置传到appium服务端并连接手机

//设置全局隐式等待30S
//        driver.manage().timeouts().implicitlyWait(160, TimeUnit.SECONDS);
//
        Thread.sleep(5000);

        System.out.println("-------------------------------");

        TouchAction release = new TouchAction(driver).press(PointOption.point(1000, 500)).moveTo(PointOption.point(100, 500)).perform().release();
        release.wait(2000);
        TouchAction release2 =  new TouchAction(driver).press(PointOption.point(1000, 500)).moveTo(PointOption.point(100, 500)).perform().release();
        release2.wait(2000);



    }


}
