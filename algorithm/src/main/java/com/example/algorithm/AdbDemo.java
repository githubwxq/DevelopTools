package com.example.algorithm;

import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AdbDemo {

    public static void main(String[] a){

        Executors.newScheduledThreadPool(4).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("wwwwww");
            
                
                String adbHome="C:/Users/wxq/AppData/Local/Android/Sdk/platform-tools/";
//                String cmd=adbHome+"adb version";  adbdevices
                String cmd=adbHome+"adb -s 4d82c1ef shell input touchscreen tap 733 417";
                Process process;
                try {
                    process=Runtime.getRuntime().exec(cmd);
                    System.out.println(InputStream2String(process.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        },1000,10000,TimeUnit.MILLISECONDS);
    }






    public static String InputStream2String(InputStream inputStream){



        String result="";
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        try {
            String temp="";
            while ((temp=br.readLine())!=null){
                result+=temp+"/n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
