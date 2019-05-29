package com.wxq.commonlibrary.imageloader.test;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/29
 * desc:
 * version:1.0
 */
public class TestRunable implements Runnable  {
    private int ticket=1000;


    @Override
    public void run() {

        while (true){
            if (ticket>0) {
                System.out.println(Thread.currentThread().getName()+"...is saling,余票："+ticket--);
            }

        }
    }



      public static void main(String[] args){
        TestRunable runable=new TestRunable();
        new Thread(runable).start();
        new Thread(runable).start();
        new Thread(runable).start();
        new Thread(runable).start();
      }




}
